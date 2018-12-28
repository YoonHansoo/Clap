package com.clap.main;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ServerMain {
	
	
	
	public static void main(String[] args) {
		try {
//			IBoardService bsi = new BoardServiceImpl();
			
			Registry reg = LocateRegistry.createRegistry(7777);
			
//			reg.rebind("board", bsi);
			  System.out.println("ChatServerImpl is running...");
			  
		} catch (Exception e) {
			System.out.println("마 에러다!");
			e.printStackTrace();
		}
		
		
	}
	

}
