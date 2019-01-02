package kr.or.ddit.clap.service.login;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import kr.or.ddit.clap.vo.member.MemberVO;

public class LoginServiceImpl extends UnicastRemoteObject implements ILoginService{

	LoginServiceImpl loginDao; // 사용할 Dao의  멤버변수를 선언
	private static LoginServiceImpl service; // Singleton패턴 
	
	protected LoginServiceImpl() throws RemoteException {
		super();
		loginDao =  LoginServiceImpl.getInstance(); // Singleton패턴
	}

	public static LoginServiceImpl getInstance() throws RemoteException {
		if(service== null) {
			service = new LoginServiceImpl();
		}
		return service;
	}

	@Override
	public MemberVO select(String id) throws RemoteException {
		return loginDao.select(id);
	}


}
