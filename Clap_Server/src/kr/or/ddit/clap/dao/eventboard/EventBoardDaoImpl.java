/**
 * 실제 DB와 연결해서 SQL문을 수행하여 결과를 작성하여
 * service에 전달하는 DAO의 interface
 * @author Hanhwa
 *
 */
package kr.or.ddit.clap.dao.eventboard;

import java.io.IOException;
import java.io.Reader;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

import kr.or.ddit.clap.main.DBUtil;
import kr.or.ddit.clap.vo.support.EventBoardVO;

public class EventBoardDaoImpl implements IEventBoardDao {

	private SqlMapClient smc;
	private static EventBoardDaoImpl dao; // Singleton 패턴

	private EventBoardDaoImpl() {
		smc = DBUtil.getConnection();
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

	@Override
	public int insertEvent(EventBoardVO vo) {
		
		int cnt = 0;
		try {
			Object obj = smc.insert("eventboard.insertEvent", vo);
			if(obj == null) { //쿼리수행이 성공적으로 끝남
				cnt = 1;
			}
			
		} catch(SQLException e) {
			System.out.println("Dao insert 실패");
			e.printStackTrace();
			
		}
		return cnt;
	}

	@Override
	public EventBoardVO eventDetailAll(String ContentNo) throws RemoteException {
		
		EventBoardVO eVO = new EventBoardVO();
		//List<EventBoardVO> list = new ArrayList<EventBoardVO>();
		try {
			eVO = (EventBoardVO) smc.queryForObject("eventboard.eventDetailAll", ContentNo);
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return eVO;
	}

	@Override
	public int updateEvent(EventBoardVO vo) {
		
		int cnt = 0;
		try {
			cnt = smc.update("eventboard.updateEvent", vo);
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return cnt;
	}

}