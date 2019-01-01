package kr.or.ddit.clap.dao.musichistory;

import java.util.List;
import kr.or.ddit.clap.vo.music.MusicHistoryVO;

/**
 * 
 * @author 진민규
 *
 */
public interface IMusicHistoryDao {
	public List<MusicHistoryVO> selectListAll();
}
