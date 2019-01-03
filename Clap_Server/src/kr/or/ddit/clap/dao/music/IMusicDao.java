package kr.or.ddit.clap.dao.music;

import java.util.List;

import kr.or.ddit.clap.vo.music.MusicReviewVO;

public interface IMusicDao {
	//리뷰
			public List<MusicReviewVO> selectReview(MusicReviewVO vo);
}
