package kr.or.ddit.clap.view.singer.singer;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class ShowSingerDetailController  implements Initializable {

	public static String singerNo; 
	@FXML Label label_singNo;
	@FXML AnchorPane contents;
	@FXML Label label_singerName1;
	@FXML Label label_singerName2;
	@FXML Label label_ActType;
	@FXML Label label_ActEra;
	@FXML Label label_DebutEra;
	@FXML Label label_DebutMus;
	@FXML Label label_Nation;
	@FXML Label label_LikeCnt;
	@FXML Label label_Intro;
	
	
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		System.out.println("가수번호:" +singerNo );
	}




	@FXML public void wideView() {}




	@FXML public void updateSinger() {}




	@FXML public void deleteSinger() {}

	
}
