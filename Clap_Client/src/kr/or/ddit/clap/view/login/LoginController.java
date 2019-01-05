package kr.or.ddit.clap.view.login;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;
import kr.or.ddit.clap.main.LoginSession;
import kr.or.ddit.clap.main.MusicMainController;
import kr.or.ddit.clap.service.login.ILoginService;
import kr.or.ddit.clap.view.join.AES256Util;
import kr.or.ddit.clap.vo.member.MemberVO;

/**
 * 로그인창 컨트롤러.
 * @author Kyunghun
 *
 */
public class LoginController implements Initializable{
	
	MusicMainController mmc = new MusicMainController();
	private ILoginService ils;
	private Registry reg;
	
	LoginSession ls = new LoginSession();
	
	@FXML JFXTextField txt_id;
	@FXML JFXPasswordField txt_pw;
	@FXML Label lb_check;
	
	List<MemberVO> list = new ArrayList<MemberVO>();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			reg = LocateRegistry.getRegistry("localhost", 8888);
			ils = (ILoginService) reg.lookup("login");
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
	}
	
	public void login() throws UnsupportedEncodingException, NoSuchAlgorithmException, GeneralSecurityException{
		AES256Util aes = new AES256Util();
		
		String encryptedPw = ""; // 암호화된 pw
		encryptedPw = aes.encrypt(txt_pw.getText());
		
		String decryptedPw = ""; // 복호화시킨 pw
		decryptedPw = aes.decrypt(encryptedPw);
		
		MemberVO vo = new MemberVO();
	
		// 아이디 확인
		Boolean idCheck = false;
		try {
			idCheck = ils.idCheck(txt_id.getText());
			// DB에 id가 없을경우 -> false
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		System.out.println("아이디체크 " + idCheck);
		if(!idCheck) {
			lb_check.setVisible(true);
			return;
		}
		
		// 비밀번호 확인
		// dao로 비밀번호 가져오기.
		try {
			list = ils.select(txt_id.getText());
		} catch (RemoteException e1) {
			e1.printStackTrace();
		}
		System.out.println("가져온 비밀번호 "+list.get(0).getMem_pw());
		
		if(encryptedPw.equals(list.get(0).getMem_pw())) {
			System.out.println("로그인 진행");
			
			// session에 vo넘기기
			vo.setMem_id(txt_id.getText());
			ls.session = list.get(0);
			System.out.println("확인 "+ls.session.getMem_id());
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../../main/MusicMain.fxml"));
			ScrollPane root = null;
			try {
				root = loader.load();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			Scene scene = new Scene(root);
			Stage primaryStage = (Stage) txt_id.getScene().getWindow();
			primaryStage.setScene(scene);
		}else {
			System.out.println("비밀번호 불일치");
			lb_check.setVisible(true);
		}
		
		// 비밀번호가 일치하지 않을 때
//		if(false) {
//			System.out.println("비밀번호를 정확히 입력해주세요.");
//			// 3회 이상 실패하면 captcha.
//		}
		
		
		
		
		
		
		
	}

}
