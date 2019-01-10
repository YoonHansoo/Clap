package kr.or.ddit.clap.service.myalbumlist;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Map;

import kr.or.ddit.clap.dao.myalbum.MyAlbumDaoImpl;
import kr.or.ddit.clap.dao.myalbumlist.MyAlbumListDaoImpl;
import kr.or.ddit.clap.vo.myalbum.MyAlbumListVO;
import kr.or.ddit.clap.vo.myalbum.MyAlbumVO;

/**
 * 
 * @author 진민규
 *
 */

public class MyAlbumListServiceImpl  extends UnicastRemoteObject implements IMyAlbumListService  {
	MyAlbumListDaoImpl myalbumlistdao; //사용할 Dao의  멤버변수를 선언
	private static MyAlbumListServiceImpl service;//Singleton패턴 
	
	private MyAlbumListServiceImpl() throws RemoteException {
		super();
		myalbumlistdao =  MyAlbumListDaoImpl.getInstance();//Singleton패턴
	}
	
	public static MyAlbumListServiceImpl getInstance() throws RemoteException {
		if(service== null) {
			service = new MyAlbumListServiceImpl();
		}
		return service;
	}

	@Override
	public int myAlbumListInsert(Map<String, String> myAlbumList) throws RemoteException {
		return myalbumlistdao.myAlbumListInsert(myAlbumList);
	}

	@Override
	public List<MyAlbumListVO> selectMyAlbList(String id) throws RemoteException {
		return myalbumlistdao.selectMyAlbList(id);
	}


}
