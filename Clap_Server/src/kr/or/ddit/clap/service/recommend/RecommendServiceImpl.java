package kr.or.ddit.clap.service.recommend;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Map;

import kr.or.ddit.clap.dao.recommend.RecommendDaoImpl;
import kr.or.ddit.clap.vo.music.MusicVO;
import kr.or.ddit.clap.vo.recommend.RecommendAlbumListVO;
import kr.or.ddit.clap.vo.recommend.RecommendAlbumVO;

public class RecommendServiceImpl extends UnicastRemoteObject implements IRecommendService {

	RecommendDaoImpl recommendDao; //사용할 Dao의  멤버변수를 선언
	private static RecommendServiceImpl service;//Singleton패턴 
	
	private RecommendServiceImpl() throws RemoteException {
		super();
		recommendDao =  RecommendDaoImpl.getInstance();//Singleton패턴
	}
	
	
	
	public static RecommendServiceImpl getInstance() throws RemoteException {
		if(service== null) {
			service = new RecommendServiceImpl();
		}
		return service;
	}



	@Override
	public List<RecommendAlbumVO> selectAllRecommendAlbum() {
		return recommendDao.selectAllRecommendAlbum();
	}



	@Override
	public int selectAlbumLikeCnt(String RcmAlbNo) throws RemoteException {
		// TODO Auto-generated method stub
		return recommendDao.selectAlbumLikeCnt(RcmAlbNo);
		}



	@Override
	public int selectAlbumListCnt(String RcmAlbNo) throws RemoteException {
		return recommendDao.selectAlbumListCnt(RcmAlbNo);
	}



	@Override
	public int insertRecommendAlbum(RecommendAlbumVO rVO) throws RemoteException {
		return recommendDao.insertRecommendAlbum(rVO);
	}



	@Override
	public String selectSequence() throws RemoteException {
		// TODO Auto-generated method stub
		return recommendDao.selectSequence();
	}



	@Override
	public int insertRecommendAlbumMusic(Map<String,String> map) throws RemoteException {
		return recommendDao.insertRecommendAlbumMusic(map);
	}



	@Override
	public RecommendAlbumVO selectRecommendAlbumDetail(String rcmAlbNo) throws RemoteException {
		return recommendDao.selectRecommendAlbumDetail(rcmAlbNo);
	}



	@Override
	public List<MusicVO> SelectRcmMusicList(String rcmAlbNo) throws RemoteException {
		return recommendDao.SelectRcmMusicList(rcmAlbNo);
	}



	@Override
	public int updateRcmAlbum(RecommendAlbumVO vo) throws RemoteException {
		return recommendDao.updateRcmAlbum(vo);
	}



	@Override
	public int deleteRcmMusic(String rcmAlbNo) throws RemoteException {
		return recommendDao.deleteRcmMusic(rcmAlbNo);
	}



	@Override
	public int deleteRecommendAlbum(String rcmAlbNo) throws RemoteException {
		return recommendDao.deleteRecommendAlbum(rcmAlbNo);
	}



	@Override
	public List<RecommendAlbumVO> selectBestRecommendAlbum() throws RemoteException {
		return recommendDao.selectBestRecommendAlbum();
		
	}
	
}
