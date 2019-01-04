package kr.or.ddit.clap.main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import kr.or.ddit.clap.view.chartmenu.main.ChartMenuController;
import com.jfoenix.controls.JFXButton;

/**
 * 메인화면의 fxml 컨트롤러.
 * @author Kyunghun
 *
 */
public class MusicMainController implements Initializable{
	
	@FXML VBox vbox;
	
	public static Stage loginDialog = new Stage(StageStyle.DECORATED);
	public static Stage joinDialog = new Stage(StageStyle.DECORATED);
	static Stage buyTicketDialog = new Stage(StageStyle.DECORATED);
	@FXML AnchorPane menu;
	@FXML AnchorPane contents;
	@FXML FontAwesomeIcon icon_firstPage;

	@FXML public JFXButton btn_login;
	@FXML public JFXButton btn_join;
	@FXML public JFXButton btn_mem;
	
	@FXML ImageView mem_img;
	
	LoginSession ls = new LoginSession();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		if(ls.session.getMem_id() == null || ls.session.getMem_id().equals("admin1")) {
			System.out.println("null "+ls.session.getMem_id());
			btn_mem.setVisible(false);
			btn_join.setVisible(true);
			btn_login.setVisible(true);			
		}else {
			System.out.println("not null "+ls.session.getMem_id());
			btn_mem.setVisible(true);
			btn_join.setVisible(false);
			btn_login.setVisible(false);
			
//			Image img = new Image(getClass().getResourceAsStream("../../img/purin.jpg"));
//			mem_img.setImage(img);
		}
	}
	
	@FXML
	public void login() throws IOException {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("../view/login/Login.fxml"));
			contents.getChildren().removeAll();
			contents.getChildren().setAll(root);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
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

	@FXML
	public void mypage() {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("../view/member/mypage/Mypage.fxml"));
			contents.getChildren().removeAll();
			contents.getChildren().setAll(root);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void buyTicket() throws IOException {
		login_PageLoad();
	}
	
	
	@FXML
	public void qna() { //고객센터 - 문의사항

		try {
			Parent qna = FXMLLoader.load(getClass().getResource("../view/support/qna/QnaMenuList.fxml"));
			contents.getChildren().removeAll();
			contents.getChildren().setAll(qna);
		} catch (IOException e) {
			e.printStackTrace();
		}

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

	public void login_PageLoad() {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("../view/login/Login.fxml"));			
			contents.getChildren().removeAll();
			contents.getChildren().setAll(root);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void firstPage() {
		try {
			System.out.println("getClass:"+getClass());
			System.out.println(contents.toString());
			Parent root = FXMLLoader.load(getClass().getResource("FirstPage.fxml"));			
			contents.getChildren().removeAll();
			contents.getChildren().setAll(root);
		} catch (IOException e) {
			e.printStackTrace();
		}
		/*FXMLLoader loader = new FXMLLoader(getClass().getResource("FirstPage.fxml"));
		AnchorPane root = null;
		try {
			root = loader.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Scene scene = new Scene(root);
		Stage primaryStage = (Stage) btn_login.getScene().getWindow();
		primaryStage.setScene(scene);*/
	}

}
