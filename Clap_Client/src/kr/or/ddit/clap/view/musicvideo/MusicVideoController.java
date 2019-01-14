package kr.or.ddit.clap.view.musicvideo;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.util.Duration;

import com.jfoenix.controls.JFXSlider;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.scene.media.MediaView;
import javafx.scene.layout.HBox;

public class MusicVideoController implements Initializable{

	@FXML Label Label_nowTime;
	@FXML JFXSlider slider_time;
	@FXML Label Label_finalTime;
	@FXML JFXSlider slider_volum;
	@FXML FontAwesomeIcon icon_play;
	@FXML MediaView view;
	@FXML Label label_musName;
	
	public static MediaPlayer mediaPlayer;
	private Media media;
	private Status status;
	public static String path;
	public static String musName;
	public static String singerName;
	@FXML HBox hbox_info;
	
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		media = new Media(new File(path).toURI().toString());
		mediaPlayer = new MediaPlayer(media);
		view.setMediaPlayer(mediaPlayer);
		view.setPreserveRatio(false);
		Ready();
		icon_play.setIconName("PAUSE");
		label_musName.setText(musName+"-"+singerName);
		mediaPlayer.setAutoPlay(true);
		sliederMove();
	}
	
	public void Ready() {
		
		mediaPlayer.setOnReady(() -> {
			int totalSeconds = (int) Math.floor(mediaPlayer.getTotalDuration().toSeconds());
			int tMinutes = totalSeconds / 60;
			int tSeconds = totalSeconds - tMinutes * 60;
			
			Label_finalTime.setText(String.format("%02d:%02d",tMinutes,tSeconds));
				
			mediaPlayer.currentTimeProperty().addListener((observable, oldValue, newValue) -> {
				int nowSeconds = (int) Math.floor(mediaPlayer.getCurrentTime().toSeconds());
				int minutes = nowSeconds / 60;
				int seconds = nowSeconds - minutes * 60;
				
				Label_nowTime.setText(String.format("%02d:%02d",minutes,seconds));
				double nowTime = mediaPlayer.getCurrentTime().toSeconds() / 
				                 mediaPlayer.getTotalDuration().toSeconds() * 100;
				
				if (!slider_time.isValueChanging()) {
					slider_time.setValue(nowTime);
				}
			});
		});
	}

	@FXML public void onPlay() {
		
		status = mediaPlayer.getStatus();
		
		if (status != status.PLAYING) {
			icon_play.setIconName("PAUSE");
			mediaPlayer.play();
		}else  {
			icon_play.setIconName("PLAY");
			mediaPlayer.pause();
		}
		
	}

	@FXML public void volumClick() {
		if(!slider_volum.isVisible()) {
			slider_volum.setVisible(true);
			setVolum();
		} else {
			slider_volum.setVisible(false);
		}
	}
	
	public void setVolum() {
		if (mediaPlayer != null) {
			slider_volum.valueProperty().addListener(observable -> {
					mediaPlayer.setVolume(slider_volum.getValue()/100);
			});
		}
	}
	
	public void sliederMove() {
		slider_time.setOnMouseClicked(e->{
			double time = (mediaPlayer.getTotalDuration().toSeconds() * 1000 / 100) * slider_time.getValue();
			mediaPlayer.seek(new Duration(time));
		});
	}

	@FXML public void hboxOn() {
		hbox_info.setVisible(true);
	}

	@FXML public void hboxClose() {
		hbox_info.setVisible(false);
	}

}
