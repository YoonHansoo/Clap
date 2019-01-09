package kr.or.ddit.clap.view.member.mypage;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTextField;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class MypageMyAlbEditController implements Initializable{
	public static String myAlbName;// 파라미터로 받은 선택한 앨범명
	@FXML JFXTextField fild_Name;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		fild_Name.setText(myAlbName);
	}

}
