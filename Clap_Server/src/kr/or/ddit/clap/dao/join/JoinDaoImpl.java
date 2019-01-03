package kr.or.ddit.clap.dao.join;

import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

import kr.or.ddit.clap.vo.member.MemberVO;

public class JoinDaoImpl implements IJoinDao{
	
	private SqlMapClient smc;
	private static JoinDaoImpl dao; // Singleton 패턴
	
	private JoinDaoImpl() {
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
