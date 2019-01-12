/**
 * Singleton 패턴을 적용한 
 * ImusicService를 구현한 클래스
 * @author Hansoo
 * 
 *
 */
package kr.or.ddit.clap.service.music;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Map;

import kr.or.ddit.clap.dao.music.MusicDaoImpl;
import kr.or.ddit.clap.vo.genre.GenreDetailVO;
import kr.or.ddit.clap.vo.genre.GenreVO;
import kr.or.ddit.clap.vo.music.MusicVO;

public class MusicServiceImpl extends UnicastRemoteObject implements IMusicService  {
	
	MusicDaoImpl musicDao; //사용할 Dao의  멤버변수를 선언
	
	private static MusicServiceImpl service;//Singleton패턴 
	
	private MusicServiceImpl() throws RemoteException {
		super();
		musicDao =  MusicDaoImpl.getInstance();//Singleton패턴
	}
	
	public static MusicServiceImpl getInstance() throws RemoteException {
		if(service== null) {
			service = new MusicServiceImpl();
		}
		return service;
	}
	
	
	// 각 메서드에서는 생성된 Dao객체를 이용하여 작업에 맞는 Dao객체의 메서드를 호출한다.
	
	@Override
	public List<MusicVO> selectListAll() throws RemoteException {
		
		return musicDao.selectListAll();
	}

	@Override
	public List<MusicVO> searchList(MusicVO vo) throws RemoteException {
		return musicDao.searchList(vo);
	}

	@Override
	public int insertMusic(MusicVO vo) throws RemoteException {
		return musicDao.insertMusic(vo);
	}



	@Override
	public int selectMusicLikeCnt(String musicNo) throws RemoteException {
		return musicDao.selectMusicLikeCnt(musicNo);
	}

	@Override
	public int updateMusicInfo(MusicVO vo) throws RemoteException {
		return musicDao.updateMusicInfo(vo);
	}

	@Override
	public int deleteMusic(String musicNo) throws RemoteException {
		return musicDao.deleteMusic(musicNo);
	}

	@Override
	public List<GenreVO> showGenre() throws RemoteException {
		return musicDao.showGenre();
	}

	
	@Override
	public List<GenreDetailVO> showGenreDetail(String genreNo) throws RemoteException {
		return musicDao.showGenreDetail(genreNo);
	}

	@Override
	public String selectGenreNO(String genreName) throws RemoteException {
		return musicDao.selectGenreNO(genreName);
	}

	@Override
	public String selectGenreDetailNO(String genreName) throws RemoteException {
		return musicDao.selectGenreDetailNO(genreName);
	}

	@Override
	public MusicVO selectMusicDetailInfo(String musicNo) throws RemoteException {
		return musicDao.selectMusicDetailInfo(musicNo);
	}

	@Override
	public List<Map> newMusicSelete(String genre_no) throws RemoteException {
		return musicDao.newMusicSelete(genre_no);
	}

	


	

	

}
