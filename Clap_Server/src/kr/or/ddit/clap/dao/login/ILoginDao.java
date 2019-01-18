package kr.or.ddit.clap.dao.login;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

import kr.or.ddit.clap.vo.member.MemberVO;
import kr.or.ddit.clap.vo.search.NewSearchWordVO;

public interface ILoginDao {
	public Boolean idCheck(String id);
	
	public List<MemberVO> select(String id); 
	
	public List<MemberVO> idSearch(MemberVO vo);
	
	public Boolean emailCheck(MemberVO vo);
	
	public List<NewSearchWordVO> selecthotkeyword();
	
	public List<NewSearchWordVO> selectHistorykeyword(String id);
	
	public List<String> gameMember(String mem_id);
	
	public int gameUpdate(Map map);
	
	public int insertSearchWord(Map<String,String> map);
}
