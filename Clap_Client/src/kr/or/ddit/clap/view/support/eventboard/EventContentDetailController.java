package kr.or.ddit.clap.view.support.eventboard;

import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import kr.or.ddit.clap.service.eventboard.IEventBoardService;
import kr.or.ddit.clap.vo.support.EventBoardVO;

import com.jfoenix.controls.JFXButton;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;

public class EventContentDetailController implements Initializable  {

	public static String ContentNo;
	private Registry reg;
	private IEventBoardService ies;
	private String temp_img_path = "";
	
	@FXML
	AnchorPane e_main;
	@FXML
	Text Text_EventNo;
	@FXML
	Text Text_EventTitle;
	@FXML
	Text Text_EventSDate;
	@FXML
	JFXButton btn_del;
	@FXML
	JFXButton btn_upd;
	@FXML
	Text Text_EventEDate;
	@FXML
	ImageView ImageView;
	@FXML
	Label lb_Content;
	
	public EventBoardVO eVO = null;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		try {
			System.out.println("EventContentDetailController : "+ContentNo);
			//reg로 IEventBoardService 객체를 받아옴 
			reg = LocateRegistry.getRegistry("localhost", 8888);
			ies = (IEventBoardService) reg.lookup("eventboard");
			eVO = ies.eventDetailAll(ContentNo);
			System.out.println(eVO.getEvent_no());
			//파라미터로 받은 정보를 PK로 상세정보를 가져옴
			
		} catch (RemoteException e) {
			System.out.println(1);
			e.printStackTrace();
		} catch (NotBoundException e) {
			System.out.println(2);
			e.printStackTrace();
		}
		
		Text_EventNo.setText(eVO.getEvent_no());
		//eVO.getEvent_no()로 받아야 함.
		Text_EventTitle.setText(eVO.getEvent_title());
		Text_EventSDate.setText(eVO.getEvent_sdate());
		//fxml에서 SDate와 EDate 간격 넓히기 
		Text_EventEDate.setText(eVO.getEvent_edate());
		lb_Content.setText(eVO.getEvent_content());
		
		Image img = new Image(eVO.getEvent_image());
		System.out.println("이미지 경로 : " + eVO.getEvent_image());
		
		temp_img_path = eVO.getEvent_image(); //eVO.getEvent_image()를 전역으로 쓰기 위해
		ImageView.setImage(img);
		
		
		
	}

}
