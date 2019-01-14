package kr.or.ddit.clap.main;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

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
import kr.or.ddit.clap.service.album.IAlbumService;
import kr.or.ddit.clap.service.login.ILoginService;
import kr.or.ddit.clap.view.chartmenu.main.ChartMenuController;
import kr.or.ddit.clap.view.musicplayer.MusicPlayerController;
import kr.or.ddit.clap.view.newmusic.main.NewMusicMenuController;
import kr.or.ddit.clap.view.singer.main.SingerMainController;
import kr.or.ddit.clap.view.singer.main.SingerMenuController;
import kr.or.ddit.clap.vo.album.AlbumVO;
import kr.or.ddit.clap.vo.member.MemberVO;

/**
 * 메인화면의 fxml 컨트롤러.
 * 
 * @author Kyunghun
 *
 */
public class MusicMainController implements Initializable {

	@FXML ScrollPane all;
	@FXML AnchorPane menu;
	@FXML AnchorPane contents;
	@FXML HBox mem_menu;

	@FXML public JFXButton btn_login;

	@FXML public JFXButton btn_goforward;
	@FXML public JFXButton btn_goback;
	@FXML  JFXTextField txt_search;
	
	
	
	
	
	
	
	
	
	
	
	@FXML public JFXButton btn_logout;
	@FXML public JFXButton btn_join;
	@FXML public JFXButton btn_mem;
	@FXML public JFXButton btn_player;
	
	@FXML VBox vbox, editorBox1, editorBox2, editorBox3, editorBox4, editorBox5;
	@FXML Menu menu_admin;
	@FXML Label lb_id;
	@FXML ImageView mem_img;
	@FXML ImageView new1, new2, new3, new4, new5;
	@FXML ImageView new6, new7, new8, new9, new10;
	@FXML ImageView tab1, tab2, tab3, tab4, tab5;
	@FXML ImageView edit1, edit2, edit3, edit4, edit5, logo_imgView;
	@FXML JFXButton btn_new1, btn_new2, btn_new3, btn_new4, btn_new5;
	@FXML JFXButton btn_new6, btn_new7, btn_new8, btn_new9, btn_new10;
	@FXML AnchorPane tab_pane1, tab_pane2, tab_pane3, tab_pane4, tab_pane5;
	
	LoginSession ls = new LoginSession();
	public static Stage musicplayer = new Stage();
	private ILoginService ils;
	private IAlbumService ias;
	private Registry reg;
	public static FXMLLoader playerLoad;
	public static Stage movieStage = new Stage();
	
	List<AlbumVO> albumList = new ArrayList<>();
	List<AlbumVO> newList = new ArrayList<>();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			reg = LocateRegistry.getRegistry("localhost", 8888);
			ils = (ILoginService) reg.lookup("login");
			ias = (IAlbumService) reg.lookup("album");
			playerLoad = new FXMLLoader(getClass().getResource("../view/musicplayer/MusicPlayer.fxml"));
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
		
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

			System.out.println("이미지");
			Image img = null;
			if(ls.session.getMem_image() == null) {
				img = new Image("file:\\\\Sem-pc\\공유폴더\\Clap\\img\\userimg\\icons8-person-64.png");
			
			}else {
				img = new Image(ls.session.getMem_image());
			}
			mem_img.setImage(img);
			
			//검색버튼
			try {
				 List<String> hotKeyword = ils.selecthotkeyword();
				 System.out.println("리스트 사이즈"+ hotKeyword.size());
				 
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
		}
		
		// 최신음악에 앨범 목록에서 등록순으로 출력되도록.
		try {
			albumList = ias.selectListAll();
			System.out.println(albumList.size());
		} catch (RemoteException e1) {
			e1.printStackTrace();
		}
		// 최신순으로 10개 뽑는 메서드. -> 쿼리 이용하도록 수정.
//		setNewList();
		
		Image logo_img = new Image("file:\\\\Sem-pc\\공유폴더\\Clap\\img\\logo90.png");
		logo_imgView.setImage(logo_img);
		
		Image[] images = new Image[albumList.size()];
		
		for(int i=0; i<albumList.size(); i++) {
			images[i] = new Image(albumList.get(i).getAlb_image());
		}
		
		new1.setImage(images[0]);
		new2.setImage(images[1]);
		new3.setImage(images[2]);
		new4.setImage(images[3]);
		new5.setImage(images[4]);
		new6.setImage(images[5]);
		new7.setImage(images[6]);
		new8.setImage(images[7]);
		new9.setImage(images[8]);
		new10.setImage(images[9]);
		
		Image[] tab_img = new Image[5];
//		for(int i=0; i<5; i++) {
//			tab_img[i] = new Image(albumList.get(i).getAlb_image());
//		}
		
		tab_img[0] = new Image("file:\\\\Sem-pc\\공유폴더\\Clap\\img\\album\\everyd4y.JPG");
		tab_img[1] = new Image("file:\\\\Sem-pc\\공유폴더\\Clap\\img\\album\\Be There.JPG");
		tab_img[2] = new Image("file:\\\\Sem-pc\\공유폴더\\Clap\\img\\album\\마지막처럼.JPG");
		tab_img[3] = new Image("file:\\\\Sem-pc\\공유폴더\\Clap\\img\\album\\Full Album RED PLANET (Hidden Track).JPG");
		tab_img[4] = new Image("file:\\\\Sem-pc\\공유폴더\\Clap\\img\\album\\도깨비 OST Part 4 (tvN 금토드라마).JPG");
		
		tab1.setImage(tab_img[0]);
		tab2.setImage(tab_img[1]);
		tab3.setImage(tab_img[2]);
		tab4.setImage(tab_img[3]);
		tab5.setImage(tab_img[4]);
		
		
		
		btn_new1.setOnAction(e->{
			SingerMenuController.albumNo = albumList.get(0).getAlb_no(); // 앨범번호를 변수로 넘겨줌
			SingerMainController.singerNo = albumList.get(0).getSing_no(); // 가수번호를 변수로 넘겨줌
			System.out.println(albumList.get(0).getSing_no());
			singerMenu();
		});
		btn_new2.setOnAction(e->{
			SingerMenuController.albumNo = albumList.get(1).getAlb_no();
			SingerMainController.singerNo = albumList.get(1).getSing_no();
			System.out.println(albumList.get(1).getAlb_no());
			singerMenu();
		});
		btn_new3.setOnAction(e->{
			SingerMenuController.albumNo = albumList.get(2).getAlb_no();
			SingerMainController.singerNo = albumList.get(2).getSing_no();
			singerMenu();
		});
		btn_new4.setOnAction(e->{
			SingerMenuController.albumNo = albumList.get(3).getAlb_no();
			SingerMainController.singerNo = albumList.get(3).getSing_no();
			singerMenu();
		});
		btn_new5.setOnAction(e->{
			SingerMenuController.albumNo = albumList.get(4).getAlb_no();
			SingerMainController.singerNo = albumList.get(4).getSing_no();
			singerMenu();
		});
		
		
		btn_new6.setOnAction(e->{
			SingerMenuController.albumNo = albumList.get(5).getAlb_no();
			SingerMainController.singerNo = albumList.get(5).getSing_no();
			singerMenu();
		});
		btn_new7.setOnAction(e->{
			SingerMenuController.albumNo = albumList.get(6).getAlb_no();
			SingerMainController.singerNo = albumList.get(6).getSing_no();
			singerMenu();
		});
		btn_new8.setOnAction(e->{
			SingerMenuController.albumNo = albumList.get(7).getAlb_no();
			SingerMainController.singerNo = albumList.get(7).getSing_no();
			singerMenu();
		});
		btn_new9.setOnAction(e->{
			SingerMenuController.albumNo = albumList.get(8).getAlb_no();
			SingerMainController.singerNo = albumList.get(8).getSing_no();
			singerMenu();
		});
		btn_new10.setOnAction(e->{
			SingerMenuController.albumNo = albumList.get(9).getAlb_no();
			SingerMainController.singerNo = albumList.get(9).getSing_no();
			singerMenu();
		});
		
	}
	
	private void setNewList() {
		newList = albumList;
		
		for(int j=0; j<10; j++) {
			int max = 0;
			int idx_max = -1;
			for(int i=j; i<newList.size(); i++) {
				if(max < Integer.valueOf(newList.get(i).getAlb_no())){
					max = Integer.valueOf(newList.get(i).getAlb_no());
					idx_max = i;
				}
			}
			newList.add(j, newList.get(idx_max));
			newList.remove(idx_max+1);			
		}
		
	}
	
	@FXML
	public void goBack(ActionEvent event) throws IOException {

		gobackStack.goBack();
		String path = gobackStack.printPage();
		System.out.println("경로:"+ path);
		URL fxmlURL = Paths.get(path).toUri().toURL();  //Stirng 값을 URL로 변환
		
		Parent goback = FXMLLoader.load(fxmlURL); //대입
		
		System.out.println(path.substring(path.length()-14,path.length()));
	
		contents.getChildren().removeAll();
		contents.getChildren().setAll(goback);
		
	}
	@FXML
	public void goforward(ActionEvent event) throws IOException {
		gobackStack.goForward();
		String path = gobackStack.printPage();
		System.out.println("goforawrd:"+ path);
		System.out.println("경로:"+ path);
		URL fxmlURL = Paths.get(path).toUri().toURL();  //Stirng 값을 URL로 변환
		
		Parent goback = FXMLLoader.load(fxmlURL); //대입
		
		contents.getChildren().removeAll();
		contents.getChildren().setAll(goback);
		
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
		if (musicplayer.isShowing()) {
			MusicPlayerController mpc = MusicMainController.playerLoad.getController();
			if(mpc.player.mediaPlayer != null) {
				mpc.player.stop();
				mpc.player.mediaPlayer = null;
			}
			musicplayer.close();
		}
	}

	@FXML
	public void join() throws IOException {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("../view/join/Join2.fxml"));
			contents.getChildren().removeAll();
			contents.getChildren().setAll(root);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void mypage() {
		// 로그인 후 회원정보버튼(btn_mem) 눌렀을때 마이페이지로 이동.

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
	public void musicPageChange(ActionEvent event) { // 최신음악메뉴에서 곡차트 클릭 했을때 페이지 전환 이벤트
		NewMusicMenuController.menuCount = 0;
		newMusicMenu_PageLoad();
	}
	
	@FXML
	public void albumPageChange(ActionEvent event) { // 최신은악메뉴에서 앨범차트 클릭 했을때 페이지 전환 이벤트
		NewMusicMenuController.menuCount = 1;
		newMusicMenu_PageLoad();
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
	
	public void newMusicMenu_PageLoad() {

		try {
			Parent page = FXMLLoader.load(getClass().getResource("../view/newmusic/main/NewMusicMenu.fxml")); // 바뀔 화면을
																												// 가져옴
			contents.getChildren().removeAll();	
			contents.getChildren().setAll(page);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void singerMenu() {
		SingerMenuController.menuCount = 1;
		try {
			Parent page = FXMLLoader.load(getClass().getResource("../view/singer/main/SingerMenu.fxml")); // 바뀔 화면을
																											// 가져옴
			contents.getChildren().removeAll();
			contents.getChildren().setAll(page);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void firstPage() {
		List<MemberVO> list = new ArrayList<MemberVO>();
		try {
			if(ls.session!=null) {
				list = ils.select(ls.session.getMem_id());				
				ls.session = list.get(0);
			}
		} catch (RemoteException e1) {
			e1.printStackTrace();
		}
		
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
	
	//추천
	@FXML
	public void hotRcmList(ActionEvent event) {
		try {
			Parent recommendManage = FXMLLoader.load(getClass().getResource("../view/recommend/album/HotRcmList.fxml")); //바뀔 화면을 가져옴
			
			String temp_path = (getClass().getResource("../view/recommend/album/HotRcmList.fxml")).getPath();
			String path = temp_path.substring(1, temp_path.length()); //현재화면 절대경로
			
			gobackStack.goURL(path);  
			
			contents.getChildren().removeAll();
			contents.getChildren().setAll(recommendManage);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	//베스트
	@FXML
	public void BestRcmList(ActionEvent event) {
		try {
			Parent recommendManage = FXMLLoader.load(getClass().getResource("../view/recommend/album/BestRcmList.fxml")); //바뀔 화면을 가져옴
			
			String temp_path = (getClass().getResource("../view/recommend/album/BestRcmList.fxml")).getPath();
			String path = temp_path.substring(1, temp_path.length()); //현재화면 절대경로
			
			gobackStack.goURL(path);  
			
			contents.getChildren().removeAll();
			contents.getChildren().setAll(recommendManage);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	
	
	@FXML
	public void singerManage(ActionEvent event) { // 가수관리를 클릭 했을 때.
		try {
			//상대경로
			Parent singerManage = FXMLLoader.load(getClass().getResource("../view/singer/singer/ShowSingerList.fxml")); 
		
			//현재화면 절대경로
			String temp_path = (getClass().getResource("../view/singer/singer/ShowSingerList.fxml")).getPath();
			String path = temp_path.substring(1, temp_path.length()); 
			
			//현재화면의 fxml절대경로를 저장해야함
			gobackStack.goURL(path);  
			
			contents.getChildren().removeAll();
			contents.getChildren().setAll(singerManage);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public void albumManage(ActionEvent event) { //앨범관리를 클릭 했을 때. 
		   try {
			   Parent albumManage = FXMLLoader.load(getClass().getResource("../view/album/album/ShowAlbumLIst.fxml")); //바뀔 화면을 가져옴
			   
			   String temp_path = (getClass().getResource("../view/album/album/ShowAlbumLIst.fxml")).getPath();
				String path = temp_path.substring(1, temp_path.length()); //현재화면 절대경로
				
				gobackStack.goURL(path);  
			   
			   contents.getChildren().removeAll();
			   contents.getChildren().setAll(albumManage);
			   
		   } catch (IOException e) {
			   e.printStackTrace();
		   }
		   
	}
	public void musicManage(ActionEvent event) { //곡 관리를 클릭 했을 때. 
		try {
			Parent albumManage = FXMLLoader.load(getClass().getResource("../view/music/music/MusicList.fxml")); //바뀔 화면을 가져옴
			
			String temp_path = (getClass().getResource("../view/music/music/MusicList.fxml")).getPath();
				String path = temp_path.substring(1, temp_path.length()); //현재화면 절대경로
				
				gobackStack.goURL(path);  

			contents.getChildren().removeAll();
			contents.getChildren().setAll(albumManage);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void recommendManage(ActionEvent event) { //추천앨범 관리를 클릭 했을 때. 
		try {
			Parent recommendManage = FXMLLoader.load(getClass().getResource("../view/recommend/album/RecommendAlbumList.fxml")); //바뀔 화면을 가져옴
			
			String temp_path = (getClass().getResource("../view/recommend/album/RecommendAlbumList.fxml")).getPath();
			String path = temp_path.substring(1, temp_path.length()); //현재화면 절대경로
			
			gobackStack.goURL(path);  
			
			contents.getChildren().removeAll();
			contents.getChildren().setAll(recommendManage);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@FXML
	public void musicPlayer(ActionEvent event) { // MusicPlayer를 클릭 했을 때.
		if (LoginSession.session == null) {
			return;
		}else {
			if (!musicplayer.isShowing()) {
				try {
					playerLoad = new FXMLLoader(getClass().getResource("../view/musicplayer/MusicPlayer.fxml"));
					AnchorPane root = playerLoad.load();
					Scene scene = new Scene(root);
					musicplayer.setTitle("MusicPlayer");
					musicplayer.setScene(scene);
					musicplayer.show();
					
				} catch (IOException e) {
					e.printStackTrace();
				}
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
	
	
	public void eventManage(ActionEvent event) { //이벤트관리를 클릭 했을 때. 
		   try {
			   Parent eventManage = FXMLLoader.load(getClass().getResource("../view/support/eventboard/EventShowList.fxml")); //바뀔 화면을 가져옴
			   contents.getChildren().removeAll();
			   contents.getChildren().setAll(eventManage);
			   
		   } catch (IOException e) {
			   e.printStackTrace();
		   }
		   
	}
	
	public void salesMange(ActionEvent event) { //매출관리 클릭시
		   try {
			   Parent salesManage = FXMLLoader.load(getClass().getResource("../view/ticket/salemanage/salesmanage.fxml")); //바뀔 화면을 가져옴
			   contents.getChildren().removeAll();
			   contents.getChildren().setAll(salesManage);
			   
		   } catch (IOException e) {
			   e.printStackTrace();
		   }
		   
	}
	
	public void  memManage(){
		try {
			   Parent salesManage = FXMLLoader.load(getClass().getResource("../view/member/manage/memmanage.fxml")); //바뀔 화면을 가져옴	
			   contents.getChildren().removeAll();
			   contents.getChildren().setAll(salesManage);
			   
		   } catch (IOException e) {
			   e.printStackTrace();
		   }
	}
	
	
	@FXML
	public void event() { // 이벤트

		try {
			Parent event = FXMLLoader.load(getClass().getResource("../view/support/eventboard/EventClientShowList.fxml"));
			contents.getChildren().removeAll();
			contents.getChildren().setAll(event);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	

}