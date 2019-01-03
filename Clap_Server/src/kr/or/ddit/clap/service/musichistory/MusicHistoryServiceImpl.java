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

public class MusicHistoryServiceImpl extends UnicastRemoteObject implements IMusicHistoryService  {
	
	MusicHistoryDaoImpl musicHistoryDao; //사용할 Dao의  멤버변수를 선언
	private static MusicHistoryServiceImpl service;//Singleton패턴 
	
	private MusicHistoryServiceImpl() throws RemoteException {
		super();
		musicHistoryDao =  MusicHistoryDaoImpl.getInstance();//Singleton패턴
		System.out.println("생성자 실행");
	}
	
	public static MusicHistoryServiceImpl getInstance() throws RemoteException {
		if(service== null) {
			service = new MusicHistoryServiceImpl();
		}
		return service;
	}
	
	
	// 각 메서드에서는 생성된 Dao객체를 이용하여 작업에 맞는 Dao객체의 메서드를 호출한다.
	
	@Override
	public List<Map> selectList() throws RemoteException {
		
		return musicHistoryDao.selectList();
	}

}
