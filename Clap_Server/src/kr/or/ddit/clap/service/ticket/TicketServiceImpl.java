package kr.or.ddit.clap.service.ticket;

import java.rmi.RemoteException;
import java.util.List;

import kr.or.ddit.clap.dao.ticket.TicketDaoImpl;
import kr.or.ddit.clap.vo.ticket.TicketBuyListVO;

public class TicketServiceImpl implements ITicketService{

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

}
