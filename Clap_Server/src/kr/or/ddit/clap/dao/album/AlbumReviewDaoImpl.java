package kr.or.ddit.clap.dao.album;

import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

import kr.or.ddit.clap.vo.album.AlbumReviewVO;
import kr.or.ddit.clap.vo.music.MusicReviewVO;

public class AlbumReviewDaoImpl implements IAlbumReviewDao {

	private SqlMapClient smc;
	private static AlbumReviewDaoImpl dao; // Singleton 패턴

	private AlbumReviewDaoImpl() {
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
