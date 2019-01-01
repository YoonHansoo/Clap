package kr.or.ddit.clap.dao.mypage;

import java.util.List;

import kr.or.ddit.clap.vo.member.MemberVO;

public interface IMypageDao {

	public List<MemberVO> selectListAll();
	
}
