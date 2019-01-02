package kr.or.ddit.clap.view.login;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import kr.or.ddit.clap.main.AES256Util;

/**
 * 로그인창 컨트롤러.
 * @author Kyunghun
 *
 */
public class LoginController implements Initializable{
	
	@FXML JFXTextField txt_id;
	@FXML JFXPasswordField txt_pw;
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	
	public void login() throws UnsupportedEncodingException, NoSuchAlgorithmException, GeneralSecurityException {
		AES256Util aes = new AES256Util();
		
		String encryptedPw = ""; // 암호화된 pw
		encryptedPw = aes.encrypt(txt_pw.getText());
		
		String decryptedPw = ""; // 복호화시킨 pw
		decryptedPw = aes.decrypt(encryptedPw);
				
		System.out.println(txt_id.getText());
		System.out.println(encryptedPw);
		System.out.println(decryptedPw);
		
	}

}
