package kr.or.ddit.clap.dao.ticket;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ibatis.sqlmap.client.SqlMapClient;

import kr.or.ddit.clap.main.DBUtil;
import kr.or.ddit.clap.vo.ticket.TicketBuyListVO;
import kr.or.ddit.clap.vo.ticket.TicketVO;

public class TicketDaoImpl implements ITicketDao{
	
	private SqlMapClient smc;
	private static TicketDaoImpl dao; // Singleton 패턴

	private TicketDaoImpl() {
		smc = DBUtil.getConnection();
	}
	
	public static TicketDaoImpl getInstance() { // Singleton 패턴
		if (dao == null) {
			dao = new TicketDaoImpl();
		}
		return dao;
	}
	
	@Override
	public List<TicketBuyListVO> selectList(String id) {
		List<TicketBuyListVO> list = new ArrayList<TicketBuyListVO>();
		try {
			
			list = smc.queryForList("ticket.selectList", id);
		
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
		return list;
	}

	@Override
	public List<TicketBuyListVO> selectTickBuyAllList(TicketBuyListVO vo) {
		List<TicketBuyListVO> list = new ArrayList<TicketBuyListVO>();
		try {
			
			list = smc.queryForList("ticket.selectTickBuyAllList", vo);
		
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
		return list;
	}

	@Override
	public List<TicketVO> selectTicket() {
		List<TicketVO> list = new ArrayList<TicketVO>();
		try {
			
			list = smc.queryForList("ticket.selectTicket");
		
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
		return list;
	}

	@Override
	public int insertTicketBuy(TicketBuyListVO vo) {
		int cnt = 0;
		try {
			Object obj = smc.insert("ticket.insertTicketBuy", vo);
			if (obj == null) { // 쿼리수행이 성공적으로 끝남
				cnt = 1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cnt;
	}

	@Override
	public List<TicketBuyListVO> selectTickChart() {
		List<TicketBuyListVO> list = new ArrayList<TicketBuyListVO>();
		try {
			
			list = smc.queryForList("ticket.selectTickChart");
		
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
		return list;
	}

	@Override
	public List<TicketBuyListVO> buyfind(String id) {
		List<TicketBuyListVO> list = new ArrayList<TicketBuyListVO>();
		try {
			
			list = smc.queryForList("ticket.buyfind", id);
		
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
		return list;
	}

	@Override
	public List<TicketBuyListVO> selectBuyAllist(String id) {
		List<TicketBuyListVO> list = new ArrayList<TicketBuyListVO>();
		try {
			
			list = smc.queryForList("ticket.selectBuyAllist", id);
		
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
		return list;
	}

}
