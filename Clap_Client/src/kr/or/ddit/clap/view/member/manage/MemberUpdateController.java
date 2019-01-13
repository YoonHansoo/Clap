package kr.or.ddit.clap.view.member.manage;

import java.awt.TextArea;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import kr.or.ddit.clap.service.mypage.IMypageService;
import kr.or.ddit.clap.view.join.AES256Util;
import kr.or.ddit.clap.view.singer.singer.ShowSingerDetailController;
import kr.or.ddit.clap.vo.member.MemberVO;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class MemberUpdateController implements Initializable{
	
	private Registry reg;
	private IMypageService ims;

	public static String memid;
	@FXML AnchorPane main;
	
	@FXML Label label_MemName1;
	@FXML ImageView imgview_MemImg;
	@FXML Label label_Memid;
	@FXML JFXPasswordField textF_Mempw;
	@FXML Label label_BlackCnt ;
	@FXML JFXTextField   textF_Memname2;
	@FXML JFXComboBox  combo_Memgender;
	@FXML JFXTextField   textF_MemTel;
	@FXML JFXDatePicker  Date_MemBir;
	@FXML JFXComboBox  combo_MemAuth;
	
	@FXML JFXComboBox  combo_MemGrade;
	@FXML JFXTextField  textF_MemEmail;
	@FXML JFXDatePicker  Date_Indate;
	@FXML JFXComboBox  combo_DelTF;
	@FXML JFXComboBox  combo_blackTF;
	
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

		MemberVO vo = new MemberVO();

		vo.setMem_id(memid);
		MemberVO mvo = new MemberVO();
		try {
			mvo = ims.select(vo);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		//원래 데이터값 넣기
		label_MemName1.setText(mvo.getMem_name());
		//imgview_MemImg 
		label_Memid .setText(memid);
		
		AES256Util aes = null;
		try {
			aes = new AES256Util();
		} catch (UnsupportedEncodingException e11) {
			e11.printStackTrace();
		}
		
		String encryptedPw = ""; // 암호화된 pw
		try {
			encryptedPw = aes.encrypt(mvo.getMem_pw());
		} catch (UnsupportedEncodingException | GeneralSecurityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		String decryptedPw = ""; // 복호화시킨 pw
		try {
			decryptedPw = aes.decrypt(encryptedPw);
		} catch (UnsupportedEncodingException | GeneralSecurityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		textF_Mempw.setText(decryptedPw);
		textF_Memname2.setText(mvo.getMem_name());
			if(mvo.getMem_gender().equals("f")) {
				mvo.setMem_gender("여성");
			}else {
				mvo.setMem_gender("남성");
			}
		
		combo_Memgender.setValue(mvo.getMem_gender());
		textF_MemTel.setText(mvo.getMem_tel());
		
	/*	
		SimpleDateFormat dt = new SimpleDateFormat("yyyyy-mm-dd hh:mm:ss"); 
		Date date = dt.parse(mvo.getMem_bir()); 
		Date_MemBir.setValue((mvo.getMem_bir());*/
		
		
		combo_MemAuth.setValue(mvo.getMem_auth());
		combo_MemGrade.setValue(mvo.getMem_grade());
		textF_MemEmail.setText(mvo.getMem_email());
		//Date_Indate
		combo_DelTF.setValue(mvo.getMem_del_tf());
		combo_blackTF.setValue(mvo.getMem_blacklist_tf());
		//txt_intro.setText(mvo.getMem_intro());
		label_BlackCnt.setText(mvo.getMem_black_cnt());
		
		//콤보박스에 값넣기
		combo_Memgender.getItems().add("여성");
		combo_Memgender.getItems().add("남성");
		combo_MemAuth.getItems().add("사용자");
		combo_MemAuth.getItems().add("관리자");
		combo_MemGrade.getItems().add("일반");
		combo_MemGrade.getItems().add("vip");
		combo_DelTF.getItems().add("O");
		combo_DelTF.getItems().add("X");
		combo_blackTF.getItems().add("O");
		combo_blackTF.getItems().add("X");

	}
	
	public void updateMem() {
		
	}
	public void cancel() {
		try {
			//바뀔 화면(FXML)을 가져옴
			//singerDetail
			MemberDetailController.memid = memid;//가수번호를 변수로 넘겨줌
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource("memditail.fxml"));// init실행됨
			Parent memDetail= loader.load(); 
			main.getChildren().removeAll();
			main.getChildren().setAll(memDetail);
			
			
		} catch (IOException e1) {
			e1.printStackTrace();
		} 
	}
	
	public void ChangeView() {
		
	}

}
