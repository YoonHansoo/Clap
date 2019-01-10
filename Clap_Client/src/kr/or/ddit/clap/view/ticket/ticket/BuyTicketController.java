package kr.or.ddit.clap.view.ticket.ticket;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
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
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		TicketController tc = new TicketController();
		LoginSession ls = new LoginSession();
		SimpleDateFormat sdf2 = new SimpleDateFormat("~ yyyy-MM-dd");
		
		lb1.setText((String) tc.ticketInfo[0]+" 이용권");
		lb2.setText((String) tc.ticketInfo[2]);
		
		lb3.setText(tc.ticketDate[0]);
		// lb4 값 구하기.
		// 날짜 + 일수 ( ~ 2019-02-20 (+31일) )
		Date parseDate = null;
		try {
			parseDate = sdf2.parse(tc.ticketDate[0]);
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
		lb4.setText(lb4_str);
		
		
		Image img = new Image(getClass().getResourceAsStream("../../../../../../../ticket90.jpg"));
		imgView.setImage(img);
		
		
		System.out.println(tc.ticketInfo[0]);
		System.out.println(tc.ticketInfo[1]);
		System.out.println(tc.ticketInfo[2]);
	}

}
