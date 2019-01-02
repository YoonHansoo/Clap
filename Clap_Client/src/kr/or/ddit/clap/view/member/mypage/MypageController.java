package kr.or.ddit.clap.view.member.mypage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import kr.or.ddit.clap.main.LoginSession;
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

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		String user_id = LoginSession.session.getMem_id();
		userid.setText(user_id); // 현재 로그인한 사용자 아이디 가져오기
		String user_img = LoginSession.session.getMem_image();
		System.out.println("userimg: " + user_img);

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
	public void btn_my() throws IOException { // 프로필 수정 클릭시

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
