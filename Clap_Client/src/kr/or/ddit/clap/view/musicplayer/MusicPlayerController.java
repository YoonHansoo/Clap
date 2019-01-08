package kr.or.ddit.clap.view.musicplayer;

import java.io.File;
import java.net.URL;
import java.text.Format;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.stage.Stage;
import kr.or.ddit.clap.main.MusicMainController;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import com.jfoenix.controls.JFXSlider;
import javafx.scene.control.Label;

public class MusicPlayerController implements Initializable{
	private Stage stage;
	private MediaPlayer mediaPlayer;
	private Media media;
	private String path;
	private Status status;
	
	@FXML FontAwesomeIcon icon_play;
	@FXML JFXSlider slider_time;
	@FXML Label Label_nowTime;
	@FXML Label Label_finalTime; 

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		stage = MusicMainController.musicplayer;
		path = "////Sem-pc//공유폴더//Clap//mp3//1.mp3";
		media = new Media(new File(path).toURI().toString());
		mediaPlayer = new MediaPlayer(media);
		
		stage.setOnCloseRequest(e -> {
			mediaPlayer.stop();
		});
		
		mediaPlayer.setOnReady(new Runnable() {
			
			
			@Override
			public void run() {
				int totalSeconds = (int) Math.floor(mediaPlayer.getTotalDuration().toSeconds());
				int tMinutes = totalSeconds / 60;
				int tSeconds = totalSeconds - tMinutes * 60;
				
				Label_finalTime.setText(String.format("%02d:%02d",tMinutes,tSeconds));
				
				mediaPlayer.currentTimeProperty().addListener(new ChangeListener() {
					@Override
					public void changed(ObservableValue observable, Object oldValue, Object newValue) {
					
					int nowSeconds = (int) Math.floor(mediaPlayer.getCurrentTime().toSeconds());
					int minutes = nowSeconds / 60;
					int seconds = nowSeconds - minutes * 60;
					
					Label_nowTime.setText(String.format("%02d:%02d",minutes,seconds));
					double nowTime = mediaPlayer.getCurrentTime().toSeconds() / 
					                 mediaPlayer.getTotalDuration().toSeconds() * 100;
						slider_time.setValue(nowTime);
					}
				});
			}
		});
		
		
	}

	@FXML public void onPlay() {
		status = mediaPlayer.getStatus();
		
		if (status == Status.PAUSED ||
			status == Status.READY ||
			status == Status.STOPPED) {
			icon_play.setIconName("PAUSE");
			mediaPlayer.play();
		}else  {
			icon_play.setIconName("PLAY");
			mediaPlayer.pause();
		}
	}

}
