/**
 * 실제 DB와 연결해서 SQL문을 수행하여 결과를 작성하여
 * service에 전달하는 DAO의 interface
 * @author Hansoo
 *
 */

package kr.or.ddit.clap.dao.myalbum;

import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;
import kr.or.ddit.clap.vo.music.MusicHistoryVO;
import kr.or.ddit.clap.vo.myalbum.MyAlbumVO;
import kr.or.ddit.clap.vo.singer.SingerVO;

/**
 * 
 * @author 진민규
 *
 */
public class MyAlbumyDaoImpl implements IMyAlbumyDao{

	private SqlMapClient smc;
	private static MyAlbumyDaoImpl dao; // Singleton 패턴

	private MyAlbumyDaoImpl() {
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

	public static MyAlbumyDaoImpl getInstance() { // Singleton 패턴
		if (dao == null) {
			dao = new MyAlbumyDaoImpl();
		}
		return dao;
	}

	@Override
	public List<MyAlbumVO> myAlbumSelect(String id) {
		List<MyAlbumVO> list = new ArrayList<MyAlbumVO>();
		try {
			
			list = smc.queryForList("myalbum.myalbumselect",id);
		
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
		return list;
	}
	
	public static void main(String[] args) {
		new MyAlbumyDaoImpl().myAlbumSelect("user1");
		
	}

}
