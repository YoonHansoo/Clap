package kr.or.ddit.clap.dao.singer;

import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

import kr.or.ddit.clap.vo.singer.SingerVO;

public class SingerDao {
	private SqlMapClient smc;
	
	public SingerDao() {
		Reader rd;
		try {
			rd = Resources.getResourceAsReader("SqlMapConfig.xml");
			smc = SqlMapClientBuilder.buildSqlMapClient(rd);
			rd.close();
		} catch(IOException e) {
			System.out.println("SqlMapClient객체 생성 실패!!");
			e.printStackTrace();
		}
	}
	
	public List<SingerVO> boardList() {
		List<SingerVO> list = new ArrayList<SingerVO>();
		try {
			
			list = smc.queryForList("board.boardList");
		
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
		return list;
	}
	

	
}
