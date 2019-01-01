package kr.or.ddit.clap.dao.mypage;

import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

import kr.or.ddit.clap.vo.music.MusicHistoryVO;

public class MypageDaoImpl {

	private SqlMapClient smc;
	private static MypageDaoImpl dao; // Singleton 패턴

	private MypageDaoImpl() {
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

	public static MypageDaoImpl getInstance() { // Singleton 패턴
		if (dao == null) {
			dao = new MypageDaoImpl();
		}
		return dao;
	}

	public List<MusicHistoryVO> selectListAll() {
		List<MusicHistoryVO> list = new ArrayList<MusicHistoryVO>();
		try {

			list = smc.queryForList("mypage.selectListAll");

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}

	
}
