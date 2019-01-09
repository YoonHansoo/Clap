/**
 * 실제 DB와 연결해서 SQL문을 수행하여 결과를 작성하여
 * service에 전달하는 DAO의 interface
 * @author Hansoo
 *
 */

package kr.or.ddit.clap.dao.musichistory;

import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;
import kr.or.ddit.clap.vo.music.MusicHistoryVO;
import kr.or.ddit.clap.vo.singer.SingerVO;

/**
 * 
 * @author 진민규
 *
 */
public class MusicHistoryDaoImpl implements IMusicHistoryDao{

	private SqlMapClient smc;
	private static MusicHistoryDaoImpl dao; // Singleton 패턴

	private MusicHistoryDaoImpl() {
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
		Map day = new HashMap<String,String>();
		
		day.put("monday", "18/12/24");
		day.put("sunday", "18/12/30");
		List<Map> list = new MusicHistoryDaoImpl().interiorSelect("18");
		
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

}
