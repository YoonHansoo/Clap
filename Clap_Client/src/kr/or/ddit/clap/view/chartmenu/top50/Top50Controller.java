package kr.or.ddit.clap.view.chartmenu.top50;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import kr.or.ddit.clap.service.musichistory.IMusicHistoryService;

/**
 * 
 * @author 진민규
 *
 */

public class Top50Controller implements Initializable{

	@FXML VBox mainBox;
	@FXML JFXCheckBox cb_main;
	@FXML JFXButton btn_ToDay;
	@FXML JFXButton btn_Week;
	@FXML JFXButton btn_Month;
	@FXML Label la_Date;
	@FXML StackPane stackpane;
	
	private Registry reg;
	private IMusicHistoryService imhs;
	private ObservableList<Map> toDayRank;
	private ObservableList<Map> weekRank;
	private ObservableList<Map> monthRank;
	private ObservableList<JFXCheckBox> cbnList = FXCollections.observableArrayList();
	private ObservableList<JFXButton> btnPlayList = FXCollections.observableArrayList();
	private ObservableList<JFXButton> btnAddList = FXCollections.observableArrayList();
	private ObservableList<JFXButton> btnPutList = FXCollections.observableArrayList();
	private ObservableList<JFXButton> btnMovieList = FXCollections.observableArrayList();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			reg = LocateRegistry.getRegistry("localhost", 8888);
			imhs = (IMusicHistoryService) reg.lookup("history");
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 일간 조회 차트 
		toDayChart();
	}
	
	// 메인 재생 버튼 이벤트
	@FXML public void btnMainPlay() {
		ArrayList<String> list = musicList();
		System.out.println(list);
	}

	// 메인 추가 버튼 이벤트
	@FXML public void btnMainAdd() {
		ArrayList<String> list = musicList();
		System.out.println(list);
	}

	// 메인 담기 버튼 이벤트
	@FXML public void btnMainPut() {
		ArrayList<String> list = musicList();
		System.out.println(list);
	}
	
	// 체크 박스 선택한 곡넘버 보내기
	private ArrayList<String> musicList() {
		ArrayList<String> list = new ArrayList<>();
		for (int i = 0; i < cbnList.size(); i++) {
			if (cbnList.get(i).isSelected()) {
				list.add(cbnList.get(i).getId());
			}
		}
		return list;
	}

	// 뮤비 버튼 클릭시 이벤트
	private void btnMovieClick() {
		for (int i = 0; i < btnMovieList.size(); i++) {
			btnMovieList.get(i).setOnAction(e->{
				JFXButton btn_MovieMy = (JFXButton) e.getSource();
				System.out.println(btn_MovieMy.getId());
			});
		}
	}
	
	// 담기 버튼 클릭시 이벤트
	private void btnPutClick() {
		for (int i = 0; i < btnPutList.size(); i++) {
			btnPutList.get(i).setOnAction(e->{
				dialog();
			});
		}
	}
	
	private void dialog() {
		StackPane content;
		try {
			content = FXMLLoader.load(getClass().getResource("../main/Dialog.fxml"));
			JFXDialog dialog = new JFXDialog(stackpane, content, JFXDialog.DialogTransition.CENTER);
			dialog.show();
			
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	// 추가 버튼 클릭시 이벤트
	private void btnAddClick() {
		for (int i = 0; i < btnAddList.size(); i++) {
			btnAddList.get(i).setOnAction(e->{
				JFXButton btn_AddMy = (JFXButton) e.getSource();
				System.out.println(btn_AddMy.getId());
			});
		}
	}

	// 재생 버튼 클릭시 이벤트
	private void btnPlayClick() {
		for (int i = 0; i < btnPlayList.size(); i++) {
			btnPlayList.get(i).setOnAction(e->{
				JFXButton btn_PlayMy = (JFXButton) e.getSource();
				System.out.println(btn_PlayMy.getId());
			});
		}
	}
	@FXML public void toDayChart() {
		try {
			toDayRank = FXCollections.observableArrayList(imhs.toDaySelect());
			btn_ToDay.setStyle("-fx-background-color:#9c0000;-fx-text-fill:#FFFFFF;");
			btn_Week.setStyle("-fx-background-color:#FFFFFF;");
			btn_Month.setStyle("-fx-background-color:#FFFFFF;");
			cb_main.setSelected(false);
			
			Calendar cal = Calendar.getInstance();
			String toDay = "";
			toDay = cal.get(Calendar.YEAR)+"." + (cal.get(Calendar.MONTH)+1) + 
					"." + cal.get(Calendar.DAY_OF_MONTH);
			la_Date.setText(toDay);
			
			musicList(toDayRank);
			
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	@FXML public void weekChart() {
		try {
			btn_ToDay.setStyle("-fx-background-color:#FFFFFF;");
			btn_Week.setStyle("-fx-background-color:#9c0000;-fx-text-fill:#FFFFFF;");
			btn_Month.setStyle("-fx-background-color:#FFFFFF;");
			cb_main.setSelected(false);
			
			Calendar cal = Calendar.getInstance();
			Calendar cal2 = Calendar.getInstance();
			cal.add(Calendar.DATE, -7);
			System.out.println(cal.get(Calendar.DAY_OF_MONTH));
			int temp = 0; 
			int beforeWeek = 0; // 지난주차
			
			// 주자 월요일 날짜 구하기
			if (cal.get(Calendar.DAY_OF_WEEK) > 2) {
				temp = cal.get(Calendar.DAY_OF_WEEK) - 2;
			}else if (cal.get(Calendar.DAY_OF_WEEK) < 2) {
				temp = 6;
			}
			cal.add(Calendar.DATE, -temp);
			
			// 몇주차 구하기
			int count = 1;
			cal2.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), 1);
			System.out.println(cal2.get(Calendar.MONTH));
			
			while(cal2.get(Calendar.DAY_OF_WEEK) != 2 ) {
				count++;
				cal2.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), count);
			}
			
			int first_week = cal2.get(Calendar.DAY_OF_MONTH);
			int last_week = cal.get(Calendar.DAY_OF_MONTH); 
			
			for(;first_week<=last_week; first_week+=7) {
				beforeWeek++;
			}
			
			String toDay = cal.get(Calendar.YEAR) + "." + (cal.get(Calendar.MONTH) +1) + "." + beforeWeek+"주차";
			
			la_Date.setText(toDay);
			
			Map day = new HashMap<String,String>();
			
			String startday = cal.get(Calendar.YEAR)+"/" + (cal.get(Calendar.MONTH)+1) + "/" + cal.get(Calendar.DAY_OF_MONTH);
			cal.add(Calendar.DATE, 6);
			String endday = cal.get(Calendar.YEAR)+"/" + (cal.get(Calendar.MONTH)+1) + "/" + cal.get(Calendar.DAY_OF_MONTH);
			day.put("startday", startday);
			
			day.put("endday", endday);
			
			weekRank = FXCollections.observableArrayList(imhs.periodSelect(day));
			musicList(weekRank);
			
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	@FXML public void monthChart() {
		try {
			
			btn_ToDay.setStyle("-fx-background-color:#FFFFFF;");
			btn_Week.setStyle("-fx-background-color:#FFFFFF;");
			btn_Month.setStyle("-fx-background-color:#9c0000;-fx-text-fill:#FFFFFF;");
			cb_main.setSelected(false);
			
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.MONTH, -1);
			String monthText = cal.get(Calendar.YEAR)+"."+(cal.get(Calendar.MONTH)+1);
			la_Date.setText(monthText);
			
			Map month = new HashMap<String,String>();
			cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), 1);
			String startday = cal.get(Calendar.YEAR)+"/" + (cal.get(Calendar.MONTH)+1) + "/" + cal.get(Calendar.DAY_OF_MONTH);
			String endday = cal.get(Calendar.YEAR)+"/" + (cal.get(Calendar.MONTH)+1) + "/" + cal.getActualMaximum(Calendar.DAY_OF_MONTH);
			month.put("startday", startday);
			month.put("endday", endday);
			
			monthRank = FXCollections.observableArrayList(imhs.periodSelect(month)); // 맵에서 가져왓어요
			musicList(monthRank);
			
		} catch (RemoteException e) {
			e.printStackTrace();
		}
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
	
	// 곡 리스트 로직 만드는 메서드
	private void musicList(ObservableList<Map> list) {
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
					la_PreRank.setPrefWidth(26);
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
				
				// 담기 버튼
				JFXButton btn_Movie = new JFXButton();
				btn_Movie.setRipplerFill(Color.valueOf("#9c0000"));
				btn_Movie.setAlignment(Pos.CENTER_LEFT);
				btn_Movie.setPrefWidth(30);
				btn_Movie.setPrefHeight(46);
				btn_Movie.setId(list.get(i).get("MUS_NO").toString());
					
					// 담기 아이콘
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
		mainBox.getChildren().add(vbox);
		
		btnPlayClick();
		btnAddClick();
		btnPutClick();
		btnMovieClick();
	}

	
}