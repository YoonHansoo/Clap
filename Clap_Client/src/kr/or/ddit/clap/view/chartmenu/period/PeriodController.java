package kr.or.ddit.clap.view.chartmenu.period;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import kr.or.ddit.clap.main.LoginSession;
import kr.or.ddit.clap.main.MusicMainController;
import kr.or.ddit.clap.service.musichistory.IMusicHistoryService;
import kr.or.ddit.clap.service.playlist.IPlayListService;
import kr.or.ddit.clap.view.chartmenu.dialog.MyAlbumDialogController;
import kr.or.ddit.clap.view.chartmenu.musiclist.MusicList;
import kr.or.ddit.clap.view.musicplayer.MusicPlayerController;
import kr.or.ddit.clap.vo.music.PlayListVO;

/**
 * 
 * @author 진민규
 *
 */

public class PeriodController implements Initializable{

	@FXML VBox mainBox;
	@FXML JFXCheckBox cb_main;
	@FXML JFXButton btn_Interior;
	@FXML JFXButton btn_Foreign;
	@FXML Label la_Date;
	@FXML StackPane stackpane;
	
	
	private Registry reg;
	private IMusicHistoryService imhs;
	private IPlayListService ipls;
	private MusicList musicList;
	private ObservableList<Map> interiorRank;
	private ObservableList<Map> foreignRank;
	private ObservableList<JFXCheckBox> cbnList = FXCollections.observableArrayList();
	private ObservableList<JFXButton> btnPlayList = FXCollections.observableArrayList();
	private ObservableList<JFXButton> btnAddList = FXCollections.observableArrayList();
	private ObservableList<JFXButton> btnPutList = FXCollections.observableArrayList();
	private ObservableList<JFXButton> btnMovieList = FXCollections.observableArrayList();
	private MusicPlayerController mpc;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			reg = LocateRegistry.getRegistry("localhost", 8888);
			imhs = (IMusicHistoryService) reg.lookup("history");
			ipls = (IPlayListService) reg.lookup("playlist");
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		musicList = new MusicList(cbnList, btnPlayList, btnAddList, btnPutList,
								  btnMovieList, mainBox, stackpane);
		
		// 일간 조회 차트 
		interiorChart();
	}
	
	// 메인 재생 버튼 이벤트
	@FXML public void btnMainPlay() {
		ArrayList<String> list = musicCheckList();
		playListInsert(list,true);
		cb_main.setSelected(false);
		mainCheck();
	}

	// 메인 추가 버튼 이벤트
	@FXML public void btnMainAdd() {
		ArrayList<String> list = musicCheckList();
		playListInsert(list,false);
		cb_main.setSelected(false);
		mainCheck();
	}

	// 메인 담기 버튼 이벤트
	@FXML public void btnMainPut() {
		ArrayList<String> list = musicCheckList();
		MyAlbumDialogController.mus_no.clear();
		MyAlbumDialogController.mus_no = list;
		musicList.myAlbumdialog();
		cb_main.setSelected(false);
		mainCheck();
	}
	
	// 전체 선택 및 해제 메서드
	@FXML public void mainCheck() {
		if (cb_main.isSelected()) {
			for(int i = 0; i < cbnList.size(); i++) {
				cbnList.get(i).setSelected(true);
			}
		} else {
			for(int i = 0; i < cbnList.size(); i++) {
				cbnList.get(i).setSelected(false);
			}
		}
	}
	
	// 체크 박스 선택한 곡넘버 보내기
	private ArrayList<String> musicCheckList() {
		ArrayList<String> list = new ArrayList<>();
		for (int i = 0; i < cbnList.size(); i++) {
			if (cbnList.get(i).isSelected()) {
				list.add(cbnList.get(i).getId());
			}
		}
		return list;
	}
	
	@FXML public void interiorChart() {
		
		try {
			Calendar cal = Calendar.getInstance();
			String year = "";
			year = ""+(cal.get(Calendar.YEAR)-1);
			la_Date.setText(year);
			year = (year.substring(year.length()-2, year.length()));
			
			interiorRank = FXCollections.observableArrayList(imhs.interiorSelect(year));
			btn_Interior.setStyle("-fx-background-color:#9c0000;-fx-text-fill:#FFFFFF;");
			btn_Foreign.setStyle("-fx-background-color:#FFFFFF;");
			cb_main.setSelected(false);
			
			musicList.musicList(interiorRank);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
	}

	@FXML public void foreignChart() {
		
		try {
			Calendar cal = Calendar.getInstance();
			String year = "";
			year = ""+(cal.get(Calendar.YEAR)-1);
			la_Date.setText(year);
			year = (year.substring(year.length()-2, year.length()));
			foreignRank = FXCollections.observableArrayList(imhs.foreignSelect(year));
			
			btn_Interior.setStyle("-fx-background-color:#FFFFFF;");
			btn_Foreign.setStyle("-fx-background-color:#9c0000;-fx-text-fill:#FFFFFF;");
			cb_main.setSelected(false);
			
			musicList.musicList(foreignRank);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	private void playListInsert(ArrayList<String> list, boolean play) {
		for (int i = 0; i < list.size(); i++) {
			PlayListVO vo = new PlayListVO();
			vo.setMus_no(list.get(i));
			vo.setMem_id(LoginSession.session.getMem_id());
			try {
				ipls.playlistInsert(vo);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			
			if (MusicMainController.musicplayer.isShowing()) {
				mpc = MusicMainController.playerLoad.getController();
				mpc.reFresh();
				if(play) {
					mpc.selectIndex();
				}
			}
		}
	}
}