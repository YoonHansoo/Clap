package kr.or.ddit.clap.dao.ticket;

import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

import kr.or.ddit.clap.vo.myalbum.MyAlbumVO;
import kr.or.ddit.clap.vo.ticket.TicketBuyListVO;

public class TicketDaoImpl implements ITicketDao{
	
	private SqlMapClient smc;
	private static TicketDaoImpl dao; // Singleton 패턴

	private TicketDaoImpl() {
		Reader rd;
		try {
			rd = Resources.getResourceAsReader("SqlMapConfig.xml");
			smc = SqlMapClientBuilder.buildSqlMapClient(rd);
			rd.close();
		} catch (IOException e) {
			System.out.println("SqlMapClient객체 생성 실패!!");
			e.printStackTrace();
		}
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

}
