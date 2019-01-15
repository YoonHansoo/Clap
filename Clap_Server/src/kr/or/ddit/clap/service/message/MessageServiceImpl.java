package kr.or.ddit.clap.service.message;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import kr.or.ddit.clap.dao.message.MessageDaoImpl;
import kr.or.ddit.clap.vo.support.MessageVO;

public class MessageServiceImpl extends UnicastRemoteObject implements IMessageService  {
	
	MessageDaoImpl msgDao; //사용할 Dao의  멤버변수를 선언
	
	private static MessageServiceImpl service;//Singleton패턴 
	
	private MessageServiceImpl() throws RemoteException {
		super();
		msgDao =  MessageDaoImpl.getInstance();//Singleton패턴
	}
	
	public static MessageServiceImpl getInstance() throws RemoteException {
		if(service== null) {
			service = new MessageServiceImpl();
		}
		return service;
	}

	@Override
	public List<MessageVO> selectMessage(MessageVO vo) throws RemoteException {
		return msgDao.selectMessage(vo);
	}

	@Override
	public String selectMessFCnt(MessageVO vo) throws RemoteException {
		return msgDao.selectMessFCnt(vo);
	}

	@Override
	public int updateMessage(MessageVO vo) throws RemoteException {
		return msgDao.updateMessage(vo);
	}
}
