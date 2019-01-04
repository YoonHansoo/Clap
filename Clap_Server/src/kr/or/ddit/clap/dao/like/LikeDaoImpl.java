package kr.or.ddit.clap.dao.like;

import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

import kr.or.ddit.clap.vo.member.MemberVO;
import kr.or.ddit.clap.vo.music.MusicLikeVO;

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

	public MusicLikeVO selectLike(MusicLikeVO vo) {
		MusicLikeVO check = null;
		try {
			check = (MusicLikeVO) smc.queryForObject("like.selectLike" ,vo);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return check;
	}
	
}