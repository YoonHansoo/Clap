/**
 *가수 리스트 상세보기를 출력하는 화면 controller
 * 
 * 
 * @author Hansoo
 *
 */
package kr.or.ddit.clap.view.singer.singer;

import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import kr.or.ddit.clap.service.singer.ISingerService;
import kr.or.ddit.clap.vo.singer.SingerVO;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.TextArea;

public class ShowSingerDetailController  implements Initializable {

	public static String singerNo;// 파라미터로 받은 선택한 가수의 PK 
	private Registry reg;
	private ISingerService iss;
	
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
	
	
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		System.out.println("가수번호:" +singerNo );
		
		SingerVO sVO = null;
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
		imgview_singImg.setImage(img);
		
		//좋아요 수를 가져오는 쿼리
		int like_cnt = 0;
		try {
			like_cnt =  iss.selectSingerLikeCnt(singerNo);
		} catch (RemoteException e) {
			e.printStackTrace();
		}

		//좋아요는 다른 VO에서 가져와야함...
		String str_like_cnt =like_cnt+"";
		label_LikeCnt.setText(str_like_cnt);
		
	}




	@FXML public void wideView() {}




	@FXML public void updateSinger() {}




	@FXML public void deleteSinger() {}

	
}
