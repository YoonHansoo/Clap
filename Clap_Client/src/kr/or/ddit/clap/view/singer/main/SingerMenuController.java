package kr.or.ddit.clap.view.singer.main;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTabPane;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import kr.or.ddit.clap.main.LoginSession;
import kr.or.ddit.clap.main.MusicMainController;
import kr.or.ddit.clap.service.album.IAlbumService;
import kr.or.ddit.clap.service.music.IMusicService;
import kr.or.ddit.clap.service.playlist.IPlayListService;
import kr.or.ddit.clap.service.singer.ISingerService;
import kr.or.ddit.clap.view.album.album.SelectSingerController;
import kr.or.ddit.clap.view.chartmenu.dialog.MyAlbumDialogController;
import kr.or.ddit.clap.view.chartmenu.musiclist.MusicList;
import kr.or.ddit.clap.view.member.mypage.OtherMypageController;
import kr.or.ddit.clap.view.musicplayer.MusicPlayerController;
import kr.or.ddit.clap.vo.album.AlbumVO;
import kr.or.ddit.clap.vo.music.PlayListVO;
import kr.or.ddit.clap.vo.singer.SingerVO;

/**
 * 
 * @author 박경훈
 *
 */

public class SingerMenuController implements Initializable{
	
	public static int menuCount = 0;
	
	@FXML JFXTabPane tabPane;
	@FXML Tab tab_main;
	@FXML Tab tab_album;
	@FXML Tab tab_music;
	@FXML AnchorPane main;
	@FXML StackPane singerMain;
	@FXML StackPane singerAlbum;
	@FXML StackPane singerMusic;
	
	public static String albumNo; // 파라미터로 받은 선택한 가수의 PK
	private Registry reg;
	private IAlbumService ias;
	private ISingerService iss;
	private String temp_img_path = "";

	// 파라미터로 넘기기 위해 전역으로 선언
	public AlbumVO aVO = null;
	public SingerVO sVO = null;
	public String str_like_cnt;
	public static AnchorPane contents;

	// @FXML Label label_singNo;
	@FXML Label label_albumName1;
	@FXML Label label_singerName;
	@FXML Label label_saledate;
	@FXML Label label_saleEnter;
	@FXML Label label_entertain;
	@FXML Label label_LikeCnt;
	@FXML ImageView imgview_albumImg;
	@FXML Label txt_intro;
	
	@FXML VBox box;
	@FXML Label lb_singer;
	
	@FXML VBox mainBox;
	@FXML JFXCheckBox cb_main;
	@FXML JFXButton btn_Song;
	@FXML JFXButton btn_Pop;
	@FXML JFXButton btn_Ost;
	@FXML JFXButton btn_Other;
	@FXML StackPane stackpane;
	
	@FXML Label lb_total;
	@FXML Label lb_intro;
	@FXML Line line_intro;
	
	
	
	private IMusicService ims;
	private IPlayListService ipls;
	private MusicList musicList;
	private ObservableList<Map> songRank;
	private ObservableList<Map> popRank;
	private ObservableList<Map> ostRank;
	private ObservableList<Map> otherRank;
	private ObservableList<JFXCheckBox> cbnList = FXCollections.observableArrayList();
	private ObservableList<JFXButton> btnPlayList = FXCollections.observableArrayList();
	private ObservableList<JFXButton> btnAddList = FXCollections.observableArrayList();
	private ObservableList<JFXButton> btnPutList = FXCollections.observableArrayList();
	private ObservableList<JFXButton> btnMovieList = FXCollections.observableArrayList();
	private MusicPlayerController mpc;
	private int itemsForPage;
	private Pagination p_page;

	// ShowSingerList.fxml는 VBOX를 포함한 전부이기 때문에
	// 현재 씬의 VBox까지 모두 제거 후 ShowSingerList를 불러야함.
	public void givePane(AnchorPane contents) {
		this.contents = contents;
		System.out.println("contents 적용완료");
	}
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		if(menuCount != 1) {
			box.setVisible(false);
			mainBox.setVisible(false);
			line_intro.setVisible(false);
			lb_intro.setVisible(false);
			txt_intro.setVisible(false);
		}

		try {
			//AnchorPane pane = FXMLLoader.load(getClass().getResource("SingerMain.fxml"));
			
				/////
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource("SingerMain.fxml"));
			Parent SingerMain= loader.load(); 
			SingerMainController cotroller = loader.getController();
			
			cotroller.setcontroller(main);
///
				
			singerMain.getChildren().removeAll();
			singerMain.getChildren().setAll(SingerMain);
			
			
			
			
			
			
			StackPane pane2 = FXMLLoader.load(getClass().getResource("SingerAlbum.fxml"));
			pane2.setVisible(false);
			singerAlbum.getChildren().removeAll();
			singerAlbum.getChildren().setAll(pane2);
			
			StackPane pane3 = FXMLLoader.load(getClass().getResource("SingerMusic.fxml"));
			singerMusic.getChildren().removeAll();
			singerMusic.getChildren().setAll(pane3);
			
			tabPane.getSelectionModel().select(menuCount);
			tabPane.setOnKeyPressed(e->{
				box.setVisible(false);
				mainBox.setVisible(false);
				line_intro.setVisible(false);
				lb_intro.setVisible(false);
				txt_intro.setVisible(false);
				pane2.setVisible(true);				
			});
			tabPane.setOnMouseClicked(e->{
				box.setVisible(false);
				mainBox.setVisible(false);
				line_intro.setVisible(false);
				lb_intro.setVisible(false);
				txt_intro.setVisible(false);
				pane2.setVisible(true);
			});
			
//			StackPane pane_main = FXMLLoader.load(getClass().getResource("SingerMainMusic.fxml"));
//			pane_main.setLayoutY(350);
//			singerMain.getChildren().setAll(pane_main);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("앨범번호:" + albumNo);
		

		try {
			// reg로 ISingerService객체를 받아옴
			reg = LocateRegistry.getRegistry("localhost", 8888);
			ias = (IAlbumService) reg.lookup("album");
			aVO = ias.albumDetailInfo(albumNo);
			
			iss = (ISingerService) reg.lookup("singer");
			sVO = iss.singerDetailInfo(aVO.getSing_no());
			
			System.out.println(aVO.getSing_no());
			// 파라미터로 받은 정보를 PK로 상세정보를 가져옴
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}

		lb_singer.setText(sVO.getSing_name());
		lb_singer.setOnMouseClicked(e->{
			box.setVisible(false);
			tabPane.getSelectionModel().select(0);
		});
		
		label_albumName1.setText(aVO.getAlb_name());
//		label_albumName2.setText(aVO.getAlb_name());
		label_singerName.setText(aVO.getSing_name());
		label_saledate.setText(aVO.getAlb_saledate());
		label_saleEnter.setText(aVO.getAlb_sale_enter());

		label_entertain.setText(aVO.getAlb_entertain());
		txt_intro.setText(aVO.getAlb_intro());

		Image img = new Image(aVO.getAlb_image());
		temp_img_path = aVO.getAlb_image(); // aVO.getSing_image()를 전역으로 쓰기위해
		imgview_albumImg.setImage(img);

		// 좋아요 수를 가져오는 쿼리
		int like_cnt = 0;
		try {
			like_cnt = ias.selectAlbumLikeCnt(albumNo);
		} catch (RemoteException e) {
			e.printStackTrace();
		}

		// 좋아요는 다른 VO에서 가져와야함...
		str_like_cnt = like_cnt + "";
		label_LikeCnt.setText(str_like_cnt);
		
		
		try {
			// reg로 ISingerService객체를 받아옴
			reg = LocateRegistry.getRegistry("localhost", 8888);
			
			ims = (IMusicService) reg.lookup("music");
			ipls = (IPlayListService) reg.lookup("playlist");
			itemsForPage = 8;
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
		
		musicList = new MusicList(cbnList, btnPlayList, btnAddList, btnPutList,
				  btnMovieList, mainBox, stackpane);

		// 일간 조회 차트 
		songChart();

	}

	@FXML
	public void wideView() {
		// img_wideimg
		System.out.println("크게보기 버튼클릭");
		try {
			AnchorPane pane = FXMLLoader.load(getClass().getResource("../../album/album/AlbummgWiderDialog.fxml"));
			Stage stage = new Stage(StageStyle.UTILITY);
			Scene scene = new Scene(pane);
			stage.setScene(scene);
			stage.initModality(Modality.APPLICATION_MODAL);
			Stage primaryStage = (Stage) label_albumName1.getScene().getWindow();
			stage.initOwner(primaryStage);
			stage.setResizable(false);

			ImageView img_wideimg = (ImageView) pane.lookup("#img_wideimg");
			Image temp_img = new Image(temp_img_path);
			img_wideimg.setImage(temp_img);

			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 메인 재생 버튼 이벤트
	@FXML public void btnMainPlay() {
		if (LoginSession.session == null) {
			return;
		}
		
		ArrayList<String> list = musicCheckList();
		playListInsert(list,true);
		if (!MusicMainController.musicplayer.isShowing()) {
			try {
				MusicMainController.playerLoad = new FXMLLoader(getClass().getResource("../../musicplayer/MusicPlayer.fxml"));
				AnchorPane root = MusicMainController.playerLoad.load();
				Scene scene = new Scene(root);
				MusicMainController.musicplayer.setTitle("MusicPlayer");
				MusicMainController.musicplayer.setScene(scene);
				MusicMainController.musicplayer.show();
				mpc = MusicMainController.playerLoad.getController();
				mpc.reFresh();
				mpc.selectIndex();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		cb_main.setSelected(false);
		mainCheck();
	}

	// 메인 추가 버튼 이벤트
	@FXML public void btnMainAdd() {
		if (LoginSession.session == null) {
			return;
		}
		
		ArrayList<String> list = musicCheckList();
		playListInsert(list,false);
		cb_main.setSelected(false);
		mainCheck();
	}

	// 메인 담기 버튼 이벤트
	@FXML public void btnMainPut() {
		if (LoginSession.session == null) {
			return;
		}
		
		ArrayList<String> list = musicCheckList();
		MyAlbumDialogController.mus_no.clear();
		MyAlbumDialogController.mus_no = list;
		musicList.myAlbumdialog();
		cb_main.setSelected(false);
		mainCheck();
	}
	
	// 전체 선택 및 해제 메서드
	@FXML public void mainCheck() {
		if (cb_main.isSelected()) {
			for(int i = 0; i < cbnList.size(); i++) {
				cbnList.get(i).setSelected(true);
			}
		} else {
			for(int i = 0; i < cbnList.size(); i++) {
				cbnList.get(i).setSelected(false);
			}
		}
	}
	
	// 체크 박스 선택한 곡넘버 보내기
	private ArrayList<String> musicCheckList() {
		ArrayList<String> list = new ArrayList<>();
		for (int i = 0; i < cbnList.size(); i++) {
			if (cbnList.get(i).isSelected()) {
				list.add(cbnList.get(i).getId());
			}
		}
		return list;
	}
	
	// 가요장르
	@FXML public void songChart() {
		try { // 앨범 번호로 찾기
			songRank = FXCollections.observableArrayList(ims.selectAlbum(SingerMenuController.albumNo));
			cb_main.setSelected(false);
			lb_total.setText("수록곡 (총 "+songRank.size()+"개)");
			
			pageing(songRank);
			
			// 아티스트 소개 y좌표 설정
			line_intro.setLayoutY(621 + (songRank.size()-1)*73);
			lb_intro.setLayoutY(626 + (songRank.size()-1)*73);
			txt_intro.setLayoutY(671 + (songRank.size()-1)*73);
			
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	public VBox createPage(int pageIndex, ObservableList<Map> list, int itemsForPage) {
        int page = pageIndex * itemsForPage;
        return musicList.pagenation(list,itemsForPage,page);
    }

	private void playListInsert(ArrayList<String> list, boolean play) {
		for (int i = 0; i < list.size(); i++) {
			PlayListVO vo = new PlayListVO();
			vo.setMus_no(list.get(i));
			vo.setMem_id(LoginSession.session.getMem_id());
			try {
				ipls.playlistInsert(vo);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			
			if (MusicMainController.musicplayer.isShowing()) {
				mpc = MusicMainController.playerLoad.getController();
				mpc.reFresh();
				if(play) {
					mpc.selectIndex();
				}
			}
		}
	}
	
	private void pageing(ObservableList<Map> list) {
		
		if (mainBox.getChildren().size() == 8) {
			mainBox.getChildren().remove(7);
		}
		
		if (list.size() == 0) return;
		int totalPage = (list.size() / itemsForPage) + (list.size() % itemsForPage > 0 ? 1 : 0);
		
		p_page = new Pagination(totalPage, 0);
		p_page.setPageFactory(new Callback<Integer, Node>() {
            @Override
            public Node call(Integer pageIndex) {
                return createPage(pageIndex,list,itemsForPage);
            }
	    });
		
		mainBox.getChildren().addAll(p_page);
	}
	
}
