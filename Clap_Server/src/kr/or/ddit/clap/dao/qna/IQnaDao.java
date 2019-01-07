/**
 * 실제 DB와 연결해서 SQL문을 수행하여 결과를 작성하여
 * service에 전달하는 DAO의 interface
 * @author Hanhwa
 *
 */
package kr.or.ddit.clap.dao.qna;

import java.rmi.RemoteException;
import java.util.List;

import kr.or.ddit.clap.vo.support.QnaVO;

public interface IQnaDao {
	

	public List<QnaVO> selectListAll();
	
	
	public QnaVO qnaDetailContent(String title) throws RemoteException;
	
	public int insertQna(QnaVO vo);
	

}
