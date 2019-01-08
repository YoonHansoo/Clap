package kr.or.ddit.clap.service.login;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import kr.or.ddit.clap.dao.login.LoginDaoImpl;
import kr.or.ddit.clap.vo.member.MemberVO;

public class LoginServiceImpl extends UnicastRemoteObject implements ILoginService{

	LoginDaoImpl loginDao; // 사용할 Dao의  멤버변수를 선언
	private static LoginServiceImpl service; // Singleton패턴 
	
	private LoginServiceImpl() throws RemoteException {
		super();
		loginDao =  LoginDaoImpl.getInstance(); // Singleton패턴
	}

	public static LoginServiceImpl getInstance() throws RemoteException {
		if(service== null) {
			service = new LoginServiceImpl();
		}
		return service;
	}

	@Override
	public Boolean idCheck(String id) throws RemoteException {
		return loginDao.idCheck(id);
	}

	@Override
	public List<MemberVO> select(String id) throws RemoteException {
		return loginDao.select(id);
	}
	
	@Override
	public List<MemberVO> idSearch(MemberVO vo) throws RemoteException {
		return loginDao.idSearch(vo);
	}

	
	public static void main(String[] args) {
		try {
			service = service.getInstance();
			List<MemberVO> list = new ArrayList<MemberVO>();
			MemberVO vo = new MemberVO();
			boolean a = false;
//			a = service.idCheck("park11");
//			list = service.select("park11");
//			System.out.println(list.get(0).getMem_id());
			
			vo.setMem_bir("90/09/09");
			vo.setMem_tel("01099998888");
			list = service.idSearch(vo);
			System.out.println(list.get(0).getMem_id());
			
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

}
