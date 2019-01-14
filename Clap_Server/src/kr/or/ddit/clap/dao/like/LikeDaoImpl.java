package kr.or.ddit.clap.dao.like;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ibatis.sqlmap.client.SqlMapClient;

import kr.or.ddit.clap.main.DBUtil;
import kr.or.ddit.clap.vo.member.LikeVO;

public class LikeDaoImpl implements ILikeDao{
	
	private SqlMapClient smc;
	private static LikeDaoImpl dao; // Singleton 패턴
	
	private LikeDaoImpl() {
		smc = DBUtil.getConnection();
	}
	
	public static LikeDaoImpl getInstance() { // Singleton 패턴
		if (dao == null) {
			dao = new LikeDaoImpl();
		}
		return dao;
	}

	
	@Override
	public List<LikeVO> selectMusLike(LikeVO vo) {
		List<LikeVO> list = new ArrayList<LikeVO>();
		try {
			
			list = smc.queryForList("like.selectMusLike",vo);
		
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
		return list;
	}

	@Override
	public int deleteMusLike(LikeVO vo) {
		int cnt = 0;
		try {
			cnt = smc.delete("like.deleteMusLike",vo);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cnt;
	}

	@Override
	public List<LikeVO> selectAlbLike(LikeVO vo) {
		List<LikeVO> list = new ArrayList<LikeVO>();
		try {
			
			list = smc.queryForList("like.selectAlbLike",vo);
		
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
		return list;
	}

	@Override
	public int deleteAlbLike(LikeVO vo) {
		int cnt = 0;
		try {
			cnt = smc.delete("like.deleteAlbLike",vo);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cnt;
	}

	@Override
	public List<LikeVO> selectRcmLike(LikeVO vo) {
		List<LikeVO> list = new ArrayList<LikeVO>();
		try {
			
			list = smc.queryForList("like.selectRcmLike",vo);
		
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
		return list;
	}

	@Override
	public int deleteRcmLike(LikeVO vo) {
		int cnt = 0;
		try {
			cnt = smc.delete("like.deleteRcmLike",vo);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cnt;
	}

	@Override
	public List<LikeVO> selectSingLike(LikeVO vo) {
		List<LikeVO> list = new ArrayList<LikeVO>();
		try {
			
			list = smc.queryForList("like.selectSingLike",vo);
		
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
		return list;
	}

	@Override
	public int deleteSingLike(LikeVO vo) {
		int cnt = 0;
		try {
			cnt = smc.delete("like.deleteSingLike",vo);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cnt;
	}

	
}