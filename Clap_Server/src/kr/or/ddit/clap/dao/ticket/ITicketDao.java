package kr.or.ddit.clap.dao.ticket;

import java.util.List;

import kr.or.ddit.clap.vo.ticket.TicketBuyListVO;
import kr.or.ddit.clap.vo.ticket.TicketVO;

public interface ITicketDao {
	public List<TicketBuyListVO> selectList(String id);
	public List<TicketBuyListVO> selectTickBuyAllList(TicketBuyListVO vo);
	public List<TicketVO> selectTicket();
	public List<TicketBuyListVO> selectTickChart();
	
	public int insertTicketBuy(TicketBuyListVO vo);
	
	public List<TicketBuyListVO> buyfind(String id);
	
	public List<TicketBuyListVO> selectBuyAllist(String id);
}
