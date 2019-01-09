package kr.or.ddit.clap.dao.playlist;

import java.util.List;
import kr.or.ddit.clap.vo.music.PlayListVO;


/**
 * 
 * @author 진민규
 *
 */
public interface IPlayListDao {
	public List<PlayListVO> PlayListSelect(String str_id);
	public int PlayListInsert(PlayListVO vo);
	public int PlayListDelete(PlayListVO vo);
	
}
