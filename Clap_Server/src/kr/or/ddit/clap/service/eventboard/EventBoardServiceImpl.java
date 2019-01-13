package kr.or.ddit.clap.service.eventboard;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import kr.or.ddit.clap.dao.eventboard.EventBoardDaoImpl;
import kr.or.ddit.clap.vo.support.EventBoardVO;

public class EventBoardServiceImpl extends UnicastRemoteObject implements IEventBoardService {
	
	EventBoardDaoImpl eventboarddao;
	private static EventBoardServiceImpl service;

	protected EventBoardServiceImpl() throws RemoteException {
		super();
		eventboarddao = EventBoardDaoImpl.getInstance(); //Singleton패턴
		
	}
	
	public static EventBoardServiceImpl getInstance() throws RemoteException {
		
		if(service == null) {
			service = new EventBoardServiceImpl();
		}
		return service;
		
	}

	@Override
	public List<EventBoardVO> selectListAll() throws RemoteException {
		
		return eventboarddao.selectListAll();
	}

	@Override
	public List<EventBoardVO> searchList(EventBoardVO vo) throws RemoteException {
		
		return eventboarddao.searchList(vo);
	}

	@Override
	public int insertEvent(EventBoardVO vo) throws RemoteException {
		return eventboarddao.insertEvent(vo);
	}

	@Override
	public EventBoardVO eventDetailAll(String ContentNo) throws RemoteException {
		return eventboarddao.eventDetailAll(ContentNo);
	}

	@Override
	public int updateEvent(EventBoardVO vo) throws RemoteException {
		return eventboarddao.updateEvent(vo);
		
	}

}
