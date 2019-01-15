package kr.or.ddit.clap.view.ticket.buylist;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;

import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import javafx.fxml.Initializable;
import kr.or.ddit.clap.main.LoginSession;
import kr.or.ddit.clap.service.ticket.ITicketService;
import kr.or.ddit.clap.view.ticket.ticket.TicketController;
import kr.or.ddit.clap.vo.ticket.TicketBuyListVO;
import kr.or.ddit.clap.vo.ticket.TicketVO;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.TreeTableColumn;

public class TicketBuyListController implements Initializable{
	
	private static String user_id = LoginSession.session.getMem_id();
	private Registry reg;
	private ITicketService its;
	
	private ObservableList<TicketBuyListVO> tickeylist1, currenttickeylist1;
	private ObservableList<TicketBuyListVO> tickeylist2, currenttickeylist2;
	private int from, to, itemsForPage, totalPageCnt;
	@FXML Label la_Date;
	@FXML Label la_Date2;
	@FXML AnchorPane contents;
	@FXML TreeTableView<TicketBuyListVO> tbl1_ticket;
	@FXML TreeTableColumn<TicketBuyListVO,String> col1_ticketname;
	@FXML TreeTableColumn<TicketBuyListVO,String> col1_tickettime;
	@FXML TreeTableView<TicketBuyListVO> tbl2_ticket;
	@FXML TreeTableColumn<TicketBuyListVO,String> col2_ticketname;
	@FXML TreeTableColumn<TicketBuyListVO,String> col2_tickettime;
	@FXML TreeTableColumn<TicketBuyListVO,String> col2_ticketbuydate;
	@FXML TreeTableColumn<TicketBuyListVO,String> col2_buyType;
	@FXML Pagination p_paging1;
	@FXML Pagination p_paging2;
	
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
	      System.out.println("0"+arr[0]);
	      System.out.println("1"+arr[1]);
	      System.out.println(arr[2]);
	      
	      la_Date.setText(arr[1]);
	      la_Date2.setText(arr[2]);
	      
	      TicketBuyListVO vo =new TicketBuyListVO();
			try {
				tickeylist1 = FXCollections.observableArrayList(its.selectTickBuyAllList(vo));
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			
		/*	for(int i=0; i< tickeylist1.size(); i++) {
				if(tickeylist1.get(i).getTicket_no().)
			}*/
			//col1_ticketname.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getValue().()));
			col1_tickettime.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getValue().getPrice()));
			TreeItem<TicketBuyListVO> root = new RecursiveTreeItem<>(tickeylist1, RecursiveTreeObject::getChildren);
			tbl1_ticket.setRoot(root);
			tbl1_ticket.setShowRoot(false);

			itemsForPage = 10; // 한페이지 보여줄 항목 수 설정
			
	      
	}

	@FXML public void buyTicket() {
		try {
		Parent root = FXMLLoader.load(getClass().getResource("../ticket/Ticket.fxml"));
		contents.getChildren().removeAll();
		contents.getChildren().setAll(root);

	} catch (IOException e) {
		e.printStackTrace();
		}
	}
	

}
