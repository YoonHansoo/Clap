/**
 * Album의 service Interface
 * @author Hansoo
 *
 */

package kr.or.ddit.clap.service.album;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import kr.or.ddit.clap.vo.album.AlbumVO;

public interface IAlbumService extends Remote {
	public List<AlbumVO> selectListAll() throws RemoteException;
	
	public List<AlbumVO> searchList(AlbumVO vo) throws RemoteException;
	
	public int insertAlbum(AlbumVO vo) throws RemoteException;
	
	public AlbumVO albumDetailInfo(String albumNo) throws RemoteException;
	
	public int selectAlbumLikeCnt(String albumNo) throws RemoteException;
	
	public int updateAlbumInfo(AlbumVO vo) throws RemoteException;
	
	 public int deleteAlbum(String albumNo) throws RemoteException;
}
