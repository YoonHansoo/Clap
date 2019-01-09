/**
 * 실제 DB와 연결해서 SQL문을 수행하여 결과를 작성하여
 * service에 전달하는 DAO의 interface
 * @author Hanhwa
 *
 */
package kr.or.ddit.clap.dao.noticeboard;

import java.io.IOException;
import java.io.Reader;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

import kr.or.ddit.clap.service.noticeboard.INoticeBoardService;
import kr.or.ddit.clap.vo.support.NoticeBoardVO;
import kr.or.ddit.clap.vo.support.QnaVO;

public class NoticeBoardDaoImpl implements INoticeBoardDao {
	
	private SqlMapClient smc;
	private static NoticeBoardDaoImpl dao; // Singleton 패턴
	
	private NoticeBoardDaoImpl() {
		Reader rd;
		try {
			rd = Resources.getResourceAsReader("SqlMapConfig.xml");
			smc = SqlMapClientBuilder.buildSqlMapClient(rd);
			rd.close();
		} catch (IOException e) {
			System.out.println("SqlMapClient객체 생성 실패!!");
			e.printStackTrace();
		}
	}
	
	public static NoticeBoardDaoImpl getInstance() { // Singleton 패턴
		if (dao == null) {
			dao = new NoticeBoardDaoImpl();
		}
		return dao;
	}

	@Override
	public List<NoticeBoardVO> selectListAll() {
		List<NoticeBoardVO> list = new ArrayList<NoticeBoardVO>();
		try {

			list = smc.queryForList("notice.selectListAll");

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}

	@Override
	public NoticeBoardVO NoticeBoardDetailContent(String NoticeNo) throws RemoteException {
		NoticeBoardVO nVO = new NoticeBoardVO();
		try {

			nVO = (NoticeBoardVO)smc.queryForObject("notice.NoticeBoardDetailContent", NoticeNo);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		
		
		return nVO;
	}

	@Override
	public int insertNotice(NoticeBoardVO vo) {
		
		int cnt = 0;
		
		try {
			Object obj = smc.insert("notice.insertNotice", vo);
		} catch(SQLException e) {
			System.out.println("insert실패");
			e.printStackTrace();
		}
		
		
		return cnt;
	}

	@Override
	public int deleteNoticeContent(String notice_no) {

		int cnt = 0; 
		
		try {
			cnt = smc.delete("notice.deleteNoticeContent", notice_no);
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return cnt;
	}

}
