package kr.or.ddit.clap.main;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import kr.or.ddit.clap.dao.noticeboard.NoticeBoardDaoImpl;
import kr.or.ddit.clap.service.album.AlbumServiceImpl;
import kr.or.ddit.clap.service.album.IAlbumService;
import kr.or.ddit.clap.service.join.IJoinService;
import kr.or.ddit.clap.service.join.JoinServiceImpl;
import kr.or.ddit.clap.service.like.ILikeService;
import kr.or.ddit.clap.service.like.LikeServiceImpl;
import kr.or.ddit.clap.service.login.ILoginService;
import kr.or.ddit.clap.service.login.LoginServiceImpl;
import kr.or.ddit.clap.service.music.IMusicService;
import kr.or.ddit.clap.service.music.MusicServiceImpl;
import kr.or.ddit.clap.service.musichistory.IMusicHistoryService;
import kr.or.ddit.clap.service.musichistory.MusicHistoryServiceImpl;
import kr.or.ddit.clap.service.musicreview.IMusicReviewService;
import kr.or.ddit.clap.service.musicreview.MusicReviewServiceImpl;
import kr.or.ddit.clap.service.myalbum.IMyAlbumService;
import kr.or.ddit.clap.service.myalbum.MyAlbumServiceImpl;
import kr.or.ddit.clap.service.myalbumlist.IMyAlbumListService;
import kr.or.ddit.clap.service.myalbumlist.MyAlbumListServiceImpl;
import kr.or.ddit.clap.service.mypage.IMypageService;
import kr.or.ddit.clap.service.mypage.MypageServiceImpl;
import kr.or.ddit.clap.service.noticeboard.INoticeBoardService;
import kr.or.ddit.clap.service.noticeboard.NoticeBoardServiceImpl;
import kr.or.ddit.clap.service.qna.IQnaService;
import kr.or.ddit.clap.service.qna.QnaServiceImpl;
import kr.or.ddit.clap.service.singer.ISingerService;
import kr.or.ddit.clap.service.singer.SingerServiceImpl;

public class ServerMain {
	public static void main(String[] args) {
		
		try {
//			IBoardService bsi = new BoardServiceImpl();
			ISingerService ssi 		  = SingerServiceImpl.getInstance();		// 가수
			IMypageService ms 		  = MypageServiceImpl.getInstance();		// 마이페이지
			ILoginService ils 		  = LoginServiceImpl.getInstance();			// 로그인
			IQnaService iqs     	  = QnaServiceImpl.getInstance();			// 문의사항
			IJoinService ijs     	  = JoinServiceImpl.getInstance();			// 회원가입
			IMusicReviewService imrs  = MusicReviewServiceImpl.getInstance();	// 뮤직댓글
			IMusicHistoryService imhs = MusicHistoryServiceImpl.getInstance(); 	// 뮤직순위
			ILikeService ilks 		  = LikeServiceImpl.getInstance(); 			// 좋아요
			IMyAlbumService imas 	  = MyAlbumServiceImpl.getInstance();		// 마이앨범
			IMyAlbumListService imals = MyAlbumListServiceImpl.getInstance(); 	// 마이앨범리스트
			IAlbumService ias 		  = AlbumServiceImpl.getInstance(); 		// 앨범
			INoticeBoardService ins   = NoticeBoardServiceImpl.getInstance();	// 공지사항
			
			IMusicService ims = MusicServiceImpl.getInstance(); 				//곡
			
			Registry reg = LocateRegistry.createRegistry(8888);
			
			reg.rebind("singer", ssi);
			reg.rebind("mypage", ms);
			reg.rebind("login", ils);
			reg.rebind("qna", iqs);
			reg.rebind("join", ijs);
			reg.rebind("history", imhs);
			reg.rebind("musicreview", imrs);
			reg.rebind("like", ilks);
			reg.rebind("myalbum", imas);
			reg.rebind("myalbumlist", imals);
			reg.rebind("album", ias);
			reg.rebind("music", ims);
			
			
			 System.out.println("clap server  is running...");
			  
		} catch (Exception e) {
			System.out.println("마 에러다!");
			e.printStackTrace();
		}
		
		
		
	}
	
	

}
