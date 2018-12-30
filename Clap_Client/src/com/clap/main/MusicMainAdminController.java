package com.clap.main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.omg.CORBA.PRIVATE_MEMBER;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import com.jfoenix.controls.JFXButton;

public class MusicMainAdminController implements Initializable {

	@FXML
private MenuItem item_SigerManage;
	
	@FXML JFXButton btn_login;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	
	@FXML
	public void singerManage(ActionEvent event) {
		try {
			Parent singerManage = FXMLLoader.load(getClass().getResource("../view/singer/singer/ShowSingerList.fxml"));
			Scene scene = new Scene(singerManage);
			
			Stage primaryStage = (Stage) btn_login.getScene().getWindow(); //Stage객체를 받아옴
			primaryStage.setScene(scene);
		//System.out.println(item_SigerManage.getText());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
