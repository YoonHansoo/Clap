package kr.or.ddit.clap.service.playlist;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Map;

import kr.or.ddit.clap.dao.playlist.PlayListDaoImpl;
import kr.or.ddit.clap.vo.music.PlayListVO;

/**
 * 
 * @author 진민규
 *
 */

public class PlayListServiceImpl  extends UnicastRemoteObject implements IPlayListService  {
	PlayListDaoImpl playlistdao; //사용할 Dao의  멤버변수를 선언
	private static PlayListServiceImpl service;//Singleton패턴 
	
	private PlayListServiceImpl() throws RemoteException {
		super();
		playlistdao =  PlayListDaoImpl.getInstance();//Singleton패턴
	}
	
	public static PlayListServiceImpl getInstance() throws RemoteException {
		if(service== null) {
			service = new PlayListServiceImpl();
		}
		return service;
	}

	@Override
	public List<PlayListVO> playlistSelect(String mem_id) throws RemoteException {
		return playlistdao.playlistSelect(mem_id);
	}

	@Override
	public int playlistInsert(PlayListVO vo) throws RemoteException {
		return playlistdao.playlistInsert(vo);
	}

	@Override
	public int playlistDelete(PlayListVO vo) throws RemoteException {
		return playlistdao.playlistDelete(vo);
	}

}
