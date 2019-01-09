/**
 *가수 리스트를 출력하는 화면 controller
 * 
 * 
 * @author Hansoo
 *
 */
package kr.or.ddit.clap.view.music.music;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Pagination;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import kr.or.ddit.clap.service.music.IMusicService;
import kr.or.ddit.clap.view.album.album.AlbumDetailController;
import kr.or.ddit.clap.view.album.album.InsertAlbumController;
import kr.or.ddit.clap.vo.music.MusicVO;

public class MusicListController implements Initializable {

	@FXML
	Pagination p_paging;
	@FXML
	JFXTreeTableView<MusicVO> tbl_Music;
	@FXML
	TreeTableColumn<MusicVO, ImageView> col_musicImg;
	@FXML
	TreeTableColumn<MusicVO, String> col_musicTitle;
	@FXML
	TreeTableColumn<MusicVO, String> col_albumName;
	@FXML
	TreeTableColumn<MusicVO, String> col_singerName;
	@FXML
	TreeTableColumn<MusicVO, String> col_genreDetail;
	@FXML
	TreeTableColumn<MusicVO, String> col_musicNo;
	
	@FXML JFXComboBox<String> combo_search;
	@FXML TextField text_search;
	@FXML Button btn_search;	
	
	private Registry reg;
	private IMusicService ims;
	private ObservableList<MusicVO> musicList, currentmusicList;
	private int from, to, itemsForPage, totalPageCnt;
	@FXML AnchorPane main;
	@FXML AnchorPane contents;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			reg = LocateRegistry.getRegistry("localhost", 8888);
			ims =  (IMusicService) reg.lookup("music");
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
		
		col_musicImg
				.setCellValueFactory(param -> new SimpleObjectProperty<ImageView>(param.getValue().getValue().getImgView()));
		
		col_musicTitle
				.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getValue().getMus_title()));

		col_albumName
				.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getValue().getAlb_name()));

		col_singerName
				.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getValue().getSing_name()));

		col_genreDetail.setCellValueFactory(
				param -> new SimpleStringProperty(param.getValue().getValue().getGen_detail_name()));

		col_musicNo.setCellValueFactory(
				param -> new SimpleStringProperty(param.getValue().getValue().getMus_no()));

	
		
		try {
			musicList = FXCollections.observableArrayList(ims.selectListAll());
		} catch (RemoteException e) {
			System.out.println("에러");
			e.getMessage();
			e.printStackTrace();
		}
		System.out.println(musicList.size());
		
		//데이터 삽입
		
		TreeItem<MusicVO> root = new RecursiveTreeItem<>(musicList, RecursiveTreeObject::getChildren);
		
		tbl_Music.setRoot(root);
		tbl_Music.setShowRoot(false);
		
		
		itemsForPage=10; // 한페이지 보여줄 항목 수 설정
		
		paging();
		
		combo_search.getItems().addAll("곡","앨범","아티스트");
		combo_search.setValue(combo_search.getItems().get(0));
		
		
		//검색버튼 클릭
		btn_search.setOnAction(e ->{
			search();
		});
		
		//더블클릭
		tbl_Music.setOnMouseClicked(e ->{
			System.out.println("dd");
			if (e.getClickCount()  > 1) {
				int index = tbl_Music.getSelectionModel().getSelectedIndex();
				System.out.println("선택한 인덱스 : "+index);
				MusicVO vo = musicList.get(index);
				System.out.println("가수번호:" + vo.getAlb_no());
				String albumNo =  vo.getAlb_no(); //가수번호(PK)를 받아옴
				
				
				try {
					//바뀔 화면(FXML)을 가져옴

					AlbumDetailController.albumNo = albumNo;//가수번호를 변수로 넘겨줌
					
					FXMLLoader loader = new FXMLLoader(getClass().getResource("AlbumDetail.fxml"));// init실행됨
					Parent albumDetail= loader.load(); 
					
					AlbumDetailController cotroller = loader.getController();
					cotroller.givePane(contents); 
					
					main.getChildren().removeAll();
					main.getChildren().setAll(albumDetail);
					
					
				} catch (IOException e1) {
					e1.printStackTrace();
				} 
			}
		});
		}
		
	
	//페이징  메서드
	private void paging() {
		totalPageCnt = musicList.size() % itemsForPage == 0 ? musicList.size() / itemsForPage
				: musicList.size() / itemsForPage + 1;
		
		p_paging.setPageCount(totalPageCnt); // 전체 페이지 수 설정
		
		p_paging.setPageFactory((Integer pageIndex) -> {
			
			from = pageIndex * itemsForPage;
			to = from + itemsForPage - 1;
			
			
			TreeItem<MusicVO> root = new RecursiveTreeItem<>(getTableViewData(from, to), RecursiveTreeObject::getChildren);
			tbl_Music.setRoot(root);
			tbl_Music.setShowRoot(false);
			return tbl_Music;
		});
	}
	
	//페이징에 맞는 데이터를 가져옴
private ObservableList<MusicVO> getTableViewData(int from, int to) {
		
	currentmusicList = FXCollections.observableArrayList(); //
		int totSize = musicList.size();
		for (int i = from; i <= to && i < totSize; i++) {
			
			currentmusicList.add(musicList.get(i));
		}
		
		return currentmusicList;
	}
//검색 메서드
private void search() {
	try {
		MusicVO vo = new MusicVO();
		ObservableList<MusicVO> searchlist = FXCollections.observableArrayList();
		switch (combo_search.getValue()) {
		
		case "곡":
			vo.setMus_title(text_search.getText());
			searchlist = FXCollections.observableArrayList(ims.searchList(vo));
			break;
		case "앨범":
			vo.setAlb_name(text_search.getText());
			searchlist = FXCollections.observableArrayList(ims.searchList(vo));
			break;
		case "아티스트":
			vo.setSing_name(text_search.getText());
			searchlist = FXCollections.observableArrayList(ims.searchList(vo));
			break;
			
		default :
			break;
		}
		
		musicList = FXCollections.observableArrayList(searchlist); //검색조건에 맞는 리스트를 저장
		paging();
	}
	catch (Exception e) {
		e.printStackTrace();
	}
}

public void InsertMusic() {
	
	try {
		//바뀔 화면(FXML)을 가져옴
		
	FXMLLoader loader = new FXMLLoader(getClass().getResource("insertMusic.fxml"));// init실행됨
		Parent InsertMusic= loader.load(); 
		
		InsertMusicController cotroller = loader.getController();
		cotroller.givePane(contents); 
		
		main.getChildren().removeAll();
		main.getChildren().setAll(InsertMusic);
		
		
	} catch (IOException e1) {
		e1.printStackTrace();
	} 
	
	
}
}
