package kr.or.ddit.clap.view.join;

import java.io.IOException;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import kr.or.ddit.clap.main.MusicMainController;
import kr.or.ddit.clap.service.join.IJoinService;
import kr.or.ddit.clap.service.login.ILoginService;
import kr.or.ddit.clap.vo.member.MemberVO;

/**
 * 회원가입 창 컨트롤러.
 * @author Kyunghun
 *
 */
public class JoinController implements Initializable{
	
	@FXML JFXTextField txt_id;
	@FXML JFXPasswordField txt_pw;
	@FXML JFXPasswordField txt_pwCheck;
	@FXML JFXTextField txt_birth;
	@FXML JFXTextField txt_tel;
	@FXML JFXTextField txt_email;
	@FXML JFXTextField txt_name;
	
	@FXML JFXButton btn_ok;
	@FXML JFXRadioButton radio_m;
	@FXML JFXRadioButton radio_f;
	
	@FXML Label lb_id;
	
	ToggleGroup group = new ToggleGroup();
	
	private IJoinService ijs;
	private ILoginService ils;
	private Registry reg;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			reg = LocateRegistry.getRegistry("localhost", 8888);
			ijs = (IJoinService) reg.lookup("join");
			ils = (ILoginService) reg.lookup("login");
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
				System.out.println(txt_id.getText() + "-회원 가입 성공");
			}else{
				System.out.println(txt_id.getText() + "-회원 가입 실패");
				return;
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		System.out.println(vo.getMem_id());
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

	}
	
	public void idCheck() {
		System.out.println("아이디중복확인");
		// 유효성 검사 후 아이디 확인
		Pattern p = Pattern.compile("(^[a-zA-Z0-9]{6,}$)");
		Matcher m = p.matcher(txt_id.getText());
		
        if(m.find()) { // m.find() -> true or false.
        	System.out.println("ok");
        	lb_id.setVisible(false);
        	
    		Boolean idCheck = false;
    		try {
    			idCheck = ils.idCheck(txt_id.getText());
    			// DB에 id가 없을경우 -> false
    		} catch (RemoteException e) {
    			e.printStackTrace();
    		}
    		
    		if(!idCheck) {
    			// id 사용가능.
    			lb_id.setVisible(true);
    			lb_id.setText("사용가능합니다.");
    			lb_id.setTextFill(Color.valueOf("#00cc00"));
    		}else {
    			lb_id.setVisible(true);
    			lb_id.setText("사용중인 아이디입니다.");
    			lb_id.setTextFill(Color.RED);
    	        txt_id.requestFocus();
    			
    		}
        }
        else{
        	System.out.println("no");
        	lb_id.setVisible(true);
        	lb_id.setText("규칙에 맞게 입력해주세요.");
        	lb_id.setTextFill(Color.RED);
        	txt_id.requestFocus();
        }  
		

	}
	
}
