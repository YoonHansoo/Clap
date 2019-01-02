package kr.or.ddit.clap.view.join;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 * 회원가입 창 컨트롤러.
 * @author Kyunghun
 *
 */
public class JoinController implements Initializable{
	
	@FXML JFXTextField txt_id;
	@FXML JFXTextField txt_pw;
	@FXML JFXTextField txt_pwCheck;
	@FXML JFXTextField txt_birth;
	@FXML JFXTextField txt_tel;
	@FXML JFXTextField txt_email;
	
	@FXML JFXButton btn_ok;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}

	public void join() throws UnsupportedEncodingException, NoSuchAlgorithmException, GeneralSecurityException {
		AES256Util aes = new AES256Util();
		
		String encryptedPw = aes.encrypt(txt_pw.getText());
		String decryptedPw = aes.decrypt(encryptedPw);
		
		String encryptedPwCheck = aes.encrypt(txt_pwCheck.getText());
		String decryptedPwCheck = aes.decrypt(encryptedPwCheck);
		
		System.out.println("아이디:"+txt_id.getText());
		System.out.println("비번:"+txt_pw.getText());
		System.out.println("비번확인:"+txt_pwCheck.getText());
		System.out.println("암호화 비번:"+encryptedPw);
		System.out.println("암호화 비번확인:"+encryptedPwCheck);
		
		System.out.println("생일:"+txt_birth.getText());
		System.out.println("번호:"+txt_tel.getText());
		System.out.println("이메일:"+txt_email.getText());
	}
	
}
