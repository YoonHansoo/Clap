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

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import kr.or.ddit.clap.service.like.ILikeService;
import kr.or.ddit.clap.vo.music.MusicLikeVO;

public class LikeController implements Initializable{
	private Registry reg;
	private ILikeService ilks;
	

	@FXML JFXTreeTableView<MusicLikeVO> tbl_like;
	@FXML TreeTableColumn col_Checks;
	@FXML TreeTableColumn col_No;
	@FXML TreeTableColumn col_Img;
	@FXML TreeTableColumn col_MusInfo;
	@FXML TreeTableColumn col_Its;
	@FXML TreeTableColumn col_Alb;
	@FXML TreeTableColumn col_LikeIndate;
	@FXML TreeTableColumn col_Like;
	
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
		
		
		col_singerDebutMus.setCellValueFactory(
				param -> new SimpleStringProperty(param.getValue().getValue().getSing_debut_mus()));

		col_singerNo.setCellValueFactory(
				param -> new SimpleStringProperty(param.getValue().getValue().getSing_no()));
		
		try {
			singerList = FXCollections.observableArrayList(iss.selectListAll());
		} catch (RemoteException e) {
			System.out.println("에러");
			e.printStackTrace();
		}
		
		//데이터 삽입
		TreeItem<MusicLikeVO> root = new RecursiveTreeItem<>(singerList, RecursiveTreeObject::getChildren);
		tbl_singer.setRoot(root);
		tbl_singer.setShowRoot(false);
		
		itemsForPage=10; // 한페이지 보여줄 항목 수 설정
		
		paging();
		
		
		
	}
}
