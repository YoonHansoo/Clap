package kr.or.ddit.clap.dao.mypage;

import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

import kr.or.ddit.clap.vo.member.MemberVO;

public class MypageDaoImpl implements IMypageDao{

	private SqlMapClient smc;
	private static MypageDaoImpl dao; // Singleton 패턴

	private MypageDaoImpl(){
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

	public MemberVO select(MemberVO vo) {
		MemberVO check = null;
		try {
			check = (MemberVO) smc.queryForObject("mypage.select" ,vo);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return check;
	}


}
