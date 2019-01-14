package kr.or.ddit.clap.view.singer.main;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class SingerAlbumController implements Initializable{
	@FXML ImageView imgView1, imgView2;
	@FXML AnchorPane albumPane;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		albumPane.setVisible(false);
		
		Image img = new Image("file:\\\\Sem-pc\\공유폴더\\Clap\\img\\album\\180˚.JPG");
		imgView1.setImage(img);
		imgView2.setImage(img);
	}

}
