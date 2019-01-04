package kr.or.ddit.clap.service.like;

import java.rmi.Remote;
import java.rmi.RemoteException;

import kr.or.ddit.clap.vo.music.MusicLikeVO;

public interface ILikeService extends Remote {
	public MusicLikeVO selectLike(MusicLikeVO vo) throws RemoteException;
}
