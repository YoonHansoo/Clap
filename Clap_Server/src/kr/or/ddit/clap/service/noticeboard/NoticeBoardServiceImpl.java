/**
 * Singleton 패턴을 적용한 
 * INoticeBoardService를 구현한 클래스
 * @author Hanhwa
 * 
 *
 */
package kr.or.ddit.clap.service.noticeboard;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import kr.or.ddit.clap.dao.noticeboard.NoticeBoardDaoImpl;
import kr.or.ddit.clap.vo.support.NoticeBoardVO;

public class NoticeBoardServiceImpl extends UnicastRemoteObject implements INoticeBoardService {

	
	NoticeBoardDaoImpl noticeBoardDao; //사용할 Dao의  멤버변수를 선언
	private static NoticeBoardServiceImpl service;//Singleton패턴
	
	private NoticeBoardServiceImpl() throws RemoteException {
		super();
		noticeBoardDao =  NoticeBoardDaoImpl.getInstance();//Singleton패턴
	}
	
	public static NoticeBoardServiceImpl getInstance() throws RemoteException {
		if(service== null) {
			service = new NoticeBoardServiceImpl();
		}
		return service;
	}

	
	// 각 메서드에서는 생성된 Dao객체를 이용하여 작업에 맞는 Dao객체의 메서드를 호출한다.
	
	@Override
	public List<NoticeBoardVO> selectListAll() throws RemoteException {
		
		return noticeBoardDao.selectListAll();
	}

	@Override
	public NoticeBoardVO NoticeBoardDetailContent(String NoticeNo) throws RemoteException {
		return noticeBoardDao.NoticeBoardDetailContent(NoticeNo);
	}

	@Override
	public int insertNotice(NoticeBoardVO vo) throws RemoteException {
		return noticeBoardDao.insertNotice(vo);
	}

	@Override
	public int deleteNoticeContent(String notice_no) throws RemoteException {
		return noticeBoardDao.deleteNoticeContent(notice_no);
	}
	
	
	
	
	

}
