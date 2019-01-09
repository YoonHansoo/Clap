package kr.or.ddit.clap.view.musicplayer;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import kr.or.ddit.clap.main.MusicMainController;
import javafx.fxml.FXML;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import com.jfoenix.controls.JFXSlider;
import javafx.scene.control.Label;

public class MusicPlayerController implements Initializable{
	
	@FXML FontAwesomeIcon icon_play;
	@FXML JFXSlider slider_time;
	@FXML Label Label_nowTime;
	@FXML Label Label_finalTime; 
	
	private Stage stage;
	private String path;
	private MusicPlayer player;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		stage = MusicMainController.musicplayer;
//		path = "C://Users//jin//Downloads//3.mp3";
		path = "////Sem-pc//공유폴더//Clap//mp3//2.mp3";
		player = new MusicPlayer(path);
		player.Ready(Label_nowTime, Label_finalTime, slider_time);
		
		stage.setOnCloseRequest(e -> { 
			player.stop();
		});
		
		slider_time.setOnMouseClicked(e->{
			player.musicSync(slider_time.getValue());
		});
		
	}

	@FXML public void onPlay() {
		player.play(icon_play);
	}

}
