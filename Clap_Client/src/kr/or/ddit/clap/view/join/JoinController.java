package kr.or.ddit.clap.view.join;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
	@FXML JFXTextField txt_bir;
	@FXML JFXTextField txt_tel;
	@FXML JFXTextField txt_email;
	@FXML JFXTextField txt_emailCheck;
	@FXML JFXTextField txt_name;
	@FXML JFXTextField txt_captcha;
	
	@FXML JFXButton btn_ok;
	@FXML JFXRadioButton radio_m;
	@FXML JFXRadioButton radio_f;
	
	@FXML ImageView img_captcha;
	@FXML Label lb_id;
	@FXML Label lb_pw;
	@FXML Label lb_pwCheck;
	@FXML Label lb_bir;
	@FXML Label lb_tel;
	@FXML Label lb_email;
	@FXML Label lb_emailCheck;
	@FXML Label lb_ok;
	@FXML Label lb_captcha;
	@FXML Label lb_captcha2;
	
	ToggleGroup group = new ToggleGroup();
	
	private IJoinService ijs;
	private ILoginService ils;
	private Registry reg;
	
	private boolean pwFlag;
	String code = null; // 이메일 인증코드
	private String captchaKey = "";
	File f = null; // captchaimg파일
	String captchaImg = ""; // captcha img 파일이름.
	SimpleDateFormat sdf = new SimpleDateFormat("yy/MM/dd");
	private boolean idFlag = false;
	private boolean pwFlag2 = false; // 비밀번호 확인까지 완료되었는지 확인.
	private boolean birFlag = false;
	private boolean telFlag = false;
	private boolean emailFlag = false;
	private boolean captchaFlag = false;

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
		
		txt_pw.setOnAction(e->{
			pwCheck();
		});
		
		txt_pwCheck.setOnAction(e->{
			pwEqualCheck();
		});
		
		txt_bir.setOnAction(e->{
			birCheck();
		});
		
		txt_tel.setOnAction(e->{
			telCheck();
		});
		
		captchaKey = captchaKey();
		captchaImage(captchaKey);
		
		
	}
	
	public void captchaImage(String CaptchaKey) {
        String clientId = "klxHW7Dv5xl3eCyGm4My";//애플리케이션 클라이언트 아이디값";
        String clientSecret = "FLAfQWcfxj";//애플리케이션 클라이언트 시크릿값";
        try {
            String key = CaptchaKey; // https://openapi.naver.com/v1/captcha/nkey 호출로 받은 키값
            String apiURL = "https://openapi.naver.com/v1/captcha/ncaptcha.bin?key=" + key;
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("X-Naver-Client-Id", clientId);
            con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
            int responseCode = con.getResponseCode();
            BufferedReader br;
            if(responseCode==200) { // 정상 호출
                InputStream is = con.getInputStream();
                int read = 0;
                byte[] bytes = new byte[1024];
                // 랜덤한 이름으로 파일 생성
                captchaImg = Long.valueOf(new Date().getTime()).toString();
                // 파일 저장위치 변경해보자 (d:/D_Other/복사본_Tulips.jpg)
                // join패키지에 넣자 일단.
                f = new File("captchaImg.jpg");
                f.createNewFile();
                OutputStream outputStream = new FileOutputStream(f);
                while ((read =is.read(bytes)) != -1) {
                    outputStream.write(bytes, 0, read);
                }
                is.close();
            } else {  // 에러 발생
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = br.readLine()) != null) {
                    response.append(inputLine);
                }
                br.close();
                System.out.println(response.toString());
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        
		Image captcha = new Image("file:"+f.getAbsolutePath());
		img_captcha.setImage(captcha);
	}
	
	public String captchaKey() {
        String clientId = "klxHW7Dv5xl3eCyGm4My";//애플리케이션 클라이언트 아이디값";
        String clientSecret = "FLAfQWcfxj";//애플리케이션 클라이언트 시크릿값";
        String result = "";
        try {
            String code = "0"; // 키 발급시 0,  캡차 이미지 비교시 1로 세팅
            String apiURL = "https://openapi.naver.com/v1/captcha/nkey?code=" + code;
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("X-Naver-Client-Id", clientId);
            con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
            int responseCode = con.getResponseCode();
            BufferedReader br;
            if(responseCode==200) { // 정상 호출
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {  // 에러 발생
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();
            System.out.println(response.toString());
            result = response.toString().split("\"")[3];
        } catch (Exception e) {
            System.out.println(e);
        }

		return result;
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
		
		
		// 아이디 유효성 검사
		if(txt_id.getText().equals("")) {
			lb_ok.setVisible(true);
			lb_ok.setText("아이디를 입력해주세요.");
			lb_ok.setTextFill(Color.RED);
			txt_id.requestFocus();	
		}else if(!idFlag) {
			lb_ok.setVisible(true);
			lb_ok.setText("아이디 중복확인을 해주세요.");
			lb_ok.setTextFill(Color.RED);
			txt_id.requestFocus();
		}else if(txt_pw.getText().equals("")) {
			lb_ok.setVisible(true);
			lb_ok.setText("비밀번호를 입력해주세요.");
			lb_ok.setTextFill(Color.RED);
			txt_pw.requestFocus();			
		}else if(!pwFlag) {
			pwCheck();
		}else if(txt_pwCheck.getText().equals("")) {
			lb_ok.setVisible(true);
			lb_ok.setText("비밀번호를 한번 더 입력해주세요.");
			lb_ok.setTextFill(Color.RED);
			txt_pwCheck.requestFocus();			
		}else if(!pwFlag2) {
			pwCheck();
			lb_ok.setVisible(true);
			lb_ok.setText("비밀번호가 일치하지 않습니다.");
			lb_ok.setTextFill(Color.RED);
			txt_pwCheck.requestFocus();						
		}else if(txt_name.getText().equals("")) {
			lb_ok.setVisible(true);
			lb_ok.setText("이름을 입력해주세요.");
			lb_ok.setTextFill(Color.RED);
			txt_name.requestFocus();	
		}else if(txt_bir.getText().equals("")) {
			lb_ok.setVisible(true);
			lb_ok.setText("생년월일을 입력해주세요.");
			lb_ok.setTextFill(Color.RED);
			txt_bir.requestFocus();
		}else if(!birFlag) {
			birCheck();
			lb_ok.setVisible(true);
			lb_ok.setText("생년월일을 확인해주세요.");
			lb_ok.setTextFill(Color.RED);
			txt_bir.requestFocus();
		} else if (txt_tel.getText().equals("")) {
			lb_ok.setVisible(true);
			lb_ok.setText("전화번호를 입력해주세요.");
			lb_ok.setTextFill(Color.RED);
			txt_tel.requestFocus();
		} else if (!telFlag) {
			telCheck();
			lb_ok.setVisible(true);
			lb_ok.setText("전화번호를 확인해주세요.");
			lb_ok.setTextFill(Color.RED);
			txt_tel.requestFocus();
		} else if (txt_email.getText().equals("")) {
			lb_ok.setVisible(true);
			lb_ok.setText("메일주소를 입력해주세요.");
			lb_ok.setTextFill(Color.RED);
			txt_email.requestFocus();
		} else if (!emailFlag) {
			lb_ok.setVisible(true);
			lb_ok.setText("메일 인증을 완료해주세요.");
			lb_ok.setTextFill(Color.RED);
			txt_emailCheck.requestFocus();
		} else if (txt_captcha.getText().equals("")) {
			lb_ok.setVisible(true);
			lb_ok.setText("보안문자를 입력해주세요.");
			lb_ok.setTextFill(Color.RED);
			txt_captcha.requestFocus();
		} else if (!captchaFlag) {
			lb_ok.setVisible(true);
			lb_ok.setText("보안문자를 확인을 완료해주세요.");
			lb_ok.setTextFill(Color.RED);
			txt_captcha.requestFocus();
		}else {
			vo.setMem_id(txt_id.getText());
			vo.setMem_pw(encryptedPw);
			vo.setMem_name(txt_name.getText());
			vo.setMem_email(txt_email.getText());
			vo.setMem_bir(txt_bir.getText());

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
    			idFlag = true;
    			lb_ok.setVisible(false);
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
	
	public void pwCheck() {
		Pattern p = Pattern.compile("(^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[`~!@#$%^&*?]).{8,}$)");
		Matcher m = p.matcher(txt_pw.getText());
		
        if(m.find()) { // m.find() -> true or false.
        	System.out.println("ok");
        	pwFlag = true;
        	lb_ok.setVisible(false);
        	lb_ok.setVisible(false);
        	lb_pw.setVisible(false);
        }else{
        	System.out.println("no");
        	lb_pw.setVisible(true);
        	lb_pw.setText("규칙에 맞게 입력해주세요.");
        	lb_pw.setTextFill(Color.RED);
        	txt_pw.requestFocus();
        }  
	}
	
	public void pwEqualCheck() {
		if(!pwFlag) {
			lb_pwCheck.setVisible(true);
			lb_pwCheck.setText("비밀번호를 먼저 입력해주세요.");			
			lb_pwCheck.setTextFill(Color.RED);
			txt_pw.requestFocus();
		}else if(!txt_pw.getText().equals(txt_pwCheck.getText())) {
			lb_pwCheck.setVisible(true);
			lb_pwCheck.setText("비밀번호가 일치하지 않습니다.");
			lb_pwCheck.setTextFill(Color.RED);
			txt_pwCheck.requestFocus();
		}else {
			lb_pwCheck.setVisible(true);
			lb_pwCheck.setText("확인되었습니다.");
			pwFlag2 = true;
			lb_pwCheck.setTextFill(Color.valueOf("#00cc00"));
		}
	}
	
	public void birCheck() {
		// 생년월일. 정규표현식 검사후 유효성 검사.
		Pattern p = Pattern.compile("(^\\d{2}/\\d{2}/\\d{2}$)");
		Matcher m = p.matcher(txt_bir.getText());
		
		if(!m.find()) {
			lb_bir.setVisible(true);
			lb_bir.setText("형식에 맞게 입력해주세요.");
			lb_bir.setTextFill(Color.RED);
			txt_bir.requestFocus();
		}else {
			// 유효성 검사.
			boolean birCheck = dateCheck(txt_bir.getText(), "yy/MM/dd");
			System.out.println(birCheck);
			if(!birCheck) {
				lb_bir.setVisible(true);
				lb_bir.setText("생년월일을 다시 확인해주세요.");
				lb_bir.setTextFill(Color.RED);
				txt_bir.requestFocus();
			}else if(birCheck){
				birFlag = true;
				lb_ok.setVisible(false);
				lb_bir.setVisible(false);				
			}
		}
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
	
	public void telCheck() {
		Pattern p = Pattern.compile("(^01\\d{9}$)");
		Matcher m = p.matcher(txt_tel.getText());
		
		if(!m.find()) {
			lb_tel.setVisible(true);
			lb_tel.setText("형식에 맞게 입력해주세요.");
			lb_tel.setTextFill(Color.RED);
			txt_tel.requestFocus();
		}else {
			telFlag = true;
			lb_ok.setVisible(false);
			lb_tel.setVisible(false);
		}
	}
	
	public void emailCheck() throws UnsupportedEncodingException, NoSuchAlgorithmException, GeneralSecurityException {
		AES256Util aes = new AES256Util();
		
		if(txt_email.getText().equals("")) {
			lb_email.setVisible(true);
			lb_email.setText("메일주소을 입력해주세요.");
			lb_email.setTextFill(Color.RED);
			txt_email.requestFocus();
			return;
		}
		
		System.out.println(txt_email.getText());
		// java mail 로직. 인증코드 6자. 난수발생후 코드를..
		String ran = String.valueOf((int)(Math.random()*100)+1);
		code = aes.encrypt(ran).substring(0, 6);
		
		String host = "smtp.naver.com";
		final String user = "ykh1762@naver.com";
		final String password = "q1w2e3r4";

		System.out.println(txt_email.getText()+"로 이메일 발송");
		String to = txt_email.getText();
		
		Properties props = new Properties();
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.auth", "true");

		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(user, password);
			}
		});
		
		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(user));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

			message.setSubject("[JavaMail 인증코드] clap:음악, 그리고 설레임.");

			message.setText("clap:음악, 그리고 설레임. 다음의 인증코드를 입력하고 회원가입을 진행하세요. 인증코드 : "+code);

			// send the message
			Transport.send(message);
			System.out.println("message sent successfully...");

		} catch (MessagingException e) {
			e.printStackTrace();
		}
		
		lb_email.setVisible(true);
		lb_email.setText("인증코드가 메일로 발송되었습니다.");
		lb_email.setTextFill(Color.valueOf("#00cc00"));
		txt_emailCheck.requestFocus();
	}
	
	public void codeCheck() {
		if(txt_emailCheck.getText().equals("")) {
			lb_emailCheck.setVisible(true);
			lb_emailCheck.setText("인증코드를 입력해주세요.");
			lb_emailCheck.setTextFill(Color.RED);
			txt_emailCheck.requestFocus();			
		}else if(code == null) {
			lb_emailCheck.setVisible(true);
			lb_emailCheck.setText("인증코드 발송을 먼저 해주세요.");
			lb_emailCheck.setTextFill(Color.RED);
		}else if(!code.equals(txt_emailCheck.getText())) {
			lb_emailCheck.setVisible(true);
			lb_emailCheck.setText("인증코드가 일치하지 않습니다.");
			lb_emailCheck.setTextFill(Color.RED);
			txt_emailCheck.requestFocus();
		}else if(code.equals(txt_emailCheck.getText()) && code != null){
			lb_emailCheck.setVisible(true);
			lb_emailCheck.setText("확인되었습니다.");
			emailFlag = true;
			lb_ok.setVisible(false);
			lb_emailCheck.setTextFill(Color.valueOf("#00cc00"));
		}
	}
	
	public void imgRefresh() {
		captchaKey = captchaKey();
		captchaImage(captchaKey);
	}
	
	public void captchaCheck() {
		String result = captchaResult(captchaKey, txt_captcha.getText());
		if(txt_captcha.getText().equals("")) {
			lb_captcha.setVisible(true);
			lb_captcha.setText("보안문자를 입력해주세요.");
			captchaFlag = false;
			lb_captcha.setTextFill(Color.RED);
			txt_captcha.requestFocus();	
		}else if(result.equals("false")) {
			imgRefresh();
			lb_captcha.setVisible(true);
			lb_captcha2.setVisible(true);
			lb_captcha.setPadding(new Insets(0));
			lb_captcha.setText("보안문자가 일치하지 않습니다.");
			lb_captcha2.setText("새로 입력해주세요.");
			captchaFlag = false;
			lb_captcha.setTextFill(Color.RED);
			lb_captcha2.setTextFill(Color.RED);
			txt_captcha.requestFocus();	
		}else if(result.equals("true,")) {
			lb_captcha.setVisible(true);
			lb_captcha2.setVisible(false);
			lb_captcha.setPadding(new Insets(3));
			lb_captcha.setText("확인되었습니다.");
			captchaFlag = true;
			lb_ok.setVisible(false);
			lb_captcha.setTextFill(Color.valueOf("#00cc00"));
		}
	}
	
	public String captchaResult(String CaptchaKey, String input) {
        String clientId = "klxHW7Dv5xl3eCyGm4My";//애플리케이션 클라이언트 아이디값";
        String clientSecret = "FLAfQWcfxj";//애플리케이션 클라이언트 시크릿값";
        String result = "";
        try {
            String code = "1"; // 키 발급시 0,  캡차 이미지 비교시 1로 세팅
            String key = CaptchaKey; // 캡차 키 발급시 받은 키값
            String value = input; // 사용자가 입력한 캡차 이미지 글자값
            String apiURL = "https://openapi.naver.com/v1/captcha/nkey?code=" + code +"&key="+ key + "&value="+ value;

            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("X-Naver-Client-Id", clientId);
            con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
            int responseCode = con.getResponseCode();
            BufferedReader br;
            if(responseCode==200) { // 정상 호출
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {  // 에러 발생
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();
            System.out.println(response.toString());
            result = response.toString().substring(10, 15);
        } catch (Exception e) {
            System.out.println(e);
        }
		return result;
	}
	
}
