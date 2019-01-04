/**
 * 실제 DB와 연결해서 SQL문을 수행하여 결과를 작성하여
 * service에 전달하는 DAO의 interface
 * @author Hansoo
 *
 */

package kr.or.ddit.clap.dao.singer;
import java.util.List;

import kr.or.ddit.clap.vo.singer.SingerVO;

public interface ISingerDao {
	
	public List<SingerVO> selectListAll();
	
	public List<SingerVO> searchList(SingerVO vo);
	
	public SingerVO singerDetailInfo(String singerNo);
	
	public int selectSingerLikeCnt(String singerNo);
}
