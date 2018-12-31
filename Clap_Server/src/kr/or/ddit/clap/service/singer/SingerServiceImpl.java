package kr.or.ddit.clap.service.singer;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import kr.or.ddit.clap.dao.singer.SingerDao;
import kr.or.ddit.clap.vo.singer.SingerVO;

public class SingerServiceImpl extends UnicastRemoteObject implements ISingerService  {

	SingerDao dao;
	
	public SingerServiceImpl() throws RemoteException {
		
		super();
		dao= new SingerDao();
		System.out.println("생성자 실행");
	}
	
	@Override
	public List<SingerVO> singerList() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

}
