package kr.or.ddit.clap.view.recommend.album;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import kr.or.ddit.clap.main.LoginSession;
import kr.or.ddit.clap.main.MusicMainController;
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
	
	private IPlayListService ipls;
	public static String rcmAlbNo;
	private Registry reg;
	private IRecommendService irs;
	private MusicList musicList;
	public RecommendAlbumVO rVO;
	private String temp_img_path = "";
	
	private ObservableList<MusicVO> rcmAlbMusic;
	
	private ObservableList<JFXCheckBox> cbnList = FXCollections.observableArrayList();
	private ObservableList<JFXButton> btnPlayList = FXCollections.observableArrayList();
	private ObservableList<JFXButton> btnAddList = FXCollections.observableArrayList();
	private ObservableList<JFXButton> btnPutList = FXCollections.observableArrayList();
	private ObservableList<JFXButton> btnMovieList = FXCollections.observableArrayList();
	private MusicPlayerController mpc;

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

			// 좋아요 카운트 쿼리
			System.out.println("추천앨범번호" + rcmAlbNo);
			int likeCnt = irs.selectAlbumLikeCnt(rcmAlbNo);
			System.out.println("likeCnt:" + likeCnt);
			label_LikeCnt.setText(likeCnt+ "");
			
			//리스트 수 
			System.out.println("추천앨범번호" + rcmAlbNo);
			int listCnt = irs.selectAlbumListCnt(rcmAlbNo);
			System.out.println("listCnt:" + listCnt);
			lable_cntMusic.setText(listCnt+"");
			

			// 추천앨범no를 통해서 해당 추천앨법 곡을 가져오는 쿼리
			/*musicList = (MusicList) FXCollections.observableArrayList(irs.SelectRcmMusicList(rcmAlbNo));
			System.out.println("해당 추천앨범 곡 개수 :" + musicList.size());
*/
		} catch (Exception e) {
		}
		
		musicList = new MusicList(cbnList, btnPlayList, btnAddList, btnPutList,
				  btnMovieList, mainBox);
		
		//조회차트
		songChart();
	}

	@FXML
	public void btn_heart() {
	}

	@FXML
	public void songChart() {
		try {
			rcmAlbMusic = FXCollections.observableArrayList(irs.SelectRcmMusicList(rcmAlbNo));
			cb_main.setSelected(false);
			
	
			
			musicList.musicList_VO(rcmAlbMusic);
			
			/*//댓글 창 생성 
			 
			HBox HboxReply = new HBox();
			HboxReply.setPrefWidth(100);
			HboxReply.setPrefHeight(20);
			HboxReply.setPadding(new Insets(0,0,10,0));
			//temp_hbox.setPadding(new Insets(5, 5, 5, 15));
			//temp_hbox.setStyle("-fx-bordertop-color:#090948;");
			//temp_hbox.setStyle("-fx-border-style : solid hidden hidden hidden;");
			//vbox.setMargin(temp_hbox, new Insets(50, 0, 0, 0));
			
			
			
			
			// Title 곡 라벨 갯수
			Label reply = new Label();
			reply.setFont(Font.font("-윤고딕350", 14));
			reply.setTextFill(Color.valueOf("#000"));
			reply.setPrefWidth(40);
			reply.setPrefHeight(40);
			reply.setText("댓글");
			
			
			// Title 곡 라벨 갯수
			Label replyCnt = new Label();
			replyCnt.setFont(Font.font("-윤고딕350", 14));
			replyCnt.setTextFill(Color.valueOf("#9c0000"));
			replyCnt.setPrefWidth(40);
			replyCnt.setPrefHeight(40);
			replyCnt.setText("0개");

		
			
			//댓글창과 버튼을 담는 hbox 테두리 
			HBox h_reply = new HBox();
			h_reply.setPrefWidth(770);
			h_reply.setPrefHeight(60);
			h_reply.setStyle("-fx-border-color: #090948");
			h_reply.setStyle("-fx-background-color: #f0f0f0");

			//hbox
			TextArea input_reply = new TextArea();
			input_reply.setPrefWidth(660);
			input_reply.setPromptText("명예회손, 개인정보 유출, 인격권 침해, 허위사실 유포 등은 이용약관 및 관련법률에 의해 제재를 받을 수 있습니다. 건전한 댓글문화 정착을 위해 이용에 주의를 부탁드립니다.");
			input_reply.setWrapText(true);
			input_reply.setEditable(true);

			//댓글등록버튼
			JFXButton btnReplyInsert = new JFXButton();
			//vbox.setMargin(temp_hbox, new Insets(50, 0, 0, 0));
			HBox.setMargin(btnReplyInsert,  new Insets(0, 0, 0, 10));
			btnReplyInsert.setPrefWidth(130);
			btnReplyInsert.setPrefHeight(60);
			btnReplyInsert.setTextFill(Color.valueOf("#fff"));
			btnReplyInsert.setStyle("-fx-background-color: #090948 ;");
			btnReplyInsert.setText("댓글등록");
			
			
			
			
			//세팅 
			
			HboxReply.getChildren().addAll(reply,replyCnt);
			h_reply.getChildren().addAll(input_reply,btnReplyInsert);
			mainBox.getChildren().addAll(HboxReply, h_reply);
			
			
			
			//댓글 불러오기
			
			*/
			
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
