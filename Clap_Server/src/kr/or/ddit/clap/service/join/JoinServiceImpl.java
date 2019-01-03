package kr.or.ddit.clap.service.join;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import kr.or.ddit.clap.dao.join.JoinDaoImpl;
import kr.or.ddit.clap.vo.member.MemberVO;

public class JoinServiceImpl extends UnicastRemoteObject implements IJoinService{

	JoinDaoImpl joinDao; // 사용할 Dao의  멤버변수를 선언
	private static JoinServiceImpl service; // Singleton패턴 
	
	private JoinServiceImpl() throws RemoteException {
		super();
		joinDao =  JoinDaoImpl.getInstance(); // Singleton패턴
	}
	
	public static JoinServiceImpl getInstance() throws RemoteException {
		if(service== null) {
			service = new JoinServiceImpl();
		}
		return service;
	}
	
	@Override
	public int insert(MemberVO vo) throws RemoteException {
		return joinDao.insert(vo);
	}

}
