package kr.or.ddit.clap.service.myalbum;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Map;

import kr.or.ddit.clap.dao.myalbum.MyAlbumDaoImpl;
import kr.or.ddit.clap.vo.myalbum.MyAlbumVO;

/**
 * 
 * @author 진민규
 *
 */

public class MyAlbumServiceImpl  extends UnicastRemoteObject implements IMyAlbumService  {
	MyAlbumDaoImpl myalbumdao; //사용할 Dao의  멤버변수를 선언
	private static MyAlbumServiceImpl service;//Singleton패턴 
	
	private MyAlbumServiceImpl() throws RemoteException {
		super();
		myalbumdao =  MyAlbumDaoImpl.getInstance();//Singleton패턴
	}
	
	public static MyAlbumServiceImpl getInstance() throws RemoteException {
		if(service== null) {
			service = new MyAlbumServiceImpl();
		}
		return service;
	}

	@Override
	public List<MyAlbumVO> myAlbumSelect(String id) {
		return myalbumdao.myAlbumSelect(id);
	}

	@Override
	public int myAlbumInsert(Map<String, String> myAlbum) throws RemoteException {
		// TODO Auto-generated method stub
		return myalbumdao.myAlbumInsert(myAlbum);
	}

	@Override
	public int deleteMyalb(MyAlbumVO vo) throws RemoteException {
		return myalbumdao.deleteMyalb(vo);
	}

	@Override
	public int updateMyalb(MyAlbumVO vo) throws RemoteException {
		return myalbumdao.updateMyalb(vo);
	}

}
