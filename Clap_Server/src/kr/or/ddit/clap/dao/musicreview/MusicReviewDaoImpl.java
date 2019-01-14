package kr.or.ddit.clap.dao.musicreview;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ibatis.sqlmap.client.SqlMapClient;

import kr.or.ddit.clap.main.DBUtil;
import kr.or.ddit.clap.vo.music.MusicReviewVO;

public class MusicReviewDaoImpl implements IMusicReviewDao{
		
	private SqlMapClient smc;
	private static MusicReviewDaoImpl dao; // Singleton 패턴

	private MusicReviewDaoImpl(){
		smc = DBUtil.getConnection();
	}

	public static MusicReviewDaoImpl getInstance() { // Singleton 패턴
		if (dao == null) {
			dao = new MusicReviewDaoImpl();
		}
		return dao;
	}

	@Override
	public List<MusicReviewVO> selectReview(MusicReviewVO vo) {
		List<MusicReviewVO> list = new ArrayList<MusicReviewVO>();
		try {
			
			list = smc.queryForList("musicreview.selectReview",vo);
		
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
		return list;
	}

	@Override
	public List<MusicReviewVO> selectMusReview(MusicReviewVO vo) {
		List<MusicReviewVO> list = new ArrayList<MusicReviewVO>();
		try {
			
			list = smc.queryForList("musicreview.selectMusReview",vo);
		
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
		return list;
	}

	@Override
	public int deleteMusReview(MusicReviewVO vo) {
		int cnt = 0;
		try {
			cnt = smc.delete("musicreview.deleteMusReview",vo);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cnt;
	}
	}




