package kr.or.ddit.clap.service.mypage;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import kr.or.ddit.clap.vo.member.MemberVO;

/**
 * @author hyuns현지
 */

public interface IMypageService  extends Remote {

	public List<MemberVO> selectListAll() throws RemoteException;;
	
}
