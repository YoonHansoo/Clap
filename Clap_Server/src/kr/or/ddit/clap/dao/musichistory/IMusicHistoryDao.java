package kr.or.ddit.clap.dao.musichistory;

import java.util.List;
import java.util.Map;
import kr.or.ddit.clap.vo.music.MusicHistoryVO;


/**
 * 
 * @author 진민규
 *
 */
public interface IMusicHistoryDao {
	public List<Map> toDaySelect();
	public List<Map> genreSelect(String genre);
	public List<Map> periodSelect(Map<String, String> day);
	public List<Map> interiorSelect(String year);
	public List<Map> foreignSelect(String year);
	public List<MusicHistoryVO> selectMayIts(MusicHistoryVO vo);
	public List<MusicHistoryVO> selectMayIndate(MusicHistoryVO vo);
}
