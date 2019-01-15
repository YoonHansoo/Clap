package kr.or.ddit.clap.view.recommend.album;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import kr.or.ddit.clap.main.LoginSession;
import kr.or.ddit.clap.main.MusicMainController;
import kr.or.ddit.clap.main.createReply;
import kr.or.ddit.clap.service.playlist.IPlayListService;
import kr.or.ddit.clap.service.recommend.IRecommendService;
import kr.or.ddit.clap.view.chartmenu.dialog.MyAlbumDialogController;
import kr.or.ddit.clap.view.chartmenu.musiclist.MusicList;
import kr.or.ddit.clap.view.musicplayer.MusicPlayerController;
import kr.or.ddit.clap.vo.music.MusicVO;
import kr.or.ddit.clap.vo.music.PlayListVO;
import kr.or.ddit.clap.vo.recommend.RecommendAlbumVO;


public class UserRcmDetailController implements Initializable {

	@FXML
	Label labelrcmName1;
	@FXML
	AnchorPane main;
	@FXML
	ImageView imgview_img;
	@FXML
	VBox main_vbox;
	@FXML
	Label label_LikeCnt;
	@FXML
	Label lable_cntMusic;
	@FXML
	Label label_RcmContents;
	@FXML
	VBox mainBox;
	@FXML
	JFXCheckBox cb_main;
	@FXML FontAwesomeIcon icon_heart;
	
	private IPlayListService ipls;
	public static String rcmAlbNo;
	private Registry reg;
	private IRecommendService irs;
	private MusicList musicList;
	public RecommendAlbumVO rVO;
	private String temp_img_path = "";
	int yn = 0;
	private ObservableList<MusicVO> rcmAlbMusic;
	
	private ObservableList<JFXCheckBox> cbnList = FXCollections.observableArrayList();
	private ObservableList<JFXButton> btnPlayList = FXCollections.observableArrayList();
	private ObservableList<JFXButton> btnAddList = FXCollections.observableArrayList();
	private ObservableList<JFXButton> btnPutList = FXCollections.observableArrayList();
	private ObservableList<JFXButton> btnMovieList = FXCollections.observableArrayList();
	private MusicPlayerController mpc;
	
	Map<String, String> pMap = new HashMap<String, String>();

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		System.out.println("추천앨범 클릭");
		System.out.println("받은 추천앨범번호:" + rcmAlbNo);
		try {
			reg = LocateRegistry.getRegistry("localhost", 8888);
			irs = (IRecommendService) reg.lookup("recommend");
			ipls = (IPlayListService) reg.lookup("playlist");
			// 추천앨범 상세정보 가져오는 쿼리
			rVO = irs.selectRecommendAlbumDetail(rcmAlbNo);

			// 값 세팅
			labelrcmName1.setText(rVO.getRcm_alb_name());

			label_RcmContents.setText(rVO.getRcm_content());

			// 이미지 세팅
			temp_img_path = rVO.getRcm_alb_image();
			Image img = new Image(temp_img_path);
			imgview_img.setImage(img);

			resetCnt();
			
			//세션아이디와 추천앨범번호를 매개변수로 좋아요를 눌렀는 지 확인하는 메서드
			String id = LoginSession.session.getMem_id();
			pMap.put("rcmAlbNo", rcmAlbNo);
			pMap.put("id",id);
			 yn = irs.checkHeartYN(pMap);
			 //pMap.clear();
			System.out.println(yn);
			
			icon_heart.setIconName("HEART_ALT"); //초기화 빈하트
			if(yn>0) {
				//icon_heart.setFill(value);
				icon_heart.setIconName("HEART");
			}
		} catch (Exception e) {
		}
		
		musicList = new MusicList(cbnList, btnPlayList, btnAddList, btnPutList,
				  btnMovieList, mainBox);
		
		//조회차트
		songChart();
		
		
		//댓글창 생성
		createReply.creatReply(mainBox);
		
		//댓글조회
		
		for(int i =0; i<2; i++) {
			
			HBox hbox = new HBox();
			hbox.setPrefWidth(731);
			hbox.setPrefHeight(73);
			
			ImageView imgView = new ImageView();
			Image img = new Image("file:\\\\Sem-pc\\공유폴더\\Clap\\img\\userimg\\neo2.png");
			imgView.setImage(img);
			imgView.setFitWidth(63);
			imgView.setFitHeight(48);
			
			VBox vbox = new VBox();
		    vbox.setPrefWidth(653);
		    vbox.setPrefHeight(73);
		    
		    HBox small_hbox = new HBox();
		    small_hbox.setPrefWidth(533);
		    small_hbox.setPrefHeight(30);
		    
		    Label label_id = new Label();
		    label_id.setPrefWidth(54);
		    label_id.setPrefHeight(15);
		    label_id.setText("user1");
		    
		    Label label_date = new Label();
		    label_id.setPrefWidth(75);
		    label_id.setPrefHeight(15);
		    label_date.setText("2018/01/15");
		    
		    JFXButton btn_report = new JFXButton();
		    btn_report.setPrefWidth(27);
		    btn_report.setPrefHeight(15);
		    btn_report.setText("신고");
		    
		    Label label_contents = new Label();
		    label_contents.setPrefWidth(598);
		    label_contents.setPrefHeight(43);
		    label_contents.setText("노래가 너무 좋아요");
		    
		    small_hbox.getChildren().addAll(label_id,label_date,btn_report);
		    vbox.getChildren().addAll(small_hbox,label_contents);
		    
		    hbox.getChildren().addAll(imgView,vbox);
		    
		    mainBox.getChildren().add(hbox);
		    
		}
	
		
	}

	private void resetCnt() throws RemoteException {
		// 좋아요 카운트 쿼리
		int likeCnt = irs.selectAlbumLikeCnt(rcmAlbNo);
		label_LikeCnt.setText(likeCnt+ "");
		
		//리스트 수 
		int listCnt = irs.selectAlbumListCnt(rcmAlbNo);
		lable_cntMusic.setText(listCnt+"");
		
		yn = irs.checkHeartYN(pMap);
	}

	@FXML
	
	public void btn_heart() throws RemoteException {
		if(yn>0) //좋아요 취소일 때 
		{
			
			//취소 메서드
			System.out.println("취소메서드 클릭");
			irs.deleteRcmLike(pMap);
			resetCnt();
			icon_heart.setIconName("HEART_ALT");
			
		}
		else 
		{
			//추가 메서드
			System.out.println("추가메서드 클릭");
			irs.insertRcmLike(pMap);
			resetCnt();
			icon_heart.setIconName("HEART");
		}
		
		
		
	}

	@FXML
	public void songChart() {
		try {
			rcmAlbMusic = FXCollections.observableArrayList(irs.SelectRcmMusicList(rcmAlbNo));
			cb_main.setSelected(false);
			
	
			
			musicList.musicList_VO(rcmAlbMusic);
			
			
			
			
			
			
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	
	}

	
	@FXML
	public void mainCheck() {
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

	@FXML
	public void btnMainPlay() {
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

	@FXML
	public void btnMainAdd() {
		if (LoginSession.session == null) {
			return;
		}
		
		ArrayList<String> list = musicCheckList();
		playListInsert(list,false);
		cb_main.setSelected(false);
		mainCheck();
		
	}

	@FXML
	public void btnMainPut() {
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
}
