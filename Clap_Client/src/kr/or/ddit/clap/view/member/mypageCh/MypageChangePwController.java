package kr.or.ddit.clap.view.member.mypageCh;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXPasswordField;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.control.Button;
import kr.or.ddit.clap.main.LoginSession;
import kr.or.ddit.clap.service.mypage.IMypageService;
import kr.or.ddit.clap.vo.member.MemberVO;

public class MypageChangePwController implements Initializable{
	
	static Stage mainDialog = new Stage(StageStyle.DECORATED);

	private Registry reg;
	private IMypageService ims;
	@FXML JFXPasswordField textF_NowPw;
    @FXML JFXPasswordField textF_NewPw;
	@FXML JFXPasswordField textF_NewPwCh;
	@FXML Button btn_Ok;
	@FXML Button btn_Cl;
	
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
		
		btn_Ok.setOnAction(e1->{
			String user_id = LoginSession.session.getMem_id();
			MemberVO vo = new MemberVO();
			vo.setMem_id(user_id);
			MemberVO vo2 = new MemberVO();

			try {
				vo2 = ims.select(vo);
				
			} catch (RemoteException e) {
				System.out.println("에러입니다");
				e.printStackTrace();
			}
			
			if(textF_NowPw.getText().equals(vo2.getMem_pw())) {
				if(textF_NewPw.getText().equals(textF_NewPwCh.getText())) {
					vo.setMem_pw(textF_NewPw.getText());
					try {
						int result = ims.updatePw(vo);
					} catch (RemoteException e) {
						e.printStackTrace();
					}
				}else if(!textF_NewPw.getText().equals(textF_NewPwCh.getText())) {
					warning("변경하실 패스워드를 정확히 입력해주세요.", "");
				}
				
				infoMsg("비밀번호 변경 완료", "");
				
			}else if(!textF_NowPw.getText().equals(vo2.getMem_pw())) {
				warning("잘못된 패스워드를 입력하셨습니다.", "");
				
			}			
		});//btn_ok
		
	btn_Cl.setOnAction(e2->{
		try {
			Parent root = FXMLLoader.load(getClass().getResource("../../../main/MusicMain.fxml"));
			Scene scene = new Scene(root);
			mainDialog.setTitle("모여서 각잡고 코딩 - clap");
			
			mainDialog.setScene(scene);
			mainDialog.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		});
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
