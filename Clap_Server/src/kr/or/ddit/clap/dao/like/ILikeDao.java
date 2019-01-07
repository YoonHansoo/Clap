package kr.or.ddit.clap.dao.like;

import java.util.List;

import kr.or.ddit.clap.vo.member.LikeVO;

public interface ILikeDao {
	public List<LikeVO> selectMusLike(LikeVO vo);
	public int deleteMusLike(LikeVO vo);
	
	public List<LikeVO> selectAlbLike(LikeVO vo);

}
