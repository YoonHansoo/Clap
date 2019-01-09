/**
 * Music의 service Interface
 * @author Hansoo
 *
 */

package kr.or.ddit.clap.service.music;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import kr.or.ddit.clap.vo.genre.GenreDetailVO;
import kr.or.ddit.clap.vo.genre.GenreVO;
import kr.or.ddit.clap.vo.music.MusicVO;

public interface IMusicService extends Remote {
	public List<MusicVO> selectListAll() 						throws RemoteException;
	
	public List<MusicVO> searchList(MusicVO vo) 				throws RemoteException;
	
	public List<GenreVO> showGenre() 							throws RemoteException;
	
	public String selectGenreNO(String genreName) 				throws RemoteException;
	
	public String selectGenreDetailNO(String genreName)			throws RemoteException;
	
	public List<GenreDetailVO> showGenreDetail(String genreNo) 	throws RemoteException;
	
	
	public int insertMusic(MusicVO vo) 							throws RemoteException;
	
	public MusicVO musicDetailInfo(String musicNo) 				throws RemoteException;
	
	public int selectMusicLikeCnt(String musicNo) 				throws RemoteException;
	
	public int updateMusicInfo(MusicVO vo)						throws RemoteException;
	
	public int deleteMusic(String musicNo) 						throws RemoteException;
}
