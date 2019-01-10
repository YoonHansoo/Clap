package kr.or.ddit.clap.dao.eventboard;

import java.util.List;

import kr.or.ddit.clap.vo.support.EventBoardVO;

public interface IEventBoardDao {
	
	public List<EventBoardVO> selectListAll();
	
	public List<EventBoardVO> searchList(EventBoardVO vo);

}
