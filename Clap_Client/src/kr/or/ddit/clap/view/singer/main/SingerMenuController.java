package kr.or.ddit.clap.view.singer.main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import com.jfoenix.controls.JFXTabPane;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import kr.or.ddit.clap.service.album.IAlbumService;
import kr.or.ddit.clap.service.singer.ISingerService;
import kr.or.ddit.clap.vo.album.AlbumVO;
import kr.or.ddit.clap.vo.singer.SingerVO;

/**
 * 
 * @author 박경훈
 *
 */

public class SingerMenuController implements Initializable{
	
	public static int menuCount = 0;
	
	@FXML JFXTabPane tabPane;
	@FXML Tab tab_main;
	@FXML Tab tab_album;
	@FXML Tab tab_music;
	@FXML AnchorPane main;
	@FXML StackPane singerMain;
	@FXML StackPane singerAlbum;
	@FXML StackPane singerMusic;
	
	public static String albumNo;// 파라미터로 받은 선택한 가수의 PK
	private Registry reg;
	private IAlbumService ias;
	private ISingerService ssi;
	private String temp_img_path = "";

	// 파라미터로 넘기기 위해 전역으로 선언
	public AlbumVO aVO = null;
	public SingerVO sVO = null;
	public String str_like_cnt;
	public static AnchorPane contents;

	// @FXML Label label_singNo;
	@FXML Label label_albumName1;
	@FXML Label label_albumName2;
	@FXML Label label_singerName;
	@FXML Label label_saledate;
	@FXML Label label_saleEnter;
	@FXML Label label_entertain;
	@FXML Label label_LikeCnt;
	@FXML ImageView imgview_albumImg;
	@FXML Label txt_intro;
	
	@FXML VBox box;
	@FXML Label lb_singer;

	// ShowSingerList.fxml는 VBOX를 포함한 전부이기 때문에
	// 현재 씬의 VBox까지 모두 제거 후 ShowSingerList를 불러야함.
	public void givePane(AnchorPane contents) {
		this.contents = contents;
		System.out.println("contents 적용완료");
	}
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		tabPane.getSelectionModel().select(menuCount);
		tabPane.setOnKeyPressed(e->{
			box.setVisible(false);
		});
		tabPane.setOnMouseClicked(e->{
			box.setVisible(false);
		});
		
		try {
			AnchorPane pane = FXMLLoader.load(getClass().getResource("SingerMain.fxml"));
			singerMain.getChildren().removeAll();
			singerMain.getChildren().setAll(pane);
			
			AnchorPane pane2 = FXMLLoader.load(getClass().getResource("SingerAlbum.fxml"));
			singerAlbum.getChildren().removeAll();
			singerAlbum.getChildren().setAll(pane2);
			
			StackPane pane3 = FXMLLoader.load(getClass().getResource("SingerMusic.fxml"));
			singerMusic.getChildren().removeAll();
			singerMusic.getChildren().setAll(pane3);
			
//			StackPane pane_main = FXMLLoader.load(getClass().getResource("SingerMainMusic.fxml"));
//			pane_main.setLayoutY(350);
//			singerMain.getChildren().setAll(pane_main);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("앨범번호:" + albumNo);

		try {
			// reg로 ISingerService객체를 받아옴
			reg = LocateRegistry.getRegistry("localhost", 8888);
			ias = (IAlbumService) reg.lookup("album");
			aVO = ias.albumDetailInfo(albumNo);
			
			ssi = (ISingerService) reg.lookup("singer");
			sVO = ssi.singerDetailInfo(aVO.getSing_no());
			
			System.out.println(aVO.getSing_no());
			// 파라미터로 받은 정보를 PK로 상세정보를 가져옴
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}

		lb_singer.setText(sVO.getSing_name());
		lb_singer.setOnMouseClicked(e->{
			box.setVisible(false);
			tabPane.getSelectionModel().select(0);
		});
		
		label_albumName1.setText(aVO.getAlb_name());
		label_albumName2.setText(aVO.getAlb_name());
		label_singerName.setText(aVO.getSing_name());
		label_saledate.setText(aVO.getAlb_saledate());
		label_saleEnter.setText(aVO.getAlb_sale_enter());

		label_entertain.setText(aVO.getAlb_entertain());
		txt_intro.setText(aVO.getAlb_intro());

		Image img = new Image(aVO.getAlb_image());
		temp_img_path = aVO.getAlb_image(); // aVO.getSing_image()를 전역으로 쓰기위해
		imgview_albumImg.setImage(img);

		// 좋아요 수를 가져오는 쿼리
		int like_cnt = 0;
		try {
			like_cnt = ias.selectAlbumLikeCnt(albumNo);
		} catch (RemoteException e) {
			e.printStackTrace();
		}

		// 좋아요는 다른 VO에서 가져와야함...
		str_like_cnt = like_cnt + "";
		label_LikeCnt.setText(str_like_cnt);

	}

	@FXML
	public void wideView() {
		// img_wideimg
		System.out.println("크게보기 버튼클릭");
		try {
			AnchorPane pane = FXMLLoader.load(getClass().getResource("../../album/album/AlbummgWiderDialog.fxml"));
			Stage stage = new Stage();
			Scene scene = new Scene(pane);
			stage.setScene(scene);
			stage.initModality(Modality.APPLICATION_MODAL);
			Stage primaryStage = (Stage) label_albumName1.getScene().getWindow();
			stage.initOwner(primaryStage);
			stage.setWidth(500);
			stage.setHeight(600);

			ImageView img_wideimg = (ImageView) pane.lookup("#img_wideimg");
			Image temp_img = new Image(temp_img_path);
			img_wideimg.setImage(temp_img);

			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

//	// 수정화면으로 이동
//	@FXML
//	public void updateAlbum() {
//
//		try {
//			// 바뀔 화면(FXML)을 가져옴
//			UpdateAlbumController.albumNo = albumNo;// 가수번호를 변수로 넘겨줌
//
//			FXMLLoader loader = new FXMLLoader(getClass().getResource("UpdateAlbum.fxml"));// initialize실행됨
//			Parent UpdateAlbum = loader.load();
//			UpdateAlbumController cotroller = loader.getController();
//			cotroller.initData(aVO, str_like_cnt);
//			main.getChildren().removeAll();
//			main.getChildren().setAll(UpdateAlbum);
//
//		} catch (IOException e1) {
//			e1.printStackTrace();
//		}
//
//	}

//	@FXML
//	public void deleteAlbum() {
//
//		// Alert창을 출력해 정말 삭제할 지 물어봄
//		try { if(0>alertConfrimDelete()) 
//		{ return; }
//
//		int cnt = ias.deleteAlbum(albumNo);
//		System.out.println("삭제 여부:" + cnt);
//		if (cnt >= 1) {
//			System.out.println("삭제성공");
//		}
//
//		else {
//			System.out.println("삭제실패");
//
//		}
//	}
//	catch(RemoteException e)
//	{
//		e.printStackTrace();
//	}
//
//	// 화면전환 
//		FXMLLoader loader = new FXMLLoader(getClass().getResource("ShowAlbumLIst.fxml")); 
//	Parent albumList;
//		  try {
//		  
//			  albumList = loader.load(); //ShowSingerList.fxml는 VBOX를 포함한 전부이기 때문에 //현재 씬의
//		//  VBox까지 모두 제거 후 ShowSingerList를 불러야함.
//		  
//		  contents.getChildren().removeAll(); // main.getChildren().removeAll();
//		  contents.getChildren().setAll(albumList);
//		  
//		  } catch (IOException e) { e.printStackTrace(); }
//		  
//		  
//		 
//	}

//	// 사용자가 확인을 누르면 1을 리턴 이외는 -1
//	public int alertConfrimDelete() {
//		Alert alertConfirm = new Alert(AlertType.CONFIRMATION);
//
//		alertConfirm.setTitle("CONFIRMATION");
//		alertConfirm.setContentText("정말로 삭제하시겠습니까?(해당 앨범 및 곡이 모두 삭제됩니다)");
//
//		// Alert창을 보여주고 사용자가 누른 버튼 값 읽어오기
//		ButtonType confirmResult = alertConfirm.showAndWait().get();
//
//		if (confirmResult == ButtonType.OK) {
//			System.out.println("OK 버튼을 눌렀습니다.");
//			return 1;
//		} else if (confirmResult == ButtonType.CANCEL) {
//			System.out.println("취소 버튼을 눌렀습니다.");
//			return -1;
//		}
//		return -1;
//	}
}
