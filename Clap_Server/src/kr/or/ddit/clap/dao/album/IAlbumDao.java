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
	
	public int insertAlbum(AlbumVO vo);
	
public AlbumVO albumDetailInfo(String albumNo);

	public int selectAlbumLikeCnt(String albumNo);
	
	public int updateAlbumInfo(AlbumVO vo);

	
	 public int deleteAlbum(String albumNo);
}
