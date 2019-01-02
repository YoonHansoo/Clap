package kr.or.ddit.clap.service.mypage;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import kr.or.ddit.clap.dao.mypage.MypageDaoImpl;
import kr.or.ddit.clap.vo.member.MemberVO;
import kr.or.ddit.clap.vo.singer.SingerVO;

public class MypageServiceImpl   extends UnicastRemoteObject implements IMypageService  {
	MypageDaoImpl mypageDao; //사용할 Dao의  멤버변수를 선언
	private static MypageServiceImpl service;//Singleton패턴 
	
	private MypageServiceImpl() throws RemoteException {
		super();
		mypageDao =  MypageDaoImpl.getInstance();//Singleton패턴
		System.out.println("생성자 실행");
	}
	
	public static MypageServiceImpl getInstance() throws RemoteException {
		if(service== null) {
			service = new MypageServiceImpl();
		}
		return service;
	}
	
	
	// 각 메서드에서는 생성된 Dao객체를 이용하여 작업에 맞는 Dao객체의 메서드를 호출한다.

	@Override
	public MemberVO select(MemberVO vo) throws RemoteException {
		
		return mypageDao.select(vo);
	}

	@Override
	public int updateTel(MemberVO vo) throws RemoteException {
		return mypageDao.updateTel(vo);
	}

	@Override
	public int updateEmail(MemberVO vo) throws RemoteException {
		return mypageDao.updateEmail(vo);
	}

	
}
