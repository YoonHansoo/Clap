package kr.or.ddit.clap.main;

import java.io.File;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXTextField;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import kr.or.ddit.clap.service.music.IMusicService;
import kr.or.ddit.clap.service.ticket.ITicketService;
import kr.or.ddit.clap.vo.ticket.TicketBuyListVO;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

public class GameController implements Initializable{
	
	@FXML JFXButton btn_play;
	@FXML JFXButton btn_true;
	@FXML JFXButton btn_next;
	@FXML JFXTextField tf_musName;
	@FXML JFXTextField tf_singerName;
	@FXML JFXSlider slider_time;
	@FXML Label label_nowTime;
	@FXML Label label_count;
	@FXML AnchorPane anchorPane_main;
	@FXML StackPane stackpane;
	
	public  MediaPlayer mediaPlayer;
	private Media media;
	private Registry reg;
	private ITicketService its;
	private IMusicService ims;
	private List<TicketBuyListVO> buyticket;
	private ObservableList<Map> list;
	private static int count = 0;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			reg = LocateRegistry.getRegistry("localhost", 8888);
			its = (ITicketService) reg.lookup("ticket");
			ims = (IMusicService) reg.lookup("music");
			list = FXCollections.observableArrayList(ims.gameSelect());
			label_count.setText(count+"/3");
			if (LoginSession.session != null) {
				buyticket = its.buyfind(LoginSession.session.getMem_id());
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
		
		suffle();
		
		if (LoginSession.session == null) {
			btn_play.setDisable(true);
			btn_true.setDisable(true);
		}else if(LoginSession.session != null && buyticket.size() == 0) {
			btn_true.setDisable(true);
			btn_play.setDisable(true);
		}
		slider_time.setValue(0);
		
	}
	
	public void Ready(Label Label_nowTime, JFXSlider slider_time) {
		
		mediaPlayer.setOnReady(() -> {
			
			if (LoginSession.session == null) {
				mediaPlayer.setStopTime(new Duration(0));
			}else if(LoginSession.session != null && buyticket.size() == 0) {
				mediaPlayer.setStopTime(new Duration(0));
			}else {
				mediaPlayer.setStopTime(new Duration(5000));
			}
		
			int totalSeconds = (int) Math.floor(mediaPlayer.getTotalDuration().toSeconds());
			int tMinutes = totalSeconds / 60;
			int tSeconds = totalSeconds - tMinutes * 60;
			
			mediaPlayer.currentTimeProperty().addListener((observable, oldValue, newValue) -> {
				if (mediaPlayer != null) {
					int nowSeconds = (int) Math.floor(mediaPlayer.getCurrentTime().toSeconds());
					int minutes = nowSeconds / 60;
					int seconds = nowSeconds - minutes * 60;
					
					Label_nowTime.setText(String.format("%02d:%02d",minutes,seconds));
					double nowTime = mediaPlayer.getCurrentTime().toSeconds() / 
					                 mediaPlayer.getTotalDuration().toSeconds() * 100;
					slider_time.setValue(nowTime);
				}
			});
		});
	}
	
	public void setMedia(String path) {
		if (mediaPlayer != null) {
			mediaPlayer.stop();
		}
		media = new Media(new File(path).toURI().toString());
		mediaPlayer = new MediaPlayer(media);
		Ready(label_nowTime,slider_time);
		mediaPlayer.setVolume(1.0);
		mediaPlayer.play();
	}
	
	public void suffle() {
		for (int i = 0; i < list.size() * 10; i++) {
			int math = (int)(Math.random() * list.size());
			Map temp = list.get(math);
			list.remove(math);
			list.add(temp);
		}
	}

	@FXML public void playClick() {
		if (count < 3) {
			setMedia(list.get(count).get("MUS_FILE").toString());
		}
		
	}
	
	@FXML public void check() {
		String text = "";

		if (!tf_musName.getText().equals(list.get(count).get("MUS_TITLE").toString()) &&
			!tf_singerName.getText().equals(list.get(count).get("SING_NAME").toString())) {
			text = "공백이거나 틀렸습니다.";
			tf_musName.setText("");
			tf_singerName.setText("");
		} else if (tf_musName.getText().equals(list.get(count).get("MUS_TITLE").toString()) &&
			tf_singerName.getText().equals(list.get(count).get("SING_NAME").toString())) {
			text = "정답입니다.";
			tf_musName.setText("");
			tf_singerName.setText("");
			count++;
			label_count.setText(count+"/3");
			
		}
		
		Label label = new Label(text);
		label.setPrefWidth(300);
		label.setPrefHeight(80);
		label.setAlignment(Pos.CENTER);
		label.setStyle("-fx-font-family: \"-윤고딕320\"; -fx-font-size: 24;");
		
		
		JFXDialog dialog = new JFXDialog(stackpane, label, JFXDialog.DialogTransition.CENTER);
		dialog.setBackground(Background.EMPTY);
		dialog.show();
		
		if(count == 3) {
			System.out.println("상품주세요!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
			return;
		}
		
	}
	
	
	
	
}