package kr.or.ddit.clap.main;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import kr.or.ddit.clap.service.singer.ISingerService;
import kr.or.ddit.clap.service.singer.SingerServiceImpl;

public class ServerMain {
	public static void main(String[] args) {
		try {
//			IBoardService bsi = new BoardServiceImpl();
			ISingerService ssi = SingerServiceImpl.getInstance();
			
			Registry reg = LocateRegistry.createRegistry(8888);
			
			reg.rebind("singer", ssi);
			  System.out.println("clap server  is running...");
			  
		} catch (Exception e) {
			System.out.println("마 에러다!");
			e.printStackTrace();
		}
		
		
	}
	

}
