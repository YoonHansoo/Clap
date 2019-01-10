/**
 * 실제 DB와 연결해서 SQL문을 수행하여 결과를 작성하여
 * service에 전달하는 DAO의 interface
 * @author Hanhwa
 *
 */
package kr.or.ddit.clap.dao.eventboard;

import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

import kr.or.ddit.clap.vo.support.EventBoardVO;

public class EventBoardDaoImpl implements IEventBoardDao {

	private SqlMapClient smc;
	private static EventBoardDaoImpl dao; // Singleton 패턴

	private EventBoardDaoImpl() {
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

	public static EventBoardDaoImpl getInstance() { // Singleton 패턴
		if (dao == null) {
			dao = new EventBoardDaoImpl();
		}
		return dao;

	}

	//공지사항 조회를 위한 쿼리문
	@Override
	public List<EventBoardVO> selectListAll() {
		List<EventBoardVO> list = new ArrayList<EventBoardVO>();
		try {
			list = smc.queryForList("eventboard.selectListAll");
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}

	//검색조건에 맞게 검색하는 쿼리문
	@Override
	public List<EventBoardVO> searchList(EventBoardVO vo) {
		List<EventBoardVO> list = new ArrayList<EventBoardVO>();
		
		try {
			list = smc.queryForList("eventboard.searchList", vo);
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}

}