package kr.or.ddit.clap.service.qna;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import kr.or.ddit.clap.vo.support.QnaVO;

public interface IQnaService extends Remote {
	

	public List<QnaVO> selectListAll() throws RemoteException;

	public QnaVO qnaDetailContent(String ContentNo) throws RemoteException;


}
