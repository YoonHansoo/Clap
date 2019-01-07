package kr.or.ddit.clap.service.like;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import kr.or.ddit.clap.dao.like.LikeDaoImpl;
import kr.or.ddit.clap.dao.musichistory.MusicHistoryDaoImpl;
import kr.or.ddit.clap.service.musichistory.IMusicHistoryService;
import kr.or.ddit.clap.service.musichistory.MusicHistoryServiceImpl;
import kr.or.ddit.clap.vo.member.LikeVO;
import kr.or.ddit.clap.vo.music.MusicLikeVO;

public class LikeServiceImpl extends UnicastRemoteObject implements ILikeService  {
	
	LikeDaoImpl likeDao; //사용할 Dao의  멤버변수를 선언
	private static LikeServiceImpl service;//Singleton패턴 
	
	private LikeServiceImpl() throws RemoteException {
		super();
		likeDao =  LikeDaoImpl.getInstance();//Singleton패턴
	}
	
	public static LikeServiceImpl getInstance() throws RemoteException {
		if(service== null) {
			service = new LikeServiceImpl();
		}
		return service;
	}



	@Override
	public List<LikeVO> selectMusLike(LikeVO vo) throws RemoteException {
	return likeDao.selectMusLike(vo);
	}

	@Override
	public int deleteMusLike(LikeVO vo) {
		return likeDao.deleteMusLike(vo);
	}

	@Override
	public List<LikeVO> selectAlbLike(LikeVO vo) throws RemoteException {
		return likeDao.selectAlbLike(vo);
	}
}