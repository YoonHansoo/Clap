/**
 * 실제 DB와 연결해서 SQL문을 수행하여 결과를 작성하여
 * service에 전달하는 DAO의 interface
 * @author Hanhwa
 *
 */
package kr.or.ddit.clap.dao.qna;

import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

import kr.or.ddit.clap.vo.singer.SingerVO;
import kr.or.ddit.clap.vo.support.QnaVO;

public class QnaDaoImpl {
	
	private SqlMapClient smc;
	private static QnaDaoImpl dao; // Singleton 패턴
	
	private QnaDaoImpl() {
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
	
	public static QnaDaoImpl getInstance() { // Singleton 패턴
		if (dao == null) {
			dao = new QnaDaoImpl();
		}
		return dao;
	}
	
	public List<QnaVO> selectListAll() {
		List<QnaVO> list = new ArrayList<QnaVO>();
		try {

			list = smc.queryForList("qna.selectListAll");

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}
	
	

}
