package kr.or.ddit.clap.view.chartmenu.top50;

import java.net.URL;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXCheckBox;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

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
		
		for (int i = 0; i < 2; i++) {
			// 파란색 라인 HBox 
			HBox hLine = new HBox();
			hLine.setPrefWidth(1004);
			hLine.setPrefHeight(3);
			hLine.setStyle("-fx-background-color: #70d3ff;");
			
				// 순위 곡정보 듣기 추가 담기 뮤비를 담는 전체 HBox 
				HBox hTable = new HBox();
				hTable.setPadding(new Insets(10,10,10,10));
				hTable.setSpacing(10);
				
					// 체크박스,라벨,이미지뷰를 담는 HBox
					HBox hCheckRankImg = new HBox();
					hCheckRankImg.setPrefWidth(170);
					hCheckRankImg.setPrefHeight(51);
					hCheckRankImg.setMaxWidth(170);
					
					JFXCheckBox chbCheck = new JFXCheckBox();
					chbCheck.setPrefWidth(30);
					chbCheck.setPrefHeight(15);
					chbCheck.setCheckedColor(Color.valueOf("#70d3ff"));
					
			
			
			
					hCheckRankImg.getChildren().addAll(chbCheck);
				hTable.getChildren().add(hCheckRankImg);
			vbox.getChildren().addAll(hLine,hTable);
			
		}
		
		mainBox.getChildren().add(vbox);
		
	}

}
