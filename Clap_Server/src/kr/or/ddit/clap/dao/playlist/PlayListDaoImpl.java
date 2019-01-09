/**
 * 실제 DB와 연결해서 SQL문을 수행하여 결과를 작성하여
 * service에 전달하는 DAO의 interface
 * @author Hansoo
 *
 */

package kr.or.ddit.clap.dao.playlist;

import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;
import kr.or.ddit.clap.vo.music.PlayListVO;

/**
 * 
 * @author 진민규
 *
 */
public class PlayListDaoImpl implements IPlayListDao{

	private SqlMapClient smc;
	private static PlayListDaoImpl dao; // Singleton 패턴

	private PlayListDaoImpl() {
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

	public static PlayListDaoImpl getInstance() { // Singleton 패턴
		if (dao == null) {
			dao = new PlayListDaoImpl();
		}
		return dao;
	}

	public static void main(String[] args) {
		/*
		List<PlayListVO> list = new PlayListDaoImpl().PlayListSelect("user1");
		System.out.println(list.size());
		
		PlayListVO vo = new PlayListVO();
		vo.setMem_id("user1");
		vo.setMus_no("4");
		
		int cnt = new PlayListDaoImpl().PlayListDelete(vo);
		System.out.println(cnt);*/
		
		List<Map> list = new PlayListDaoImpl().infoSelect("3");
		
	}

	@Override
	public List<PlayListVO> playlistSelect(String mem_id) {
		List<PlayListVO> list = new ArrayList<PlayListVO>();
		try {

			list = smc.queryForList("playlist.playlistselect", mem_id);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}

	@Override
	public int playlistInsert(PlayListVO vo) {
		int cnt = 0;
		try {
	
			cnt = smc.update("playlist.playlistinsert", vo);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cnt;
	}

	@Override
	public int playlistDelete(PlayListVO vo) {
		int cnt = 0;
		try {
	
			cnt = smc.delete("playlist.playlistdelete", vo);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cnt;
	}

	@Override
	public List<Map> infoSelect(String mus_no) {
		List<Map> list = new ArrayList<Map>();
		try {

			list = smc.queryForList("playlist.infoselect", mus_no);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}

}
