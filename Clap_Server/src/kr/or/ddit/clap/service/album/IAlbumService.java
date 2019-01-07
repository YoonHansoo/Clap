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
	
	/*public SingerVO singerDetailInfo(String singerNo) throws RemoteException;
	
	public int selectSingerLikeCnt(String singerNo) throws RemoteException;
	
	public int updateSingerInfo(SingerVO vo) throws RemoteException;
	
	public int insertSinger(SingerVO vo) throws RemoteException;
	
	 public int deleteSinger(String singerNo) throws RemoteException;*/
}
