/**
 * 실제 DB와 연결해서 SQL문을 수행하여 결과를 작성하여
 * service에 전달하는 DAO의 interface
 * @author Hansoo
 *
 */

package kr.or.ddit.clap.dao.music;

import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

import kr.or.ddit.clap.vo.music.MusicVO; 

public class MusicDaoImpl implements IMusicDao {

	private SqlMapClient smc;
	private static MusicDaoImpl dao; // Singleton 패턴

	private MusicDaoImpl() {
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

	public static MusicDaoImpl getInstance() { // Singleton 패턴
		if (dao == null) {
			dao = new MusicDaoImpl();
		}
		return dao;
	}


	//가수목록 조회를 위한 쿼리문
	public List<MusicVO> selectListAll() {
		List<MusicVO> list = new ArrayList<MusicVO>();
		try {

			list = smc.queryForList("Music.selectListAll");

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}
	
	//검색조건에 맞게 검색하는 쿼리문
		@Override
		public List<MusicVO> searchList(MusicVO vo) {
			List<MusicVO> list = new ArrayList<MusicVO>();
			try {
				
				list = smc.queryForList("Music.searchList",vo);
			
			} catch (SQLException e) {
				e.printStackTrace();
			} 
			
			return list;
		}

		@Override
		public int insertMusic(MusicVO vo) {
			int cnt = 0;
			try {
			Object obj = smc.insert("Music.insertMusic", vo);
			if(obj == null) { //쿼리수행이 성공적으로 끝남
				cnt = 1;
			}
			} catch (SQLException e) {
				e.printStackTrace();
			} 
			return cnt;
		
		}

		@Override
		public MusicVO musicDetailInfo(String MusicNo) {
			MusicVO aVO = new MusicVO();
			try {
				
				aVO = (MusicVO) smc.queryForObject("Music.MusicDetailInfo", MusicNo);
			
			} catch (SQLException e) {
				e.printStackTrace();
			} 
			
			return aVO;
		}

		@Override
		public int selectMusicLikeCnt(String MusicNo) {
			int singerLikeCnt = 0;
			
			try {
				singerLikeCnt = (int) smc.queryForObject("Music.selectMusicLikeCnt",MusicNo);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return singerLikeCnt;
		}

		@Override
		public int updateMusicInfo(MusicVO vo) {
			int cnt = 0;
			try {
			cnt = smc.update("Music.updateMusicInfo",vo);
			} catch (SQLException e) {

				e.printStackTrace();
			} 
			return cnt;
			
		}

		@Override
		public int deleteMusic(String MusicNo) {
			
			int cnt = 0;
			try {
				cnt = smc.delete("Music.deleteMusic",MusicNo);
				
			} catch (SQLException e) {
				e.printStackTrace();
			} 
			return cnt;
		}

}
