package kr.or.ddit.clap.service.qna;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import kr.or.ddit.clap.vo.singer.SingerVO;
import kr.or.ddit.clap.vo.support.QnaVO;

public interface IQnaService extends Remote{

	List<QnaVO> selectListAll() throws RemoteException;

}
