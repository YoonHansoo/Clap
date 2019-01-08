package kr.or.ddit.clap.dao.album;

import java.util.List;

import kr.or.ddit.clap.vo.album.AlbumReviewVO;

public interface IAlbumReviewDao {

	public List<AlbumReviewVO> selectAlbReview(AlbumReviewVO vo);
	public int deleteAlbReview(AlbumReviewVO vo);
}
