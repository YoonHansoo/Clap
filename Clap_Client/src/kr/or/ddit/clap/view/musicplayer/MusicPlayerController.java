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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import com.jfoenix.controls.JFXButton;

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
	
	private Stage stage;
	private MusicPlayer player;
	private ObservableList<PlayListVO> list;
	private IPlayListService ipls;
	private Registry reg;
	private ObservableList<MediaPlayer> playlist;
	
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		try {
			reg = LocateRegistry.getRegistry("localhost", 8888);
			ipls = (IPlayListService) reg.lookup("playlist");
			list = FXCollections.observableArrayList(ipls.playlistSelect(LoginSession.session.getMem_id()));
			player = new MusicPlayer();
			stage = MusicMainController.musicplayer;
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
		int index = t_table.getSelectionModel().getSelectedIndex() + 1;
		if(index < list.size()) {
			t_table.getSelectionModel().select(index);
		}
	}

	@FXML public void backWard() {
		int index = t_table.getSelectionModel().getSelectedIndex() - 1;
		if(index >= 0) {
			t_table.getSelectionModel().select(index);
		}
	}
	
	@FXML public void retweetClick() {
//		if(list.size())
	}
	
	@FXML public void randomClick() {}

	@FXML public void volumeClick() {}

	@FXML public void lyricsClick() {}
	
	public void ready(int index) {
		player.setMedia(list.get(index).getMus_file());
		label_musicName.setText(list.get(index).getMus_title());
		label_singerName.setText(list.get(index).getSing_name());
		imgview_album.setImage(new Image(list.get(index).getAlb_image()));
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
			System.out.println("dafsdfs");
			int index = t_table.getSelectionModel().getSelectedIndex() + 1;
			if(index < list.size()) {
				t_table.getSelectionModel().select(index);
			}
			
		});
	}
}
