package kr.or.ddit.clap.dao.mypage;

import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

import kr.or.ddit.clap.vo.member.MemberVO;
import kr.or.ddit.clap.vo.music.MusicReviewVO;
import kr.or.ddit.clap.vo.singer.SingerVO;

public class MypageDaoImpl implements IMypageDao{

	private SqlMapClient smc;
	private static MypageDaoImpl dao; // Singleton 패턴

	private MypageDaoImpl(){
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

	public static MypageDaoImpl getInstance() { // Singleton 패턴
		if (dao == null) {
			dao = new MypageDaoImpl();
		}
		return dao;
	}

	public MemberVO select(MemberVO vo) {
		MemberVO check = null;
		try {
			check = (MemberVO) smc.queryForObject("mypage.select" ,vo);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return check;
	}

	@Override
	public int  updateTel(MemberVO vo) {
			int cnt = 0;
			try {
		
				cnt = smc.update("mypage.updateTel", vo);
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return cnt;
	}

	@Override
	public int updateEmail(MemberVO vo) {
		int cnt = 0;
		try {
	
			cnt = smc.update("mypage.updateEmail", vo);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cnt;
	}

	@Override
	public int updatePw(MemberVO vo) {
		int cnt = 0;
		try {
	
			cnt = smc.update("mypage.updatePw", vo);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cnt;
	}

	@Override
	public int updateDelTF(MemberVO vo) {
		int cnt = 0;
		try {
	
			cnt = smc.update("mypage.updateDelTF", vo);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cnt;
	}

	@Override
	public int updateInfo(MemberVO vo) {
		int cnt = 0;
		try {
	
			cnt = smc.update("mypage.updateInfo", vo);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cnt;
	}

	
	}



