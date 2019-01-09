package kr.or.ddit.clap.service.eventboard;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import kr.or.ddit.clap.dao.eventboard.EventBoardDaoImpl;

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

}
