package kr.or.ddit.clap.dao.mypage;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import kr.or.ddit.clap.vo.member.MemberVO;
import kr.or.ddit.clap.vo.music.MusicReviewVO;
import kr.or.ddit.clap.vo.singer.SingerVO;

/**
 * 
 * 
 * @author hyuns현지
 *
 */
public interface IMypageDao {

	public MemberVO select(MemberVO vo);
	
	public List<MemberVO> selectAll();
	
	public List<MemberVO> searchMemList(MemberVO vo);

	public int  updateTel(MemberVO vo);
	
	public int  updateEmail(MemberVO vo);
	
	public int  updatePw(MemberVO vo);
	
	public int  updateDelTF(MemberVO vo);
	
	public int  updateInfo(MemberVO vo);
	
	public int  updateImage(MemberVO vo);
	
	public int  updateAll(MemberVO vo);

	
	
}
