package kr.or.ddit.clap.main;

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
import kr.or.ddit.clap.view.chartmenu.main.ChartMenuController;

import com.jfoenix.controls.JFXButton;
import javafx.scene.layout.AnchorPane;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;

public class MusicMainAdminController implements Initializable {

	@FXML private MenuItem item_SigerManage;
	
	@FXML JFXButton btn_login;

	@FXML AnchorPane header;
	
	@FXML AnchorPane contents;

	@FXML FontAwesomeIcon logo_Main;


	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	
	@FXML
	public void singerManage(ActionEvent event) { //가수관리를 클릭 했을 때.
		try {
			Parent singerManage = FXMLLoader.load(getClass().getResource("../view/singer/singer/ShowSingerList.fxml")); //바뀔 화면을 가져옴
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
	
	@FXML
	public void top50PageChange(ActionEvent event) { //차트메뉴에서 Top50차트 클릭 했을때 페이지 전환 이벤트
		try {
			Parent top50Page = FXMLLoader.load(getClass().getResource("../view/chartmenu/main/ChartMenu.fxml")); //바뀔 화면을 가져옴
			ChartMenuController.menuCount = 0;
			contents.getChildren().removeAll();
			contents.getChildren().setAll(top50Page);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@FXML
	public void genrePageChange(ActionEvent event) { //차트메뉴에서 장르별차트 클릭 했을때 페이지 전환 이벤트
		try {
			Parent genrePage = FXMLLoader.load(getClass().getResource("../view/chartmenu/main/ChartMenu.fxml")); //바뀔 화면을 가져옴
			ChartMenuController.menuCount = 1;
			contents.getChildren().removeAll();
			contents.getChildren().setAll(genrePage);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@FXML
	public void periodPageChange(ActionEvent event) { //차트메뉴에서 시대별차트 클릭 했을때 페이지 전환 이벤트
		try {
			Parent periodPage = FXMLLoader.load(getClass().getResource("../view/chartmenu/main/ChartMenu.fxml")); //바뀔 화면을 가져옴
			ChartMenuController.menuCount = 2;
			contents.getChildren().removeAll();
			contents.getChildren().setAll(periodPage);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@FXML
	public void musicvideoPageChange(ActionEvent event) { //차트메뉴에서 뮤직비디오차트 클릭 했을때 페이지 전환 이벤트
		try {
			Parent musicvideoPage = FXMLLoader.load(getClass().getResource("../view/chartmenu/main/ChartMenu.fxml")); //바뀔 화면을 가져옴
			ChartMenuController.menuCount = 3;
			contents.getChildren().removeAll();
			contents.getChildren().setAll( musicvideoPage);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
