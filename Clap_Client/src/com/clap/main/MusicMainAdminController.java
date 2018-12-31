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
import javafx.scene.control.ScrollPane;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import com.jfoenix.controls.JFXButton;
import javafx.scene.layout.AnchorPane;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;

public class MusicMainAdminController implements Initializable {

	@FXML
private MenuItem item_SigerManage;
	
	@FXML JFXButton btn_login;

	@FXML AnchorPane header;
	
	@FXML AnchorPane contents;

	@FXML FontAwesomeIcon logo_Main;


	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	
	@FXML
	public void singerManage(ActionEvent event) {
		try {
			Parent singerManage = FXMLLoader.load(getClass().getResource("../view/singer/singer/ShowSingerList.fxml"));
			contents.getChildren().removeAll();
			contents.getChildren().setAll(singerManage);
			
		//System.out.println(item_SigerManage.getText());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	

	@FXML public void logo_Main() throws IOException {
		if(LoginSession.session.getMem_auth().equals("t")) {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("MusicMainAdmin.fxml"));
			ScrollPane root = loader.load();
			
			Scene scene = new Scene(root);
			Stage primaryStage = (Stage) btn_login.getScene().getWindow();
			primaryStage.setScene(scene);
		}
		
		//로그인은 안했거나/ 일반 사용자일 경우
		else {
			System.out.println("일반사용자 로그인 또는 비회원");
			FXMLLoader loader = new FXMLLoader(getClass().getResource(".fxml"));
			ScrollPane root = loader.load();
			
			Scene scene = new Scene(root);
			Stage primaryStage = (Stage) btn_login.getScene().getWindow();
			primaryStage.setScene(scene);
		}
		
	}
	
}
