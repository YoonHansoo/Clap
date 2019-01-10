package kr.or.ddit.clap.view.ticket.ticket;

import java.net.URL;
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
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import kr.or.ddit.clap.main.LoginSession;

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
	
	SimpleDateFormat sdf2 = new SimpleDateFormat("~ yyyy-MM-dd");
	TicketController tc = new TicketController();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		LoginSession ls = new LoginSession();
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
		
		rb1.setUserData("card");
		rb2.setUserData("2");
		rb3.setUserData("3");
		rb4.setUserData("4");
		rb_card1.setUserData("개인");
		rb_card2.setUserData("법인");
		rb1.setSelected(true);
		
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
		
		combo1.getItems().addAll("국민", "비씨", "신한", "현대", "삼성", "롯데", "외환", "NH", 
				"하나", "카카오뱅크", "케이뱅크");
		combo1.setValue("국민");
		combo2.getItems().add("일시불");
		combo2.setValue("일시불");
		
		btn_ok.setOnAction(e->{
			
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

}
