package kr.or.ddit.clap.service.singer;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import kr.or.ddit.clap.dao.singer.SingerReviewDaoImpl;
import kr.or.ddit.clap.vo.singer.SingerReviewVO;

public class SingerReviewServiceImpl extends UnicastRemoteObject implements ISingerReviewService  {
	
	SingerReviewDaoImpl singerreDao; //사용할 Dao의  멤버변수를 선언
	private static SingerReviewServiceImpl service;//Singleton패턴 
	
	private SingerReviewServiceImpl() throws RemoteException {
		super();
		singerreDao =  SingerReviewDaoImpl.getInstance();//Singleton패턴
	}
	
	public static SingerReviewServiceImpl getInstance() throws RemoteException {
		if(service== null) {
			service = new SingerReviewServiceImpl();
		}
		return service;
	}

	@Override
	public List<SingerReviewVO> selectItsReview(SingerReviewVO vo) throws RemoteException {
		return singerreDao.selectItsReview(vo);
	}

	@Override
	public int deleteItsReview(SingerReviewVO vo) throws RemoteException {
		return singerreDao.deleteItsReview(vo);
	}
	
}
