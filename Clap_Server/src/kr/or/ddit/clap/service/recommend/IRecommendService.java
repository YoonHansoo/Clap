package kr.or.ddit.clap.service.recommend;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

import kr.or.ddit.clap.vo.album.AlbumVO;
import kr.or.ddit.clap.vo.recommend.RecommendAlbumVO;

public interface IRecommendService extends Remote {

	
	//앨범 전체리스트 조회
	public List<RecommendAlbumVO> selectAllRecommendAlbum() throws RemoteException;
	
	//좋아요 수
	public int selectAlbumLikeCnt(String RcmAlbNo) throws RemoteException;
	
	//리스트 수
	public int selectAlbumListCnt(String RcmAlbNo) throws RemoteException; 
	
	
	//추천앨범 생성쿼리 
	public int insertRecommendAlbum(RecommendAlbumVO rVO) throws RemoteException;
	
	
	
	//현재 시퀀스를 조회하는 쿼리 추가
	public String selectSequence() throws RemoteException;
			
	//추천앨범곡을 추가
	public int insertRecommendAlbumMusic(Map<String, String> map) throws RemoteException;
	
}
