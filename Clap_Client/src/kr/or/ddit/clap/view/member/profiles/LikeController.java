package kr.or.ddit.clap.view.member.profiles;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Pagination;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.image.ImageView;
import kr.or.ddit.clap.main.LoginSession;
import kr.or.ddit.clap.service.like.ILikeService;
import kr.or.ddit.clap.vo.member.LikeVO;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class LikeController implements Initializable {

	private Registry reg;
	private ILikeService ilks;

	private int no;
	@FXML
	JFXTreeTableView<LikeVO> tbl_like;
	@FXML
	TreeTableColumn<LikeVO, JFXCheckBox> col_Checks;
	@FXML
	TreeTableColumn<LikeVO, String> col_No;
	@FXML
	TreeTableColumn<LikeVO, ImageView> col_Img;
	@FXML
	TreeTableColumn<LikeVO, String> col_MusInfo;
	@FXML
	TreeTableColumn<LikeVO, String> col_Its;
	@FXML
	TreeTableColumn<LikeVO, String> col_Alb;
	@FXML
	TreeTableColumn<LikeVO, String> col_LikeIndate;
	@FXML
	TreeTableColumn<LikeVO, JFXButton> col_Like;
	private ObservableList<LikeVO> likeList, currentsingerList;
	private int from, to, itemsForPage, totalPageCnt;

	@FXML
	Pagination p_Paging;
	@FXML
	JFXCheckBox chbox_main;
	@FXML AnchorPane Head;
	@FXML AnchorPane contents;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			reg = LocateRegistry.getRegistry("localhost", 8888);
			ilks = (ILikeService) reg.lookup("like");
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
		col_Img.setCellValueFactory(param -> new SimpleObjectProperty<ImageView>(param.getValue().getValue().getImgView()));
		col_No.setCellValueFactory(param -> new SimpleStringProperty("" + no++));
		col_Its.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getValue().getSing_name()));
		col_MusInfo.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getValue().getMus_title()));
		col_Alb.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getValue().getAlb_name()));
		col_LikeIndate.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getValue().getlike_date().substring(0, 10)));
		col_Checks.setCellValueFactory(param -> new SimpleObjectProperty<JFXCheckBox>(param.getValue().getValue().getChBox()));
		col_Like.setCellValueFactory(param -> new SimpleObjectProperty<JFXButton>(param.getValue().getValue().getBtnLike()));

		String user_id = LoginSession.session.getMem_id();
		LikeVO vo = new LikeVO();
		vo.setMem_id(user_id);
		try {
			likeList = FXCollections.observableArrayList(ilks.selectMusLike(vo));
			
		} catch (RemoteException e) {
			System.out.println("에러");
			e.printStackTrace();
		}
		
		
		// 데이터 삽입

		TreeItem<LikeVO> root = new RecursiveTreeItem<>(likeList, RecursiveTreeObject::getChildren);
		tbl_like.setRoot(root);
		tbl_like.setShowRoot(false);

		itemsForPage = 10; // 한페이지 보여줄 항목 수 설정

		paging();

	}

	private void paging() {
		totalPageCnt = likeList.size() % itemsForPage == 0 ? likeList.size() / itemsForPage
				: likeList.size() / itemsForPage + 1;

		p_Paging.setPageCount(totalPageCnt); // 전체 페이지 수 설정

		p_Paging.setPageFactory((Integer pageIndex) -> {

			from = pageIndex * itemsForPage;
			to = from + itemsForPage - 1;

			TreeItem<LikeVO> root = new RecursiveTreeItem<>(getTableViewData(from, to),
					RecursiveTreeObject::getChildren);
			tbl_like.setRoot(root);
			tbl_like.setShowRoot(false);
			return tbl_like;
		});

	}

	private ObservableList<LikeVO> getTableViewData(int from, int to) {

		currentsingerList = FXCollections.observableArrayList(); //
		int totSize = likeList.size();
		for (int i = from; i <= to && i < totSize; i++) {

			currentsingerList.add(likeList.get(i));
		}

		return currentsingerList;
	}

	// 전체 선택 및 해제 메서드
	@FXML
	public void mainCheck() {
		no = 0;

		if (chbox_main.isSelected()) {
			for (int i = 0; i < likeList.size(); i++) {
				likeList.get(i).getchBox1().setSelected(true);
			}

		} else {
			for (int i = 0; i < likeList.size(); i++) {
				likeList.get(i).getchBox1().setSelected(false);
			}
		}
	}
	
	@FXML 
	public void btn_mus() {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("like.fxml"));
			Head.getChildren().removeAll();
			Head.getChildren().setAll(root);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML 
	public void btn_alb() {
		try {
			Parent root1 = FXMLLoader.load(getClass().getResource("alblike.fxml"));
			contents.getChildren().removeAll();
			contents.getChildren().setAll(root1);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML 
	public void btn_Rcm() {
		try {
			Parent root1 = FXMLLoader.load(getClass().getResource("rcmlike.fxml"));
			contents.getChildren().removeAll();
			contents.getChildren().setAll(root1);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML 
	public void btn_Its() {
		try {
			Parent root1 = FXMLLoader.load(getClass().getResource("itslike.fxml"));
			contents.getChildren().removeAll();
			contents.getChildren().setAll(root1);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
