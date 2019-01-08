package kr.or.ddit.clap.view.login;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import kr.or.ddit.clap.service.login.ILoginService;
import kr.or.ddit.clap.vo.member.MemberVO;

public class SearchController implements Initializable{
	
	static Stage idDialog = new Stage(StageStyle.UTILITY);
	private ILoginService ils;
	private Registry reg;
	
	@FXML TextField txt_bir;
	@FXML TextField txt_tel;
	@FXML TextField txt_id;
	@FXML TextField txt_email;
	@FXML TextField txt_pwCheck;
	
	@FXML Label lb_id;
	@FXML Label lb_pw;
	private boolean idFlag = false;
	private boolean emailFlag = false;
	private boolean pwCheckFlag = false;
	

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
	
	public void idSearch() {
		lb_id.setVisible(false);
		
		if(txt_bir.getText().equals("")) {
			lb_id.setVisible(true);
			lb_id.setText("생년월일을 입력해주세요.");
			lb_id.setTextFill(Color.RED);
			txt_bir.requestFocus();
//			lb_captcha.setPadding(new Insets(0));
//			captchaFlag = false;
			return;
		}
		
		Pattern p = Pattern.compile("(^\\d{2}/\\d{2}/\\d{2}$)");
		Matcher m = p.matcher(txt_bir.getText());
		
		if(!m.find()) {
			lb_id.setVisible(true);
			lb_id.setText("형식에 맞게 입력해주세요.");
			lb_id.setTextFill(Color.RED);
			txt_bir.requestFocus();
			return;
		}else {
			// 유효성 검사.
			boolean birCheck = dateCheck(txt_bir.getText(), "yy/MM/dd");
			System.out.println(birCheck);
			if(!birCheck) {
				lb_id.setVisible(true);
				lb_id.setText("생년월일을 다시 확인해주세요.");
				lb_id.setTextFill(Color.RED);
				txt_bir.requestFocus();
				return;
			}
		}
		
		// 전화번호 검사.
		if(txt_tel.getText().equals("")) {
			lb_id.setVisible(true);
			lb_id.setText("전화번호를 입력해주세요.");
			lb_id.setTextFill(Color.RED);
			txt_tel.requestFocus();
			return;
		}
		
		p = Pattern.compile("(^01\\d{9}$)");
		m = p.matcher(txt_tel.getText());
		
		if(!m.find()) {
			lb_id.setVisible(true);
			lb_id.setText("형식에 맞게 입력해주세요.");
			lb_id.setTextFill(Color.RED);
			txt_tel.requestFocus();
			return;
		}
		
		// service로 쿼리.
		List<MemberVO> list = new ArrayList<MemberVO>();
		MemberVO vo = new MemberVO();
		vo.setMem_bir(txt_bir.getText());
		vo.setMem_tel(txt_tel.getText());
		
		try {
			list = ils.idSearch(vo);
			if(list.size()==0) {
				lb_id.setVisible(true);
				lb_id.setText("정보에 맞는 아이디가 존재하지 않습니다.");
				lb_id.setTextFill(Color.RED);
				return;
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		// 있으면 새로운창
		if(idDialog.getModality()==null) {
			idDialog.initModality(Modality.APPLICATION_MODAL);			
		}
		if(idDialog.getOwner()==null) {
			idDialog.initOwner(txt_bir.getScene().getWindow());			
		}
		idDialog.setTitle("Clap:음악, 그리고 설레임");
		
		Parent parent = null;
		try {
			parent = FXMLLoader.load(getClass().getResource("IdSearch.fxml"));
		}catch(IOException ee) {
			ee.printStackTrace();
		}
		
		Scene scene = new Scene(parent);
		
		idDialog.setScene(scene);
		idDialog.setResizable(false);
		idDialog.show();
		
		
		Button btn_ok = (Button) parent.lookup("#btn_ok");
		btn_ok.setOnAction(event->{
			idDialog.close();
		});
		Label lb_idSearch = (Label) parent.lookup("#lb_idSearch");
		lb_idSearch.setText(list.get(0).getMem_id()+" 입니다.");
		
	}

	public boolean dateCheck(String date, String format) {
		SimpleDateFormat dateFormatParser = new SimpleDateFormat(format, Locale.KOREA);
		dateFormatParser.setLenient(false);
		try {
			dateFormatParser.parse(date);
			return true;
		} catch (Exception Ex) {
			return false;
		}
	}
	
	public void pwSearch() {
		lb_pw.setVisible(false);
		
		if(txt_id.getText().equals("")) {
			lb_pw.setVisible(true);
			lb_pw.setText("아이디를 입력해주세요.");
			lb_pw.setTextFill(Color.RED);
			txt_id.requestFocus();
			return;
		}else if(txt_id.getText().length()<6) {
			lb_pw.setVisible(true);
			lb_pw.setText("아이디는 6자 이상입니다.");
			lb_pw.setTextFill(Color.RED);
			txt_id.requestFocus();
			return;
		}
		
		
		
		if(txt_email.getText().equals("")) {
			lb_pw.setVisible(true);
			lb_pw.setText("메일주소를 입력해주세요.");
			lb_pw.setTextFill(Color.RED);
			txt_email.requestFocus();
			return;
		}else if(!emailFlag) {
			lb_pw.setVisible(true);
			lb_pw.setText("메일 발송을 진행해주세요.");
			lb_pw.setTextFill(Color.RED);
			txt_email.requestFocus();
			return;
		}else if(txt_pwCheck.getText().equals("")) {
			lb_pw.setVisible(true);
			lb_pw.setText("임시 비밀번호를 입력해주세요.");
			lb_pw.setTextFill(Color.RED);
			txt_pwCheck.requestFocus();
			return;
		}else if(!pwCheckFlag) {
			lb_pw.setVisible(true);
			lb_pw.setText("임시 비밀번호가 일치하지 않습니다.");
			lb_pw.setTextFill(Color.RED);
			txt_pwCheck.requestFocus();
			return;
		}

		
		Pattern p = Pattern.compile("(^[a-zA-Z0-9]{6,}$)");
		Matcher m = p.matcher(txt_id.getText());
		
        if(m.find()) {
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
    			idFlag = true;
//    			lb_ok.setVisible(false);
    			lb_id.setTextFill(Color.valueOf("#00cc00"));
    			idFlag  = true;
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
	
	public void emailCheck() {
		lb_pw.setVisible(false);
		
		if(txt_id.getText().equals("")) {
			lb_pw.setVisible(true);
			lb_pw.setText("아이디를 입력해주세요.");
			lb_pw.setTextFill(Color.RED);
			txt_id.requestFocus();
			return;
		}else if(txt_id.getText().length()<6) {
			lb_pw.setVisible(true);
			lb_pw.setText("아이디는 6자 이상입니다.");
			lb_pw.setTextFill(Color.RED);
			txt_id.requestFocus();
			return;
		}else if(txt_email.getText().equals("")) {
			lb_pw.setVisible(true);
			lb_pw.setText("메일주소를 입력해주세요.");
			lb_pw.setTextFill(Color.RED);
			txt_email.requestFocus();
			return;
		}
		
		// 아이디, 이메일 확인
		MemberVO vo = new MemberVO();
		vo.setMem_id(txt_id.getText());
		vo.setMem_email(txt_email.getText());
		
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
			idFlag = true;
//			lb_ok.setVisible(false);
			lb_id.setTextFill(Color.valueOf("#00cc00"));
			idFlag  = true;
		}
		
	}


}
