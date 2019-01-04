package kr.or.ddit.clap.service.mypage;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

import kr.or.ddit.clap.vo.member.MemberVO;
import kr.or.ddit.clap.vo.music.MusicReviewVO;

public interface IMypageService  extends Remote {
	public MemberVO select(MemberVO vo) throws RemoteException;
	public int  updateTel(MemberVO vo) throws RemoteException;
	public int  updateEmail(MemberVO vo) throws RemoteException;
	public int  updatePw(MemberVO vo) throws RemoteException;
	public int  updateDelTF(MemberVO vo) throws RemoteException;
	public int  updateInfo(MemberVO vo) throws RemoteException;
	public List<Map> selectMusLike(MemberVO vo) throws RemoteException;
}
