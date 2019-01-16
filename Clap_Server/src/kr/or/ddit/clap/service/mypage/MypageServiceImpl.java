package kr.or.ddit.clap.service.mypage;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Map;

import kr.or.ddit.clap.dao.mypage.MypageDaoImpl;
import kr.or.ddit.clap.vo.member.MemberVO;
import kr.or.ddit.clap.vo.music.MusicReviewVO;
import kr.or.ddit.clap.vo.singer.SingerVO;

public class MypageServiceImpl   extends UnicastRemoteObject implements IMypageService  {
	MypageDaoImpl mypageDao; //사용할 Dao의  멤버변수를 선언
	private static MypageServiceImpl service;//Singleton패턴 
	
	private MypageServiceImpl() throws RemoteException {
		super();
		mypageDao =  MypageDaoImpl.getInstance();//Singleton패턴
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

	@Override
	public int updatePw(MemberVO vo) throws RemoteException {
		return mypageDao.updatePw(vo);
	}

	@Override
	public int updateDelTF(MemberVO vo) throws RemoteException {
		return mypageDao.updateDelTF(vo);
	}

	@Override
	public int updateInfo(MemberVO vo) throws RemoteException {
		return mypageDao.updateInfo(vo);
	}

	@Override
	public int updateImage(MemberVO vo) throws RemoteException {
		return mypageDao.updateImage(vo);
	}

	@Override
	public List<MemberVO> selectAll() throws RemoteException {
		return mypageDao.selectAll();
	}

	@Override
	public List<MemberVO> searchMemList(MemberVO vo) throws RemoteException {
		return mypageDao.searchMemList(vo);
	}

	@Override
	public int updateAll(MemberVO vo) throws RemoteException {
		return mypageDao.updateAll(vo);
	}

	@Override
	public int updateGrade(MemberVO vo) throws RemoteException {
		return mypageDao.updateGrade(vo);
	}

	

	
	
}
