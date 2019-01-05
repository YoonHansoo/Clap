package kr.or.ddit.clap.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;
import kr.or.ddit.clap.vo.member.MemberVO;

/**
 * 프로그램을 실행시킬 Main Class
 * 
 * 
 * @author Hansoo
 *
 */

public class ClientMain extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		LoginSession ls = new LoginSession();

		LoginSession.session = new MemberVO(); 
		LoginSession.session.setMem_id("user1");
		LoginSession.session.setMem_pw("1234");
		LoginSession.session.setMem_gender("m");
		LoginSession.session.setMem_name("윤한수");
		LoginSession.session.setMem_auth("t");

			System.out.println("Start Clap!");
			FXMLLoader loader = new FXMLLoader(getClass().getResource("MusicMain.fxml"));
			ScrollPane root = loader.load();
			
			Scene scene = new Scene(root);
			primaryStage.setTitle("Clap:음악, 그리고 설레임");
			primaryStage.setScene(scene);
			primaryStage.show();
		
	}
	public static void main(String[] args) {
		launch(args);
	}
	
}
