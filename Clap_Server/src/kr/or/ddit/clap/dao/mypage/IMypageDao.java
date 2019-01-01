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

	public List<MemberVO> selectListAll();
	
}
