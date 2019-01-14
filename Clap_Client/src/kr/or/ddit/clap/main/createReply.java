package kr.or.ddit.clap.main;

import com.jfoenix.controls.JFXButton;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class createReply {
	//댓글 만들어주는 메서드 
	public static void creatReply(VBox mainBox) {
		
		//댓글 창 생성 
		 
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
		
	}
}
