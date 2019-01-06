/**
 * 실제 DB와 연결해서 SQL문을 수행하여 결과를 작성하여
 * service에 전달하는 DAO의 interface
 * @author Hansoo
 *
 */

package kr.or.ddit.clap.dao.myalbumlist;

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
public class MyAlbumListDaoImpl implements IMyAlbumListDao{

	private SqlMapClient smc;
	private static MyAlbumListDaoImpl dao; // Singleton 패턴

	private MyAlbumListDaoImpl() {
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

	public static MyAlbumListDaoImpl getInstance() { // Singleton 패턴
		if (dao == null) {
			dao = new MyAlbumListDaoImpl();
		}
		return dao;
	}

	@Override
	public int myAlbumListInsert(Map<String, String> myAlbumList) {
		int cnt = 0;
		try {
	
			cnt = smc.update("myalbumlist.myalbumlistinsert",myAlbumList);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cnt;
	}
	
	public static void main(String[] args) {
		Map<String, String> myAlbumList = new HashMap<>();
		myAlbumList.put("myalb_no", "3");
		myAlbumList.put("mus_no", "5");
		int cnt = new MyAlbumListDaoImpl().myAlbumListInsert(myAlbumList);
		System.out.println(cnt);
		
	}

	

}
