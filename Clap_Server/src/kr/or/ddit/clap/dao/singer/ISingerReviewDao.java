package kr.or.ddit.clap.dao.singer;

import java.util.List;

import kr.or.ddit.clap.vo.singer.SingerReviewVO;

public interface ISingerReviewDao {
	public List<SingerReviewVO> selectItsReview(SingerReviewVO vo);
	public int deleteItsReview(SingerReviewVO vo);
}
