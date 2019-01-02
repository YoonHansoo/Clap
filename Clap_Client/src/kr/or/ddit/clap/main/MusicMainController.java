package kr.or.ddit.clap.main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * 메인화면의 fxml 컨트롤러.
 * @author Kyunghun
 *
 */
public class MusicMainController implements Initializable{
	
	static Stage loginDialog = new Stage(StageStyle.DECORATED);
	static Stage joinDialog = new Stage(StageStyle.DECORATED);
	static Stage buyTicketDialog = new Stage(StageStyle.DECORATED);
	
	/**
	 * 현지
	 */
	static Stage mypageDialog = new Stage(StageStyle.DECORATED);
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	
	public void login() throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("../view/login/Login.fxml"));
		Scene scene = new Scene(root);
		loginDialog.setTitle("모여서 각잡고 코딩 - clap");
		if(loginDialog.getModality() == null) {
			loginDialog.initModality(Modality.APPLICATION_MODAL);			
		}
		
		loginDialog.setScene(scene);
		loginDialog.show();
	}
	
	public void join() throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("../view/join/Join.fxml"));
		Scene scene = new Scene(root);
		joinDialog.setTitle("모여서 각잡고 코딩 - clap");
		if(joinDialog.getModality() == null) {
			joinDialog.initModality(Modality.APPLICATION_MODAL);			
		}
		
		joinDialog.setScene(scene);
		joinDialog.show();
	}
	
	public void mypage() throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("../view/member/mypage/Mypage.fxml"));
		Scene scene = new Scene(root);
		mypageDialog.setTitle("모여서 각잡고 코딩 - clap");
		
		mypageDialog.setScene(scene);
		mypageDialog.show();
	}
	
	public void buyTicket() throws IOException {
//		Parent root = FXMLLoader.load(getClass().getResource("../view/member/mypage/Mypage.fxml"));
//		Scene scene = new Scene(root);
//		buyTicketDialog.setTitle("모여서 각잡고 코딩 - clap");
//		
//		buyTicketDialog.setScene(scene);
//		buyTicketDialog.show();
		System.out.println("이용권 구매 화면");
	}

}
