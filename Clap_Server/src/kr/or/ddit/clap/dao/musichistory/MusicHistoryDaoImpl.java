/**
 * 실제 DB와 연결해서 SQL문을 수행하여 결과를 작성하여
 * service에 전달하는 DAO의 interface
 * @author Hansoo
 *
 */

package kr.or.ddit.clap.dao.musichistory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ibatis.sqlmap.client.SqlMapClient;

import kr.or.ddit.clap.main.DBUtil;
import kr.or.ddit.clap.vo.music.MusicHistoryVO;

/**
 * 
 * @author 진민규
 *
 */
public class MusicHistoryDaoImpl implements IMusicHistoryDao{

	private SqlMapClient smc;
	private static MusicHistoryDaoImpl dao; // Singleton 패턴

	private MusicHistoryDaoImpl() {
		smc = DBUtil.getConnection();
	}

	public static MusicHistoryDaoImpl getInstance() { // Singleton 패턴
		if (dao == null) {
			dao = new MusicHistoryDaoImpl();
		}
		return dao;
	}

	public List<Map> toDaySelect() {
		List<Map> list = new ArrayList<Map>();
		try {

			list = smc.queryForList("musichistory.todayselect");

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}
	
	public List<Map> periodSelect(Map<String, String> day) {
		List<Map> list = new ArrayList<Map>();
		try {

			list = smc.queryForList("musichistory.periodselect",day);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}
	
	public List<MusicHistoryVO> selectMayIts(MusicHistoryVO vo) {
		List<MusicHistoryVO> list = new ArrayList<MusicHistoryVO>();
		try {
			
			list = smc.queryForList("musichistory.selectMayIts",vo);
		
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
		return list;
	}

	@Override
	public List<MusicHistoryVO> selectMayIndate(MusicHistoryVO vo) {
		List<MusicHistoryVO> list = new ArrayList<MusicHistoryVO>();
		try {
			
			list = smc.queryForList("musichistory.selectMayIndate",vo);
		
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
		return list;
	}

	@Override
	public List<Map> genreSelect(String genre) {
		List<Map> list = new ArrayList<Map>();
		try {

			list = smc.queryForList("musichistory.genreselect",genre);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}
	
	@Override
	public List<Map> interiorSelect(String year) {
		List<Map> list = new ArrayList<Map>();
		try {

			list = smc.queryForList("musichistory.interiorselect", year);
 
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}

	@Override
	public List<Map> foreignSelect(String year) {
		List<Map> list = new ArrayList<Map>();
		try {

			list = smc.queryForList("musichistory.foreignselect", year);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}
	
	public static void main(String[] args) {
		MusicHistoryVO vo = new MusicHistoryVO();
		vo.setMem_id("user1");
		vo.setMus_no("10");
		
		
		new MusicHistoryDaoImpl().historyInsert(vo);
		
	}

	@Override
	public List<MusicHistoryVO> selectNewLisMus(String id) {
		List<MusicHistoryVO> list = new ArrayList<MusicHistoryVO>();
		try {
			
			list = smc.queryForList("musichistory.selectNewLisMus",id);
		
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
		return list;
	}

	@Override
	public List<MusicHistoryVO> selectManyLisMus(String id) {
		List<MusicHistoryVO> list = new ArrayList<MusicHistoryVO>();
		try {
			
			list = smc.queryForList("musichistory.selectManyLisMus",id);
		
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
		return list;
	}

	@Override
	public List<MusicHistoryVO> selectManyIts(String id) {
		List<MusicHistoryVO> list = new ArrayList<MusicHistoryVO>();
		try {
			
			list = smc.queryForList("musichistory.selectManyIts",id);
		
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
		return list;
	}

	@Override
	public int historyInsert(MusicHistoryVO vo) {
		int cnt = 0;
		try {
	
			cnt = smc.update("musichistory.historyinsert", vo);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cnt;
	}

	@Override
	public List<Map> top10Select() {
		List<Map> list = new ArrayList<Map>();
		try {

			list = smc.queryForList("musichistory.top10select");

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}

}
