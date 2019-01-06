package kr.or.ddit.clap.service.myalbumlist;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;

/**
 * 
 * @author 진민규
 *
 */

public interface IMyAlbumListService  extends Remote {
	
	public int myAlbumListInsert(Map<String,String> myAlbumList) throws RemoteException;
}
