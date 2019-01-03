/**
 * MusicHistory의 service Interface
 * @author 진민규
 *
 */

package kr.or.ddit.clap.service.musichistory;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

import kr.or.ddit.clap.vo.music.MusicHistoryVO;

public interface IMusicHistoryService extends Remote {
	public List<Map> selectList() throws RemoteException;
	
}
