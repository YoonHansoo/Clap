package kr.or.ddit.clap.view.chartmenu.genre;

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
import kr.or.ddit.clap.service.musichistory.IMusicHistoryService;
import kr.or.ddit.clap.view.chartmenu.dialog.MyAlbumDialogController;
import kr.or.ddit.clap.view.chartmenu.musiclist.MusicList;

/**
 * 
 * @author 진민규
 *
 */

public class GenreController implements Initializable{

	@FXML VBox mainBox;
	@FXML JFXCheckBox cb_main;
	@FXML JFXButton btn_Song;
	@FXML JFXButton btn_Pop;
	@FXML JFXButton btn_Ost;
	@FXML JFXButton btn_Other;
	@FXML Label la_Date;
	@FXML StackPane stackpane;
	
	private Registry reg;
	private IMusicHistoryService imhs;
	private MusicList musicList;
	private ObservableList<Map> songRank;
	private ObservableList<Map> popRank;
	private ObservableList<Map> ostRank;
	private ObservableList<Map> otherRank;
	private ObservableList<JFXCheckBox> cbnList = FXCollections.observableArrayList();
	private ObservableList<JFXButton> btnPlayList = FXCollections.observableArrayList();
	private ObservableList<JFXButton> btnAddList = FXCollections.observableArrayList();
	private ObservableList<JFXButton> btnPutList = FXCollections.observableArrayList();
	private ObservableList<JFXButton> btnMovieList = FXCollections.observableArrayList();
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			reg = LocateRegistry.getRegistry("localhost", 8888);
			imhs = (IMusicHistoryService) reg.lookup("history");
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
		songChart();
	}
	
	// 메인 재생 버튼 이벤트
	@FXML public void btnMainPlay() {
		ArrayList<String> list = musicCheckList();
		System.out.println(list);
	}

	// 메인 추가 버튼 이벤트
	@FXML public void btnMainAdd() {
		ArrayList<String> list = musicCheckList();
		System.out.println(list);
	}

	// 메인 담기 버튼 이벤트
	@FXML public void btnMainPut() {
		ArrayList<String> list = musicCheckList();
		MyAlbumDialogController.mus_no.clear();
		MyAlbumDialogController.mus_no = list;
		musicList.myAlbumdialog();
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
	
	// 가요장르
	@FXML public void songChart() {
		try {
			songRank = FXCollections.observableArrayList(imhs.toDaySelect());
			btn_Song.setStyle("-fx-background-color:#9c0000;-fx-text-fill:#FFFFFF;");
			btn_Pop.setStyle("-fx-background-color:#FFFFFF;");
			btn_Ost.setStyle("-fx-background-color:#FFFFFF;");
			btn_Other.setStyle("-fx-background-color:#FFFFFF;");
			cb_main.setSelected(false);
			
			Calendar cal = Calendar.getInstance();
			String toDay = "";
			toDay = cal.get(Calendar.YEAR)+"." + (cal.get(Calendar.MONTH)+1) + 
					"." + cal.get(Calendar.DAY_OF_MONTH);
			la_Date.setText(toDay);
			
			musicList.musicList(songRank);
			
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	// POP장르
	@FXML public void popChart() {
		try {
			popRank = FXCollections.observableArrayList(imhs.toDaySelect());
			btn_Song.setStyle("-fx-background-color:#FFFFFF;");
			btn_Pop.setStyle("-fx-background-color:#9c0000;-fx-text-fill:#FFFFFF;");
			btn_Ost.setStyle("-fx-background-color:#FFFFFF;");
			btn_Other.setStyle("-fx-background-color:#FFFFFF;");
			cb_main.setSelected(false);
			
			Calendar cal = Calendar.getInstance();
			String toDay = "";
			toDay = cal.get(Calendar.YEAR)+"." + (cal.get(Calendar.MONTH)+1) + 
					"." + cal.get(Calendar.DAY_OF_MONTH);
			la_Date.setText(toDay);
			
			musicList.musicList(popRank);
			
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	// OST장르
	@FXML public void ostChart() {
		try {
			ostRank = FXCollections.observableArrayList(imhs.toDaySelect());
			btn_Song.setStyle("-fx-background-color:#FFFFFF;");
			btn_Pop.setStyle("-fx-background-color:#FFFFFF;");
			btn_Ost.setStyle("-fx-background-color:#9c0000;-fx-text-fill:#FFFFFF;");
			btn_Other.setStyle("-fx-background-color:#FFFFFF;");
			cb_main.setSelected(false);
			
			Calendar cal = Calendar.getInstance();
			String toDay = "";
			toDay = cal.get(Calendar.YEAR)+"." + (cal.get(Calendar.MONTH)+1) + 
					"." + cal.get(Calendar.DAY_OF_MONTH);
			la_Date.setText(toDay);
			
			musicList.musicList(ostRank);
			
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	// 그 외 장르
	@FXML public void otherChart() {
		try {
			otherRank = FXCollections.observableArrayList(imhs.toDaySelect());
			btn_Song.setStyle("-fx-background-color:#FFFFFF;");
			btn_Pop.setStyle("-fx-background-color:#FFFFFF;");
			btn_Ost.setStyle("-fx-background-color:#FFFFFF;");
			btn_Other.setStyle("-fx-background-color:#9c0000;-fx-text-fill:#FFFFFF;");
			cb_main.setSelected(false);
			
			Calendar cal = Calendar.getInstance();
			String toDay = "";
			toDay = cal.get(Calendar.YEAR)+"." + (cal.get(Calendar.MONTH)+1) + 
					"." + cal.get(Calendar.DAY_OF_MONTH);
			la_Date.setText(toDay);
			
			musicList.musicList(otherRank);
			
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
}