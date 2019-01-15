/**
 * 실제 DB와 연결해서 SQL문을 수행하여 결과를 작성하여
 * service에 전달하는 DAO의 interface
 * @author Hansoo
 *
 */

package kr.or.ddit.clap.dao.singer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ibatis.sqlmap.client.SqlMapClient;

import kr.or.ddit.clap.main.DBUtil;
import kr.or.ddit.clap.vo.singer.SingerVO;

public class SingerDaoImpl implements ISingerDao {

	private SqlMapClient smc;
	private static SingerDaoImpl dao; // Singleton 패턴

	private SingerDaoImpl() {
		smc = DBUtil.getConnection();
	}

	public static SingerDaoImpl getInstance() { // Singleton 패턴
		if (dao == null) {
			dao = new SingerDaoImpl();
		}
		return dao;
	}


	//가수목록 조회를 위한 쿼리문
	public List<SingerVO> selectListAll() {
		List<SingerVO> list = new ArrayList<SingerVO>();
		try {

			list = smc.queryForList("singer.selectListAll");

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}

	//검색조건에 맞게 검색하는 쿼리문
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

	//가수 상세보기 조회를  위한 쿼리문
	@Override
	public SingerVO singerDetailInfo(String singerNo) {
		SingerVO sVO = new SingerVO();
		try {
			
			sVO = (SingerVO) smc.queryForObject("singer.singerDetailInfo", singerNo);
		
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
		return sVO;
	}
//선택한 가수의 좋아요의 수를 조회하는 쿼리문
	@Override
	public int selectSingerLikeCnt(String singerNo) {
		int singerLikeCnt = 0;
		
		try {
			singerLikeCnt = (int) smc.queryForObject("singer.selectSingerLikeCnt",singerNo);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return singerLikeCnt;
	}

	//가수정보를 수정하는 쿼리
	@Override
	public int updateSingerInfo(SingerVO vo) {
		int cnt = 0;
		try {
		cnt = smc.update("singer.updateSingerInfo",vo);
		} catch (SQLException e) {

			e.printStackTrace();
		} 
		return cnt;
	}

	@Override
	public int insertSinger(SingerVO vo) {
		int cnt = 0;
		try {
		Object obj = smc.insert("singer.insertSinger", vo);
		if(obj == null) { //쿼리수행이 성공적으로 끝남
			cnt = 1;
		}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return cnt;
	
	}

	@Override
	public int deleteSinger(String singerNo) {
		int cnt = 0;
		try {
			cnt = smc.delete("singer.deleteSinger",singerNo);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return cnt;
	}

	@Override
	public int checkHeartYN(Map<String, String> map) {
		int checkHeartYN = 0;

		try {
			checkHeartYN = (int) smc.queryForObject("singer.checkHeartYN", map);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return checkHeartYN;
	}

	@Override
	public int deleteSingerLike(Map<String, String> map) {
		int cnt = 0;
		try {
			cnt = smc.delete("singer.deleteSingerLike",map);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return cnt;
	}

	@Override
	public int insertSingerLike(Map<String, String> map) {
		int cnt = 0;
		try {
			Object obj = smc.insert("singer.insertSingerLike", map);
			if (obj == null) { // 쿼리수행이 성공적으로 끝남
				cnt = 1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cnt;
	}

	@Override
	public List<Map<String,String>> selectReply(String singerNo){
		List<Map<String,String>> list = new ArrayList<>();
		
		 try {
			 list = smc.queryForList("singer.selectReply", singerNo);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	}

	@Override
	public int insertReply(Map<String, String> map) {
		int cnt = 0;
		try {
			Object obj = smc.insert("singer.insertReply", map);
			if (obj == null) { // 쿼리수행이 성공적으로 끝남
				cnt = 1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cnt;
	}
}
