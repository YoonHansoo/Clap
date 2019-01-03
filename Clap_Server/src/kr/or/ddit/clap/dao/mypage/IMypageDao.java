package kr.or.ddit.clap.dao.mypage;

import java.util.List;

import kr.or.ddit.clap.vo.member.MemberVO;

/**
 * 
 * 
 * @author hyuns현지
 *
 */

public interface IMypageDao {

	public MemberVO select(MemberVO vo);
	
	public int  updateTel(MemberVO vo);
	
	public int  updateEmail(MemberVO vo);
	
	public int  updatePw(MemberVO vo);
	
	public int  updateDelTF(MemberVO vo);
	
	
}
