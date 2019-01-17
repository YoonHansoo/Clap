/**
 * 실제 DB와 연결해서 SQL문을 수행하여 결과를 작성하여
 * service에 전달하는 DAO의 interface
 * @author Hansoo
 *
 */

package kr.or.ddit.clap.dao.singer;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

import kr.or.ddit.clap.vo.singer.SingerVO;

public interface ISingerDao {

	public List<SingerVO> selectListAll();

	public List<SingerVO> searchList(SingerVO vo);

	public SingerVO singerDetailInfo(String singerNo);

	public int selectSingerLikeCnt(String singerNo);

	public int updateSingerInfo(SingerVO vo);

	public int insertSinger(SingerVO vo);

	public int deleteSinger(String singerNo);

	// 가수앨범 좋아요 여부 채크
	public int checkHeartYN(Map<String, String> map);

	// 가수 라이크 취소
	public int deleteSingerLike(Map<String, String> map);

	// 가수 라이크 등록
	public int insertSingerLike(Map<String, String> map);

	// 가수 댓글 조회
	public List<Map<String, String>> selectReply(String singerNo);

	// 가수 댓글 등록
	public int insertReply(Map<String, String> map);

	// 멤버 신고횟수 1 증가
	public int insertBlackCnt(String id);

	// 가수 댓글 삭제
	public int deleteSigerReply(String id);
}
