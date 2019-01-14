package kr.or.ddit.clap.view.member.manage;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import kr.or.ddit.clap.service.mypage.IMypageService;
import kr.or.ddit.clap.view.singer.singer.ShowSingerDetailController;
import kr.or.ddit.clap.vo.member.MemberVO;
import kr.or.ddit.clap.vo.singer.SingerVO;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import javafx.scene.Parent;
import javafx.scene.control.Pagination;
import javafx.scene.control.TreeItem;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import javafx.scene.control.TreeTableColumn;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

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
	@FXML JFXButton btn_search;
	@FXML JFXTextField text_search;
	@FXML AnchorPane contents;
	@FXML AnchorPane main;

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

		for (int i = 0; i < memList.size(); i++) {
			if(memList.get(i).getMem_del_tf().equals("f")) {
				memList.get(i).setMem_del_tf("X");
			}else {
				memList.get(i).setMem_del_tf("O");
			}
		}
		
		for (int i = 0; i < memList.size(); i++) {
			if(memList.get(i).getMem_blacklist_tf().equals("f  ")) {
				memList.get(i).setMem_image("file:\\\\Sem-pc\\공유폴더\\Clap\\img\\userimg\\userf.png");
			}else {
				memList.get(i).setMem_image("file:\\\\Sem-pc\\공유폴더\\Clap\\img\\userimg\\usert.png");
			}
		}
		// 데이터 삽입
		TreeItem<MemberVO> root = new RecursiveTreeItem<>(memList, RecursiveTreeObject::getChildren);
		tbl_Member.setRoot(root);
		tbl_Member.setShowRoot(false);

		itemsForPage = 10; // 한페이지 보여줄 항목 수 설정

		paging();

		combo_search.getItems().addAll("이름", "아이디");
		combo_search.setValue(combo_search.getItems().get(0));

		
		btn_search.setOnAction(e ->{
			search();
		});
		
		tbl_Member.setOnMouseClicked(e ->{
			if (e.getClickCount()  > 1) {
				//int index = tbl_singer.getSelectionModel().getSelectedIndex();
				String memid = tbl_Member.getSelectionModel().getSelectedItem().getValue().getMem_id();
				
				
				try {
					//바뀔 화면(FXML)을 가져옴
					MemberDetailController.memid = memid;//가수번호를 변수로 넘겨줌
					
					FXMLLoader loader = new FXMLLoader(getClass().getResource("memditail.fxml"));// init실행됨
					Parent memetail= loader.load(); 
					
					MemberDetailController cotroller = loader.getController();
					cotroller.givePane(contents); 
					
					main.getChildren().removeAll();
					main.getChildren().setAll(memetail);
					
					
				} catch (IOException e1) {
					e1.printStackTrace();
				} 
			}
		});
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

//검색버튼 클릭
		
		


private void search() {
	try {
		MemberVO vo = new MemberVO();
		ObservableList<MemberVO> searchlist = FXCollections.observableArrayList();
		switch (combo_search.getValue()) {
		
		case "이름":
			vo.setMem_name(text_search.getText());
			searchlist = FXCollections.observableArrayList(ims.searchMemList(vo));
			break;
		case "아이디":
			vo.setMem_id(text_search.getText());
			searchlist = FXCollections.observableArrayList(ims.searchMemList(vo));
			break;
			
		default :
			break;
		} 
		
		memList = FXCollections.observableArrayList(searchlist); //검색조건에 맞는 리스트를 저장
		paging();
	}
	catch (Exception e) {
		e.printStackTrace();
	}
}
}
