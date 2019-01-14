package kr.or.ddit.clap.dao.message;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ibatis.sqlmap.client.SqlMapClient;

import kr.or.ddit.clap.main.DBUtil;
import kr.or.ddit.clap.vo.member.LikeVO;
import kr.or.ddit.clap.vo.support.MessageVO;

public class MessageDaoImpl implements IMessageDao{
	
	private SqlMapClient smc;
	private static MessageDaoImpl dao; // Singleton 패턴
	
	private MessageDaoImpl() {
		smc = DBUtil.getConnection();
	}
	
	public static MessageDaoImpl getInstance() { // Singleton 패턴
		if (dao == null) {
			dao = new MessageDaoImpl();
		}
		return dao;
	}

	@Override
	public List<MessageVO> selectMessage(MessageVO vo) {
		List<MessageVO> list = new ArrayList<MessageVO>();
		try {
			
			list = smc.queryForList("message.selectMessage",vo);
		
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
		return list;
	}

	@Override
	public String selectMessFCnt(MessageVO vo) {
		String	list="";
		try {
			
			list = (String) smc.queryForObject("message.selectMessFCnt",vo);
		
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return list;
	}
}