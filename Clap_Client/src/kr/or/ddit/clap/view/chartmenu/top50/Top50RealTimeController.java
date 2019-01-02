package kr.or.ddit.clap.view.chartmenu.top50;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * 
 * @author 진민규
 *
 */

public class Top50RealTimeController implements Initializable{

	@FXML VBox mainBox;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		
		VBox vbox = new VBox();
		
		for (int i = 0; i < 20; i++) {
			// 파란색 라인 HBox 
			HBox h_Line = new HBox();
			h_Line.setPrefWidth(1004);
			h_Line.setPrefHeight(3);
			h_Line.setStyle("-fx-background-color: #70d3ff;");
			
				// 순위 곡정보 듣기 추가 담기 뮤비를 담는 전체 HBox 
				HBox h_Table = new HBox();
				h_Table.setPadding(new Insets(10,10,10,10));
				h_Table.setSpacing(10);
				
					// 체크박스,라벨,이미지뷰를 담는 HBox
					HBox h_CheckRankImg = new HBox();
					h_CheckRankImg.setAlignment(Pos.CENTER_LEFT);
					h_CheckRankImg.setSpacing(10);
					h_CheckRankImg.setPrefWidth(170);
					h_CheckRankImg.setPrefHeight(51);
					h_CheckRankImg.setMaxWidth(170);
					
						JFXCheckBox chb_Check = new JFXCheckBox();
						chb_Check.setPrefWidth(30);
						chb_Check.setPrefHeight(15);
						chb_Check.setCheckedColor(Color.valueOf("#70d3ff"));
						
						// 순위를 나타내는 Label
						Label la_Rank = new Label();
						h_CheckRankImg.setMargin(la_Rank, new Insets(0,0,0,5));
						la_Rank.setFont(Font.font("YDIYGO350", 18));
						la_Rank.setPrefWidth(23);
						la_Rank.setPrefHeight(23);
						la_Rank.setText("" + (i+1));
						
						// 앨범이미지를 표시하는 ImageView
						ImageView iv_Album = new ImageView();
						h_CheckRankImg.setMargin(iv_Album, new Insets(0,0,0,20));
						Image img_Path = new Image("http://image.genie.co.kr/Y/IMAGE/IMG_ALBUM/081/142/924/81142924_1546233762478_1_600x600.JPG");
						iv_Album.setImage(img_Path);
						iv_Album.setFitWidth(50);
						iv_Album.setFitHeight(50);
						
					
					// 곡제목 및 아티스트 VBox를 담는 HBox
					HBox h_MusicInfo = new HBox();
					h_Table.setMargin(h_MusicInfo, new Insets(0,0,0,60));
					h_MusicInfo.setAlignment(Pos.CENTER_LEFT);
					h_MusicInfo.setPrefWidth(400);
					h_MusicInfo.setPrefHeight(50);
						
						// 곡제목 및 아티스트명을 담는 VBox
						VBox v_MusicInfo = new VBox();
						v_MusicInfo.setAlignment(Pos.CENTER_LEFT);
						v_MusicInfo.setSpacing(5);

							// 곡제목을 담당하는 라벨
							Label la_MusicName = new Label();
							la_MusicName.setFont(Font.font("YDIYGO350", 25));
							la_MusicName.setText("곡제목");
							
							// 곡제목을 담당하는 라벨
							Label la_SingerName = new Label();
							la_SingerName.setFont(Font.font("YDIYGO350", 18));
							la_SingerName.setText("아티스트명");
							
							
					// 듣기 추가 담기 뮤비를 담는 HBox
					HBox h_MusicBtn = new HBox();
					h_MusicBtn.setAlignment(Pos.CENTER_LEFT);
					h_MusicBtn.setPrefWidth(170);
					h_MusicBtn.setPrefHeight(50);
					h_MusicBtn.setMaxWidth(170);
					h_MusicBtn.setSpacing(10);
					
						// 듣기 버튼
						JFXButton btn_Play = new JFXButton();
						btn_Play.setRipplerFill(Color.valueOf("#70d3ff"));
						btn_Play.setAlignment(Pos.CENTER_LEFT);
						btn_Play.setPrefWidth(30);
						btn_Play.setPrefHeight(46);
							
							// 듣기 아이콘
							FontAwesomeIcon icon_Play = new FontAwesomeIcon();
							icon_Play.setIconName("PLAY_CIRCLE_ALT");
							icon_Play.setFill(Color.valueOf("#318dc5"));
							icon_Play.setSize("1cm");
							btn_Play.setGraphic(icon_Play);
							
						// 추가 버튼
						JFXButton btn_Add = new JFXButton();
						btn_Add.setRipplerFill(Color.valueOf("#70d3ff"));
						btn_Add.setAlignment(Pos.CENTER_LEFT);
						btn_Add.setPrefWidth(30);
						btn_Add.setPrefHeight(46);
							
							// 추가 아이콘
							FontAwesomeIcon icon_Add = new FontAwesomeIcon();
							icon_Add.setIconName("PLUS");
							icon_Add.setFill(Color.valueOf("#318dc5"));
							icon_Add.setSize("1cm");
							btn_Add.setGraphic(icon_Add);
							
						// 담기 버튼
						JFXButton btn_Put = new JFXButton();
						btn_Put.setRipplerFill(Color.valueOf("#70d3ff"));
						btn_Put.setAlignment(Pos.CENTER_LEFT);
						btn_Put.setPrefWidth(30);
						btn_Put.setPrefHeight(46);
							
							// 담기 아이콘
							FontAwesomeIcon icon_Put = new FontAwesomeIcon();
							icon_Put.setIconName("FOLDER_ALT");
							icon_Put.setFill(Color.valueOf("#318dc5"));
							icon_Put.setSize("1cm");
							btn_Put.setGraphic(icon_Put);
						
						// 담기 버튼
						JFXButton btn_Movie = new JFXButton();
						btn_Movie.setRipplerFill(Color.valueOf("#70d3ff"));
						btn_Movie.setAlignment(Pos.CENTER_LEFT);
						btn_Movie.setPrefWidth(30);
						btn_Movie.setPrefHeight(46);
							
							// 담기 아이콘
							FontAwesomeIcon icon_Movie = new FontAwesomeIcon();
							icon_Movie.setIconName("VIMEO_SQUARE");
							icon_Movie.setFill(Color.valueOf("#318dc5"));
							icon_Movie.setSize("1cm");
							btn_Movie.setGraphic(icon_Movie);
						
						
							
							
							
							
					
						v_MusicInfo.getChildren().addAll(la_MusicName,la_SingerName);
					h_MusicBtn.getChildren().addAll(btn_Play,btn_Add,btn_Put,btn_Movie);
					h_MusicInfo.getChildren().add(v_MusicInfo);
					h_CheckRankImg.getChildren().addAll(chb_Check,la_Rank,iv_Album);
				h_Table.getChildren().addAll(h_CheckRankImg,h_MusicInfo,h_MusicBtn);
			vbox.getChildren().addAll(h_Line,h_Table);
			
		}
		
		mainBox.getChildren().add(vbox);
		
	}

}
