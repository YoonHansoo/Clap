package kr.or.ddit.clap.service.myalbum;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import kr.or.ddit.clap.dao.myalbum.MyAlbumyDaoImpl;
import kr.or.ddit.clap.vo.myalbum.MyAlbumVO;

/**
 * 
 * @author 진민규
 *
 */

public class MyAlbumServiceImpl  extends UnicastRemoteObject implements IMyAlbumService  {
	MyAlbumyDaoImpl myalbumydao; //사용할 Dao의  멤버변수를 선언
	private static MyAlbumServiceImpl service;//Singleton패턴 
	
	private MyAlbumServiceImpl() throws RemoteException {
		super();
		myalbumydao =  MyAlbumyDaoImpl.getInstance();//Singleton패턴
	}
	
	public static MyAlbumServiceImpl getInstance() throws RemoteException {
		if(service== null) {
			service = new MyAlbumServiceImpl();
		}
		return service;
	}

	@Override
	public List<MyAlbumVO> myAlbumSelect(String id) {
		return myalbumydao.myAlbumSelect(id);
	}


}
