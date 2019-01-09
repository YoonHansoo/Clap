package kr.or.ddit.clap.view.musicplayer;

import java.io.File;
import com.jfoenix.controls.JFXSlider;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.scene.control.Label;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.util.Duration;

    public class   MusicPlayer {
	private MediaPlayer mediaPlayer;
	private Media media;
	private Status status;
	
	
	public MusicPlayer() {
		
	}
	
	public MusicPlayer(String path) {
		media = new Media(new File(path).toURI().toString());
		mediaPlayer = new MediaPlayer(media);
	}
	
	public void play(FontAwesomeIcon icon_play) {
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
	
	public void stop() {
		mediaPlayer.stop();
	}
	
	public Status getStatus() {
		return mediaPlayer.getStatus();
	}
	
	public void musicSync(double value) {
		double time = (mediaPlayer.getTotalDuration().toSeconds() * 1000 / 100) * value;
		mediaPlayer.seek(new Duration(time));
	}
	
	public void Ready(Label Label_nowTime, Label Label_finalTime, JFXSlider slider_time) {
		
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
}
