package kr.or.ddit.clap.dao.mypage;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ibatis.sqlmap.client.SqlMapClient;

import kr.or.ddit.clap.main.DBUtil;
import kr.or.ddit.clap.vo.member.MemberVO;

public class MypageDaoImpl implements IMypageDao{

	private SqlMapClient smc;
	private static MypageDaoImpl dao; // Singleton 패턴

	private MypageDaoImpl(){
		smc = DBUtil.getConnection();
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

	@Override
	public int updateImage(MemberVO vo) {
		int cnt = 0;
		try {
	
			cnt = smc.update("mypage.updateImage", vo);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cnt;
	}

	@Override
	public List<MemberVO> selectAll() {
		List<MemberVO> list = new ArrayList<MemberVO>();
		try {
			
			list = smc.queryForList("mypage.selectAll");
		
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
		return list;
	}

	@Override
	public List<MemberVO> searchMemList(MemberVO vo) {
		List<MemberVO> list = new ArrayList<MemberVO>();
		try {
			
			list = smc.queryForList("mypage.searchMemList",vo);
		
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
		return list;
	}

	@Override
	public int updateAll(MemberVO vo) {
		int cnt = 0;
		try {
	
			cnt = smc.update("mypage.updateAll", vo);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cnt;
	}

	
	
	}



