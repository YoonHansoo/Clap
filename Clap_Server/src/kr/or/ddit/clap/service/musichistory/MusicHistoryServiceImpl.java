/**
 * Singleton 패턴을 적용한 
 * ISingerService를 구현한 클래스
 * @author 진민규
 * 
 *
 */
package kr.or.ddit.clap.service.musichistory;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Map;

import kr.or.ddit.clap.dao.musichistory.MusicHistoryDaoImpl;
import kr.or.ddit.clap.vo.music.MusicHistoryVO;

/**
 * 
 * @author 진민규
 *
 */

public class MusicHistoryServiceImpl extends UnicastRemoteObject implements IMusicHistoryService  {
	
	MusicHistoryDaoImpl musicHistoryDao; //사용할 Dao의  멤버변수를 선언
	private static MusicHistoryServiceImpl service;//Singleton패턴 
	
	private MusicHistoryServiceImpl() throws RemoteException {
		super();
		musicHistoryDao =  MusicHistoryDaoImpl.getInstance();//Singleton패턴
	}
	
	public static MusicHistoryServiceImpl getInstance() throws RemoteException {
		if(service== null) {
			service = new MusicHistoryServiceImpl();
		}
		return service;
	}
	
	
	// 각 메서드에서는 생성된 Dao객체를 이용하여 작업에 맞는 Dao객체의 메서드를 호출한다.
	
	@Override
	public List<Map> toDaySelect() throws RemoteException {
		
		return musicHistoryDao.toDaySelect();
	}

	@Override
	public List<Map> periodSelect(Map<String, String> day) throws RemoteException {
		
		return musicHistoryDao.periodSelect(day);
	}

	@Override
	public List<MusicHistoryVO> selectMayIts(MusicHistoryVO vo) throws RemoteException {
		return musicHistoryDao.selectMayIts(vo);
	}

	@Override
	public List<MusicHistoryVO> selectMayIndate(MusicHistoryVO vo) throws RemoteException {
		return musicHistoryDao.selectMayIndate(vo);
	}

	@Override
	public List<Map> genreSelect(String genre) throws RemoteException {
		return musicHistoryDao.genreSelect(genre);
	}

	@Override
	public List<Map> interiorSelect(String year) {
		return musicHistoryDao.interiorSelect(year);
	}

	@Override
	public List<Map> foreignSelect(String year) {
		return musicHistoryDao.foreignSelect(year);
	}

	@Override
	public List<MusicHistoryVO> selectNewLisMus(String id) throws RemoteException {
		return musicHistoryDao.selectNewLisMus(id);
	}

	@Override
	public List<MusicHistoryVO> selectManyLisMus(String id) throws RemoteException {
		return musicHistoryDao.selectManyLisMus(id);
	}

	@Override
	public List<MusicHistoryVO> selectManyIts(String id) throws RemoteException {
		return musicHistoryDao.selectManyIts(id);
	}

	@Override
	public int historyInsert(MusicHistoryVO vo) throws RemoteException {
		return musicHistoryDao.historyInsert(vo);
	}

	@Override
	public List<Map> top10Select() throws RemoteException {
		return musicHistoryDao.top10Select();
	}

}
