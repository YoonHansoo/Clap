package kr.or.ddit.clap.dao.myalbum;

import java.util.List;
import java.util.Map;
import kr.or.ddit.clap.vo.myalbum.MyAlbumVO;


/**
 * 
 * @author 진민규
 *
 */
public interface IMyAlbumyDao {
	public List<MyAlbumVO> myAlbumSelect(String id);
	public int myAlbumInsert(Map<String,String> myAlbum);
	
}
