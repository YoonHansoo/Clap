package kr.or.ddit.clap.view.musicplayer;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.fxml.FXML;
import com.jfoenix.controls.JFXButton;

public class MusicPlayerController implements Initializable{
	private MediaPlayer mediaPlayer;
	private Media media;
	private String path;
	@FXML JFXButton play; 

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		path = "kr/or/ddit/clap/view/musicplayer/1.mp3";
		media = new Media(new File(path).toURI().toString());
		mediaPlayer = new MediaPlayer(media);
	}

	@FXML public void onPlay() {
		mediaPlayer.play();
	}

}
