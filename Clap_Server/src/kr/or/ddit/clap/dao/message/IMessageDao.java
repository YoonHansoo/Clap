package kr.or.ddit.clap.dao.message;

import java.util.List;

import kr.or.ddit.clap.vo.support.MessageVO;

public interface IMessageDao {
	
	public List<MessageVO> selectMessage(MessageVO vo);
	
	public String selectMessFCnt(MessageVO vo);

}
