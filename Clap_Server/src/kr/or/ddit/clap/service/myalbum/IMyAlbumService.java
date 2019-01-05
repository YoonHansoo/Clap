package kr.or.ddit.clap.service.myalbum;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import kr.or.ddit.clap.vo.myalbum.MyAlbumVO;

/**
 * 
 * @author 진민규
 *
 */

public interface IMyAlbumService  extends Remote {
	
	public List<MyAlbumVO> myAlbumSelect(String id) throws RemoteException;
}
