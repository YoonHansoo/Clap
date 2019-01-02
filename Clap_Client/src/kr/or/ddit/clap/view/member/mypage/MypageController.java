package kr.or.ddit.clap.view.member.mypage;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import kr.or.ddit.clap.main.LoginSession;
import kr.or.ddit.clap.service.mypage.IMypageService;
import kr.or.ddit.clap.service.singer.ISingerService;
import kr.or.ddit.clap.vo.member.MemberVO;
import kr.or.ddit.clap.vo.singer.SingerVO;

import com.jfoenix.controls.JFXButton;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;

public class MypageController implements Initializable {
	static Stage mypageDialog = new Stage(StageStyle.DECORATED);
	@FXML
	Label userid;
	@FXML
	Image user_img;
	@FXML
	AnchorPane contents;
	private Registry reg;
	private IMypageService ims;
	private ObservableList<MemberVO> memberList;
	static Stage pwok = new Stage(StageStyle.DECORATED);
	String checkPw;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		
		try {
			reg = LocateRegistry.getRegistry("localhost", 8888);
			ims = (IMypageService) reg.lookup("mypage");
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
		

	
		String user_id = LoginSession.session.getMem_id();
		userid.setText(user_id); // 현재 로그인한 사용자 아이디 가져오기
		String user_img = LoginSession.session.getMem_image();
		System.out.println("userimg: " + user_img);
		
		MemberVO vo = new MemberVO();
		vo.setMem_id(user_id);
		
		try {
			MemberVO memvo = ims.select(vo);
		} catch (RemoteException e) {
			System.out.println("에러");
			e.printStackTrace();
		}
		

	}

	@FXML
	public void btn_profch() throws IOException { // 프로필 수정 클릭시
		Parent root = FXMLLoader.load(getClass().getResource("../profile/profile.fxml"));
		Scene scene = new Scene(root);
		mypageDialog.setTitle("모여서 각잡고 코딩 - clap");

		mypageDialog.setScene(scene);
		mypageDialog.show();
	}

	@FXML
	public void btn_my() throws IOException { // 내정보  클릭시
			Parent root = FXMLLoader.load(getClass().getResource("pwcheck.fxml"));
			Scene scene = new Scene(root);
			pwok.setTitle("모여서 각잡고 코딩 - clap");
			if(pwok.getModality() == null) {
				pwok.initModality(Modality.APPLICATION_MODAL);			
			}
			
			pwok.setScene(scene);
			pwok.show();
			
			for(int i=0; i<memberList.size(); i++) {
				if(LoginSession.session.getMem_id().equals(memberList.get(i).getMem_id())) {
					checkPw = memberList.get(i).getMem_pw();
				}
			}
		Button ok=(Button)root.lookup("#btn_ok");
		ok.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				if(LoginSession.session.getMem_pw().equals(checkPw)) {
					try {
						Parent myCh = FXMLLoader.load(getClass().getResource("mypageCh.fxml"));
						contents.getChildren().removeAll();
						contents.getChildren().setAll(myCh);

						// System.out.println(item_SigerManage.getText());
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				
			}
		});
	

	}
}


