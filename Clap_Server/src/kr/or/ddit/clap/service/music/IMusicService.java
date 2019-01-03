package kr.or.ddit.clap.service.music;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import kr.or.ddit.clap.vo.member.MemberVO;
import kr.or.ddit.clap.vo.music.MusicReviewVO;

public interface IMusicService  extends Remote {
	//리뷰
			public List<MusicReviewVO> selectReview(MusicReviewVO vo) throws RemoteException;
}
