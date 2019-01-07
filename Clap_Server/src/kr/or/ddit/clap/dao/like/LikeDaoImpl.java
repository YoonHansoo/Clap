package kr.or.ddit.clap.dao.like;

import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

import kr.or.ddit.clap.vo.member.LikeVO;
import kr.or.ddit.clap.vo.member.MemberVO;
import kr.or.ddit.clap.vo.music.MusicLikeVO;
import kr.or.ddit.clap.vo.singer.SingerVO;

public class LikeDaoImpl implements ILikeDao{
	
	private SqlMapClient smc;
	private static LikeDaoImpl dao; // Singleton 패턴
	
	private LikeDaoImpl() {
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

	
}