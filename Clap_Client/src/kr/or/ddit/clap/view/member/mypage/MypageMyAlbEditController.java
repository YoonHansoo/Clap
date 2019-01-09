package kr.or.ddit.clap.view.member.mypage;

import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTextField;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import kr.or.ddit.clap.main.LoginSession;
import kr.or.ddit.clap.service.myalbum.IMyAlbumService;
import kr.or.ddit.clap.view.album.album.InsertAlbumController;
import kr.or.ddit.clap.vo.myalbum.MyAlbumVO;

public class MypageMyAlbEditController implements Initializable{
	private Registry reg;
	private IMyAlbumService imas;
	public static String myAlbName;//앨범명 변수로 넘겨줌
	public static String myAlbNo;//앨범번호 변수로 넘겨줌
	
	@FXML JFXTextField fild_Name;
	private ObservableList<MyAlbumVO> myAlbList;
	private MypageMyAlbController iAC;
	
	public void setcontroller(MypageMyAlbController iAC){
		this.iAC = iAC;
	}
	
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

		fild_Name.setText(myAlbName);
	}
	
	public void btn_Ok(){
		String user_id = LoginSession.session.getMem_id();
		try {
			myAlbList = FXCollections.observableArrayList(imas.myAlbumSelect(user_id));
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		for(int i=0; i<myAlbList.size(); i++) {
			if(myAlbList.get(i).getMyalb_name().equals(fild_Name.getText())){
				errMsg("중복되는 앨범명이 있습니다.");
				return;
			}else {
				MyAlbumVO vo = new MyAlbumVO();
				vo.setMem_id(user_id);
				vo.setMyalb_name(fild_Name.getText());
				vo.setMyalb_no(myAlbNo);
				try {
				int OK=imas.updateMyalb(vo);
				if(OK>0) {
					warning("앨범명 변경 완료");
				}
				} catch (RemoteException e) {
					e.printStackTrace();
				}
				
				
				Stage dialogStage = (Stage) fild_Name.getScene().getWindow();
				dialogStage.close();

			}
		}
	}
	public void btn_Cl(){
		
		//자식창 닫음
		Stage dialogStage = (Stage) fild_Name.getScene().getWindow();
		dialogStage.close();
		
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
