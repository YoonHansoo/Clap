package kr.or.ddit.clap.dao.recommend;

import java.util.List;

import kr.or.ddit.clap.vo.recommend.RecommendAlbumVO;

public interface IRecommendDao {

	//앨범 전체리스트 조회
	public List<RecommendAlbumVO> selectAllRecommendAlbum();

	//좋아요 수
	public int selectAlbumLikeCnt(String RcmAlbNo);
	
	//리스트 수
	public int selectAlbumListCnt(String RcmAlbNo);
}
