package kr.or.ddit.clap.view.ticket.ticket;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import kr.or.ddit.clap.main.LoginSession;
import kr.or.ddit.clap.service.join.IJoinService;
import kr.or.ddit.clap.service.login.ILoginService;
import kr.or.ddit.clap.service.ticket.ITicketService;
import kr.or.ddit.clap.vo.ticket.TicketBuyListVO;
import kr.or.ddit.clap.vo.ticket.TicketVO;

public class TicketController implements Initializable{
	
	private ITicketService its;
	private Registry reg;
	
	@FXML BorderPane pane;
	@FXML Label lb_date1;
	@FXML Label lb_date2;
	@FXML Label lb_date3; // 이용권 없는 회원
	
	String[] ticketDate = new String[2]; // 이용권 기한. [0]은 만료일. [1]은 남은 일수.

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		LoginSession ls = new LoginSession();
		
		try {
			reg = LocateRegistry.getRegistry("localhost", 8888);
			its = (ITicketService) reg.lookup("ticket");
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
		
		if(ls.session==null) {
			lb_date3.setVisible(true);			
			lb_date3.setText("로그인이 필요합니다.");			
		}else {
			List<TicketBuyListVO> list = new ArrayList<TicketBuyListVO>();
			try {
				list = its.selectList(ls.session.getMem_id());
				System.out.println("사이즈 "+list.size());
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			
			
			if(list.size()==0) {
				lb_date3.setVisible(true);			
				lb_date3.setText("사용중인 이용권이 없습니다.");	
			}else {
				ticketDate = dateCheck(list);
				
				if(ticketDate.equals("no")) {
					lb_date3.setVisible(true);			
					lb_date3.setText("사용중인 이용권이 없습니다.");
				}else {
					lb_date3.setVisible(false);			
					lb_date1.setVisible(true);			
					lb_date2.setVisible(true);			
					lb_date1.setText("이용기간 : "+ticketDate[0]);	
					lb_date1.setTextFill(Color.BLACK);
					lb_date2.setText("( "+ticketDate[1]+"일 남았습니다. )");					
					lb_date2.setTextFill(Color.BLACK);
				}
			}
					
//			lb_date1.setTextFill(Color.valueOf("#00cc00"));

			
		}
		
	}

	private String[] dateCheck(List<TicketBuyListVO> list) {
		// 이용권 기간 계산.
		Date now = new Date();
		long nowTime = now.getTime();
		System.out.println("nowTime  "+nowTime);
		long finalEndTime = 0;
		String finalEndDate = null; // 끝나는 날.
		String checkDay = null; // 남은 일 수.
		String[] result = new String[2];
		
		SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("~ yyyy-MM-dd");
		
		List<TicketBuyListVO> checkList = new ArrayList<TicketBuyListVO>();
		for(int i=0; i<list.size(); i++) {
			// 기간이 유효한 이용권을 checkList에 넣는다.
			// list -> ticket_no, ticket_buydate(예-String 18/12/25)
			// ticket_no : 1,2는 31일. 3,4는 183일. 5,6은 365일.
			// times : 2678400000, 15811200000, 31536000000
			
			// 이용권 만료날짜 계산.
			Date parseDate = null;
			try {
				parseDate = sdf.parse(list.get(i).getTicket_buydate().substring(0, 10));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			long parseDateTime = parseDate.getTime();
			System.out.println("parseDateTime  " + parseDateTime);
			long endDateTime = 0;
			
			// int 범위를 넘어가면 l(long)을 붙여줘야되네.
			if(list.get(i).getTicket_no().equals("1") || list.get(i).getTicket_no().equals("2")) {
				endDateTime = parseDateTime + 2678400000l;
			}else if(list.get(i).getTicket_no().equals("3") || list.get(i).getTicket_no().equals("4")) {
				endDateTime = parseDateTime + 15811200000l;
			}else if(list.get(i).getTicket_no().equals("5") || list.get(i).getTicket_no().equals("6")) {
				endDateTime = parseDateTime + 31536000000l;
			}

			if(endDateTime > nowTime) {
				System.out.println(endDateTime - nowTime);
				finalEndTime += (endDateTime - nowTime);
			}
			
		}
		
		finalEndTime += nowTime;
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(finalEndTime);
		finalEndDate = sdf2.format(cal.getTime());
		
		if(finalEndTime<nowTime) {
			finalEndDate = "no";
			result[1] = "no";
		}else {
			checkDay = String.valueOf((finalEndTime - nowTime)/(1000*60*60*24));
			result[1] = checkDay;
		}
		
		result[0] = finalEndDate;
		
		return result;
	}

	public void buyTicket() {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("BuyTicket.fxml"));
			pane.getChildren().removeAll();
			pane.getChildren().setAll(root);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}