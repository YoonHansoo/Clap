package kr.or.ddit.clap.main;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import kr.or.ddit.clap.service.login.ILoginService;
import kr.or.ddit.clap.service.login.LoginServiceImpl;
import kr.or.ddit.clap.service.mypage.IMypageService;
import kr.or.ddit.clap.service.mypage.MypageServiceImpl;
import kr.or.ddit.clap.service.singer.ISingerService;
import kr.or.ddit.clap.service.singer.SingerServiceImpl;

public class ServerMain {
	public static void main(String[] args) {
		
		try {
//			IBoardService bsi = new BoardServiceImpl();
			ISingerService ssi 	= SingerServiceImpl.getInstance();
			IMypageService ms 	= MypageServiceImpl.getInstance(); //객체생성
			ILoginService ils 	= LoginServiceImpl.getInstance();
			
			Registry reg = LocateRegistry.createRegistry(8888);
			
			reg.rebind("singer", ssi);
			reg.rebind("mypage", ms);
			reg.rebind("login", ils);
			  System.out.println("clap server  is running...");
			  
		} catch (Exception e) {
			System.out.println("마 에러다!");
			e.printStackTrace();
		}
		
		
	}
	
	

}
