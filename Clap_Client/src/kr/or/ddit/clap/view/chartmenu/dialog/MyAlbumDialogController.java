package kr.or.ddit.clap.view.chartmenu.dialog;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import kr.or.ddit.clap.main.LoginSession;
import kr.or.ddit.clap.service.musichistory.IMusicHistoryService;
import kr.or.ddit.clap.service.myalbum.IMyAlbumService;
import kr.or.ddit.clap.vo.myalbum.MyAlbumVO;

public class MyAlbumDialogController implements Initializable{
	
	@FXML JFXTextField tf_albumName;
	@FXML JFXButton btn_ok;
	@FXML JFXTreeTableView<MyAlbumVO> t_table;
	@FXML TreeTableColumn<MyAlbumVO, String> tcol_album;
	
	private ObservableList<MyAlbumVO> albumList;
	private Registry reg;
	private IMyAlbumService imas;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		try {
			reg = LocateRegistry.getRegistry("localhost", 8888);
			imas = (IMyAlbumService) reg.lookup("myalbum");
			String id = LoginSession.session.getMem_id();
			albumList = FXCollections.observableArrayList(imas.myAlbumSelect(id));
			
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		} 
		
		tcol_album.setCellValueFactory( param -> 
			new SimpleStringProperty(param.getValue().getValue().getMyalb_name())
		);
	
		TreeItem<MyAlbumVO> root = new RecursiveTreeItem<>(albumList, RecursiveTreeObject::getChildren);
		t_table.setRoot(root);
		t_table.setShowRoot(false);
		
	}

}
