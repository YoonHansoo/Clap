package kr.or.ddit.clap.view.member.profiles;

import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.image.ImageView;
import kr.or.ddit.clap.main.LoginSession;
import kr.or.ddit.clap.service.like.ILikeService;
import kr.or.ddit.clap.vo.music.MusicLikeVO;

public class LikeController implements Initializable{
	private Registry reg;
	private ILikeService ilks;
	

	@FXML JFXTreeTableView<MusicLikeVO> tbl_like;
	@FXML TreeTableColumn<MusicLikeVO, CheckBox> col_Checks;
	@FXML TreeTableColumn col_No;
	@FXML TreeTableColumn<MusicLikeVO, ImageView> col_Img;
	@FXML TreeTableColumn<MusicLikeVO, String> col_MusInfo;
	@FXML TreeTableColumn<MusicLikeVO, String> col_Its;
	@FXML TreeTableColumn<MusicLikeVO, String> col_Alb;
	@FXML TreeTableColumn<MusicLikeVO, String> col_LikeIndate;
	@FXML TreeTableColumn<MusicLikeVO, String> col_Like;
	private ObservableList<MusicLikeVO> likeList;
	
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
		
		col_Img
		.setCellValueFactory(param -> new SimpleObjectProperty<ImageView>(param.getValue().getValue().getImgView()));

		col_Its.setCellValueFactory(
				param -> new SimpleStringProperty(param.getValue().getValue().getSname()));

		col_LikeIndate.setCellValueFactory(
				param -> new SimpleStringProperty(param.getValue().getValue().getMus_like_date()));
		
		String user_id=LoginSession.session.getMem_id();
		MusicLikeVO vo = new MusicLikeVO();
		vo.setMem_id(user_id);
		try {
			likeList = FXCollections.observableArrayList(ilks.selectLike(vo));
		} catch (RemoteException e) {
			System.out.println("에러");
			e.printStackTrace();
		}
		
		//데이터 삽입
		TreeItem<MusicLikeVO> root = new RecursiveTreeItem<>(likeList, RecursiveTreeObject::getChildren);
		tbl_like.setRoot(root);
		tbl_like.setShowRoot(false);
		
	/*	itemsForPage=10; // 한페이지 보여줄 항목 수 설정
		*/
		//paging();
		
		
		
	}
}
