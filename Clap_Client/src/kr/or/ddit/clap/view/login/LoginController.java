package kr.or.ddit.clap.view.login;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;
import kr.or.ddit.clap.main.MusicMainController;
import kr.or.ddit.clap.service.login.ILoginService;
import kr.or.ddit.clap.view.join.AES256Util;

/**
 * 로그인창 컨트롤러.
 * @author Kyunghun
 *
 */
public class LoginController implements Initializable{
	
	MusicMainController mmc = new MusicMainController();
	private ILoginService ils;
	private Registry reg;
	
	@FXML JFXTextField txt_id;
	@FXML JFXPasswordField txt_pw;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			reg = LocateRegistry.getRegistry("localhost", 8888);
			ils = (ILoginService) reg.lookup("login");
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
	}
	
	public void login() throws UnsupportedEncodingException, NoSuchAlgorithmException, GeneralSecurityException{
		AES256Util aes = new AES256Util();
		
		String encryptedPw = ""; // 암호화된 pw
		encryptedPw = aes.encrypt(txt_pw.getText());
		
		String decryptedPw = ""; // 복호화시킨 pw
		decryptedPw = aes.decrypt(encryptedPw);
	
		// 아이디 확인
		Boolean idCheck = false;
		try {
			idCheck = ils.idCheck(txt_id.getText());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		// 아이디가 존재하지 않을 때
//		if(vo == null) {
//			System.out.println("존재하지 않는 ID입니다.");
//		}
		
		// 비밀번호 확인
		
		// 비밀번호가 일치하지 않을 때
		if(false) {
			System.out.println("비밀번호를 정확히 입력해주세요.");
			// 3회 이상 실패하면 captcha.
		}
		
		
		
		System.out.println(txt_id.getText());
		System.out.println(encryptedPw);
		System.out.println(decryptedPw);
		
		MusicMainController.loginDialog.close();
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../../main/MusicMain.fxml"));
		ScrollPane root = null;
		try {
			root = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// 로그인 mem 버튼 변경
		mmc.btn_mem.setVisible(true);
		mmc.btn_join.setVisible(false);
		mmc.btn_login.setVisible(false);
		
		Scene scene = new Scene(root);
		Stage primaryStage = (Stage) txt_id.getScene().getWindow();
		primaryStage.setScene(scene);
		
		
		
		
	}

}
