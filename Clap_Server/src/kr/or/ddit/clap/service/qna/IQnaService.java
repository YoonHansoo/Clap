package kr.or.ddit.clap.service.qna;

import java.rmi.RemoteException;
import java.util.List;

import kr.or.ddit.clap.vo.singer.SingerVO;

public interface IQnaService {

	List<SingerVO> selectListAll() throws RemoteException;

}
