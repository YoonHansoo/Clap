package kr.or.ddit.clap.service.ticket;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import kr.or.ddit.clap.vo.ticket.TicketBuyListVO;

public interface ITicketService extends Remote{
	public List<TicketBuyListVO> selectList(String id) throws RemoteException;
}
