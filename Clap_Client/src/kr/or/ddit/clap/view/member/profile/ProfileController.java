package kr.or.ddit.clap.view.member.profile;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.event.ActionEvent;import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import kr.or.ddit.clap.main.LoginSession;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ProfileController implements Initializable {
	public Stage primaryStage;
	@FXML Button btn_sh;
	@FXML ImageView img;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		
		
		btn_sh.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				 FileChooser fileChooser = new FileChooser();

		         // 확장자별로 파일 구분하는 필터 등록하기
		         fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Text Files", "*,txt"),
		               new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"),
		               new ExtensionFilter("Audio Files", "*.wav", "*.mp3"), new ExtensionFilter("All Files", "*.*"));
		         
		         //Dialog창에서 '열기'버튼을 누르면선택한 파일 정보기 반환되고
		         //'취소'버튼을 누르면 null이 반환된다.
		         File selectFile = fileChooser.showOpenDialog(primaryStage);
		         if(selectFile!=null) {
		            //이영역에서 파일내용을 일거오는 작업을 수행한다.
		        	 LoginSession.session.setMem_image(selectFile.getPath());
		         }
			}
		      });
		}
	
	
		
	
}


