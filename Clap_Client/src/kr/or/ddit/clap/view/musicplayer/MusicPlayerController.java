package kr.or.ddit.clap.view.musicplayer;

import java.io.IOException;
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
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXSlider;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.layout.AnchorPane;
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
import com.jfoenix.controls.JFXButton;

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
	@FXML FontAwesomeIcon icon_retweet;
	@FXML FontAwesomeIcon icon_random;
	@FXML FontAwesomeIcon icon_play;
	@FXML FontAwesomeIcon icon_lyrics;
	@FXML ScrollPane scroll_lyrics;
	@FXML ImageView imgview_album;
	@FXML JFXTabPane tabpane_main;
	@FXML AnchorPane anchorpane_myalbum;
	@FXML JFXCheckBox btn_check;
	@FXML JFXButton btn_add;
	@FXML JFXButton btn_del;


	private Stage stage;
	private MusicPlayer player;
	private ObservableList<PlayListVO> playList;
	private ObservableList<String> addMus_no;
	private ObservableList<String> delMus_no;
	private Registry reg;
	private IPlayListService ipls;
	private boolean retweenFlag = false;
	private boolean randomFlag = false;
	private boolean refreshFlag = false;
	private int[] randomIndex;
	private int count;
	private int mus_index;
	private TreeItem<PlayListVO> playListRoot;
	private MyAlbumListController mal;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		try {
			reg = LocateRegistry.getRegistry("localhost", 8888);
			ipls = (IPlayListService) reg.lookup("playlist");
			playList = FXCollections.observableArrayList(ipls.playlistSelect(LoginSession.session.getMem_id()));
			player = new MusicPlayer();
			stage = MusicMainController.musicplayer;
			scroll_lyrics.setVisible(false);
			btn_add.setVisible(false);
			addMus_no = FXCollections.observableArrayList();
			delMus_no = FXCollections.observableArrayList();
			t_playListTable.setPlaceholder(new Label(""));
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
		
	
		
		close();
		playListTableSelet();
		sliederMove();
		tapPaneSelete();
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
	
	@FXML public void playListAdd() {
		addMus_no.clear();
		mal = MyAlbumController.loader.getController();
		
		for (int i = 0; i < mal.myalbumList.size(); i++) {
			if (mal.t_table.getTreeItem(i).getValue().getChBox1().isSelected()) {
				addMus_no.add(mal.t_table.getTreeItem(i).getValue().getMus_no());
			}
		}
		
		PlayListVO vo = new PlayListVO();
		for (int i = 0; i < addMus_no.size(); i++) {
			vo.setMem_id(LoginSession.session.getMem_id());
			vo.setMus_no(addMus_no.get(i));
			try {
				ipls.playlistInsert(vo);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		btn_check.setSelected(false);
		allCheck();
		reFresh();
	}

	@FXML public void playListDelete() {
		delMus_no.clear();
		
		for (int i = 0; i < playList.size(); i++) {
			if (t_playListTable.getTreeItem(i).getValue().getCheckbox1().isSelected()) {
				delMus_no.add(t_playListTable.getTreeItem(i).getValue().getMus_no());
			}
		}
		
		PlayListVO vo = new PlayListVO();
		for (int i = 0; i < delMus_no.size(); i++) {
			vo.setMem_id(LoginSession.session.getMem_id());
			vo.setMus_no(delMus_no.get(i));
			try {
				ipls.playlistDelete(vo);
				if (player.mediaPlayer != null) {
					player.mediaPlayer.stop();
					label_musicName.setText("재생 목록이 없습니다");
					label_singerName.setText("듣고 싶은 곡을 선택해 보세요!");
					imgview_album.setImage(null);
				}
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		btn_check.setSelected(false);
		reFresh();
	}
	
	@FXML public void allCheck() {
		if (MyAlbumController.loader != null && tabpane_main.getSelectionModel().isSelected(1)) {
			mal = MyAlbumController.loader.getController();
			if (btn_check.isSelected()) {
				for (int i = 0; i < mal.myalbumList.size(); i++) {
					mal.t_table.getTreeItem(i).getValue().getChBox1().setSelected(true);
				}
				
			}else {
				for (int i = 0; i < mal.myalbumList.size(); i++) {
					mal.t_table.getTreeItem(i).getValue().getChBox1().setSelected(false);
				}
			}
		}
		
		if (tabpane_main.getSelectionModel().isSelected(0)) {
			if (btn_check.isSelected()) {
				for (int i = 0; i < playList.size(); i++) {
					t_playListTable.getTreeItem(i).getValue().getCheckbox1().setSelected(true);
				}
			}else {
				for (int i = 0; i < playList.size(); i++) {
					t_playListTable.getTreeItem(i).getValue().getCheckbox1().setSelected(false);
				}
			}
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
	
	public void playListTableSelet() {
		
		t_playListTable.getSelectionModel().selectedIndexProperty().addListener((observable,oldValue,newValue) -> {
			System.out.println("asdf");
			if (refreshFlag && player.mediaPlayer != null) {
				t_playListTable.getSelectionModel().select(mus_index);
				refreshFlag=false;
			}else {
				mus_index = newValue.intValue();
				ready(mus_index);
				onPlay();
			}
		});
	}
	
	public void tapPaneSelete() {
		
		tabpane_main.getSelectionModel().selectedIndexProperty().addListener(new InvalidationListener() {
			@Override
			public void invalidated(Observable observable) {
				if (tabpane_main.getSelectionModel().isSelected(1)) {
					try {
						btn_del.setVisible(false);
						AnchorPane pane = FXMLLoader.load(getClass().getResource("MyAlbum.fxml"));
						anchorpane_myalbum.getChildren().removeAll();
						anchorpane_myalbum.getChildren().setAll(pane);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}else {
					btn_del.setVisible(true);
					btn_add.setVisible(false);
				}
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
		try {
			refreshFlag = true;
			playList = FXCollections.observableArrayList(ipls.playlistSelect(LoginSession.session.getMem_id()));
			playListRoot = new RecursiveTreeItem<>(playList, RecursiveTreeObject::getChildren);
			t_playListTable.setRoot(playListRoot);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
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
	
	public void selectIndex() {
		t_playListTable.getSelectionModel().select(playList.size()-1);
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
