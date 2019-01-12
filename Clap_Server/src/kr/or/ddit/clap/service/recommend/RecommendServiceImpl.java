package kr.or.ddit.clap.service.recommend;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import kr.or.ddit.clap.dao.recommend.RecommendDaoImpl;
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
	
}
