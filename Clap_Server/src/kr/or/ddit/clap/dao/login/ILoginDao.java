package kr.or.ddit.clap.dao.login;

import java.util.List;
import java.util.Map;

import kr.or.ddit.clap.vo.member.MemberVO;

public interface ILoginDao {
	public Boolean idCheck(String id);
	
	public List<MemberVO> select(String id); 
	
	public List<MemberVO> idSearch(MemberVO vo);
	
	public Boolean emailCheck(MemberVO vo);
	
	public List<String> selecthotkeyword();
	
	public List<String> gameMember(String mem_id);
	
	public int gameUpdate(Map map);
}
