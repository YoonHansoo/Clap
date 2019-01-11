package kr.or.ddit.clap.dao.myalbumlist;

import java.util.List;
import java.util.Map;

import kr.or.ddit.clap.vo.member.LikeVO;
import kr.or.ddit.clap.vo.myalbum.MyAlbumListVO;
import kr.or.ddit.clap.vo.myalbum.MyAlbumVO;


/**
 * 
 * @author 진민규
 *
 */
public interface IMyAlbumListDao {
	public int myAlbumListInsert(Map<String,String> myAlbumList);
	public List<MyAlbumListVO> selectMyAlbList(MyAlbumListVO vo);
	public int deleteMyAlbList(MyAlbumListVO vo);
}
