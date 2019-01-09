package kr.or.ddit.clap.service.ticket;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import kr.or.ddit.clap.dao.ticket.TicketDaoImpl;
import kr.or.ddit.clap.vo.ticket.TicketBuyListVO;

public class TicketServiceImpl extends UnicastRemoteObject implements ITicketService{

	TicketDaoImpl ticketDao; // 사용할 Dao의  멤버변수를 선언
	private static TicketServiceImpl service; // Singleton패턴
	
	private TicketServiceImpl() throws RemoteException {
		super();
		ticketDao =  TicketDaoImpl.getInstance(); // Singleton패턴
	}
	
	public static TicketServiceImpl getInstance() throws RemoteException {
		if(service== null) {
			service = new TicketServiceImpl();
		}
		return service;
	}
	
	@Override
	public List<TicketBuyListVO> selectList(String id) throws RemoteException {
		return ticketDao.selectList(id);
	}
	
	public static void main(String[] args) {
		try {
			service = service.getInstance();
			List<TicketBuyListVO> list = new ArrayList<TicketBuyListVO>();
			list = service.selectList("admin1");
			System.out.println("size  "+list.size());
			System.out.println(list.get(0).getTicket_no());
			System.out.println(list.get(1).getTicket_no());
			System.out.println(list.get(2).getTicket_no());
			System.out.println(list.get(3).getTicket_no());
			System.out.println(list.get(4).getTicket_no());
			
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

}
