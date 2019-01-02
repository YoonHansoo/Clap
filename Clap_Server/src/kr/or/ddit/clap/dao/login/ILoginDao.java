package kr.or.ddit.clap.dao.login;

import kr.or.ddit.clap.vo.member.MemberVO;

public interface ILoginDao {
	public MemberVO select(String id);
}
