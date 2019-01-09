package kr.or.ddit.clap.dao.myalbum;

import java.util.List;
import java.util.Map;

import kr.or.ddit.clap.vo.member.LikeVO;
import kr.or.ddit.clap.vo.myalbum.MyAlbumVO;


/**
 * 
 * @author 진민규
 *
 */
public interface IMyAlbumDao {
	public List<MyAlbumVO> myAlbumSelect(String id);
	public int myAlbumInsert(Map<String,String> myAlbum);
	
	/**
	 * 현지
	 */
	public int deleteMyalb(MyAlbumVO vo);
	
}
