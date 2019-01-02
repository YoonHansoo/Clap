/**
 * Singleton 패턴을 적용한 
 * ISingerService를 구현한 클래스
 * @author Hansoo
 * 
 *
 */
package kr.or.ddit.clap.service.singer;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import kr.or.ddit.clap.dao.singer.SingerDaoImpl;
import kr.or.ddit.clap.vo.singer.SingerVO;

public class SingerServiceImpl extends UnicastRemoteObject implements ISingerService  {
	
	SingerDaoImpl singerDao; //사용할 Dao의  멤버변수를 선언
	private static SingerServiceImpl service;//Singleton패턴 
	
	private SingerServiceImpl() throws RemoteException {
		super();
		singerDao =  SingerDaoImpl.getInstance();//Singleton패턴
		System.out.println("생성자 실행");
	}
	
	public static SingerServiceImpl getInstance() throws RemoteException {
		if(service== null) {
			service = new SingerServiceImpl();
		}
		return service;
	}
	
	
	// 각 메서드에서는 생성된 Dao객체를 이용하여 작업에 맞는 Dao객체의 메서드를 호출한다.
	
	@Override
	public List<SingerVO> selectListAll() throws RemoteException {
		
		return singerDao.selectListAll();
	}

	@Override
	public List<SingerVO> searchList(SingerVO vo) throws RemoteException {
		
		return singerDao.searchList(vo);
	}

}
