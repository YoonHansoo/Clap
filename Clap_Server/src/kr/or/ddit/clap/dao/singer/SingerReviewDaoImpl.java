package kr.or.ddit.clap.dao.singer;

import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

import kr.or.ddit.clap.vo.album.AlbumReviewVO;
import kr.or.ddit.clap.vo.singer.SingerReviewVO;

public class SingerReviewDaoImpl implements ISingerReviewDao {

	private SqlMapClient smc;
	private static SingerReviewDaoImpl dao; // Singleton 패턴

	private SingerReviewDaoImpl() {
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