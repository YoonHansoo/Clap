package kr.or.ddit.clap.dao.ticket;

import java.util.List;

import kr.or.ddit.clap.vo.ticket.TicketBuyListVO;
import kr.or.ddit.clap.vo.ticket.TicketVO;

public interface ITicketDao {
	public List<TicketBuyListVO> selectList(String id);
	public List<TicketBuyListVO> selectTickBuyAllList(TicketBuyListVO vo);
	public List<TicketVO> selectTicket();
	
	public int insertTicketBuy(TicketBuyListVO vo);
}
