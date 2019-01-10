package kr.or.ddit.clap.view.member.mypage;

import java.io.IOException;
import java.net.URL;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TextFieldTreeTableCell;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TreeTableColumn.CellEditEvent;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import kr.or.ddit.clap.main.LoginSession;
import kr.or.ddit.clap.service.musichistory.IMusicHistoryService;
import kr.or.ddit.clap.service.musicreview.IMusicReviewService;
import kr.or.ddit.clap.service.myalbum.IMyAlbumService;
import kr.or.ddit.clap.service.mypage.IMypageService;
import kr.or.ddit.clap.view.album.album.InsertAlbumController;
import kr.or.ddit.clap.view.album.album.SelectSingerController;
import kr.or.ddit.clap.view.singer.singer.InsertSingerController;
import kr.or.ddit.clap.view.singer.singer.ShowSingerDetailController;
import kr.or.ddit.clap.vo.myalbum.MyAlbumVO;
import kr.or.ddit.clap.vo.singer.SingerVO;
import javafx.scene.layout.AnchorPane;

public class MypageMyAlbController implements Initializable{
	private static String user_id = LoginSession.session.getMem_id();
	private Registry reg;
	private IMyAlbumService imas;
	
	@FXML JFXTreeTableView<MyAlbumVO> tbl_Myalb;
	@FXML TreeTableColumn<MyAlbumVO,JFXCheckBox> col_Chbox;
	@FXML TreeTableColumn<MyAlbumVO,String> col_No;
	@FXML TreeTableColumn<MyAlbumVO,String> col_MyAlbname;
	@FXML TreeTableColumn<MyAlbumVO,String> col_MusCount;
	
	static Stage myalb = new Stage(StageStyle.DECORATED);
	String myAlbName;
	String myAlbNo;
	private int number;
	private ObservableList<MyAlbumVO> myAlbList, currentsingerList;
	@FXML JFXCheckBox chbox_main;
	@FXML Label la_Muscount;
	@FXML AnchorPane Head;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			reg = LocateRegistry.getRegistry("localhost", 8888);
			imas = (IMyAlbumService) reg.lookup("myalbum");
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
		myAlb();

		tbl_Myalb.setEditable(true);
		col_MyAlbname.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());	//클릭시 컬럼이 필드로 변경 값 저장시 엔터 키로 저장 시켜야 한다.
		col_MyAlbname.setOnEditCommit(new EventHandler<CellEditEvent<MyAlbumVO, String>>() {

			@Override
			public void handle(CellEditEvent<MyAlbumVO, String> e) {
				int index = tbl_Myalb.getSelectionModel().getSelectedIndex();
				e.getTreeTableView().getTreeItem(index).getValue().setMyalb_name(e.getNewValue());
			}

		});

	}

	public void myAlb() {
		// 마이앨범
	
		try {
			myAlbList = FXCollections.observableArrayList(imas.myAlbumSelect(user_id));
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		//col_No.setCellValueFactory(param -> new SimpleStringProperty(""+number++));
		col_Chbox.setCellValueFactory(param -> new SimpleObjectProperty<JFXCheckBox>(param.getValue().getValue().getChBox()));
		col_MyAlbname.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getValue().getMyalb_name()));
		col_MusCount.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getValue().getMus_count()));


		TreeItem<MyAlbumVO> root3 = new RecursiveTreeItem<>(myAlbList, RecursiveTreeObject::getChildren);
		tbl_Myalb.setRoot(root3);
		tbl_Myalb.setShowRoot(false);
	}
	
	@FXML
	public void mainCheck() {
		if (chbox_main.isSelected()) {
			for (int i = 0; i < myAlbList.size(); i++) {
				myAlbList.get(i).getchBox1().setSelected(true);
			}

		} else {
			for (int i = 0; i < myAlbList.size(); i++) {
				myAlbList.get(i).getchBox1().setSelected(false);
			}
		}
	}
	
	public void chBoxCount() {
	
	}
	@FXML public void btn_edit() {}
/*	@FXML public void btn_edit() throws IOException {
		
		TextField fild= new TextField(); 
		int index = tbl_Myalb.getSelectionModel().getSelectedIndex();
		String myAlbName=col_MyAlbname.getCellData(index).toString();
	
		MypageMyAlbEditController.myAlbName = myAlbName; //앨범명 변수로 넘겨줌
		MypageMyAlbEditController.myAlbNo = myAlbNo;//앨범번호 변수로 넘겨줌
		InsertSinger();
		}*/
	
	
	

	@FXML public void btn_del() {
			for (int i = 0; i < myAlbList.size(); i++) {
				if(myAlbList.get(i).getchBox1().isSelected()) {
					
					String user_id = LoginSession.session.getMem_id();
					MyAlbumVO vo = new MyAlbumVO();
					vo.setMem_id(user_id);
					vo.setMyalb_no(myAlbList.get(i).getMyalb_no());
					try {
						 int ok=imas.deleteMyalb(vo);
						 if(ok>0) {
						 infoMsg("삭제 완료", "");
						 }
						 myAlb();
					} catch (RemoteException e) {
						e.printStackTrace();
					}
					
			}
			;
		}
	}

	public void btn_Cl() {
		Stage dialogStage = (Stage) chbox_main.getScene().getWindow();
		dialogStage.close();
	}

	public void btn_Ok() {
		;
		int index = tbl_Myalb.getSelectionModel().getSelectedIndex();
		if (index <= 0) {
			
			Stage dialogStage = (Stage) chbox_main.getScene().getWindow();
			dialogStage.close();return;
			
		}
		String myAlbName = col_MyAlbname.getCellData(index).toString();
		String myAlbNo = myAlbList.get(index).getMyalb_no();

		try {
			myAlbList = FXCollections.observableArrayList(imas.myAlbumSelect(user_id));
			for (int i = 0; i < myAlbList.size(); i++) {
				if (myAlbList.get(i).getMyalb_name().equals(myAlbName)) {
					errMsg("중복되는 앨범명이 있습니다.");
					return;
				}

			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}

		MyAlbumVO vo = new MyAlbumVO();
		vo.setMem_id(user_id);
		vo.setMyalb_name(myAlbName);
		vo.setMyalb_no(myAlbNo);
		try {
			int OK = imas.updateMyalb(vo);
			if (OK > 0) {
				warning("앨범명 변경 완료");
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}

		Stage dialogStage = (Stage) chbox_main.getScene().getWindow();
		dialogStage.close();
		

	}

	/*
	 * public void InsertSinger() throws IOException { FXMLLoader loader = new
	 * FXMLLoader(getClass().getResource("myalbedit.fxml")); Parent myEdit=
	 * loader.load(); MypageMyAlbEditController cotroller = loader.getController();
	 * 
	 * cotroller.setcontroller(this);
	 * 
	 * Stage stage = new Stage(); Scene scene = new Scene(myEdit);
	 * stage.setScene(scene); stage.initModality(Modality.APPLICATION_MODAL); Stage
	 * primaryStage = (Stage)Head.getScene().getWindow();
	 * stage.initOwner(primaryStage); stage.show();
	 * 
	 * 
	 * }
	 */

	public void infoMsg(String headerText, String msg) {
		Alert infoAlert = new Alert(AlertType.INFORMATION);
		infoAlert.setTitle("정보 확인");
	infoAlert.setHeaderText(headerText);
	infoAlert.setContentText(msg);
	infoAlert.showAndWait();
}
public void errMsg(String msg) {
	Alert errAlert = new Alert(AlertType.ERROR);
	errAlert.setTitle("중복 검사");
	errAlert.setHeaderText("중복 검사");
	errAlert.setContentText(msg);
	errAlert.showAndWait();
}

public void warning(String msg) {
	Alert alertWarning = new Alert(AlertType.WARNING);
	alertWarning.setTitle("완료");
	alertWarning.setContentText(msg);
	alertWarning.showAndWait();
}
	
}
	
	
	
	
	
	

