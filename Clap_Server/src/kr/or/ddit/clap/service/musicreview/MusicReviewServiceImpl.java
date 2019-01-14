package kr.or.ddit.clap.service.musicreview;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import kr.or.ddit.clap.dao.musicreview.MusicReviewDaoImpl;
import kr.or.ddit.clap.vo.music.MusicReviewVO;

public class MusicReviewServiceImpl  extends UnicastRemoteObject implements IMusicReviewService  {
	MusicReviewDaoImpl musicreviewDao; //사용할 Dao의  멤버변수를 선언
	private static MusicReviewServiceImpl service;//Singleton패턴 
	
	private MusicReviewServiceImpl() throws RemoteException {
		super();
		musicreviewDao =  MusicReviewDaoImpl.getInstance();//Singleton패턴
	}
	
	public static MusicReviewServiceImpl getInstance() throws RemoteException {
		if(service== null) {
			service = new MusicReviewServiceImpl();
		}
		return service;
	}

	@Override
	public List<MusicReviewVO> selectReview(MusicReviewVO vo) throws RemoteException{
		return musicreviewDao.selectReview(vo);
	}

	@Override
	public List<MusicReviewVO> selectMusReview(MusicReviewVO vo) throws RemoteException {
		return musicreviewDao.selectMusReview(vo);
	}

	@Override
	public int deleteMusReview(MusicReviewVO vo) throws RemoteException {
		return musicreviewDao.deleteMusReview(vo);
	}


}
