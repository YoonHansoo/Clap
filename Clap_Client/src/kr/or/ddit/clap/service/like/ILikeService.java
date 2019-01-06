package kr.or.ddit.clap.service.like;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import kr.or.ddit.clap.vo.member.LikeVO;
import kr.or.ddit.clap.vo.music.MusicLikeVO;

public interface ILikeService extends Remote{
	public LikeVO selectLike(LikeVO vo) throws RemoteException;
	public List<LikeVO> selectMusLike(LikeVO vo) throws RemoteException;
}

