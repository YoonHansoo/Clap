/**
 * Singer의 service Interface
 * @author Hansoo
 *
 */

package kr.or.ddit.clap.service.singer;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import kr.or.ddit.clap.vo.singer.SingerVO;

public interface ISingerService extends Remote {
	public List<SingerVO> selectListAll() throws RemoteException;
	
}
