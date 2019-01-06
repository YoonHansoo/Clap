package kr.or.ddit.clap.view.member.profilech;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.event.ActionEvent;import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import kr.or.ddit.clap.main.LoginSession;
import kr.or.ddit.clap.service.like.ILikeService;
import kr.or.ddit.clap.service.mypage.IMypageService;
import kr.or.ddit.clap.vo.member.MemberVO;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.TextField;

public class ProfileChController implements Initializable {
	private Registry reg;
	private IMypageService ims;
	
	public Stage primaryStage;
	@FXML Button btn_sh;
	@FXML ImageView img;
	@FXML TextField textF_Info;
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
		MemberVO vo = new MemberVO();
		vo.setMem_id(user_id);
		MemberVO memvo;
		try {
			memvo = ims.select(vo);
			textF_Info.setText(memvo.getMem_intro());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		}
	
	
	@FXML public void btn_Img() {

		
	}
		
	@FXML public void btn_Del() {
		textF_Info.setText("");
	}
}


