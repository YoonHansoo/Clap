/**
 * 실제 DB와 연결해서 SQL문을 수행하여 결과를 작성하여
 * service에 전달하는 DAO의 interface
 * @author Hansoo
 *
 */

package kr.or.ddit.clap.dao.music;
import java.util.List;

import kr.or.ddit.clap.vo.music.MusicVO;

public interface IMusicDao {
	
public List<MusicVO> selectListAll();
	
	public List<MusicVO> searchList(MusicVO vo);
	
	public int insertMusic(MusicVO vo);
	
	public MusicVO musicDetailInfo(String musicNo);
	
	public int selectMusicLikeCnt(String musicNo);
	
	public int updateMusicInfo(MusicVO vo);
	
	 public int deleteMusic(String musicNo);
}
