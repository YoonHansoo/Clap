package kr.or.ddit.clap.view.chartmenu.top50;

import java.net.URL;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXCheckBox;
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
					
					Label la_Rank = new Label();
					h_CheckRankImg.setMargin(la_Rank, new Insets(0,0,0,5));
					la_Rank.setFont(Font.font("YDIYGO350", 18));
					la_Rank.setText("" + (i+1));
					
					ImageView iv_album = new ImageView();
					Image img_Path = new Image("http://image.genie.co.kr/Y/IMAGE/IMG_ALBUM/081/142/924/81142924_1546233762478_1_600x600.JPG");
					iv_album.setImage(img_Path);
					iv_album.setFitWidth(50);
					iv_album.setFitHeight(50);
					h_CheckRankImg.setMargin(iv_album, new Insets(0,0,0,20));
					
			
			
			
					h_CheckRankImg.getChildren().addAll(chb_Check,la_Rank,iv_album);
				h_Table.getChildren().add(h_CheckRankImg);
			vbox.getChildren().addAll(h_Line,h_Table);
			
		}
		
		mainBox.getChildren().add(vbox);
		
	}

}
