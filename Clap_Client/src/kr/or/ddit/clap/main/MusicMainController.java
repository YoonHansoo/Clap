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
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import kr.or.ddit.clap.view.chartmenu.main.ChartMenuController;
import com.jfoenix.controls.JFXButton;

/**
 * 메인화면의 fxml 컨트롤러.
 * 
 * @author Kyunghun
 *
 */
public class MusicMainController implements Initializable {


	@FXML AnchorPane menu;
	@FXML AnchorPane contents;
	@FXML FontAwesomeIcon icon_firstPage;
	@FXML HBox mem_menu;

	@FXML public JFXButton btn_login;
	
	
	
	
	
	
	
	
	
	
	
	@FXML public JFXButton btn_logout;
	@FXML public JFXButton btn_join;
	@FXML public JFXButton btn_mem;
	@FXML public JFXButton btn_player;
	
	@FXML VBox vbox;
	@FXML Menu menu_admin;
	@FXML Label lb_id;
	@FXML ImageView mem_img;
	@FXML ImageView new1;
	@FXML ImageView new2;
	@FXML ImageView new3;
	@FXML ImageView new4;
	@FXML ImageView new5;
	@FXML ImageView new6;
	@FXML ImageView new7;
	@FXML ImageView new8;

	LoginSession ls = new LoginSession();
	public static Stage musicplayer = new Stage();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		System.out.println();
		if (ls.session != null) {
			System.out.println(ls.session.getMem_name());
			System.out.println(ls.session.getMem_auth());
		}
		if (ls.session == null) {
			mem_menu.setVisible(false);
			btn_join.setVisible(true);
			btn_login.setVisible(true);
		} else {
			System.out.println(ls.session.getMem_id() + ls.session.getMem_auth());
			if (ls.session.getMem_auth().equals("t")) { // 관리자 일 때 관리자모드 버튼 활성화
				menu_admin.setVisible(true);
			} else {
				menu_admin.setVisible(false); // 일반사용자일경우( 관리자로 로그인 후 사용자로 로그인 했을 경우를 대비해서만들었음
			}
			System.out.println("not null " + ls.session.getMem_id());
			mem_menu.setVisible(true);
			btn_join.setVisible(false);
			btn_login.setVisible(false);

			lb_id.setText(ls.session.getMem_id() + "님");

			Image img = null;
			if(ls.session.getMem_image() == null) {
				img = new Image(getClass().getResourceAsStream("../../../../../people_small.png"));				
			}else {
				img = new Image(getClass().getResourceAsStream("../../../../../"+ls.session.getMem_image()));
				
			}
			mem_img.setImage(img);
		}
		
		String[] names = new String[] {"ben1.jpg", "winner.jpg", "벌써 12시.jpg", "Circular.jpg", "PERCENT.jpg"
				, "알함브라 궁전의 추억 OST Part 5 (tvN 주말드라마).jpg", "SOLO.JPG", "XX.jpg"};
		Image[] images = new Image[names.length];
		
		for(int i=0; i<names.length; i++) {
			images[i] = new Image(getClass().getResourceAsStream("../../../../../"+names[i]));
		}
		
		new1.setImage(images[3]);
		new2.setImage(images[2]);
		new3.setImage(images[0]);
		new4.setImage(images[1]);
		new5.setImage(images[4]);
		new6.setImage(images[5]);
		new7.setImage(images[6]);
		new8.setImage(images[7]);
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
	public void logout() throws IOException {
		System.out.println("로그아웃처리");
		ls.session = null;
		System.out.println(ls.session);
		firstPage();
	}

	@FXML
	public void join() throws IOException {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("../view/join/Join.fxml"));
			contents.getChildren().removeAll();
			contents.getChildren().setAll(root);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void mypage() {
		// 로그인 후 회원정보버튼(btn_mem) 눌렀을때 마이페이지로 이동.
		System.out.println("마이페이지 출력");

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
		try {
			Parent root = FXMLLoader.load(getClass().getResource("../view/ticket/ticket/Ticket.fxml"));
			contents.getChildren().removeAll();
			contents.getChildren().setAll(root);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void qna() { // 고객센터 - 문의사항

		try {
			Parent qna = FXMLLoader.load(getClass().getResource("../view/support/qna/QnaMenuList.fxml"));
			contents.getChildren().removeAll();
			contents.getChildren().setAll(qna);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@FXML
	public void top50PageChange(ActionEvent event) { // 차트메뉴에서 Top50차트 클릭 했을때 페이지 전환 이벤트
		ChartMenuController.menuCount = 0;
		chartMenu_PageLoad();
	}

	@FXML
	public void genrePageChange(ActionEvent event) { // 차트메뉴에서 장르별차트 클릭 했을때 페이지 전환 이벤트
		ChartMenuController.menuCount = 1;
		chartMenu_PageLoad();
	}

	@FXML
	public void periodPageChange(ActionEvent event) { // 차트메뉴에서 시대별차트 클릭 했을때 페이지 전환 이벤트
		ChartMenuController.menuCount = 2;
		chartMenu_PageLoad();
	}

	@FXML
	public void musicvideoPageChange(ActionEvent event) { // 차트메뉴에서 뮤직비디오차트 클릭 했을때 페이지 전환 이벤트
		ChartMenuController.menuCount = 3;
		chartMenu_PageLoad();
	}

	public void chartMenu_PageLoad() {
		try {
			Parent page = FXMLLoader.load(getClass().getResource("../view/chartmenu/main/ChartMenu.fxml")); // 바뀔 화면을
																											// 가져옴
			contents.getChildren().removeAll();
			contents.getChildren().setAll(page);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public void firstPage() {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("MusicMain.fxml"));
		ScrollPane root = null;
		try {
			root = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Scene scene = new Scene(root);
		Stage primaryStage = (Stage) btn_login.getScene().getWindow();
		primaryStage.setScene(scene);
	}

	@FXML
	public void singerManage(ActionEvent event) { // 가수관리를 클릭 했을 때.
		try {
			Parent singerManage = FXMLLoader.load(getClass().getResource("../view/singer/singer/ShowSingerList.fxml")); // 바뀔
																														// 화면을
																														// 가져옴
			contents.getChildren().removeAll();
			contents.getChildren().setAll(singerManage);

			// System.out.println(item_SigerManage.getText());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public void albumManage(ActionEvent event) { //앨범관리를 클릭 했을 때. 
		   try {
			   Parent albumManage = FXMLLoader.load(getClass().getResource("../view/album/album/ShowAlbumLIst.fxml")); //바뀔 화면을 가져옴
			   contents.getChildren().removeAll();
			   contents.getChildren().setAll(albumManage);
			   
		   } catch (IOException e) {
			   e.printStackTrace();
		   }
		   
	}
	public void musicManage(ActionEvent event) { //곡 관리를 클릭 했을 때. 
		try {
			Parent albumManage = FXMLLoader.load(getClass().getResource("../view/music/music/MusicList.fxml")); //바뀔 화면을 가져옴
			contents.getChildren().removeAll();
			contents.getChildren().setAll(albumManage);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@FXML
	public void musicPlayer(ActionEvent event) { // MusicPlayer를 클릭 했을 때.
		if (!musicplayer.isShowing()) {
			try {
				AnchorPane root = FXMLLoader.load(getClass().getResource("../view/musicplayer/MusicPlayer.fxml"));
				Scene scene = new Scene(root);
				musicplayer.setTitle("MusicPlayer");
				musicplayer.setScene(scene);
				musicplayer.show();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	@FXML
	public void notice() { // 공지사항
								
		try {
			Parent notice = FXMLLoader.load(getClass().getResource("../view/support/noticeboard/NoticeMenuList.fxml"));
			contents.getChildren().removeAll();
			contents.getChildren().setAll(notice);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}