/**
 * 실제 DB와 연결해서 SQL문을 수행하여 결과를 작성하여
 * service에 전달하는 DAO의 interface
 * @author Hansoo
 *
 */

package kr.or.ddit.clap.dao.album;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

import kr.or.ddit.clap.vo.album.AlbumVO;

public interface IAlbumDao {

	public List<AlbumVO> selectListAll();

	public List<AlbumVO> searchList(AlbumVO vo);

	public int insertAlbum(AlbumVO vo);

	public AlbumVO albumDetailInfo(String albumNo);

	public int selectAlbumLikeCnt(String albumNo);

	public int updateAlbumInfo(AlbumVO vo);

	public int deleteAlbum(String albumNo);

	public List<Map> newAlbumSelect();

	public List<Map> singerAlbumSelect(AlbumVO vo);

	// 앨범 좋아요 여부 채크
	public int checkHeartYN(Map<String, String> map);

	// 앨범 라이크 취소
	public int deleteAlbLike(Map<String, String> map);

	// 앨범 라이크 등록
	public int insertAlbLike(Map<String, String> map);

	// 앨범 댓글 조회
	public List<Map<String, String>> selectAlbReply(String singerNo);

	// 앨범 댓글 등록
	public int insertAlbReply(Map<String, String> map);

	// 앨범 댓글 삭제
	public int deleteAlbReply(String id);
}
