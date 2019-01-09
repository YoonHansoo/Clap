package kr.or.ddit.clap.dao.playlist;

import java.util.List;
import java.util.Map;
import kr.or.ddit.clap.vo.music.PlayListVO;


/**
 * 
 * @author 진민규
 *
 */
public interface IPlayListDao {
	public List<PlayListVO> playlistSelect(String mem_id);
	public List<Map> infoSelect(String mus_no);
	public int playlistInsert(PlayListVO vo);
	public int playlistDelete(PlayListVO vo);
	
}
