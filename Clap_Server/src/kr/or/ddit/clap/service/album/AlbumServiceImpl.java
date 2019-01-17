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
import java.util.Map;

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

	@Override
	public int insertAlbum(AlbumVO vo) throws RemoteException {
		return albumDao.insertAlbum(vo);
	}

	@Override
	public AlbumVO albumDetailInfo(String albumNo) throws RemoteException {
		return albumDao.albumDetailInfo(albumNo);
	}

	@Override
	public int selectAlbumLikeCnt(String albumNo) throws RemoteException {
		return albumDao.selectAlbumLikeCnt(albumNo);
	}

	@Override
	public int updateAlbumInfo(AlbumVO vo) throws RemoteException {
		return albumDao.updateAlbumInfo(vo);
	}

	@Override
	public int deleteAlbum(String albumNo) throws RemoteException {
		return albumDao.deleteAlbum(albumNo);
	}

	@Override
	public List<Map> newAlbumSelect() throws RemoteException {
		return albumDao.newAlbumSelect();
	}

	@Override
	public List<Map> singerAlbumSelect(AlbumVO vo) throws RemoteException {
		return albumDao.singerAlbumSelect(vo);
	}

	@Override
	public int checkHeartYN(Map<String, String> map) throws RemoteException {
		return albumDao.checkHeartYN(map);
	}

	@Override
	public int deleteAlbLike(Map<String, String> map) throws RemoteException {
		return albumDao.deleteAlbLike(map);
	}

	@Override
	public int insertAlbLike(Map<String, String> map) throws RemoteException {
		return albumDao.insertAlbLike(map);
	}

	@Override
	public List<Map<String, String>> selectAlbReply(String singerNo) throws RemoteException {
		return albumDao.selectAlbReply(singerNo);
	}

	@Override
	public int insertAlbReply(Map<String, String> map) throws RemoteException {
		return albumDao.insertAlbReply(map);
	}

	@Override
	public int deleteAlbReply(String id) throws RemoteException {
		return albumDao.deleteAlbReply(id);
	}

	

	

}
