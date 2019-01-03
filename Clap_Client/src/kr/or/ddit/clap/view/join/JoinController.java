package kr.or.ddit.clap.view.join;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ToggleGroup;
import kr.or.ddit.clap.service.join.IJoinService;
import kr.or.ddit.clap.vo.member.MemberVO;

/**
 * 회원가입 창 컨트롤러.
 * @author Kyunghun
 *
 */
public class JoinController implements Initializable{
	
	@FXML JFXTextField txt_id;
	@FXML JFXTextField txt_pw;
	@FXML JFXTextField txt_pwCheck;
	@FXML JFXTextField txt_birth;
	@FXML JFXTextField txt_tel;
	@FXML JFXTextField txt_email;
	@FXML JFXTextField txt_name;
	
	@FXML JFXButton btn_ok;
	@FXML JFXRadioButton radio_m;
	@FXML JFXRadioButton radio_f;
	
	ToggleGroup group = new ToggleGroup();
	
	private IJoinService ijs;
	private Registry reg;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			reg = LocateRegistry.getRegistry("localhost", 8888);
			ijs = (IJoinService) reg.lookup("join");
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
		
		radio_m.setToggleGroup(group);
		radio_m.setUserData("m");
		radio_m.setSelected(true);
		
		radio_f.setToggleGroup(group);
		radio_f.setUserData("f");
		
	}

	public void join() throws UnsupportedEncodingException, NoSuchAlgorithmException, GeneralSecurityException {
		AES256Util aes = new AES256Util();
		MemberVO vo = new MemberVO();
		
		String encryptedPw = aes.encrypt(txt_pw.getText());
		String decryptedPw = aes.decrypt(encryptedPw);
		
		String encryptedPwCheck = aes.encrypt(txt_pwCheck.getText());
		String decryptedPwCheck = aes.decrypt(encryptedPwCheck);
		
		String gender = group.getSelectedToggle().getUserData().toString();
		
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yy/MM/dd");
		
		// 아이디 유효성 검사
		
		// 아이디 중복확인
		
		// 비밀번호 유효성 검사
		
		// 비밀번호 입력 확인
		
		vo.setMem_id(txt_id.getText());
		vo.setMem_pw(encryptedPw);
		vo.setMem_name(txt_name.getText());
		vo.setMem_email(txt_email.getText());
		vo.setMem_bir(txt_birth.getText());

		vo.setMem_gender(gender);
		vo.setMem_tel(txt_tel.getText());
		vo.setMem_grade("일반");
		vo.setMem_auth("f");
		vo.setMem_indate(sdf.format(now));

		vo.setMem_blacklist_tf("f");
		vo.setMem_del_tf("f");
		
		// insert vo
		try {
			int cnt = ijs.insert(vo);
			
			if(cnt>0){
				System.out.println(txt_id.getText() + "회원 가입 성공");
			}else{
				System.out.println(txt_id.getText() + "회원 가입 실패");
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		
		System.out.println("아이디:"+txt_id.getText());
		System.out.println("성별:"+gender);
	}
	
}
