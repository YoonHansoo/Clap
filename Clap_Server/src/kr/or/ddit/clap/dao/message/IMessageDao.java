package kr.or.ddit.clap.dao.message;

import java.util.List;

import kr.or.ddit.clap.vo.singer.SingerVO;
import kr.or.ddit.clap.vo.support.MessageVO;

public interface IMessageDao {
	
	public List<MessageVO> selectMessage(MessageVO vo);
	
	public String selectMessFCnt(MessageVO vo);

	public int updateMessage(MessageVO vo);
	
	public int insertMessage(MessageVO vo);

	public int deleteMessage(String no);
	
}
