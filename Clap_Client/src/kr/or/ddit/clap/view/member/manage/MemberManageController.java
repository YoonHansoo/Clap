package kr.or.ddit.clap.view.member.manage;

import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import kr.or.ddit.clap.service.mypage.IMypageService;
import kr.or.ddit.clap.vo.member.MemberVO;
import kr.or.ddit.clap.vo.singer.SingerVO;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import javafx.scene.control.Pagination;
import javafx.scene.control.TreeItem;

import com.jfoenix.controls.JFXComboBox;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.image.ImageView;

public class MemberManageController implements Initializable{
	
	private Registry reg;
	private IMypageService ims;
	
	private ObservableList<MemberVO> memList, currentMemList;
	private int from, to, itemsForPage, totalPageCnt;
	
	@FXML JFXTreeTableView<MemberVO> tbl_Member;
	@FXML Pagination p_paging;
	@FXML JFXComboBox<String> combo_search;
	@FXML TreeTableColumn<MemberVO,String> col_MemdelTF;
	@FXML TreeTableColumn<MemberVO,String> col_MemIndate;
	@FXML TreeTableColumn<MemberVO,String> col_MemId;
	@FXML TreeTableColumn<MemberVO,ImageView> col_MemImg;
	@FXML TreeTableColumn<MemberVO,String> col_MemName;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			reg = LocateRegistry.getRegistry("localhost", 8888);
			ims = (IMypageService) reg.lookup("mypage");
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
		
		col_MemImg.setCellValueFactory(param -> new SimpleObjectProperty<ImageView>(param.getValue().getValue().getImgView()));

		col_MemdelTF.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getValue().getMem_del_tf()));

		col_MemIndate
				.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getValue().getMem_indate()));

		col_MemId.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getValue().getMem_id()));

		col_MemName.setCellValueFactory(
				param -> new SimpleStringProperty(param.getValue().getValue().getMem_name()));

		try {
			memList = FXCollections.observableArrayList(ims.selectAll());
		} catch (RemoteException e) {
			System.out.println("에러");
			e.printStackTrace();

		}

		// 데이터 삽입
		TreeItem<MemberVO> root = new RecursiveTreeItem<>(memList, RecursiveTreeObject::getChildren);
		tbl_Member.setRoot(root);
		tbl_Member.setShowRoot(false);

		itemsForPage = 10; // 한페이지 보여줄 항목 수 설정

		//paging();

		combo_search.getItems().addAll("이름", "아이디");
		combo_search.setValue(combo_search.getItems().get(0));

	}

	private void paging() {
		totalPageCnt = memList.size() % itemsForPage == 0 ? memList.size() / itemsForPage
				: memList.size() / itemsForPage + 1;
		
		p_paging.setPageCount(totalPageCnt); // 전체 페이지 수 설정
		
		p_paging.setPageFactory((Integer pageIndex) -> {
			
			from = pageIndex * itemsForPage;
			to = from + itemsForPage - 1;
			
			
			TreeItem<MemberVO> root = new RecursiveTreeItem<>(getTableViewData(from, to), RecursiveTreeObject::getChildren);
			tbl_Member.setRoot(root);
			tbl_Member.setShowRoot(false);
			return tbl_Member;
		});
	}
	
	//페이징에 맞는 데이터를 가져옴
private ObservableList<MemberVO> getTableViewData(int from, int to) {
		
	currentMemList = FXCollections.observableArrayList(); //
		int totSize = memList.size();
		for (int i = from; i <= to && i < totSize; i++) {
			
			currentMemList.add(memList.get(i));
		}
		
		return currentMemList;
	}

}
