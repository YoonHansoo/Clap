package kr.or.ddit.clap.dao.recommend;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

import kr.or.ddit.clap.vo.music.MusicVO;
import kr.or.ddit.clap.vo.recommend.RecommendAlbumVO;

public interface IRecommendDao {

	// 앨범 전체리스트 조회
	public List<RecommendAlbumVO> selectAllRecommendAlbum();

	//앨범 인기순 리스트 조회
		public List<RecommendAlbumVO> selectBestRecommendAlbum();
	
	// 좋아요 수
	public int selectAlbumLikeCnt(String RcmAlbNo);

	// 리스트 수
	public int selectAlbumListCnt(String RcmAlbNo);
	
	//추천앨범 상세조회
	public RecommendAlbumVO selectRecommendAlbumDetail(String rcmAlbNo);

	//추천앨범에 들어있는 곡 조회 
	public List<MusicVO> SelectRcmMusicList(String rcmAlbNo);
	
	// 추천앨범 생성쿼리
	public int insertRecommendAlbum(RecommendAlbumVO rVO);

	// 현재 시퀀스를 조회하는 쿼리 추가
	public String selectSequence();

	// 추천앨범곡을 추가
	public int insertRecommendAlbumMusic(Map<String, String> map);
	
	//추천앨범 수정
	
	public int updateRcmAlbum(RecommendAlbumVO vo);
	
	//추천앨범에 속해있는 곡 전체삭제
	public int deleteRcmMusic(String rcmAlbNo);
	
	//추천앨범 삭제
		public int deleteRecommendAlbum(String rcmAlbNo);

}
