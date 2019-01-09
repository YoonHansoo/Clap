package kr.or.ddit.clap.service.playlist;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import kr.or.ddit.clap.vo.music.PlayListVO;

/**
 * 
 * @author 진민규
 *
 */

public interface IPlayListService  extends Remote {
	public List<PlayListVO> PlayListSelect(String str_id) throws RemoteException;
	public int PlayListInsert(PlayListVO vo) throws RemoteException;
	public int PlayListDelete(PlayListVO vo) throws RemoteException;
}
