package kr.or.ddit.clap.view.chartmenu.musiclist;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Map;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDialog;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import kr.or.ddit.clap.main.LoginSession;
import kr.or.ddit.clap.main.MusicMainController;
import kr.or.ddit.clap.service.playlist.IPlayListService;
import kr.or.ddit.clap.view.chartmenu.dialog.MyAlbumDialogController;
import kr.or.ddit.clap.view.musicplayer.MusicPlayerController;
import kr.or.ddit.clap.view.musicvideo.MusicVideoController;
import kr.or.ddit.clap.vo.music.PlayListVO;

public class MusicList {
	private Registry reg;
	private IPlayListService ipls;
	private ObservableList<JFXCheckBox> cbnList;
	private ObservableList<JFXButton> btnPlayList;
	private ObservableList<JFXButton> btnAddList;
	private ObservableList<JFXButton> btnPutList;
	private ObservableList<JFXButton> btnMovieList;
	private StackPane stackpane;
	private VBox mainBox;
	private MusicPlayerController mpc;
	private Stage stage = MusicMainController.movieStage;
	
	public MusicList() {
		
	}
	
	public MusicList( ObservableList<JFXCheckBox> cbnList, ObservableList<JFXButton> btnPlayList,
			 		  ObservableList<JFXButton> btnAddList, ObservableList<JFXButton> btnPutList,
			 		  ObservableList<JFXButton> btnMovieList, VBox mainBox , StackPane stackpane) {

		super();
		this.cbnList = cbnList;
		this.btnPlayList = btnPlayList;
		this.btnAddList = btnAddList;
		this.btnPutList = btnPutList;
		this.btnMovieList = btnMovieList;
		this.stackpane = stackpane;
		this.mainBox = mainBox;
		
		try {
			reg = LocateRegistry.getRegistry("localhost", 8888);
			ipls = (IPlayListService) reg.lookup("playlist");
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
		
	}
	
	// 재생 버튼 클릭시 이벤트
	private void btnPlayClick() {
		if (LoginSession.session == null) {
			return;
		}
		for (int i = 0; i < btnPlayList.size(); i++) {
			btnPlayList.get(i).setOnAction(e->{
				JFXButton btn_PlayMy = (JFXButton) e.getSource();
				
				PlayListVO vo = new PlayListVO();
				vo.setMus_no(btn_PlayMy.getId());
				vo.setMem_id(LoginSession.session.getMem_id());
				playListInsert(vo);
				
				if (!MusicMainController.musicplayer.isShowing()) {
					try {
						MusicMainController.playerLoad = new FXMLLoader(getClass().getResource("../../musicplayer/MusicPlayer.fxml"));
						AnchorPane root = MusicMainController.playerLoad.load();
						Scene scene = new Scene(root);
						MusicMainController.musicplayer.setTitle("MusicPlayer");
						MusicMainController.musicplayer.setScene(scene);
						MusicMainController.musicplayer.show();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				mpc = MusicMainController.playerLoad.getController();
				mpc.reFresh();
				mpc.selectIndex(vo.getMus_no());
			});
		}
	}
	
	// 추가 버튼 클릭시 이벤트
	private void btnAddClick() {
		if (LoginSession.session == null) {
			return;
		}
		for (int i = 0; i < btnAddList.size(); i++) {
			btnAddList.get(i).setOnAction(e->{
				JFXButton btn_AddMy = (JFXButton) e.getSource();
				
				PlayListVO vo = new PlayListVO();
				vo.setMus_no(btn_AddMy.getId());
				vo.setMem_id(LoginSession.session.getMem_id());
				playListInsert(vo);
				
				if (MusicMainController.musicplayer.isShowing()) {
					mpc = MusicMainController.playerLoad.getController();
					mpc.reFresh();
				}
			});
		}
	}
	
	private void playListInsert(PlayListVO vo) {
		try {
			ipls.playlistInsert(vo);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	// 담기 버튼 클릭시 이벤트
	private void btnPutClick() {
		if (LoginSession.session == null) {
			return;
		}
		for (int i = 0; i < btnPutList.size(); i++) {
			btnPutList.get(i).setOnAction(e->{
				JFXButton btn_PutMy = (JFXButton) e.getSource();
				MyAlbumDialogController.mus_no.clear();
				MyAlbumDialogController.mus_no.add(btn_PutMy.getId());
				myAlbumdialog();
			});
		}
	}
	
	// 뮤비 버튼 클릭시 이벤트
	private void btnMovieClick() {
		if (LoginSession.session == null) {
			return;
		}
		for (int i = 0; i < btnMovieList.size(); i++) {
			btnMovieList.get(i).setOnAction(e->{
				JFXButton btn_MovieMy = (JFXButton) e.getSource();
				try {
					if (stage.isShowing()) {
						if (MusicVideoController.mediaPlayer.getStatus() == Status.PLAYING) {
							MusicVideoController.mediaPlayer.stop();
						}
						stage.close();
					}
					MusicVideoController.path = btn_MovieMy.getId();
					MusicVideoController.musName = btn_MovieMy.getAccessibleText();
					MusicVideoController.singerName = btn_MovieMy.getAccessibleHelp();
					AnchorPane pane = FXMLLoader.load(getClass().getResource("../../musicvideo/MusicVideo.fxml"));
					Scene scene = new Scene(pane);
					stage.setScene(scene);
					stage.setTitle("MusicVideo");
					stage.show();
					
					stage.setOnCloseRequest(e2->{
						if (MusicVideoController.mediaPlayer.getStatus() == Status.PLAYING) {
							MusicVideoController.mediaPlayer.stop();
						}
					});
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			});
		}
	}
	
	public void myAlbumdialog() {
		StackPane content;
		try {
			content = FXMLLoader.load(getClass().getResource("../dialog/MyAlbumDialog.fxml"));
			JFXDialog dialog = new JFXDialog(stackpane, content, JFXDialog.DialogTransition.CENTER);
			dialog.setBackground(Background.EMPTY);
			dialog.show();
			
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	public void musicList(ObservableList<Map> list) {
		
		VBox vbox = new VBox();
		
		cbnList.clear();
		btnPlayList.clear();
		btnAddList.clear();
		btnPutList.clear();
		btnMovieList.clear();
		
		for (int i = 0; i < list.size(); i++) {
			// 파란색 라인 HBox 
			HBox h_Line = new HBox();
			vbox.setMargin(h_Line, new Insets(0,0,0,90));
			h_Line.setPrefWidth(710);
			h_Line.setPrefHeight(3);
			h_Line.setStyle("-fx-background-color: #090948;");
			//h_Line.alignmentProperty("")
			
			
			// 전체 HBox 
			HBox h_Table = new HBox();
			vbox.setMargin(h_Table, new Insets(0,0,0,80));
			h_Table.setAlignment(Pos.CENTER_LEFT);
			h_Table.setPadding(new Insets(10,10,10,10));
			h_Table.setSpacing(10);
			
				JFXCheckBox chb_Check = new JFXCheckBox();
				chb_Check.setPrefWidth(30);
				chb_Check.setPrefHeight(15);
				chb_Check.setCheckedColor(Color.valueOf("#9c0000"));
				chb_Check.setId(list.get(i).get("MUS_NO").toString()); //키값: 컬럼명->대문자로
				cbnList.add(chb_Check);
				
				// 곡제목 및 아티스트명을 담는 VBox
				VBox v_rank = new VBox();
				v_rank.setAlignment(Pos.CENTER_LEFT);
				v_rank.setSpacing(1);

					// 순위를 나타내는 Label
					Label la_Rank = new Label();
					la_Rank.setFont(Font.font("-윤고딕350", 18));
					la_Rank.setPrefWidth(23);
					la_Rank.setPrefHeight(23);
					la_Rank.setText("" + (i+1));
					
					// 전날 순위에 대한 변동순위를 나타내는 Label
					Label la_PreRank = new Label();
					la_PreRank.setFont(Font.font("-윤고딕350", 12));
					la_PreRank.setPrefWidth(30);
					la_PreRank.setPrefHeight(23);
					int beforRank = 0;
					if(list.get(i).get("MUS_BEFOR_RANK") != null) {
						beforRank = Integer.parseInt(list.get(i).get("MUS_BEFOR_RANK").toString());
					}
					int rank = i+1;
					String str_beforRank = "";
					FontAwesomeIcon rankImg = new FontAwesomeIcon();
					rankImg.setSize("15");
					
					
					if (rank == beforRank) {
						rankImg.setIconName("MINUS");
						rankImg.setFill(Color.valueOf("#A4A4A4"));
						la_PreRank.setGraphic(rankImg);
						
					} else if (rank <= beforRank) {
						beforRank -= rank;
						str_beforRank = Integer.toString(beforRank);
						la_PreRank.setTextFill(Color.valueOf("#FE5C62"));
						rankImg.setIconName("CARET_UP");
						rankImg.setFill(Color.valueOf("#FE5C62"));
						la_PreRank.setGraphic(rankImg);
						
					} else if (rank >= beforRank && beforRank != 0){
						rank -= beforRank;
						str_beforRank = Integer.toString(rank);
						la_PreRank.setTextFill(Color.valueOf("#609ACF"));
						rankImg.setIconName("CARET_DOWN");
						rankImg.setFill(Color.valueOf("#609ACF"));
						la_PreRank.setGraphic(rankImg);
			
					} else if (beforRank == 0){
						str_beforRank = "new";
						la_PreRank.setTextFill(Color.valueOf("#609ACF"));
						
					}
					la_PreRank.setText(str_beforRank);
				
				// 앨범이미지를 표시하는 ImageView
				ImageView iv_Album = new ImageView();
				Image img_Path = new Image(list.get(i).get("ALB_IMAGE").toString());
				iv_Album.setImage(img_Path);
				iv_Album.setFitWidth(50);
				iv_Album.setFitHeight(50);
					
				// 곡제목 및 아티스트명을 담는 VBox
				VBox v_MusicInfo = new VBox();
				v_MusicInfo.setAlignment(Pos.CENTER_LEFT);
				v_MusicInfo.setSpacing(5);
				v_MusicInfo.setPrefWidth(300);

					// 곡제목을 담당하는 라벨
					Label la_MusicName = new Label();
					la_MusicName.setFont(Font.font("-윤고딕350", 12));
					la_MusicName.setText(list.get(i).get("MUS_TITLE").toString());
					la_MusicName.setPrefWidth(300);
					
					// 가수이름을 담당하는 라벨
					Label la_SingerName = new Label();
					la_SingerName.setFont(Font.font("-윤고딕330", 12));
					la_SingerName.setText(list.get(i).get("SING_NAME").toString());
					la_SingerName.setPrefWidth(300);
						
				// 듣기 버튼
				JFXButton btn_Play = new JFXButton();
				h_Table.setMargin(btn_Play, new Insets(0,0,0,40));
				btn_Play.setRipplerFill(Color.valueOf("#9c0000"));
				btn_Play.setAlignment(Pos.CENTER_LEFT);
				btn_Play.setPrefWidth(30);
				btn_Play.setPrefHeight(46);
				btn_Play.setId(list.get(i).get("MUS_NO").toString());
					
					// 듣기 아이콘
					FontAwesomeIcon icon_Play = new FontAwesomeIcon();
					icon_Play.setIconName("PLAY_CIRCLE_ALT");
					icon_Play.setFill(Color.valueOf("#9c0000"));
					icon_Play.setSize("30");
					btn_Play.setGraphic(icon_Play);
					btnPlayList.add(btn_Play);
					
				// 추가 버튼
				JFXButton btn_Add = new JFXButton();
				btn_Add.setRipplerFill(Color.valueOf("#9c0000"));
				btn_Add.setAlignment(Pos.CENTER_LEFT);
				btn_Add.setPrefWidth(30);
				btn_Add.setPrefHeight(46);
				btn_Add.setId(list.get(i).get("MUS_NO").toString());
					
					// 추가 아이콘
					FontAwesomeIcon icon_Add = new FontAwesomeIcon();
					icon_Add.setIconName("PLUS");
					icon_Add.setFill(Color.valueOf("#9c0000"));
					icon_Add.setSize("30");
					btn_Add.setGraphic(icon_Add);
					btnAddList.add(btn_Add);
					
				// 담기 버튼
				JFXButton btn_Put = new JFXButton();
				btn_Put.setRipplerFill(Color.valueOf("#9c0000"));
				btn_Put.setAlignment(Pos.CENTER_LEFT);
				btn_Put.setPrefWidth(30);
				btn_Put.setPrefHeight(46);
				btn_Put.setId(list.get(i).get("MUS_NO").toString());
					
					// 담기 아이콘
					FontAwesomeIcon icon_Put = new FontAwesomeIcon();
					icon_Put.setIconName("FOLDER_ALT");
					icon_Put.setFill(Color.valueOf("#9c0000"));
					icon_Put.setSize("30");
					btn_Put.setGraphic(icon_Put);
					btnPutList.add(btn_Put);
				
				// 뮤비 버튼
				JFXButton btn_Movie = new JFXButton();
				btn_Movie.setRipplerFill(Color.valueOf("#9c0000"));
				btn_Movie.setAlignment(Pos.CENTER_LEFT);
				btn_Movie.setPrefWidth(30);
				btn_Movie.setPrefHeight(46);
				
				if (list.get(i).get("MUS_MVFILE") == null) {
					btn_Movie.setDisable(true);
				}else {
					btn_Movie.setId(list.get(i).get("MUS_MVFILE").toString());
					btn_Movie.setAccessibleText(list.get(i).get("MUS_TITLE").toString());
					btn_Movie.setAccessibleHelp(list.get(i).get("SING_NAME").toString());
				}
					// 뮤비 아이콘
					FontAwesomeIcon icon_Movie = new FontAwesomeIcon();
					icon_Movie.setIconName("VIMEO_SQUARE");
					icon_Movie.setFill(Color.valueOf("#9c0000"));
					icon_Movie.setSize("30");
					btn_Movie.setGraphic(icon_Movie);
					btnMovieList.add(btn_Movie);
					
				v_rank.getChildren().addAll(la_Rank,la_PreRank);
				v_MusicInfo.getChildren().addAll(la_MusicName,la_SingerName);
			h_Table.getChildren().addAll(chb_Check,v_rank,iv_Album,v_MusicInfo,btn_Play,btn_Add,btn_Put,btn_Movie);
			vbox.getChildren().addAll(h_Line,h_Table);
			
		}
		
		if (mainBox.getChildren().size() == 4) {
			mainBox.getChildren().remove(3);
		}
		//-----------------------
		mainBox.getChildren().add(vbox);
		
		btnPlayClick();
		btnAddClick();
		btnPutClick();
		btnMovieClick();
		
	}
	
public VBox pagenation(ObservableList<Map> list, int itemsForPage, int page) {
		
		VBox vbox = new VBox();
		
		cbnList.clear();
		btnPlayList.clear();
		btnAddList.clear();
		btnPutList.clear();
		btnMovieList.clear();
		
		int size = Math.min(page + itemsForPage, list.size());
		
		for (int i = page; i < size; i++) {
			// 파란색 라인 HBox 
			HBox h_Line = new HBox();
			vbox.setMargin(h_Line, new Insets(0,0,0,90));
			h_Line.setPrefWidth(710);
			h_Line.setPrefHeight(3);
			h_Line.setStyle("-fx-background-color: #090948;");
			//h_Line.alignmentProperty("")
			
			
			// 전체 HBox 
			HBox h_Table = new HBox();
			vbox.setMargin(h_Table, new Insets(0,0,0,80));
			h_Table.setAlignment(Pos.CENTER_LEFT);
			h_Table.setPadding(new Insets(10,10,10,10));
			h_Table.setSpacing(10);
			
				JFXCheckBox chb_Check = new JFXCheckBox();
				chb_Check.setPrefWidth(30);
				chb_Check.setPrefHeight(15);
				chb_Check.setCheckedColor(Color.valueOf("#9c0000"));
				chb_Check.setId(list.get(i).get("MUS_NO").toString()); //키값: 컬럼명->대문자로
				cbnList.add(chb_Check);
				
				// 곡제목 및 아티스트명을 담는 VBox
				VBox v_rank = new VBox();
				v_rank.setAlignment(Pos.CENTER_LEFT);
				v_rank.setSpacing(1);

					// 순위를 나타내는 Label
					Label la_Rank = new Label();
					la_Rank.setFont(Font.font("-윤고딕350", 18));
					la_Rank.setPrefWidth(23);
					la_Rank.setPrefHeight(23);
					la_Rank.setText("" + (i+1));
					
					// 전날 순위에 대한 변동순위를 나타내는 Label
					Label la_PreRank = new Label();
					la_PreRank.setFont(Font.font("-윤고딕350", 12));
					la_PreRank.setPrefWidth(30);
					la_PreRank.setPrefHeight(23);
					
					String str_beforRank = "";
					FontAwesomeIcon rankImg = new FontAwesomeIcon();
					rankImg.setSize("15");
					rankImg.setIconName("MINUS");
					rankImg.setFill(Color.valueOf("#A4A4A4"));
					la_PreRank.setGraphic(rankImg);
					la_PreRank.setText(str_beforRank);
				
				// 앨범이미지를 표시하는 ImageView
				ImageView iv_Album = new ImageView();
				Image img_Path = new Image(list.get(i).get("ALB_IMAGE").toString());
				iv_Album.setImage(img_Path);
				iv_Album.setFitWidth(50);
				iv_Album.setFitHeight(50);
					
				// 곡제목 및 아티스트명을 담는 VBox
				VBox v_MusicInfo = new VBox();
				v_MusicInfo.setAlignment(Pos.CENTER_LEFT);
				v_MusicInfo.setSpacing(5);
				v_MusicInfo.setPrefWidth(300);

					// 곡제목을 담당하는 라벨
					Label la_MusicName = new Label();
					la_MusicName.setFont(Font.font("-윤고딕350", 12));
					la_MusicName.setText(list.get(i).get("MUS_TITLE").toString());
					la_MusicName.setPrefWidth(300);
					
					// 가수이름을 담당하는 라벨
					Label la_SingerName = new Label();
					la_SingerName.setFont(Font.font("-윤고딕330", 12));
					la_SingerName.setText(list.get(i).get("SING_NAME").toString());
					la_SingerName.setPrefWidth(300);
						
				// 듣기 버튼
				JFXButton btn_Play = new JFXButton();
				h_Table.setMargin(btn_Play, new Insets(0,0,0,40));
				btn_Play.setRipplerFill(Color.valueOf("#9c0000"));
				btn_Play.setAlignment(Pos.CENTER_LEFT);
				btn_Play.setPrefWidth(30);
				btn_Play.setPrefHeight(46);
				btn_Play.setId(list.get(i).get("MUS_NO").toString());
					
					// 듣기 아이콘
					FontAwesomeIcon icon_Play = new FontAwesomeIcon();
					icon_Play.setIconName("PLAY_CIRCLE_ALT");
					icon_Play.setFill(Color.valueOf("#9c0000"));
					icon_Play.setSize("30");
					btn_Play.setGraphic(icon_Play);
					btnPlayList.add(btn_Play);
					
				// 추가 버튼
				JFXButton btn_Add = new JFXButton();
				btn_Add.setRipplerFill(Color.valueOf("#9c0000"));
				btn_Add.setAlignment(Pos.CENTER_LEFT);
				btn_Add.setPrefWidth(30);
				btn_Add.setPrefHeight(46);
				btn_Add.setId(list.get(i).get("MUS_NO").toString());
					
					// 추가 아이콘
					FontAwesomeIcon icon_Add = new FontAwesomeIcon();
					icon_Add.setIconName("PLUS");
					icon_Add.setFill(Color.valueOf("#9c0000"));
					icon_Add.setSize("30");
					btn_Add.setGraphic(icon_Add);
					btnAddList.add(btn_Add);
					
				// 담기 버튼
				JFXButton btn_Put = new JFXButton();
				btn_Put.setRipplerFill(Color.valueOf("#9c0000"));
				btn_Put.setAlignment(Pos.CENTER_LEFT);
				btn_Put.setPrefWidth(30);
				btn_Put.setPrefHeight(46);
				btn_Put.setId(list.get(i).get("MUS_NO").toString());
					
					// 담기 아이콘
					FontAwesomeIcon icon_Put = new FontAwesomeIcon();
					icon_Put.setIconName("FOLDER_ALT");
					icon_Put.setFill(Color.valueOf("#9c0000"));
					icon_Put.setSize("30");
					btn_Put.setGraphic(icon_Put);
					btnPutList.add(btn_Put);
				
				// 뮤비 버튼
				JFXButton btn_Movie = new JFXButton();
				btn_Movie.setRipplerFill(Color.valueOf("#9c0000"));
				btn_Movie.setAlignment(Pos.CENTER_LEFT);
				btn_Movie.setPrefWidth(30);
				btn_Movie.setPrefHeight(46);
				if (list.get(i).get("MUS_MVFILE") == null) {
					btn_Movie.setDisable(true);
				}else {
					btn_Movie.setId(list.get(i).get("MUS_MVFILE").toString());
					btn_Movie.setAccessibleText(list.get(i).get("MUS_TITLE").toString());
					btn_Movie.setAccessibleHelp(list.get(i).get("SING_NAME").toString());
				}
					
					// 뮤비 아이콘
					FontAwesomeIcon icon_Movie = new FontAwesomeIcon();
					icon_Movie.setIconName("VIMEO_SQUARE");
					icon_Movie.setFill(Color.valueOf("#9c0000"));
					icon_Movie.setSize("30");
					btn_Movie.setGraphic(icon_Movie);
					btnMovieList.add(btn_Movie);
					
				v_rank.getChildren().addAll(la_Rank,la_PreRank);
				v_MusicInfo.getChildren().addAll(la_MusicName,la_SingerName);
			h_Table.getChildren().addAll(chb_Check,v_rank,iv_Album,v_MusicInfo,btn_Play,btn_Add,btn_Put,btn_Movie);
			vbox.getChildren().addAll(h_Line,h_Table);
			
		}
		
		btnPlayClick();
		btnAddClick();
		btnPutClick();
		btnMovieClick();
		
		return vbox;
		
	}
}
