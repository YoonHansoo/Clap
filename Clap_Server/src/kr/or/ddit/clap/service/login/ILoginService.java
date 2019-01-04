package kr.or.ddit.clap.service.login;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import kr.or.ddit.clap.vo.member.MemberVO;

public interface ILoginService extends Remote{
	public Boolean idCheck(String id) throws RemoteException;

	public List<MemberVO> select(String id) throws RemoteException;
}
