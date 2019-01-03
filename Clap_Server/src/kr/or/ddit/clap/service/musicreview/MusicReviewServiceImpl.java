package kr.or.ddit.clap.service.musicreview;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import kr.or.ddit.clap.dao.musicreview.MusicReviewDaoImpl;
import kr.or.ddit.clap.dao.mypage.MypageDaoImpl;
import kr.or.ddit.clap.service.mypage.IMypageService;
import kr.or.ddit.clap.service.mypage.MypageServiceImpl;
import kr.or.ddit.clap.vo.member.MemberVO;
import kr.or.ddit.clap.vo.music.MusicReviewVO;

public class MusicReviewServiceImpl  extends UnicastRemoteObject implements IMusicReviewService  {
	MusicReviewDaoImpl musicDao; //사용할 Dao의  멤버변수를 선언
	private static MusicReviewServiceImpl service;//Singleton패턴 
	
	private MusicReviewServiceImpl() throws RemoteException {
		super();
		musicDao =  MusicReviewDaoImpl.getInstance();//Singleton패턴
		System.out.println("생성자 실행");
	}
	
	public static MusicReviewServiceImpl getInstance() throws RemoteException {
		if(service== null) {
			service = new MusicReviewServiceImpl();
		}
		return service;
	}

	@Override
	public List<MusicReviewVO> selectReview(MusicReviewVO vo) {
		return musicDao.selectReview(vo);
	}


}
