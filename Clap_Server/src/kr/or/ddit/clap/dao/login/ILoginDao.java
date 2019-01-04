package kr.or.ddit.clap.dao.login;

import java.util.List;

import kr.or.ddit.clap.vo.member.MemberVO;

public interface ILoginDao {
	public Boolean idCheck(String id);
	
	public List<MemberVO> select(String id); 
}
