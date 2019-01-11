package kr.or.ddit.clap.dao.musicreview;

import java.util.List;

import kr.or.ddit.clap.vo.music.MusicReviewVO;

public interface IMusicReviewDao {
	//리뷰
		public List<MusicReviewVO> selectReview(MusicReviewVO vo);
}
