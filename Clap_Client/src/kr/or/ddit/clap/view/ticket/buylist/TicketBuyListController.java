package kr.or.ddit.clap.view.ticket.buylist;

import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import kr.or.ddit.clap.main.LoginSession;
import kr.or.ddit.clap.service.ticket.ITicketService;
import kr.or.ddit.clap.view.ticket.ticket.TicketController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class TicketBuyListController implements Initializable{
	
	private static String user_id = LoginSession.session.getMem_id();
	private Registry reg;
	private ITicketService its;
	@FXML Label la_Date;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			reg = LocateRegistry.getRegistry("localhost", 8888);
			its = (ITicketService) reg.lookup("ticket");

			// 파라미터로 받은 정보를 PK로 상세정보를 가져옴
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
	
		
	      TicketController tc = new TicketController();
	      String[] arr = tc.ticketCheck(user_id);
	      System.out.println(arr[0]);
	      System.out.println(arr[1]);
	      System.out.println(arr[2]);
	}
	

}
