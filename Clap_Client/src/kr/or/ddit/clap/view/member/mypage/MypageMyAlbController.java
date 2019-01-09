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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.image.ImageView;
import kr.or.ddit.clap.main.LoginSession;
import kr.or.ddit.clap.service.musichistory.IMusicHistoryService;
import kr.or.ddit.clap.service.musicreview.IMusicReviewService;
import kr.or.ddit.clap.service.myalbum.IMyAlbumService;
import kr.or.ddit.clap.service.mypage.IMypageService;
import kr.or.ddit.clap.view.singer.singer.InsertSingerController;
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
		
		
	/*	int count=0;
		System.out.println(myAlbList.size());
			for (int i = 0; i < myAlbList.size(); i++) {
			if(myAlbList.get(i).getChBox().isSelected()) {
				if(myAlbList.get(i).getchBox1().isSelected())
				{
					System.out.println(myAlbList.get(i).getchBox1().isSelected());
					count++;
				la_Muscount.setText("선택"+count+"/"+ myAlbList.size());
				}
			}else {
				la_Muscount.setText("선택"+count+"/"+ myAlbList.size());
			}
		}*/
		
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
		col_MyAlbname.setId(index+"");
		col_MyAlbname.getCellData(index).length();
		//setGraphic(fild);
	/*	System.out.println("선택한 인덱스 : "+index);
		singerList.get(index);
		System.out.println("가수번호:" + vo.getSing_no());
		String singerNo =  vo.getSing_no(); //가수번호(PK)를 받아옴
		col_MyAlbname.get.setGraphic(chbox_main);*/
	
		}

	@FXML public void btn_del() {
			for (int i = 0; i < myAlbList.size(); i++) {
				if(myAlbList.get(i).getchBox1().isSelected()) {
					System.out.println(myAlbList.get(i));
				};
			

		
	}
	

	
	
}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
