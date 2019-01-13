/**
 * 실제 DB와 연결해서 SQL문을 수행하여 결과를 작성하여
 * service에 전달하는 DAO의 interface
 * @author Hanhwa
 *
 */
package kr.or.ddit.clap.dao.eventboard;

import java.rmi.RemoteException;
import java.util.List;

import kr.or.ddit.clap.vo.support.EventBoardVO;

public interface IEventBoardDao {
	
	public List<EventBoardVO> selectListAll();
	
	public List<EventBoardVO> searchList(EventBoardVO vo);
	
	public int insertEvent(EventBoardVO vo);
	
	public EventBoardVO eventDetailAll(String ContentNo) throws RemoteException;
	
	public int updateEvent(EventBoardVO vo);

}
