package kr.or.ddit.clap.dao.ticket;

import java.util.List;

import kr.or.ddit.clap.vo.ticket.TicketBuyListVO;

public interface ITicketDao {
	public List<TicketBuyListVO> selectList(String id);
}
