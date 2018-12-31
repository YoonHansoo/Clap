package kr.or.ddit.clap.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
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
		
		//임시 로그인
		LoginSession.session = new MemberVO(); 
		LoginSession.session.setMem_id("admin2");
		LoginSession.session.setMem_pw("4321");
		LoginSession.session.setMem_gender("m");
		LoginSession.session.setMem_name("윤한수");
		LoginSession.session.setMem_auth("t");
		
		
		
		//로그인한 Id가 관리자일 경우
		if(LoginSession.session.getMem_auth().equals("t")) {
			System.out.println("관리자로 로그인");
			FXMLLoader loader = new FXMLLoader(getClass().getResource("MusicMainAdmin.fxml"));
			ScrollPane root = loader.load();
			
			Scene scene = new Scene(root);
		//	scene.getStylesheets().add(getClass().getResource("MainClient.css").toString());
			primaryStage.setTitle("Clap:음악, 그리고 설레임");
			primaryStage.setScene(scene);
			primaryStage.show();
		}
		
		//로그인은 안했거나/ 일반 사용자일 경우
		else {
			System.out.println("일반사용자 로그인 또는 비회원");
			FXMLLoader loader = new FXMLLoader(getClass().getResource("MusicMain.fxml"));
			ScrollPane root = loader.load();
			
			Scene scene = new Scene(root);
			primaryStage.setTitle("Clap:음악, 그리고 설레임");
			primaryStage.setScene(scene);
			primaryStage.show();
		}
		
		
	}
	public static void main(String[] args) {
		launch(args);
	}
	
}
