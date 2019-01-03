/**
 *가수 리스트 상세보기를 출력하는 화면 controller
 * 
 * 
 * @author Hansoo
 *
 */
package kr.or.ddit.clap.view.singer.singer;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import kr.or.ddit.clap.service.singer.ISingerService;
import kr.or.ddit.clap.vo.singer.SingerVO;

public class ShowSingerDetailController  implements Initializable {

	public static String singerNo;// 파라미터로 받은 선택한 가수의 PK 
	private Registry reg;
	private ISingerService iss;
	private String temp_img_path="";
	
	//파라미터로 넘기기 위해 전역으로 선언
	public SingerVO sVO = null;
	public int like_cnt; 
	
	
	@FXML Label label_singNo;
	@FXML AnchorPane contents;
	@FXML Label label_singerName1;
	@FXML Label label_singerName2;
	@FXML Label label_ActType;
	@FXML Label label_ActEra;
	@FXML Label label_DebutEra;
	@FXML Label label_DebutMus;
	@FXML Label label_Nation;
	@FXML Label label_LikeCnt;
	@FXML ImageView imgview_singImg;
	@FXML Label txt_intro;
	@FXML AnchorPane main;
	
	
	
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		System.out.println("가수번호:" +singerNo );
		
		try {
			//reg로 ISingerService객체를 받아옴
			reg = LocateRegistry.getRegistry("localhost", 8888);  
			iss = (ISingerService) reg.lookup("singer");
			sVO=iss.singerDetailInfo(singerNo);
			System.out.println(sVO.getSing_no());
			//파라미터로 받은 정보를 PK로 상세정보를 가져옴
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}

		
		label_singerName1.setText(sVO.getSing_name());
		label_singerName2.setText(sVO.getSing_name());
		label_ActType.setText(sVO.getSing_act_type());
		label_ActEra.setText(sVO.getSing_act_era());
		label_DebutEra.setText(sVO.getSing_debut_era());
		
		label_DebutMus.setText(sVO.getSing_debut_mus());
		label_Nation.setText(sVO.getSing_nation());
		txt_intro.setText(sVO.getSing_intro());
		
		Image img = new Image(sVO.getSing_image());
		temp_img_path = sVO.getSing_image();   //sVO.getSing_image()를 전역으로 쓰기위해
		imgview_singImg.setImage(img);
		
		//좋아요 수를 가져오는 쿼리
		 like_cnt = 0;
		try {
			like_cnt =  iss.selectSingerLikeCnt(singerNo);
		} catch (RemoteException e) {
			e.printStackTrace();
		}

		//좋아요는 다른 VO에서 가져와야함...
		String str_like_cnt =like_cnt+"";
		label_LikeCnt.setText(str_like_cnt);
		
	}




	@FXML public void wideView() {
		//img_wideimg
		System.out.println("크게보기 버튼클릭");
		try {
			AnchorPane pane = FXMLLoader.load(getClass().getResource("SingerImgWiderDialog.fxml"));
			Stage stage = new Stage();
			Scene scene = new Scene(pane);
			stage.setScene(scene);
			stage.initModality(Modality.APPLICATION_MODAL);
			Stage primaryStage = (Stage)label_singerName1.getScene().getWindow();
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



	//수정화면으로 이동
	@FXML public void updateSinger() {
		try {
			//바뀔 화면(FXML)을 가져옴
			UpdateSingerController.singerNo = singerNo;//가수번호를 변수로 넘겨줌
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource("UpdateSinger.fxml"));// initialize실행됨
			Parent UpdateSinger= loader.load(); 
			UpdateSingerController cotroller = loader.getController();
			cotroller.initData(sVO,like_cnt);
			main.getChildren().removeAll();
			main.getChildren().setAll(UpdateSinger);
			
			
		} catch (IOException e1) {
			e1.printStackTrace();
		} 
		
		
	}




	@FXML public void deleteSinger() {}

	
}
