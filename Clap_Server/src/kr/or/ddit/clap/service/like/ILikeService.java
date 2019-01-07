package kr.or.ddit.clap.service.like;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

import kr.or.ddit.clap.vo.member.LikeVO;
import kr.or.ddit.clap.vo.member.MemberVO;
import kr.or.ddit.clap.vo.music.MusicLikeVO;

public interface ILikeService extends Remote{
	public List<LikeVO> selectMusLike(LikeVO vo) throws RemoteException;
	public int deleteMusLike(LikeVO vo)throws RemoteException;
}

