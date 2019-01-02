/**
 * 실제 DB와 연결해서 SQL문을 수행하여 결과를 작성하여
 * service에 전달하는 DAO의 interface
 * @author Hanhwa
 *
 */
package kr.or.ddit.clap.dao.qna;

import java.util.List;

import kr.or.ddit.clap.vo.support.QnaVO;

public interface IQnaDao {
	
	public List<QnaVO> selectListAll();

}
