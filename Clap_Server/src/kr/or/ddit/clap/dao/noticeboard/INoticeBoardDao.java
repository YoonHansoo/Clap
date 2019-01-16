/**
 * 실제 DB와 연결해서 SQL문을 수행하여 결과를 작성하여
 * service에 전달하는 DAO의 interface
 * @author Hanhwa
 *
 */
package kr.or.ddit.clap.dao.noticeboard;

import java.rmi.RemoteException;
import java.util.List;

import kr.or.ddit.clap.vo.support.NoticeBoardVO;

public interface INoticeBoardDao {
	
	public List<NoticeBoardVO> selectListAll();
	
	public NoticeBoardVO NoticeBoardDetailContent(String NoticeNo) throws RemoteException;
	
	public int insertNotice(NoticeBoardVO vo);
	
	public int deleteNoticeContent(String notice_no);
	
	public int updateNotice(NoticeBoardVO vo);
	
	public int updateCount(String notice_no); //조회수
	
	

}
