package kr.or.ddit.clap.view.member.mypage;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXPasswordField;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import kr.or.ddit.clap.main.LoginSession;
import kr.or.ddit.clap.service.mypage.IMypageService;
import kr.or.ddit.clap.vo.member.MemberVO;

public class MypageController implements Initializable {

	static Stage mypageDialog = new Stage(StageStyle.DECORATED);
	@FXML
	Label label_Id;
	@FXML
	Image img_User;
	@FXML
	AnchorPane contents;
	private Registry reg;
	private IMypageService ims;
	static Stage pwok = new Stage(StageStyle.DECORATED);

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
		label_Id.setText(user_id); // 현재 로그인한 사용자 아이디 가져오기

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
	public void btn_my() throws IOException { // 내정보 클릭시
		Parent root = FXMLLoader.load(getClass().getResource("pwcheck.fxml"));
		Scene scene = new Scene(root);
		pwok.setTitle("모여서 각잡고 코딩 - clap");

		pwok.setScene(scene);
		pwok.show();

		Button btn = (Button) root.lookup("#btn_Ok");

		btn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				String user_id = LoginSession.session.getMem_id();
				label_Id.setText(user_id); // 현재 로그인한 사용자 아이디 가져오기

				MemberVO vo = new MemberVO();
				vo.setMem_id(user_id);
				MemberVO vo2 = new MemberVO();

				try {
					vo2 = ims.select(vo);
				} catch (RemoteException e) {
					System.out.println("에러입니다");
					e.printStackTrace();
				}

				JFXPasswordField fild = (JFXPasswordField) root.lookup("#fild_ok");

				if (fild.getText().equals(vo2.getMem_pw())) {
					pwok.close();

					Parent root1 = null;
					try {
						root1 = FXMLLoader.load(getClass().getResource("../mypageCh/mypageCh_Info.fxml"));
						contents.getChildren().removeAll();
						contents.getChildren().setAll(root1);
					} catch (IOException e) {
						e.printStackTrace();
					}

				}else {
					Text test_set =(Text) root.lookup("#text");
					test_set.setText("\t   잘못된 비밀번호를 입력하였습니다.");
				}

			}
		});//btn_Ok
		
		

		Button btn_no = (Button) root.lookup("#btn_Cl");
		btn_no.setOnMouseClicked(e ->{
			pwok.close();
		});

	}

}
