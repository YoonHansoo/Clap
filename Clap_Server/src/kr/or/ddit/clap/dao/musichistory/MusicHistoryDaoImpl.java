/**
 * 실제 DB와 연결해서 SQL문을 수행하여 결과를 작성하여
 * service에 전달하는 DAO의 interface
 * @author Hansoo
 *
 */

package kr.or.ddit.clap.dao.musichistory;

import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;
import kr.or.ddit.clap.vo.music.MusicHistoryVO;

/**
 * 
 * @author 진민규
 *
 */
public class MusicHistoryDaoImpl implements IMusicHistoryDao{

	private SqlMapClient smc;
	private static MusicHistoryDaoImpl dao; // Singleton 패턴

	private MusicHistoryDaoImpl() {
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

	public static MusicHistoryDaoImpl getInstance() { // Singleton 패턴
		if (dao == null) {
			dao = new MusicHistoryDaoImpl();
		}
		return dao;
	}

	public List<MusicHistoryVO> selectListAll() {
		List<MusicHistoryVO> list = new ArrayList<MusicHistoryVO>();
		try {

			list = smc.queryForList("singer.selectListAll");

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}

}
