package kr.or.ddit.clap.view.ticket.ticket;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import kr.or.ddit.clap.main.LoginSession;
import kr.or.ddit.clap.view.join.AES256Util;
import kr.or.ddit.clap.vo.ticket.TicketBuyListVO;

public class BuyTicketController implements Initializable{

	@FXML Label lb1;
	@FXML Label lb2;
	@FXML Label lb3;
	@FXML Label lb4;
	@FXML Label lb_vip;
	@FXML ImageView imgView;
	@FXML JFXRadioButton rb1;
	@FXML JFXRadioButton rb2;
	@FXML JFXRadioButton rb3;
	@FXML JFXRadioButton rb4;
	@FXML JFXRadioButton rb_card1;
	@FXML JFXRadioButton rb_card2;
	@FXML JFXComboBox<String> combo1;
	@FXML JFXComboBox<String> combo2;
	@FXML Button btn_ok;
	
	SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	SimpleDateFormat sdf2 = new SimpleDateFormat("~ yyyy-MM-dd");
	TicketController tc = new TicketController();
	TicketBuyListVO vo = new TicketBuyListVO();
	LoginSession ls = new LoginSession();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		vo = new TicketBuyListVO();
		vo.setMem_id(ls.session.getMem_id());
		vo.setTicket_no(String.valueOf(tc.ticketInfo[4]));
		System.out.println("ticket no : "+tc.ticketInfo[4]);
		vo.setAccount_holder(ls.session.getMem_name());
		vo.setRefund_tf("f");
		Date now = new Date();
		
		lb1.setText((String) tc.ticketInfo[0]+" 이용권");
		lb2.setText((String) tc.ticketInfo[2]);
		
		System.out.println(tc.ticketInfo[3]);
		String lb4_str = "";
		if((boolean) tc.ticketInfo[3]) {
			lb3.setText(tc.ticketDate[0]);
			lb4_str = check_lb4(tc.ticketDate[0]);
			
		}else if(!(boolean) tc.ticketInfo[3]) {
			lb3.setText(sdf2.format(now).substring(2, sdf2.format(now).length()));
			String today = sdf2.format(now);
			lb4_str = check_lb4(today);
			lb4_str = "~" + lb4_str.substring(1, lb4_str.length());
		}
		lb4.setText(lb4_str);
		
		Image img = new Image(getClass().getResourceAsStream("../../../../../../../ticket90.jpg"));
		imgView.setImage(img);
		
		System.out.println(tc.ticketInfo[0]);
		
		ToggleGroup group = new ToggleGroup();
		ToggleGroup group2 = new ToggleGroup();
		rb1.setToggleGroup(group);
		rb2.setToggleGroup(group);
		rb3.setToggleGroup(group);
		rb4.setToggleGroup(group);
		rb_card1.setToggleGroup(group2);
		rb_card2.setToggleGroup(group2);
		
		rb1.setUserData("카드");
		rb2.setUserData("2");
		rb3.setUserData("3");
		rb4.setUserData("4");
		rb_card1.setUserData("개인");
		rb_card2.setUserData("법인");
		rb1.setSelected(true);
		rb_card1.setSelected(true);
		
		group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			@Override
			public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
				if(group.getSelectedToggle().getUserData()!=null) {
					System.out.println(group.getSelectedToggle().getUserData());
				}
			}
		});
		group2.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			@Override
			public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
				if(group2.getSelectedToggle().getUserData()!=null) {
					System.out.println(group2.getSelectedToggle().getUserData());
				}
			}
		});
		
		combo1.getItems().addAll("삼성카드", "비씨카드", "신한은행", "현대카드", "KB국민은행", 
				"롯데카드", "외환은행", "NH농협은행", "하나은행", "카카오뱅크", "케이뱅크");
		combo1.setValue("삼성카드");
		combo2.getItems().add("일시불");
		combo2.setValue("일시불");
		
		btn_ok.setOnAction(e->{
			Stage dialog = new Stage(StageStyle.DECORATED);
			if(dialog.getModality()==null) {
				dialog.initModality(Modality.APPLICATION_MODAL);				
			}
			if(dialog.getOwner()==null) {
				dialog.initOwner(btn_ok.getScene().getWindow());				
			}
			
			AnchorPane parent = null;
			try {
				parent = FXMLLoader.load(getClass().getResource("BuyTicketCard.fxml"));
			}catch(IOException ee) {
				ee.printStackTrace();
			}
			
			// 부모창에서 FXML로 만든 자식창의 컨트롤객체 얻기 ★★ "lookup()"	
			Label lblb1 = (Label) parent.lookup("#lblb1");
			Label lblb2 = (Label) parent.lookup("#lblb2");
			Label lb_card = (Label) parent.lookup("#lb_card");
			TextField txt1 = (TextField) parent.lookup("#txt1");
			TextField txt2 = (TextField) parent.lookup("#txt2");
			PasswordField txt3 = (PasswordField) parent.lookup("#txt3");
			PasswordField txt4 = (PasswordField) parent.lookup("#txt4");
			Button btn_okok = (Button) parent.lookup("#btn_okok");
			Button btn_cancel = (Button) parent.lookup("#btn_cancel");
			
			lblb1.setText((String) tc.ticketInfo[2]);
			Date time = new Date();
			lblb2.setText(sdf1.format(time));
			lb_card.setText(combo1.getValue());
			
			vo.setCard_bank_name(combo1.getValue());
			vo.setTicket_buy_type(String.valueOf(group.getSelectedToggle().getUserData()));
			
			btn_okok.setOnAction(event->{
				System.out.println(txt1.getText());
				
				// 카드번호 뒷부분 암호화.
				String encryptedTxt3 = "";
				String encryptedTxt4 = "";
				try {
					AES256Util aes = new AES256Util();
					encryptedTxt3 = aes.encrypt(txt3.getText());
					encryptedTxt4 = aes.encrypt(txt4.getText());
					System.out.println(encryptedTxt4);
				} catch (UnsupportedEncodingException | GeneralSecurityException e1) {
					e1.printStackTrace();
				}
				
				vo.setCard_account_no(txt1.getText()+txt2.getText()+encryptedTxt3+encryptedTxt4);
			});
			
//			btnCancel.setOnAction(event->{
//				dialog.close();
//			});
			
			// 5. Scene 객체 생성해서 컨테이너 객체 추가하기
			Scene scene = new Scene(parent);
			
			// 6. Stage 객체에 Scene 객체 추가
			dialog.setScene(scene);
			dialog.setResizable(false); // 크기 고정
			dialog.show();
			
			txt1.setOnKeyReleased(e2->{
				System.out.println(txt1.getText().length());
				if(txt1.getText().length()==4) {
					txt2.requestFocus();
				}
			});
			txt2.setOnKeyReleased(e2->{
				if(txt2.getText().length()==4) {
					txt3.requestFocus();
				}
			});
			txt3.setOnKeyReleased(e2->{
				if(txt3.getText().length()==4) {
					txt4.requestFocus();
				}
			});

		});
		
	}
	
	public String check_lb4(String compareDate) {
		// lb4 값 구하기.
		// 날짜 + 일수 ( ~ 2019-02-20 (+31일) )
		Date parseDate = null;
		try {
			parseDate = sdf2.parse(compareDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(parseDate);
		
		String lb4_str = "";
		if(String.valueOf(tc.ticketInfo[0]).equals("1개월")) {
			cal1.add(Calendar.DAY_OF_MONTH, 31);
			lb4_str = sdf2.format(cal1.getTime()) + " (+31일)";
			lb_vip.setVisible(false);
		}else if(String.valueOf(tc.ticketInfo[0]).equals("6개월")) {
			cal1.add(Calendar.DAY_OF_MONTH, 183);
			lb4_str = sdf2.format(cal1.getTime()) + " (+183일)";
			lb_vip.setVisible(false);
		}else if(String.valueOf(tc.ticketInfo[0]).equals("1년")) {
			cal1.add(Calendar.DAY_OF_MONTH, 365);
			lb4_str = sdf2.format(cal1.getTime()) + " (+365일)";
			lb_vip.setVisible(true);
		}
		
		lb4_str = "→" + lb4_str.substring(1, lb4_str.length());
		return lb4_str;
	}
	
	public void setVO() throws UnsupportedEncodingException, NoSuchAlgorithmException, GeneralSecurityException {
		AES256Util aes = new AES256Util();
//		String output = aes.encrypt();

	}

}
