package kr.or.ddit.clap.dao.myalbumlist;

import java.util.List;
import java.util.Map;

import kr.or.ddit.clap.vo.myalbum.MyAlbumListVO;
import kr.or.ddit.clap.vo.myalbum.MyAlbumVO;


/**
 * 
 * @author 진민규
 *
 */
public interface IMyAlbumListDao {
	public int myAlbumListInsert(Map<String,String> myAlbumList);
	public List<MyAlbumListVO> selectMyAlbList(String id);
}
