package kr.or.ddit.clap.dao.recommend;

import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

import kr.or.ddit.clap.vo.recommend.RecommendAlbumVO;
import kr.or.ddit.clap.vo.singer.SingerVO;

public class RecommendDaoImpl implements IRecommendDao {
	private SqlMapClient smc;
	private static RecommendDaoImpl dao; // Singleton 패턴

	private RecommendDaoImpl() {
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

	public static RecommendDaoImpl getInstance() { // Singleton 패턴
		if (dao == null) {
			dao = new RecommendDaoImpl();
		}
		return dao;
	}

	
	//추천앨범 전체조회
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
			selectAlbumLikeCnt = (int) smc.queryForObject("recommend.selectAlbumLikeCnt",RcmAlbNo);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return selectAlbumLikeCnt;
	}

	@Override
	public int selectAlbumListCnt(String RcmAlbNo) {
	int selectAlbumListCnt = 0;
		
		try {
			selectAlbumListCnt = (int) smc.queryForObject("recommend.selectAlbumListCnt",RcmAlbNo);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return selectAlbumListCnt;
	}

	
	
	
}
