package kr.or.ddit.clap.view.musicplayer;

import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import kr.or.ddit.clap.main.LoginSession;
import kr.or.ddit.clap.main.MusicMainController;
import kr.or.ddit.clap.service.playlist.IPlayListService;
import kr.or.ddit.clap.vo.music.PlayListVO;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXSlider;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.paint.Color;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import com.jfoenix.controls.JFXButton;
import javafx.scene.control.ScrollPane;
import com.jfoenix.controls.JFXPopup;

public class MusicPlayerController implements Initializable{
	
	@FXML FontAwesomeIcon icon_play;
	@FXML JFXSlider slider_time;
	@FXML Label Label_nowTime;
	@FXML Label Label_finalTime; 
	@FXML TreeTableColumn<PlayListVO, JFXCheckBox> tcol_check;
	@FXML TreeTableColumn<PlayListVO, VBox> tcol_vbox;
	@FXML JFXTreeTableView<PlayListVO> t_table;
	@FXML Label label_musicName;
	@FXML Label label_singerName;
	@FXML ImageView imgview_album;
	@FXML JFXButton btn_backward;
	@FXML JFXButton btn_forward;
	@FXML FontAwesomeIcon icon_retweet;
	@FXML FontAwesomeIcon icon_random;
	@FXML Label label_lyrics;
	@FXML ScrollPane scroll_lyrics;
	@FXML FontAwesomeIcon icon_lyrics;
	@FXML JFXPopup popup;
	
	private Stage stage;
	private MusicPlayer player;
	private ObservableList<PlayListVO> list;
	private IPlayListService ipls;
	private Registry reg;
	private ObservableList<MediaPlayer> playlist;
	private boolean retweenFlag = false;
	private boolean randomFlag = false;
	private int[] randomIndex;
	private int count;
	private JFXSlider slider_volum;
	@FXML JFXButton btn_volum;
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		try {
			reg = LocateRegistry.getRegistry("localhost", 8888);
			ipls = (IPlayListService) reg.lookup("playlist");
			list = FXCollections.observableArrayList(ipls.playlistSelect(LoginSession.session.getMem_id()));
			player = new MusicPlayer();
			stage = MusicMainController.musicplayer;
			scroll_lyrics.setVisible(false);
			popupCreative();
		} catch (RemoteException e2) {
			e2.printStackTrace();
		} catch (NotBoundException e1) {
			e1.printStackTrace();
		}

		tcol_check.setCellValueFactory( param -> 
		new SimpleObjectProperty<JFXCheckBox>(param.getValue().getValue().getCheckbox())
		);
		
		tcol_vbox.setCellValueFactory( param ->
			new SimpleObjectProperty<VBox>(param.getValue().getValue().getVbox())
		);
		
		TreeItem<PlayListVO> root = new RecursiveTreeItem<>(list, RecursiveTreeObject::getChildren);
		t_table.setRoot(root);
		t_table.setShowRoot(false);
		
		close();
		seletemodel();
		sliederMove();

	}

	@FXML public void onPlay() {
		if (list.size() != 0) {
			player.play(icon_play);
			endMusic();
		}
	}

	@FXML public void forWard() {
		
		selectIndex(true);
		
	}

	@FXML public void backWard() {
		
		selectIndex(false);
	}
	
	@FXML public void retweetClick() {
		if (!retweenFlag) {
			icon_retweet.setFill(Color.valueOf("#9c0000"));
			retweenFlag = true;
		} else {
			icon_retweet.setFill(Color.valueOf("#FFFFFF"));
			retweenFlag = false;
		}
	}
	
	@FXML public void randomClick() {
		if (!randomFlag) {
			icon_random.setFill(Color.valueOf("#9c0000"));
			randomFlag = true;
		} else {
			icon_random.setFill(Color.valueOf("#FFFFFF"));
			randomFlag = false;
		} 
	}

	@FXML public void popupVolum() {
		popup.show(JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.LEFT,170,-88);
	}

	@FXML public void lyricsClick() {
		if(!scroll_lyrics.isVisible()) {
			scroll_lyrics.setVisible(true);
			imgview_album.setVisible(false);
			icon_lyrics.setFill(Color.valueOf("#9c0000"));
		} else {
			scroll_lyrics.setVisible(false);
			imgview_album.setVisible(true);
			icon_lyrics.setFill(Color.valueOf("#FFFFFF"));
		}
	}
	
	public void ready(int index) {
		player.setMedia(list.get(index).getMus_file());
		label_musicName.setText(list.get(index).getMus_title());
		label_singerName.setText(list.get(index).getSing_name());
		imgview_album.setImage(new Image(list.get(index).getAlb_image()));
		label_lyrics.setText(list.get(index).getMus_lyrics());
		player.Ready(Label_nowTime, Label_finalTime, slider_time);
	}
	
	public void close() {
		stage.setOnCloseRequest(e -> { 
			if (list.size() != 0) {
				if(player.mediaPlayer != null && player.getStatus() == Status.PLAYING ) {
					player.stop();
				}
			}
		});
	}
	
	public void seletemodel() {
		t_table.getSelectionModel().selectedIndexProperty().addListener((observable,oldValue,newValue) -> {

			int index = newValue.intValue();
			System.out.println(index);
			System.out.println(list.get(index).getMus_file());
			ready(index);
			onPlay();
	});
	}
	
	public void sliederMove() {
		slider_time.setOnMouseClicked(e->{
			player.musicSync(slider_time.getValue());
		});
	}
	
	public void endMusic() {
		player.mediaPlayer.setOnEndOfMedia(()-> {
			selectIndex(true);
		});
	}
	
	public void popupCreative() {
		slider_volum = new JFXSlider();
		slider_volum.setPrefWidth(60);
		slider_volum.setPrefHeight(20);
		HBox hbox = new HBox(slider_volum);
		hbox.setStyle("-fx-background-color: white;");
		
		popup.setContent(hbox);
		popup.setSource(label_musicName);
	}
	
	public void volum() {
		if (player.mediaPlayer != null) {
			slider_volum.setOnMouseClicked(e->{
				player.mediaPlayer.setVolume(slider_time.getValue());
			});
			
		}
	}
	
	public void randomSuffle() {
		
		if (randomIndex == null || player.mediaPlayer.getStatus() == Status.STOPPED) {
			randomIndex = new int[list.size()];
			int temp;
			
			for (int i = 0; i < randomIndex.length; i++) {
				randomIndex[i] = i;
			}
			
			for (int i = 0; i < randomIndex.length * 3; i++) {
				int math = (int)(Math.random() * randomIndex.length);
				temp = randomIndex[0];
				randomIndex[0] = randomIndex[math];
				randomIndex[math] = temp;
			}
			count = 0;
		}
	}
	
	public void selectIndex(boolean forward) {
		int index;
		if (forward) {
			index = t_table.getSelectionModel().getSelectedIndex() + 1;
		} else {
			index = t_table.getSelectionModel().getSelectedIndex() - 1;
		}
		
		
		if (retweenFlag && !randomFlag && forward) {
			
			if (index == list.size()) {
				t_table.getSelectionModel().select(0);
			}else {
				t_table.getSelectionModel().select(index);
			}
			
		}else if (retweenFlag && !randomFlag && !forward){
			
			if (index < 0) {
				t_table.getSelectionModel().select(list.size()-1);
			}else {
				t_table.getSelectionModel().select(index);
			}
			
		}else if(randomFlag && !retweenFlag) {
			randomSuffle();
			if (!(count >= randomIndex.length)) {
				t_table.getSelectionModel().select(randomIndex[count]);
				count++;
			}else {
				player.stop();
			}
			
		}else if(randomFlag && !retweenFlag && !forward) {
			randomSuffle();
			
		}else if (retweenFlag && randomFlag) {
			t_table.getSelectionModel().select((int)(Math.random() * list.size()));
		} else if(!retweenFlag && !randomFlag && index < list.size()) {
			t_table.getSelectionModel().select(index);
		}
	}

}
