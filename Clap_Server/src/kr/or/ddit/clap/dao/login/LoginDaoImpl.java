package kr.or.ddit.clap.dao.login;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ibatis.sqlmap.client.SqlMapClient;

import kr.or.ddit.clap.main.DBUtil;
import kr.or.ddit.clap.vo.member.MemberVO;
import kr.or.ddit.clap.vo.search.BestSearchWordVO;

public class LoginDaoImpl implements ILoginDao{

	private SqlMapClient smc;
	private static LoginDaoImpl dao; // Singleton 패턴
	
	private LoginDaoImpl() {
		smc = DBUtil.getConnection();
	}
	
	public static LoginDaoImpl getInstance() { // Singleton 패턴
		if (dao == null) {
			dao = new LoginDaoImpl();
		}
		return dao;
	}
	
	@Override
	public Boolean idCheck(String id) {
		Boolean idCheck = false;
		try {
			int count = (int) smc.queryForObject("login.idCheck", id);
			
			if(count > 0) {
				idCheck = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return idCheck;
	}

	@Override
	public List<MemberVO> select(String id) {
		List<MemberVO> list = new ArrayList<MemberVO>();
		try {
			list = smc.queryForList("login.select", id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<MemberVO> idSearch(MemberVO vo) {
		List<MemberVO> list = new ArrayList<MemberVO>();
		try {
			list = smc.queryForList("login.idSearch", vo);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public Boolean emailCheck(MemberVO vo) {
		Boolean emailCheck = false;
		try {
			int count = (int) smc.queryForObject("login.emailCheck", vo);
			
			if(count > 0) {
				emailCheck = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return emailCheck;
	}

	@Override
	public List<BestSearchWordVO> selecthotkeyword() {
		List<BestSearchWordVO> list = new ArrayList<BestSearchWordVO>();
		try {
			list = smc.queryForList("login.selecthotkeyword");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
		
	}

	@Override
	public List<String> gameMember(String mem_id) {
		List<String> list = new ArrayList<String>();
		try {
			list = smc.queryForList("login.gamemember", mem_id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public int gameUpdate(Map map) {
		int cnt = 0;
		try {
			cnt = smc.update("login.gameupdate", map);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cnt;
	}



}
