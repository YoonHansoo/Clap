package kr.or.ddit.clap.main;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class LoginController implements Initializable{
	
	@FXML JFXTextField txt_id;
	@FXML JFXPasswordField txt_pw;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	
	public void login() {
		System.out.println(txt_id.getText());
		System.out.println(txt_pw.getText());
	}

}
