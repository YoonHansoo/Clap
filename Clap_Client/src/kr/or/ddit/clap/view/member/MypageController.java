package kr.or.ddit.clap.view.member;

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

public class MypageController implements Initializable {
	static Stage mypageDialog = new Stage(StageStyle.DECORATED);
	@FXML Label userid;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		String user_id= LoginSession.session.getMem_id();
		
		userid.setText(user_id);	//현재 로그인한 사용자 아이디 가져오기
		
	
	}
	
	@FXML
	public void btn_profch() throws IOException {	//프로필 수정 클릭시
		Parent root = FXMLLoader.load(getClass().getResource("propli.fxml"));
		Scene scene = new Scene(root);
		mypageDialog.setTitle("모여서 각잡고 코딩 - clap");
	
		mypageDialog.setScene(scene);
		mypageDialog.show();
	}

}
