package kr.or.ddit.clap.view.ticket.salemanage;

import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Pagination;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import kr.or.ddit.clap.main.LoginSession;
import kr.or.ddit.clap.service.ticket.ITicketService;
import kr.or.ddit.clap.vo.ticket.TicketBuyListVO;

public class SalesMangeController implements Initializable{
	
	private static String user_id = LoginSession.session.getMem_id();
	private Registry reg;
	private ITicketService its;
	
	@FXML JFXComboBox<String> combo_Ticket;
	@FXML JFXDatePicker date_End;
	@FXML JFXDatePicker date_Start;
	
	private ObservableList<TicketBuyListVO> tickeylist, currenttickeylist;
	private int from, to, itemsForPage, totalPageCnt;
	@FXML Pagination p_paging;
	@FXML JFXTreeTableView<TicketBuyListVO> tbl_sales;
	@FXML TreeTableColumn<TicketBuyListVO,String> col_Buyuser;
	@FXML TreeTableColumn<TicketBuyListVO,String> col_TictekNo;
	@FXML TreeTableColumn<TicketBuyListVO,String> col_Price;
	@FXML TreeTableColumn<TicketBuyListVO,String> col_saleDate;
	@FXML TreeTableColumn<TicketBuyListVO,String> col_BuyType;
	
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
		//콤보값 셋팅
		combo_Ticket.getItems().addAll("1개월권","6개월권","1년권","전체");
		combo_Ticket.setValue("=====");
		
		TicketBuyListVO vo =new TicketBuyListVO();
		
		try {
			tickeylist = FXCollections.observableArrayList(its.selectTickBuyAllList(vo));
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		col_Buyuser.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getValue().getMem_id()));
		col_Price.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getValue().getPrice()));
		col_saleDate.setCellValueFactory(
				param -> new SimpleObjectProperty<>(param.getValue().getValue().getTicket_buydate()));
		col_BuyType.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getValue().getTicket_buy_type()));
		TreeItem<TicketBuyListVO> root = new RecursiveTreeItem<>(tickeylist, RecursiveTreeObject::getChildren);
		tbl_sales.setRoot(root);
		tbl_sales.setShowRoot(false);

		itemsForPage = 10; // 한페이지 보여줄 항목 수 설정

		paging();
	}

	@FXML
	public void btn_Search() { // 조회버튼 클릭시
		// if(date_Start.ise)
		try {
			TicketBuyListVO vo = new TicketBuyListVO();
			vo.setTicket_buydate(date_Start.getValue().toString());
			vo.setTicket_buydate1(date_End.getValue().toString());

			ObservableList<TicketBuyListVO> searchlist = FXCollections.observableArrayList();
			switch (combo_Ticket.getValue()) {

			case "1개월권":
				vo.setTicket_no("1");
				searchlist = FXCollections.observableArrayList(its.selectTickBuyAllList(vo));
				col_TictekNo.setCellValueFactory(param -> new SimpleObjectProperty<>("1개월권"));
				break;
			case "6개월권":
				vo.setTicket_no("3");
				searchlist = FXCollections.observableArrayList(its.selectTickBuyAllList(vo));
				col_TictekNo.setCellValueFactory(param -> new SimpleObjectProperty<>("6개월권"));
				break;
			case "1년권":
				vo.setTicket_no("4");
				searchlist = FXCollections.observableArrayList(its.selectTickBuyAllList(vo));
				col_TictekNo.setCellValueFactory(param -> new SimpleObjectProperty<>("1년권"));
				break;
			case "전체":

				searchlist = FXCollections.observableArrayList(its.selectTickBuyAllList(vo));
				col_TictekNo.setCellValueFactory(
						param -> new SimpleObjectProperty<>(param.getValue().getValue().getTicket_no()));

				break;
			default:
				break;
			}

			tickeylist = FXCollections.observableArrayList(searchlist); // 검색조건에 맞는 리스트를 저장
			paging();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void paging() {
		totalPageCnt = tickeylist.size() % itemsForPage == 0 ? tickeylist.size() / itemsForPage
				: tickeylist.size() / itemsForPage + 1;

		p_paging.setPageCount(totalPageCnt); // 전체 페이지 수 설정

		p_paging.setPageFactory((Integer pageIndex) -> {

			from = pageIndex * itemsForPage;
			to = from + itemsForPage - 1;

			TreeItem<TicketBuyListVO> root = new RecursiveTreeItem<>(getTableViewData(from, to),
					RecursiveTreeObject::getChildren);
			tbl_sales.setRoot(root);
			tbl_sales.setShowRoot(false);
			return tbl_sales;
		});
	}

	// 페이징에 맞는 데이터를 가져옴
	private ObservableList<TicketBuyListVO> getTableViewData(int from, int to) {

		currenttickeylist = FXCollections.observableArrayList(); //
		int totSize = tickeylist.size();
		for (int i = from; i <= to && i < totSize; i++) {

			currenttickeylist.add(tickeylist.get(i));
		}

		return currenttickeylist;
	}

	@FXML public void graph() {
		System.out.println("dsg");
	}

}
