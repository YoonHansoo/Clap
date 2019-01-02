/**
 * 실제 DB와 연결해서 SQL문을 수행하여 결과를 작성하여
 * service에 전달하는 DAO의 interface
 * @author Hansoo
 *
 */

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

public class SingerDaoImpl implements ISingerDao {

	private SqlMapClient smc;
	private static SingerDaoImpl dao; // Singleton 패턴

	private SingerDaoImpl() {
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

	public static SingerDaoImpl getInstance() { // Singleton 패턴
		if (dao == null) {
			dao = new SingerDaoImpl();
		}
		return dao;
	}

	public List<SingerVO> selectListAll() {
		List<SingerVO> list = new ArrayList<SingerVO>();
		try {

			list = smc.queryForList("singer.selectListAll");

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}

	@Override
	public List<SingerVO> searchList(SingerVO vo) {
		List<SingerVO> list = new ArrayList<SingerVO>();
		try {
			
			list = smc.queryForList("singer.searchList",vo);
		
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
		return list;
	}

}
