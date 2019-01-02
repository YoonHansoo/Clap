package kr.or.ddit.clap.main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import kr.or.ddit.clap.view.chartmenu.main.ChartMenuController;

/**
 * 메인화면의 fxml 컨트롤러.
 * @author Kyunghun
 *
 */
public class MusicMainController implements Initializable{
	
	@FXML VBox vbox;
	Node root = vbox.getChildren().get(1);
	
	static Stage loginDialog = new Stage(StageStyle.DECORATED);
	static Stage joinDialog = new Stage(StageStyle.DECORATED);
	static Stage buyTicketDialog = new Stage(StageStyle.DECORATED);
	@FXML AnchorPane contents;
	
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
		
		Node root = FXMLLoader.load(getClass().getResource("../view/login/Login.fxml"));			
//		Scene scene = new Scene(root);
//		buyTicketDialog.setTitle("모여서 각잡고 코딩 - clap");
//		
//		buyTicketDialog.setScene(scene);
//		buyTicketDialog.show();
		System.out.println("이용권 구매 화면");
		vbox.getChildren().remove(1);
		root.setLayoutX(100);
		root.setLayoutY(0);
		vbox.getChildren().add(root);
	}
	
	@FXML
	public void top50PageChange(ActionEvent event) { //차트메뉴에서 Top50차트 클릭 했을때 페이지 전환 이벤트
		ChartMenuController.menuCount = 0;
		chartMenu_PageLoad();
	}
	
	@FXML
	public void genrePageChange(ActionEvent event) { //차트메뉴에서 장르별차트 클릭 했을때 페이지 전환 이벤트
		ChartMenuController.menuCount = 1;
		chartMenu_PageLoad();
	}
	
	@FXML
	public void periodPageChange(ActionEvent event) { //차트메뉴에서 시대별차트 클릭 했을때 페이지 전환 이벤트
		ChartMenuController.menuCount = 2;
		chartMenu_PageLoad();
	}
	
	@FXML
	public void musicvideoPageChange(ActionEvent event) { //차트메뉴에서 뮤직비디오차트 클릭 했을때 페이지 전환 이벤트
		ChartMenuController.menuCount = 3;
		chartMenu_PageLoad();
	}
	
	public void chartMenu_PageLoad() {
		try {
			Parent page = FXMLLoader.load(getClass().getResource("../view/chartmenu/main/ChartMenu.fxml")); //바뀔 화면을 가져옴
			contents.getChildren().removeAll();
			contents.getChildren().setAll(page);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
