package kr.or.ddit.clap.view.singer.main;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import kr.or.ddit.clap.main.LoginSession;
import kr.or.ddit.clap.main.MusicMainController;
import kr.or.ddit.clap.main.createReply;
import kr.or.ddit.clap.service.music.IMusicService;
import kr.or.ddit.clap.service.playlist.IPlayListService;
import kr.or.ddit.clap.service.singer.ISingerService;
import kr.or.ddit.clap.view.chartmenu.dialog.MyAlbumDialogController;
import kr.or.ddit.clap.view.chartmenu.musiclist.MusicList;
import kr.or.ddit.clap.view.musicplayer.MusicPlayerController;
import kr.or.ddit.clap.vo.music.PlayListVO;
import kr.or.ddit.clap.vo.singer.SingerVO;

public class SingerMainController implements Initializable {

	public static String singerNo;// 파라미터로 받은 선택한 가수의 PK
	private Registry reg;
	private ISingerService iss;
	private String temp_img_path = "";

	// 파라미터로 넘기기 위해 전역으로 선언
	public SingerVO sVO = null;
	public String str_like_cnt;
	public static AnchorPane contents;
	List<Map<String, String>> replyMap;
	@FXML
	Label label_singNo;
	@FXML
	Label label_singerName1;
	@FXML
	Label label_ActType;
	@FXML
	Label label_ActEra;
	@FXML
	Label label_DebutEra;
	@FXML
	Label label_DebutMus;
	@FXML
	Label label_Nation;
	@FXML
	Label label_LikeCnt;
	@FXML
	ImageView imgview_singImg;
	@FXML
	Label txt_intro;
	@FXML
	AnchorPane main;

	@FXML
	VBox mainBox;
	@FXML
	JFXCheckBox cb_main;
	@FXML
	JFXButton btn_Song;
	@FXML
	JFXButton btn_Pop;
	@FXML
	JFXButton btn_Ost;
	@FXML
	JFXButton btn_Other;
	@FXML
	StackPane stackpane;

	@FXML
	Label lb_total;
	@FXML
	Label lb_intro;
	@FXML
	Line line_intro;

	int yn = 0;
	Map<String, String> pMap = new HashMap<String, String>();

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

	@FXML
	VBox reply_vbox;
	@FXML
	FontAwesomeIcon icon_heart;
	@FXML
	AnchorPane singerMain;

	// ShowSingerList.fxml는 VBOX를 포함한 전부이기 때문에
	// 현재 씬의 VBox까지 모두 제거 후 ShowSingerList를 불러야함.
	public void givePane(AnchorPane contents) {
		this.contents = contents;
		System.out.println("contents 적용완료");
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		System.out.println("가수번호:" + singerNo);

		try {
			// reg로 ISingerService객체를 받아옴
			reg = LocateRegistry.getRegistry("localhost", 8888);
			iss = (ISingerService) reg.lookup("singer");
			sVO = iss.singerDetailInfo(singerNo);
			System.out.println(sVO.getSing_no());
			// 파라미터로 받은 정보를 PK로 상세정보를 가져옴

			ims = (IMusicService) reg.lookup("music");
			ipls = (IPlayListService) reg.lookup("playlist");
			itemsForPage = 8;
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}

		musicList = new MusicList(cbnList, btnPlayList, btnAddList, btnPutList, btnMovieList, mainBox, stackpane);

		// 일간 조회 차트
		songChart();

		label_singerName1.setText(sVO.getSing_name());
		// label_singerName2.setText(sVO.getSing_name());
		label_ActType.setText(sVO.getSing_act_type());
		label_ActEra.setText(sVO.getSing_act_era());
		label_DebutEra.setText(sVO.getSing_debut_era());

		label_DebutMus.setText(sVO.getSing_debut_mus());
		label_Nation.setText(sVO.getSing_nation());
		txt_intro.setText(sVO.getSing_intro());

		Image img = new Image(sVO.getSing_image());
		System.out.println("이미지경로:" + sVO.getSing_image());

		temp_img_path = sVO.getSing_image(); // sVO.getSing_image()를 전역으로 쓰기위해
		imgview_singImg.setImage(img);

		// 좋아요 수를 가져오는 쿼리
		try {
			int likeCnt = iss.selectSingerLikeCnt(singerNo);
			System.out.println("likecnt :" + likeCnt);
			label_LikeCnt.setText(likeCnt + "");

			// 세션아이디와 가수번호를 매개변수로 좋아요를 눌렀는 지 확인하는 메서드
			String id = LoginSession.session.getMem_id();
			pMap.put("singerNo", singerNo);
			pMap.put("id", id);
			System.out.println("singerNo:" + singerNo);
			System.out.println("id:" + id);
			System.out.println("첫번쨰" + yn);

			yn = iss.checkHeartYN(pMap);
			icon_heart.setIconName("HEART_ALT"); // 초기화 빈하트
			if (yn > 0) {
				icon_heart.setIconName("HEART");
			}

		} catch (Exception e) {

		}

		// 댓글조회
		try {
			System.out.println("singerNo:" + singerNo);
			replyMap = iss.selectReply(singerNo);
			System.out.println("리플사이즈:" + replyMap.size());
			int size = replyMap.size();
			// 댓글창 생성
			createReply.creatReply(reply_vbox, size, iss, singerNo);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (int i = 0; i < replyMap.size(); i++) {
			System.out.println("포문시작");
			HBox hbox = new HBox();
			hbox.setPrefWidth(731);
			hbox.setPrefHeight(73);
			reply_vbox.setMargin(hbox, new Insets(20, 0, 0, 0));
			
			ImageView imgView = new ImageView();
			Image image = new Image(replyMap.get(i).get("MEM_IMAGE").toString());
			imgView.setImage(image);
			imgView.setFitWidth(40);
			imgView.setFitHeight(40);
			// label_musCount.setText(list.get(i*2).get("MUS_COUNT").toString() + "곡");
			imgView.setId(replyMap.get(i).get("SING_RE_NO").toString());

			imgView.setOnMouseClicked(e -> {
				ImageView temp_imgView = (ImageView) e.getSource();
				for (int j = 0; j < replyMap.size(); j++) {
					if (temp_imgView.getId().equals(replyMap.get(j).get("SING_RE_NO").toString())) {
							
						System.out.println("아이디값" + replyMap.get(j).get("MEM_ID").toString());
						System.out.println("화면전환");
					}
				}
			});

			hbox.setMargin(imgView, new Insets(0, 10, 0, 0));
			VBox vbox = new VBox();
			vbox.setPrefWidth(653);
			vbox.setPrefHeight(73);

			HBox small_hbox = new HBox();
			small_hbox.setPrefWidth(533);
			small_hbox.setPrefHeight(30);

			Label label_id = new Label();
			label_id.setPrefWidth(40);
			label_id.setPrefHeight(15);
			label_id.setText(replyMap.get(i).get("MEM_ID").toString());

			Label label_date = new Label();
			label_id.setPrefWidth(75);
			label_id.setPrefHeight(15);
			label_date.setText(replyMap.get(i).get("SING_RE_INDATE").toString());

			JFXButton btn_report = new JFXButton();
			btn_report.setPrefWidth(40);
			btn_report.setPrefHeight(15);
			btn_report.setText("신고");
			btn_report.setId(replyMap.get(i).get("SING_RE_NO").toString());
			btn_report.setTextFill(Color.valueOf("#fff"));
			btn_report.setStyle("-fx-background-color: #9c0000;");
			small_hbox.setMargin(btn_report, new Insets(0, 0, 0, 5));

			Label label_contents = new Label();
			label_contents.setPrefWidth(598);
			label_contents.setPrefHeight(43);
			label_contents.setText(replyMap.get(i).get("SING_RE_CONTENT").toString());

			HBox h_Line = new HBox();
			// vbox.setMargin(h_Line, new Insets(0,0,0,0));
			h_Line.setPrefWidth(710);
			h_Line.setPrefHeight(20);
			h_Line.setStyle("-fx-background-color:#090948;");

			small_hbox.getChildren().addAll(label_id, label_date, btn_report);
			vbox.getChildren().addAll(small_hbox, label_contents);
			hbox.getChildren().addAll(imgView, vbox);
			reply_vbox.getChildren().addAll(hbox, h_Line);

		}

		/*
		 * //포문을 돌려서 각각의 행에 onclick메서드를 걸어준다 . for(int i=0; i<musicList.size(); i++) {
		 * System.out.println("포문시작");
		 * tbl_music.getTreeItem(i).getValue().getBtn().setOnAction(e->{ Button temp_btn
		 * = (Button) e.getSource(); for(int i=0; i<replyMap.size(); i++) {
		 * System.out.println("온클릭 포문 시작"); }
		 */
	}

	// 메인 재생 버튼 이벤트
	@FXML
	public void btnMainPlay() {
		if (LoginSession.session == null) {
			return;
		}

		ArrayList<String> list = musicCheckList();
		playListInsert(list, true);
		if (!MusicMainController.musicplayer.isShowing()) {
			try {
				MusicMainController.playerLoad = new FXMLLoader(
						getClass().getResource("../../musicplayer/MusicPlayer.fxml"));
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
	@FXML
	public void btnMainAdd() {
		if (LoginSession.session == null) {
			return;
		}

		ArrayList<String> list = musicCheckList();
		playListInsert(list, false);
		cb_main.setSelected(false);
		mainCheck();
	}

	// 메인 담기 버튼 이벤트
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

	// 전체 선택 및 해제 메서드
	@FXML
	public void mainCheck() {
		if (cb_main.isSelected()) {
			for (int i = 0; i < cbnList.size(); i++) {
				cbnList.get(i).setSelected(true);
			}
		} else {
			for (int i = 0; i < cbnList.size(); i++) {
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
	@FXML
	public void songChart() {
		try {
			songRank = FXCollections.observableArrayList(ims.selectSinger(SingerMainController.singerNo));
			cb_main.setSelected(false);
			lb_total.setText("발매곡 (총 " + songRank.size() + "개)");

			pageing(songRank);

			// 아티스트 소개 y좌표 설정
			line_intro.setLayoutY(560 + (songRank.size() - 1) * 73);
			lb_intro.setLayoutY(565 + (songRank.size() - 1) * 73);
			txt_intro.setLayoutY(620 + (songRank.size() - 1) * 73);
			reply_vbox.setLayoutY(916 + (songRank.size() - 1) * 73);

		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void wideView() {
		// img_wideimg
		System.out.println("크게보기 버튼클릭");
		try {
			AnchorPane pane = FXMLLoader.load(getClass().getResource("../singer/SingerImgWiderDialog.fxml"));
			Stage stage = new Stage(StageStyle.UTILITY);
			Scene scene = new Scene(pane);
			stage.setScene(scene);
			stage.initModality(Modality.APPLICATION_MODAL);
			Stage primaryStage = (Stage) label_singerName1.getScene().getWindow();
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

	public VBox createPage(int pageIndex, ObservableList<Map> list, int itemsForPage) {
		int page = pageIndex * itemsForPage;
		return musicList.pagenation(list, itemsForPage, page);
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
				if (play) {
					mpc.selectIndex();
				}
			}
		}
	}

	private void pageing(ObservableList<Map> list) {

		if (mainBox.getChildren().size() == 8) {
			mainBox.getChildren().remove(7);
		}

		if (list.size() == 0)
			return;
		int totalPage = (list.size() / itemsForPage) + (list.size() % itemsForPage > 0 ? 1 : 0);

		p_page = new Pagination(totalPage, 0);
		p_page.setPageFactory(new Callback<Integer, Node>() {
			@Override
			public Node call(Integer pageIndex) {
				return createPage(pageIndex, list, itemsForPage);
			}
		});

		mainBox.getChildren().addAll(p_page);
	}

	@FXML
	public void btn_heart() throws RemoteException {
		if (yn > 0) // 좋아요 취소일 때
		{
			// 취소 메서드
			System.out.println("취소메서드 클릭");
			iss.deleteSingerLike(pMap);
			resetCnt();
			icon_heart.setIconName("HEART_ALT");

		} else {
			// 추가 메서드
			System.out.println(yn);
			System.out.println("추가메서드 클릭");
			iss.insertSingerLike(pMap);
			resetCnt();
			icon_heart.setIconName("HEART");
		}

	}

	private void resetCnt() throws RemoteException {
		// 좋아요 카운트 쿼리

		int likeCnt = iss.selectSingerLikeCnt(singerNo);
		System.out.println("likecnt :" + likeCnt);
		label_LikeCnt.setText(likeCnt + "");

		yn = iss.checkHeartYN(pMap);
	}
}