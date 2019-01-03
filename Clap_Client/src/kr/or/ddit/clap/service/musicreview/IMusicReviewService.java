package kr.or.ddit.clap.service.musicreview;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import kr.or.ddit.clap.vo.music.MusicReviewVO;

public interface IMusicReviewService extends Remote {
		public List<MusicReviewVO> selectReview(MusicReviewVO vo) throws RemoteException;

}
