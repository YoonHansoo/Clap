package kr.or.ddit.clap.service.login;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ILoginService extends Remote{
	public Boolean idCheck(String id) throws RemoteException;
}
