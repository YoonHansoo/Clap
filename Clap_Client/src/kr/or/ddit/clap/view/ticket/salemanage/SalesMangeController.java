package kr.or.ddit.clap.view.ticket.salemanage;

import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import kr.or.ddit.clap.main.LoginSession;
import kr.or.ddit.clap.service.ticket.ITicketService;
import kr.or.ddit.clap.vo.myalbum.MyAlbumListVO;
import kr.or.ddit.clap.vo.ticket.TicketVO;
import javafx.fxml.FXML;
import com.jfoenix.controls.JFXComboBox;

public class SalesMangeController implements Initializable{
	
	private static String user_id = LoginSession.session.getMem_id();
	private Registry reg;
	private ITicketService its;
	
	private ObservableList<TicketVO> currentsingerList;
	@FXML JFXComboBox combo_Ticket;
	
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
		combo_Ticket.getItems().addAll("1개월권","6개월권","1년권");
		//combo_Ticket.setValue(combo_search.getItems().get(0));

	}

	
	
	
	public void settable() {
	/*	TicketVO vo = new TicketVO();
		vo.get

		vo.setMem_id(user_id);
		vo.setMyalb_no(myAlbNo);*/
	}

}
