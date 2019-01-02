package kr.or.ddit.clap.view.member.mypageCh;

import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import kr.or.ddit.clap.main.LoginSession;
import kr.or.ddit.clap.service.mypage.IMypageService;
import kr.or.ddit.clap.vo.member.MemberVO;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MypageChangeController implements Initializable {
	private Registry reg;
	private IMypageService ims;
	@FXML Label id;
	@FXML Label ph;
	
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
		id.setText(user_id);
		MemberVO vo = new MemberVO();
		vo.setMem_id(user_id);
		MemberVO vo2 = new MemberVO();
		
		try {
			vo2 = ims.select(vo);
		} catch (RemoteException e) {
			System.out.println("에러입니다");
			e.printStackTrace();
		}
		
		ph.setText(vo2.getMem_tel().substring(0, 3)+"-"+vo2.getMem_tel().substring(4, 8));
		
	}
	
	

}
