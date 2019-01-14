/**
 * 실제 DB와 연결해서 SQL문을 수행하여 결과를 작성하여
 * service에 전달하는 DAO의 interface
 * @author Hansoo
 *
 */

package kr.or.ddit.clap.dao.myalbum;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ibatis.sqlmap.client.SqlMapClient;

import kr.or.ddit.clap.main.DBUtil;
import kr.or.ddit.clap.vo.myalbum.MyAlbumVO;

/**
 * 
 * @author 진민규
 *
 */
public class MyAlbumDaoImpl implements IMyAlbumDao{

	private SqlMapClient smc;
	private static MyAlbumDaoImpl dao; // Singleton 패턴

	private MyAlbumDaoImpl() {
		smc = DBUtil.getConnection();
	}

	public static MyAlbumDaoImpl getInstance() { // Singleton 패턴
		if (dao == null) {
			dao = new MyAlbumDaoImpl();
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
	
	@Override
	public int myAlbumInsert(Map<String, String> myAlbum) {
		int cnt = 0;
		try {
	
			cnt = smc.update("myalbum.myalbuminsert",myAlbum);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cnt;
	}
	
	public static void main(String[] args) {
		Map<String, String> myAlbum = new HashMap<>();
		myAlbum.put("name", "test2");
		myAlbum.put("id", "user1");
		int cnt = new MyAlbumDaoImpl().myAlbumInsert(myAlbum);
		System.out.println(cnt);
		
	}

	@Override
	public int deleteMyalb(MyAlbumVO vo) {
		int cnt = 0;
		try {
			cnt = smc.delete("myalbum.deleteMyalb",vo);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cnt;
	}

	@Override
	public int updateMyalb(MyAlbumVO vo) {
		int cnt = 0;
		try {
			cnt = smc.update("myalbum.updateMyalb", vo);
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return cnt;
	}

	

}
