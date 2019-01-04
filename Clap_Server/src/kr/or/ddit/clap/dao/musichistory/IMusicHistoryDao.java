package kr.or.ddit.clap.dao.musichistory;

import java.util.List;
import java.util.Map;

import kr.or.ddit.clap.vo.music.MusicHistoryVO;
import kr.or.ddit.clap.vo.singer.SingerVO;


/**
 * 
 * @author 진민규
 *
 */
public interface IMusicHistoryDao {
	public List<Map> selectList();
	public List<MusicHistoryVO> selectMayIts(MusicHistoryVO vo);
	public List<MusicHistoryVO> selectMayIndate(MusicHistoryVO vo);
}
