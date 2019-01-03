package kr.or.ddit.clap.main;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import kr.or.ddit.clap.service.join.IJoinService;
import kr.or.ddit.clap.service.join.JoinServiceImpl;
import kr.or.ddit.clap.service.login.ILoginService;
import kr.or.ddit.clap.service.login.LoginServiceImpl;
<<<<<<< .mine
import kr.or.ddit.clap.service.musichistory.IMusicHistoryService;
import kr.or.ddit.clap.service.musichistory.MusicHistoryServiceImpl;
||||||| .r52131
=======
import kr.or.ddit.clap.service.music.IMusicService;
import kr.or.ddit.clap.service.music.MusicServiceImpl;
>>>>>>> .r52143
import kr.or.ddit.clap.service.mypage.IMypageService;
import kr.or.ddit.clap.service.mypage.MypageServiceImpl;
import kr.or.ddit.clap.service.qna.IQnaService;
import kr.or.ddit.clap.service.qna.QnaServiceImpl;
import kr.or.ddit.clap.service.singer.ISingerService;
import kr.or.ddit.clap.service.singer.SingerServiceImpl;

public class ServerMain {
	public static void main(String[] args) {
		
		try {
//			IBoardService bsi = new BoardServiceImpl();
			ISingerService ssi 	= SingerServiceImpl.getInstance();	//가수
			IMypageService ms 	= MypageServiceImpl.getInstance();	//마이페이지
			ILoginService ils 	= LoginServiceImpl.getInstance();	//로그인
			IQnaService iqs     = QnaServiceImpl.getInstance();		//문의사항
			IJoinService ijs     = JoinServiceImpl.getInstance();	//회원가입
			IMusicService ims     = MusicServiceImpl.getInstance();	//뮤직
			IMusicHistoryService ims = MusicHistoryServiceImpl.getInstance();
			
			Registry reg = LocateRegistry.createRegistry(8888);
			
			reg.rebind("singer", ssi);
			reg.rebind("mypage", ms);
			reg.rebind("login", ils);
			reg.rebind("qna", iqs);
			reg.rebind("join", ijs);
			reg.rebind("history", ims);
			reg.rebind("music", ims);
			 System.out.println("clap server  is running...");
			  
		} catch (Exception e) {
			System.out.println("마 에러다!");
			e.printStackTrace();
		}
		
		
		
	}
	
	

}
