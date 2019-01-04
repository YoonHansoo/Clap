package kr.or.ddit.clap.dao.like;

import java.io.IOException;
import java.io.Reader;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

public class LikeDaoImpl implements ILikeDao{
	
	private SqlMapClient smc;
	private static LikeDaoImpl dao; // Singleton 패턴
	
	private LikeDaoImpl() {
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
	
	public static LikeDaoImpl getInstance() { // Singleton 패턴
		if (dao == null) {
			dao = new LikeDaoImpl();
		}
		return dao;
	}
	
}