/**
 * 실제 DB와 연결해서 SQL문을 수행하여 결과를 작성하여
 * service에 전달하는 DAO의 interface
 * @author Hanhwa
 *
 */
package kr.or.ddit.clap.dao.eventboard;

import java.io.IOException;
import java.io.Reader;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

public class EventBoardDaoImpl implements IEventBoardDao {

	private SqlMapClient smc;
	private static EventBoardDaoImpl dao; // Singleton 패턴

	private EventBoardDaoImpl() {
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

	public static EventBoardDaoImpl getInstance() {
		if (dao == null) {
			dao = new EventBoardDaoImpl();
		}
		return dao;

	}

}