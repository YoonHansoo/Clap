/**
 * 실제 DB와 연결해서 SQL문을 수행하여 결과를 작성하여
 * service에 전달하는 DAO의 interface
 * @author Hansoo
 *
 */

package kr.or.ddit.clap.dao.playlist;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ibatis.sqlmap.client.SqlMapClient;

import kr.or.ddit.clap.main.DBUtil;
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
		smc = DBUtil.getConnection();
	}

	public static PlayListDaoImpl getInstance() { // Singleton 패턴
		if (dao == null) {
			dao = new PlayListDaoImpl();
		}
		return dao;
	}

	public static void main(String[] args) {
		/*
		
		System.out.println(list.size());
		
		PlayListVO vo = new PlayListVO();
		vo.setMem_id("user1");
		vo.setMus_no("4");
		
		int cnt = new PlayListDaoImpl().PlayListDelete(vo);
		System.out.println(cnt);*/
		List<PlayListVO> list = new PlayListDaoImpl().playlistSelect("user1");
		
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

}
