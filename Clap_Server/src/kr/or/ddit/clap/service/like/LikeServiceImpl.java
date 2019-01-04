package kr.or.ddit.clap.service.like;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import kr.or.ddit.clap.dao.like.LikeDaoImpl;
import kr.or.ddit.clap.dao.musichistory.MusicHistoryDaoImpl;
import kr.or.ddit.clap.service.musichistory.IMusicHistoryService;
import kr.or.ddit.clap.service.musichistory.MusicHistoryServiceImpl;

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
}