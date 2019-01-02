package kr.or.ddit.clap.service.login;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import kr.or.ddit.clap.vo.member.MemberVO;

public interface ILoginService extends Remote{
	public List<MemberVO> selectList() throws RemoteException;
}
