/**
 * Singleton 패턴을 적용한 
 * IAlbumService를 구현한 클래스
 * @author Hansoo
 * 
 *
 */
package kr.or.ddit.clap.service.album;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import kr.or.ddit.clap.dao.album.AlbumDaoImpl;
import kr.or.ddit.clap.vo.album.AlbumVO;

public class AlbumServiceImpl extends UnicastRemoteObject implements IAlbumService  {
	
	AlbumDaoImpl albumDao; //사용할 Dao의  멤버변수를 선언
	private static AlbumServiceImpl service;//Singleton패턴 
	
	private AlbumServiceImpl() throws RemoteException {
		super();
		albumDao =  AlbumDaoImpl.getInstance();//Singleton패턴
	}
	
	public static AlbumServiceImpl getInstance() throws RemoteException {
		if(service== null) {
			service = new AlbumServiceImpl();
		}
		return service;
	}
	
	
	// 각 메서드에서는 생성된 Dao객체를 이용하여 작업에 맞는 Dao객체의 메서드를 호출한다.
	
	@Override
	public List<AlbumVO> selectListAll() throws RemoteException {
		
		return albumDao.selectListAll();
	}

	@Override
	public List<AlbumVO> searchList(AlbumVO vo) throws RemoteException {
		// TODO Auto-generated method stub
		return albumDao.searchList(vo);
	}

	

	

}
