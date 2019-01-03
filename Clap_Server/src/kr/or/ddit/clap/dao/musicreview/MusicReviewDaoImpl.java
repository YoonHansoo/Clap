package kr.or.ddit.clap.dao.musicreview;

import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

import kr.or.ddit.clap.vo.music.MusicReviewVO;
import kr.or.ddit.clap.vo.singer.SingerVO;

public class MusicReviewDaoImpl implements IMusicReviewDao{
		
	private SqlMapClient smc;
	private static MusicReviewDaoImpl dao; // Singleton 패턴

	private MusicReviewDaoImpl(){
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
			
			list = smc.queryForList("singer.selectReview",vo);
		
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
		return list;
	}




}
