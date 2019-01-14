/**
 * 실제 DB와 연결해서 SQL문을 수행하여 결과를 작성하여
 * service에 전달하는 DAO의 interface
 * @author Hansoo
 *
 */

package kr.or.ddit.clap.dao.album;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ibatis.sqlmap.client.SqlMapClient;

import kr.or.ddit.clap.main.DBUtil;
import kr.or.ddit.clap.vo.album.AlbumVO; 

public class AlbumDaoImpl implements IAlbumDao {

	private SqlMapClient smc;
	private static AlbumDaoImpl dao; // Singleton 패턴

	private AlbumDaoImpl() {
		smc = DBUtil.getConnection();
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

		@Override
		public int insertAlbum(AlbumVO vo) {
			int cnt = 0;
			try {
			Object obj = smc.insert("album.insertAlbum", vo);
			if(obj == null) { //쿼리수행이 성공적으로 끝남
				cnt = 1;
			}
			} catch (SQLException e) {
				e.printStackTrace();
			} 
			return cnt;
		
		}

		@Override
		public AlbumVO albumDetailInfo(String albumNo) {
			AlbumVO aVO = new AlbumVO();
			try {
				
				aVO = (AlbumVO) smc.queryForObject("album.albumDetailInfo", albumNo);
			
			} catch (SQLException e) {
				e.printStackTrace();
			} 
			
			return aVO;
		}

		@Override
		public int selectAlbumLikeCnt(String albumNo) {
			int singerLikeCnt = 0;
			
			try {
				singerLikeCnt = (int) smc.queryForObject("album.selectAlbumLikeCnt",albumNo);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return singerLikeCnt;
		}

		@Override
		public int updateAlbumInfo(AlbumVO vo) {
			int cnt = 0;
			try {
			cnt = smc.update("album.updateAlbumInfo",vo);
			} catch (SQLException e) {

				e.printStackTrace();
			} 
			return cnt;
			
		}

		@Override
		public int deleteAlbum(String albumNo) {
			
			int cnt = 0;
			try {
				cnt = smc.delete("album.deleteAlbum",albumNo);
				
			} catch (SQLException e) {
				e.printStackTrace();
			} 
			return cnt;
		}

		@Override
		public List<Map> newAlbumSelect() {
			
			List<Map> list = new ArrayList<Map>();
			try {

				list = smc.queryForList("album.newalbumselect");

			} catch (SQLException e) {
				e.printStackTrace();
			}

			return list;
			
		}

}
