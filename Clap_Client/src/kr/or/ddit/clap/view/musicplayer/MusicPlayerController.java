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
import kr.or.ddit.clap.service.myalbum.IMyAlbumService;
import kr.or.ddit.clap.service.playlist.IPlayListService;
import kr.or.ddit.clap.vo.music.PlayListVO;
import kr.or.ddit.clap.vo.myalbum.MyAlbumVO;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXSlider;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.paint.Color;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.ScrollPane;
import com.jfoenix.controls.JFXTabPane;

public class MusicPlayerController implements Initializable{

	@FXML JFXSlider slider_time;
	@FXML JFXSlider slider_volum;
	@FXML Label label_musicName;
	@FXML Label label_singerName;
	@FXML Label Label_nowTime;
	@FXML Label Label_finalTime;
	@FXML Label label_lyrics;
	@FXML TreeTableColumn<PlayListVO, JFXCheckBox> tcol_playListCheck;
	@FXML TreeTableColumn<PlayListVO, VBox> tcol_playListVbox;
	@FXML JFXTreeTableView<PlayListVO> t_playListTable;
	@FXML JFXTreeTableView<MyAlbumVO> t_myAlbumTable;
	@FXML TreeTableColumn<MyAlbumVO, String> tcol_myAlbum;
	@FXML FontAwesomeIcon icon_retweet;
	@FXML FontAwesomeIcon icon_random;
	@FXML FontAwesomeIcon icon_play;
	@FXML FontAwesomeIcon icon_lyrics;
	@FXML ScrollPane scroll_lyrics;
	@FXML ImageView imgview_album;
	@FXML JFXTabPane tabpane_main;

	private Stage stage;
	private MusicPlayer player;
	private ObservableList<PlayListVO> playList;
	private ObservableList<MyAlbumVO> myAlbumList;
	private IPlayListService ipls;
	private IMyAlbumService imas;
	private Registry reg;
	private boolean retweenFlag = false;
	private boolean randomFlag = false;
	private int[] randomIndex;
	private int count;
	private TreeItem<PlayListVO> playListRoot;
	private TreeItem<MyAlbumVO> myAlbumListRoot;
	private boolean refreshFlag = false;
	private int mus_index;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		try {
			reg = LocateRegistry.getRegistry("localhost", 8888);
			ipls = (IPlayListService) reg.lookup("playlist");
			imas = (IMyAlbumService) reg.lookup("myalbum");
			playList = FXCollections.observableArrayList(ipls.playlistSelect(LoginSession.session.getMem_id()));
			myAlbumList = FXCollections.observableArrayList(imas.myAlbumSelect(LoginSession.session.getMem_id()));
			player = new MusicPlayer();
			stage = MusicMainController.musicplayer;
			scroll_lyrics.setVisible(false);
		} catch (RemoteException e2) {
			e2.printStackTrace();
		} catch (NotBoundException e1) {
			e1.printStackTrace();
		}

		tcol_playListCheck.setCellValueFactory( param -> 
		new SimpleObjectProperty<JFXCheckBox>(param.getValue().getValue().getCheckbox())
		);
		
		tcol_playListVbox.setCellValueFactory( param ->
			new SimpleObjectProperty<VBox>(param.getValue().getValue().getVbox())
		);
		
		playListRoot = new RecursiveTreeItem<>(playList, RecursiveTreeObject::getChildren);
		t_playListTable.setRoot(playListRoot);
		t_playListTable.setShowRoot(false);
		
		tabpane_main.getSelectionModel().selectedIndexProperty().addListener(observable-> {
			if(tabpane_main.getSelectionModel().isSelected(1)) {
				System.out.println("ddd");
			}
		});
		

		tcol_myAlbum.setCellValueFactory( param ->
			new SimpleStringProperty(param.getValue().getValue().getMyalb_name())
		);
		
		myAlbumListRoot = new RecursiveTreeItem<>(myAlbumList, RecursiveTreeObject::getChildren);
		t_myAlbumTable.setRoot(myAlbumListRoot);
		t_myAlbumTable.setShowRoot(false);
		
		tabpane_main.getSelectionModel().selectedIndexProperty().addListener(observable-> {
			if(tabpane_main.getSelectionModel().isSelected(1)) {
				System.out.println("ddd");
			}
		});
		
		close();
		seletemodel();
		sliederMove();

	}

	@FXML public void onPlay() {
		if (playList.size() != 0) {
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
	
	@FXML public void volumClick() {
		reFresh();
		if(!slider_volum.isVisible()) {
			slider_volum.setVisible(true);
			setVolum();
		} else {
			slider_volum.setVisible(false);
		}
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
		player.setMedia(playList.get(index).getMus_file());
		label_musicName.setText(playList.get(index).getMus_title());
		label_singerName.setText(playList.get(index).getSing_name());
		imgview_album.setImage(new Image(playList.get(index).getAlb_image()));
		label_lyrics.setText(playList.get(index).getMus_lyrics());
		player.mediaPlayer.setVolume(slider_volum.getValue()/100);
		player.Ready(Label_nowTime, Label_finalTime, slider_time);
	}
	
	public void close() {
		stage.setOnCloseRequest(e -> { 
			if (playList.size() != 0) {
				if(player.mediaPlayer != null && player.getStatus() == Status.PLAYING ) {
					player.stop();
				}
			}
		});
	}
	
	public void seletemodel() {
		t_playListTable.getSelectionModel().selectedIndexProperty().addListener((observable,oldValue,newValue) -> {
				if (refreshFlag) {
					t_playListTable.getSelectionModel().select(mus_index);
					refreshFlag=false;
				}else {
					mus_index = newValue.intValue();
					ready(mus_index);
					onPlay();
				}
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
	
	public void setVolum() {
		if (player.mediaPlayer != null) {
			slider_volum.valueProperty().addListener(observable -> {
					player.mediaPlayer.setVolume(slider_volum.getValue()/100);
			});
		}
	}
	
	public void reFresh() {
		refreshFlag = true;
		playList.add(playList.get(0));
		playListRoot = new RecursiveTreeItem<>(playList, RecursiveTreeObject::getChildren);
		t_playListTable.setRoot(playListRoot);
		
	}
	
	public void randomSuffle() {
		
		if (randomIndex == null || player.mediaPlayer.getStatus() == Status.STOPPED) {
			randomIndex = new int[playList.size()];
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
			index = t_playListTable.getSelectionModel().getSelectedIndex() + 1;
		} else {
			index = t_playListTable.getSelectionModel().getSelectedIndex() - 1;
		}
		
		
		if (retweenFlag && !randomFlag && forward) {
			
			if (index == playList.size()) {
				t_playListTable.getSelectionModel().select(0);
			}else {
				t_playListTable.getSelectionModel().select(index);
			}
			
		}else if (retweenFlag && !randomFlag && !forward){
			
			if (index < 0) {
				t_playListTable.getSelectionModel().select(playList.size()-1);
			}else {
				t_playListTable.getSelectionModel().select(index);
			}
			
		}else if(randomFlag && !retweenFlag) {
			randomSuffle();
			if (!(count >= randomIndex.length)) {
				t_playListTable.getSelectionModel().select(randomIndex[count]);
				count++;
			}else {
				player.stop();
			}
			
		}else if(randomFlag && !retweenFlag && !forward) {
			randomSuffle();
			
		}else if (retweenFlag && randomFlag) {
			t_playListTable.getSelectionModel().select((int)(Math.random() * playList.size()));
		} else if(!retweenFlag && !randomFlag && index < playList.size()) {
			t_playListTable.getSelectionModel().select(index);
		}
	}

	
}
