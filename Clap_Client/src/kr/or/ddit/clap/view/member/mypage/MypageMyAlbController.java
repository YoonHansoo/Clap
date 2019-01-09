package kr.or.ddit.clap.view.member.mypage;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;

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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import kr.or.ddit.clap.main.LoginSession;
import kr.or.ddit.clap.service.musichistory.IMusicHistoryService;
import kr.or.ddit.clap.service.musicreview.IMusicReviewService;
import kr.or.ddit.clap.service.myalbum.IMyAlbumService;
import kr.or.ddit.clap.service.mypage.IMypageService;
import kr.or.ddit.clap.view.singer.singer.InsertSingerController;
import kr.or.ddit.clap.view.singer.singer.ShowSingerDetailController;
import kr.or.ddit.clap.vo.myalbum.MyAlbumVO;
import kr.or.ddit.clap.vo.singer.SingerVO;

public class MypageMyAlbController implements Initializable{

	private Registry reg;
	private IMyAlbumService imas;
	
	@FXML JFXTreeTableView<MyAlbumVO> tbl_Myalb;
	@FXML TreeTableColumn<MyAlbumVO,JFXCheckBox> col_Chbox;
	@FXML TreeTableColumn<MyAlbumVO,String> col_No;
	@FXML TreeTableColumn<MyAlbumVO,String> col_MyAlbname;
	@FXML TreeTableColumn<MyAlbumVO,String> col_MusCount;
	
	static Stage myalb = new Stage(StageStyle.DECORATED);
	
	private int number;
	private ObservableList<MyAlbumVO> myAlbList, currentsingerList;
	@FXML JFXCheckBox chbox_main;
	@FXML Label la_Muscount;


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
		
		int index = tbl_Myalb.getSelectionModel().getSelectedIndex();
		col_MusCount.setOnEditCommit(e->{
			e.getTreeTableView().getTreeItem(e.getTreeTablePosition().getRow());
		});
		
	}

	private void myAlb() {
		// 마이앨범
		String user_id = LoginSession.session.getMem_id();
		try {
			myAlbList = FXCollections.observableArrayList(imas.myAlbumSelect(user_id));
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		col_No.setCellValueFactory(param -> new SimpleStringProperty(""+number++));
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

	@FXML public void btn_edit() {
		TextField fild= new TextField(); 
		int index = tbl_Myalb.getSelectionModel().getSelectedIndex();
		String name=col_MyAlbname.getCellData(index).toString();
		MypageMyAlbEditController.myAlbName = name;//가수번호를 변수로 넘겨줌
		InsertSinger();
	
	
		}

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
					
					
				};
			

		
	}
	}
	
public void InsertSinger() {
	
	try {
		//바뀔 화면(FXML)을 가져옴
		
		Parent root = FXMLLoader.load(getClass().getResource("myalbedit.fxml"));
		Scene scene = new Scene(root);
		myalb.setTitle("모여서 각잡고 코딩 - clap");

		myalb.setScene(scene);
		myalb.show();
		
		
	} catch (IOException e1) {
		e1.printStackTrace();
	} 
	
	
}
			
			
public void infoMsg(String headerText, String msg) {
	Alert infoAlert = new Alert(AlertType.INFORMATION);
	infoAlert.setTitle("정보 확인");
	infoAlert.setHeaderText(headerText);
	infoAlert.setContentText(msg);
	infoAlert.showAndWait();
}

	
	
}
	
	
	
	
	
	
	
	
	
/*	
	tbl_singer.setOnMouseClicked(e ->{
		if (e.getClickCount()  > 1) {
			int index = tbl_singer.getSelectionModel().getSelectedIndex();
			System.out.println("선택한 인덱스 : "+index);
			SingerVO vo = singerList.get(index);
			System.out.println("가수번호:" + vo.getSing_no());
			String singerNo =  vo.getSing_no(); //가수번호(PK)를 받아옴
			
			
			try {
				//바뀔 화면(FXML)을 가져옴

				ShowSingerDetailController.singerNo = singerNo;//가수번호를 변수로 넘겨줌
				
				FXMLLoader loader = new FXMLLoader(getClass().getResource("SingerDetail.fxml"));// init실행됨
				Parent singerDetail= loader.load(); 
				
				ShowSingerDetailController cotroller = loader.getController();
				cotroller.givePane(contents); 
				
				main.getChildren().removeAll();
				main.getChildren().setAll(singerDetail);
				
				
			} catch (IOException e1) {
				e1.printStackTrace();
			} 
		}
	});
	
	*/
	
	

	
	

	
	
	
	
	

