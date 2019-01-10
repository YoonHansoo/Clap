package kr.or.ddit.clap.service.eventboard;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import kr.or.ddit.clap.vo.support.EventBoardVO;

public interface IEventBoardService extends Remote {
	
	public List<EventBoardVO> selectListAll() throws RemoteException;
	
	public List<EventBoardVO> searchList(EventBoardVO vo) throws RemoteException;
	
	
	

}
