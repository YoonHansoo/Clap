package kr.or.ddit.clap.view.member.mypageCh;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import kr.or.ddit.clap.main.LoginSession;
import kr.or.ddit.clap.service.mypage.IMypageService;
import kr.or.ddit.clap.vo.member.MemberVO;
import kr.or.ddit.clap.vo.singer.SingerVO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

public class MypageChangeController implements Initializable {
	private Registry reg;
	private IMypageService ims;
	@FXML Label id;
	@FXML Label ph;
	static Stage tel = new Stage(StageStyle.DECORATED);
	String tell="" ;
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
		
		String user_id = LoginSession.session.getMem_id();
		id.setText(user_id);
		MemberVO vo = new MemberVO();
		vo.setMem_id(user_id);
		MemberVO vo2 = new MemberVO();
		
		try {
			vo2 = ims.select(vo);
		} catch (RemoteException e) {
			System.out.println("에러입니다");
			e.printStackTrace();
		}
		
		ph.setText(vo2.getMem_tel().substring(0, 3)+"-"+vo2.getMem_tel().substring(4, 8));
		
		
	}
	
	public void btn_telCh() throws IOException { //변경하기 클릭시
		Parent root = FXMLLoader.load(getClass().getResource("telCh.fxml"));
		Scene scene = new Scene(root);
		tel.setTitle("모여서 각잡고 코딩 - clap");
		tel.setScene(scene);
		tel.show();
		ComboBox<String> combo = (ComboBox<String>) root.lookup("#combo_tel");
		
		combo.getItems().addAll("010","016","018","011","017","019");
		combo.setValue(combo.getItems().get(0));
		combo.setOnAction(e->{combo.getValue().toString();
		});
		TextField tel1= (TextField) root.lookup("#tel1");
		TextField tel2= (TextField) root.lookup("#tel2");
	
		
		Button btn_ok =(Button) root.lookup("#btn_ok");
		btn_ok.setOnAction(ee->{
			if( !tel1.getText().matches("^[0-9]*$") || !tel2.getText().matches("^[0-9]*$") ||
					tel1.getText().length()>4|| tel2.getText().length()>4) {
				warning( "휴대폰 번호가 잘못되었습니다","다시입력해주세요");
				combo.setValue("");
				tel1.clear();
				tel2.clear();
			}
			
			tell += combo.getValue().toString();
			tell += tel1.getText();
			tell += tel2.getText();
			
			System.out.println(tell);
	
		
	});
	}
	
	public void errMsg(String headerText, String msg) {
		Alert errAlert = new Alert(AlertType.ERROR);
		errAlert.setTitle("오류");
		errAlert.setHeaderText(headerText);
		errAlert.setContentText(msg);
		errAlert.showAndWait();
	}

	public void infoMsg(String headerText, String msg) {
		Alert infoAlert = new Alert(AlertType.INFORMATION);
		infoAlert.setTitle("정보 확인");
		infoAlert.setHeaderText(headerText);
		infoAlert.setContentText(msg);
		infoAlert.showAndWait();
	}
	public void warning(String headerText, String msg) {
		 Alert alertWarning = new Alert(AlertType.WARNING);
		 alertWarning.setTitle("warning");
		 alertWarning.setHeaderText(headerText);
		 alertWarning.setContentText(msg);
		 alertWarning.showAndWait();
	}
}
