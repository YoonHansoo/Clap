/**
 * 실제 DB와 연결해서 SQL문을 수행하여 결과를 작성하여
 * service에 전달하는 DAO의 interface
 * @author Hansoo
 *
 */

package kr.or.ddit.clap.dao.music;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ibatis.sqlmap.client.SqlMapClient;

import kr.or.ddit.clap.main.DBUtil;
import kr.or.ddit.clap.vo.genre.GenreDetailVO;
import kr.or.ddit.clap.vo.genre.GenreVO;
import kr.or.ddit.clap.vo.music.MusicVO;

public class MusicDaoImpl implements IMusicDao {

	private SqlMapClient smc;
	private static MusicDaoImpl dao; // Singleton 패턴

	private MusicDaoImpl() {
		smc = DBUtil.getConnection();
	}

	public static MusicDaoImpl getInstance() { // Singleton 패턴
		if (dao == null) {
			dao = new MusicDaoImpl();
		}
		return dao;
	}

	// 노래목록 조회를 위한 쿼리문
	public List<MusicVO> selectListAll() {
		List<MusicVO> list = new ArrayList<MusicVO>();
		try {

			list = smc.queryForList("music.selectListAll");

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}

	// 검색조건에 맞게 검색하는 쿼리문
	@Override
	public List<MusicVO> searchList(MusicVO vo) {
		List<MusicVO> list = new ArrayList<MusicVO>();
		try {

			list = smc.queryForList("music.searchList", vo);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}

	@Override
	public int insertMusic(MusicVO vo) {
		int cnt = 0;
		try {
			Object obj = smc.insert("music.insertMusic", vo);
			if (obj == null) { // 쿼리수행이 성공적으로 끝남
				cnt = 1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cnt;

	}

	

	@Override
	public int selectMusicLikeCnt(String MusicNo) {
		int musicLikeCnt = 0;

		try {
			musicLikeCnt = (int) smc.queryForObject("music.selectMusicLikeCnt", MusicNo);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return musicLikeCnt;
	}

	@Override
	public int updateMusicInfo(MusicVO vo) {
		int cnt = 0;
		try {
			cnt = smc.update("music.updateMusicInfo", vo);
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return cnt;

	}

	@Override
	public int deleteMusic(String MusicNo) {

		int cnt = 0;
		try {
			cnt = smc.delete("music.deleteMusic", MusicNo);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cnt;
	}

	@Override
	public List<GenreVO> showGenre() {

		List<GenreVO> list = new ArrayList<GenreVO>();
		try {

			list = smc.queryForList("music.showGenre");

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}


	

	@Override
	public String selectGenreNO(String genreName) {

		String genreNo = null;
		try {

			genreNo = (String) smc.queryForObject("music.selectGenreNO", genreName);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return genreNo;

	}

	@Override
	public List<GenreDetailVO> showGenreDetail(String genreNO) {
		List<GenreDetailVO> list = new ArrayList<GenreDetailVO>();
		try {

			list = smc.queryForList("music.showGenreDetail",genreNO);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}

	@Override
	public String selectGenreDetailNO(String genreName) {
		String genreDetailNo = null;
		try {

			genreDetailNo = (String) smc.queryForObject("music.selectGenreDetailNO", genreName);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return genreDetailNo;
		
		
		
	}

	@Override
	public MusicVO selectMusicDetailInfo(String musicNo) {
		MusicVO aVO = new MusicVO();
		try {

			aVO = (MusicVO) smc.queryForObject("music.selectMusicDetailInfo", musicNo);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return aVO;
	}

	@Override
	public List<Map> newMusicSelete(String genre_no) {
		List<Map> list = new ArrayList<Map>();
		try {

			list = smc.queryForList("music.newmusicselete", genre_no);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}

	@Override
	public List<Map> selectSinger(String sing_no) {
		List<Map> list = new ArrayList<Map>();
		try {

			list = smc.queryForList("music.selectSinger", sing_no);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}

	@Override
	public List<String> albumMusNoSelect(String alb_no) {
		List<String> list = new ArrayList<String>();
		try {

			list = smc.queryForList("music.albummusnoselect", alb_no);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}

}
