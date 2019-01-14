package kr.or.ddit.clap.dao.album;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ibatis.sqlmap.client.SqlMapClient;

import kr.or.ddit.clap.main.DBUtil;
import kr.or.ddit.clap.vo.album.AlbumReviewVO;

public class AlbumReviewDaoImpl implements IAlbumReviewDao {

	private SqlMapClient smc;
	private static AlbumReviewDaoImpl dao; // Singleton 패턴

	private AlbumReviewDaoImpl() {
		smc = DBUtil.getConnection();
	}

	public static AlbumReviewDaoImpl getInstance() { // Singleton 패턴
		if (dao == null) {
			dao = new AlbumReviewDaoImpl();
		}
		return dao;
	}

	@Override
	public List<AlbumReviewVO> selectAlbReview(AlbumReviewVO vo) {
		List<AlbumReviewVO> list = new ArrayList<AlbumReviewVO>();
		try {
			
			list = smc.queryForList("musicreview.selectAlbReview",vo);
		
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
		return list;
	}

	@Override
	public int deleteAlbReview(AlbumReviewVO vo) {
		int cnt = 0;
		try {
			cnt = smc.delete("musicreview.deleteAlbReview",vo);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cnt;
	}
}
