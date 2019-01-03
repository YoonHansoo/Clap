package kr.or.ddit.clap.service.music;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import kr.or.ddit.clap.dao.music.MusicDaoImpl;
import kr.or.ddit.clap.dao.mypage.MypageDaoImpl;
import kr.or.ddit.clap.service.mypage.IMypageService;
import kr.or.ddit.clap.service.mypage.MypageServiceImpl;
import kr.or.ddit.clap.vo.member.MemberVO;
import kr.or.ddit.clap.vo.music.MusicReviewVO;

public class MusicServiceImpl  extends UnicastRemoteObject implements IMusicService  {
	MusicDaoImpl musicDao; //사용할 Dao의  멤버변수를 선언
	private static MusicServiceImpl service;//Singleton패턴 
	
	private MusicServiceImpl() throws RemoteException {
		super();
		musicDao =  MusicDaoImpl.getInstance();//Singleton패턴
		System.out.println("생성자 실행");
	}
	
	public static MusicServiceImpl getInstance() throws RemoteException {
		if(service== null) {
			service = new MusicServiceImpl();
		}
		return service;
	}

	@Override
	public List<MusicReviewVO> selectReview(MusicReviewVO vo) {
		return musicDao.selectReview(vo);
	}


}
