package kr.or.ddit.clap.dao.join;

import java.sql.SQLException;

import com.ibatis.sqlmap.client.SqlMapClient;

import kr.or.ddit.clap.main.DBUtil;
import kr.or.ddit.clap.vo.member.MemberVO;

public class JoinDaoImpl implements IJoinDao{
	
	private SqlMapClient smc;
	private static JoinDaoImpl dao; // Singleton 패턴
	
	private JoinDaoImpl() {
		smc = DBUtil.getConnection();
	}
	
	public static JoinDaoImpl getInstance() { // Singleton 패턴
		if (dao == null) {
			dao = new JoinDaoImpl();
		}
		return dao;
	}

	@Override
	public int insert(MemberVO vo) {
		int cnt = 0;
		try {
			 Object obj = smc.insert("join.insert", vo);
			
			 if(obj == null) {
				 cnt = 1;
			 }
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return cnt;
	}

}
