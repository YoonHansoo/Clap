package kr.or.ddit.clap.service.noticeboard;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import kr.or.ddit.clap.vo.support.NoticeBoardVO;

public interface INoticeBoardService extends Remote {
	
	public List<NoticeBoardVO> selectListAll() throws RemoteException;

}
