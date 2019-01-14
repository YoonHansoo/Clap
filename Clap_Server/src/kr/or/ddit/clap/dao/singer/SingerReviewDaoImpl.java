package kr.or.ddit.clap.dao.singer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ibatis.sqlmap.client.SqlMapClient;

import kr.or.ddit.clap.main.DBUtil;
import kr.or.ddit.clap.vo.singer.SingerReviewVO;

public class SingerReviewDaoImpl implements ISingerReviewDao {

	private SqlMapClient smc;
	private static SingerReviewDaoImpl dao; // Singleton 패턴

	private SingerReviewDaoImpl() {
		smc = DBUtil.getConnection();
	}

	public static SingerReviewDaoImpl getInstance() { // Singleton 패턴
		if (dao == null) {
			dao = new SingerReviewDaoImpl();
		}
		return dao;
	}

	@Override
	public List<SingerReviewVO> selectItsReview(SingerReviewVO vo) {
		List<SingerReviewVO> list = new ArrayList<SingerReviewVO>();
		try {
			
			list = smc.queryForList("musicreview.selectItsReview",vo);
		
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
		return list;
	}

	@Override
	public int deleteItsReview(SingerReviewVO vo) {
		int cnt = 0;
		try {
			cnt = smc.delete("musicreview.deleteItsReview",vo);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cnt;
	}
}