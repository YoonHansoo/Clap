package kr.or.ddit.clap.dao.recommend;

import java.util.List;
import java.util.Map;

import kr.or.ddit.clap.vo.recommend.RecommendAlbumVO;

public interface IRecommendDao {

	// 앨범 전체리스트 조회
	public List<RecommendAlbumVO> selectAllRecommendAlbum();

	// 좋아요 수
	public int selectAlbumLikeCnt(String RcmAlbNo);

	// 리스트 수
	public int selectAlbumListCnt(String RcmAlbNo);

	// 추천앨범 생성쿼리
	public int insertRecommendAlbum(RecommendAlbumVO rVO);

	// 현재 시퀀스를 조회하는 쿼리 추가
	public String selectSequence();

	// 추천앨범곡을 추가
	public int insertRecommendAlbumMusic(Map<String, String> map);

}
