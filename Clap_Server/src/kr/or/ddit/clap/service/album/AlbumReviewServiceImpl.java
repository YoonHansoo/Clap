package kr.or.ddit.clap.service.album;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import kr.or.ddit.clap.dao.album.AlbumReviewDaoImpl;
import kr.or.ddit.clap.vo.album.AlbumReviewVO;

public class AlbumReviewServiceImpl extends UnicastRemoteObject implements IAlbumReviewService  {
	
	AlbumReviewDaoImpl albumreDao; //사용할 Dao의  멤버변수를 선언
	private static AlbumReviewServiceImpl service;//Singleton패턴 
	
	private AlbumReviewServiceImpl() throws RemoteException {
		super();
		albumreDao =  AlbumReviewDaoImpl.getInstance();//Singleton패턴
	}
	
	public static AlbumReviewServiceImpl getInstance() throws RemoteException {
		if(service== null) {
			service = new AlbumReviewServiceImpl();
		}
		return service;
	}

	@Override
	public List<AlbumReviewVO> selectAlbReview(AlbumReviewVO vo) throws RemoteException {
		return albumreDao.selectAlbReview(vo);
	}

	@Override
	public int deleteAlbReview(AlbumReviewVO vo) throws RemoteException {
		return albumreDao.deleteAlbReview(vo);
	}
}
