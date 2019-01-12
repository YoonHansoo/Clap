package kr.or.ddit.clap.service.recommend;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import kr.or.ddit.clap.vo.recommend.RecommendAlbumVO;

public interface IRecommendService extends Remote {

	
	//앨범 전체리스트 조회
	public List<RecommendAlbumVO> selectAllRecommendAlbum() throws RemoteException;
	
	//좋아요 수
	public int selectAlbumLikeCnt(String RcmAlbNo) throws RemoteException;
	
	//리스트 수
	public int selectAlbumListCnt(String RcmAlbNo) throws RemoteException; 
}
