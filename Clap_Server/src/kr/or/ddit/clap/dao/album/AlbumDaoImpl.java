/**
 * 실제 DB와 연결해서 SQL문을 수행하여 결과를 작성하여
 * service에 전달하는 DAO의 interface
 * @author Hansoo
 *
 */

package kr.or.ddit.clap.dao.album;

import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

import kr.or.ddit.clap.vo.album.AlbumVO; 

public class AlbumDaoImpl implements IAlbumDao {

	private SqlMapClient smc;
	private static AlbumDaoImpl dao; // Singleton 패턴

	private AlbumDaoImpl() {
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

	public static AlbumDaoImpl getInstance() { // Singleton 패턴
		if (dao == null) {
			dao = new AlbumDaoImpl();
		}
		return dao;
	}


	//가수목록 조회를 위한 쿼리문
	public List<AlbumVO> selectListAll() {
		List<AlbumVO> list = new ArrayList<AlbumVO>();
		try {

			list = smc.queryForList("album.selectListAll");

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}
	
	//검색조건에 맞게 검색하는 쿼리문
		@Override
		public List<AlbumVO> searchList(AlbumVO vo) {
			List<AlbumVO> list = new ArrayList<AlbumVO>();
			try {
				
				list = smc.queryForList("album.searchList",vo);
			
			} catch (SQLException e) {
				e.printStackTrace();
			} 
			
			return list;
		}

}
