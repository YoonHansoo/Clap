package kr.or.ddit.clap.view.member.mypageCh;

import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXPasswordField;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import kr.or.ddit.clap.main.LoginSession;
import kr.or.ddit.clap.service.mypage.IMypageService;
import kr.or.ddit.clap.vo.member.MemberVO;

public class MypageChangePwController implements Initializable{

	private Registry reg;
	private IMypageService ims;
	@FXML JFXPasswordField textF_NowPw;
    @FXML JFXPasswordField textF_NewPw;
	@FXML JFXPasswordField textF_NewPwCh;
	@FXML Button btn_Ok;
	@FXML Button btn_Cl;
	
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
		
		btn_Ok.setOnAction(e1->{
			String user_id = LoginSession.session.getMem_id();
			MemberVO vo = new MemberVO();
			vo.setMem_id(user_id);
			MemberVO vo2 = new MemberVO();

			try {
				vo2 = ims.select(vo);
			} catch (RemoteException e) {
				System.out.println("에러입니다");
				e.printStackTrace();
			}
			
			if(textF_NowPw.getText().equals(vo2.getMem_pw())) {
				System.out.println("dd");
				
			}else if(!textF_NowPw.getText().equals(vo2.getMem_pw())) {
				System.out.println("비번이 틀림");
			}
		});//btn_ok
	}

}
