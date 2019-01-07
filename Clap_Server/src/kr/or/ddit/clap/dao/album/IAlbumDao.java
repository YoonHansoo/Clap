/**
 * 실제 DB와 연결해서 SQL문을 수행하여 결과를 작성하여
 * service에 전달하는 DAO의 interface
 * @author Hansoo
 *
 */

package kr.or.ddit.clap.dao.album;
import java.util.List;

import kr.or.ddit.clap.vo.album.AlbumVO;

public interface IAlbumDao {
	
	public List<AlbumVO> selectListAll();
	
	public List<AlbumVO> searchList(AlbumVO vo);
	/*
	public SingerVO singerDetailInfo(String singerNo);
	
	public int selectSingerLikeCnt(String singerNo);
	
	public int updateSingerInfo(SingerVO vo);
	
	public int insertSinger(SingerVO vo);
	
	 public int deleteSinger(String singerNo);*/
}
