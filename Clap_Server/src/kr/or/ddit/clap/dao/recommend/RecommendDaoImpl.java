package kr.or.ddit.clap.dao.recommend;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ibatis.sqlmap.client.SqlMapClient;

import kr.or.ddit.clap.main.DBUtil;
import kr.or.ddit.clap.vo.music.MusicVO;
import kr.or.ddit.clap.vo.recommend.RecommendAlbumVO;

public class RecommendDaoImpl implements IRecommendDao {
	private SqlMapClient smc;
	private static RecommendDaoImpl dao; // Singleton 패턴

	private RecommendDaoImpl() {

		smc = DBUtil.getConnection();
		
	}

	public static RecommendDaoImpl getInstance() { // Singleton 패턴
		if (dao == null) {
			dao = new RecommendDaoImpl();
		}
		return dao;
	}

	// 추천앨범 전체조회
	@Override
	public List<RecommendAlbumVO> selectAllRecommendAlbum() {
		List<RecommendAlbumVO> list = new ArrayList<RecommendAlbumVO>();
		try {

			list = smc.queryForList("recommend.selectAllRecommendAlbum");

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;

	}

	@Override
	public int selectAlbumLikeCnt(String RcmAlbNo) {
		int selectAlbumLikeCnt = 0;

		try {
			selectAlbumLikeCnt = (int) smc.queryForObject("recommend.selectAlbumLikeCnt", RcmAlbNo);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return selectAlbumLikeCnt;
	}

	@Override
	public int selectAlbumListCnt(String RcmAlbNo) {
		int selectAlbumListCnt = 0;

		try {
			selectAlbumListCnt = (int) smc.queryForObject("recommend.selectAlbumListCnt", RcmAlbNo);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return selectAlbumListCnt;
	}

	@Override
	public int insertRecommendAlbum(RecommendAlbumVO rVO) {
		int cnt = 0;
		try {
			Object obj = smc.insert("recommend.insertRecommendAlbum", rVO);
			if (obj == null) { // 쿼리수행이 성공적으로 끝남
				cnt = 1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cnt;
	}

	@Override
	public String selectSequence() {
		String sequence = null;
		try {

			sequence = (String) smc.queryForObject("recommend.selectSequence");

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return sequence;

	}

	@Override
	public int insertRecommendAlbumMusic(Map<String, String> map) {
		int cnt = 0;
		try {
			Object obj = smc.insert("recommend.insertRecommendAlbumMusic",map);
			if (obj == null) { // 쿼리수행이 성공적으로 끝남
				cnt = 1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cnt;
	}

	@Override
	public RecommendAlbumVO selectRecommendAlbumDetail(String rcmAlbNo) {

		RecommendAlbumVO rVO = new RecommendAlbumVO();
		try {
			
			rVO = (RecommendAlbumVO) smc.queryForObject("recommend.selectRecommendAlbumDetail", rcmAlbNo);
		
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
		return rVO;
	}

	@Override
	public List<MusicVO> SelectRcmMusicList(String rcmAlbNo) {
		
		List<MusicVO> list = new ArrayList<MusicVO>();
		
		try {
			list = smc.queryForList("recommend.SelectRcmMusicList", rcmAlbNo);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public int updateRcmAlbum(RecommendAlbumVO vo) {
		int cnt = 0;
		try {
		cnt = smc.update("recommend.updateRcmAlbum",vo);
		} catch (SQLException e) {

			e.printStackTrace();
		} 
		return cnt;
	}

	@Override
	public int deleteRcmMusic(String rcmAlbNo) {
		int cnt = 0;
		try {
			cnt = smc.delete("recommend.deleteRcmMusic",rcmAlbNo);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return cnt;
	
	}

	@Override
	public int deleteRecommendAlbum(String rcmAlbNo) {
		int cnt = 0;
		try {
			cnt = smc.delete("recommend.deleteRecommendAlbum",rcmAlbNo);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return cnt;
	}

	@Override
	public List<RecommendAlbumVO> selectBestRecommendAlbum() {
		List<RecommendAlbumVO> list = new ArrayList<RecommendAlbumVO>();
		try {

			list = smc.queryForList("recommend.selectBestRecommendAlbum");

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}

	@Override
	public int checkHeartYN(Map<String, String> map) {
		int checkHeartYN = 0;

		try {
			checkHeartYN = (int) smc.queryForObject("recommend.checkHeartYN", map);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return checkHeartYN;
	}

	@Override
	public int deleteRcmLike(Map<String, String> map) {
		int cnt = 0;
		try {
			cnt = smc.delete("recommend.deleteRcmLike",map);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return cnt;
	}

	@Override
	public int insertRcmLike(Map<String, String> map) {
		int cnt = 0;
		try {
			Object obj = smc.insert("recommend.insertRcmLike", map);
			if (obj == null) { // 쿼리수행이 성공적으로 끝남
				cnt = 1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cnt;
	}

}
