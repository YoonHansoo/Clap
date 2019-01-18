package kr.or.ddit.clap.dao.login;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

import kr.or.ddit.clap.vo.member.MemberVO;
import kr.or.ddit.clap.vo.search.BestSearchWordVO;

public interface ILoginDao {
	public Boolean idCheck(String id);
	
	public List<MemberVO> select(String id); 
	
	public List<MemberVO> idSearch(MemberVO vo);
	
	public Boolean emailCheck(MemberVO vo);
	
	public List<BestSearchWordVO> selecthotkeyword();
	
	public List<String> gameMember(String mem_id);
	
	public int gameUpdate(Map map);
}
