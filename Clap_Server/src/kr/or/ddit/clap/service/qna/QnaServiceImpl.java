/**
 * Singleton 패턴을 적용한 
 * ISingerService를 구현한 클래스
 * @author Hanhwa
 * 
 *
 */
package kr.or.ddit.clap.service.qna;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import kr.or.ddit.clap.dao.qna.QnaDaoImpl;
import kr.or.ddit.clap.dao.singer.SingerDaoImpl;
import kr.or.ddit.clap.service.singer.SingerServiceImpl;
import kr.or.ddit.clap.vo.singer.SingerVO;
import kr.or.ddit.clap.vo.support.QnaVO;

public class QnaServiceImpl extends UnicastRemoteObject implements IQnaService {
	
	
	QnaDaoImpl qnaDao; //사용할 Dao의  멤버변수를 선언
	private static QnaServiceImpl service;//Singleton패턴 
	
	private QnaServiceImpl() throws RemoteException {
		super();
		qnaDao =  QnaDaoImpl.getInstance();//Singleton패턴
	}
	
	public static QnaServiceImpl getInstance() throws RemoteException {
		if(service== null) {
			service = new QnaServiceImpl();
		}
		return service;
	}
	
	
	// 각 메서드에서는 생성된 Dao객체를 이용하여 작업에 맞는 Dao객체의 메서드를 호출한다.
	
	@Override
	public List<QnaVO> selectListAll() throws RemoteException {
		
		return qnaDao.selectListAll();
	}

	@Override
	public QnaVO qnaDetailContent(String ContentNo) throws RemoteException {
		return qnaDao.qnaDetailContent(ContentNo);
	}



	

}
