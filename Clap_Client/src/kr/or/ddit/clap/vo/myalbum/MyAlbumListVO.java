package kr.or.ddit.clap.vo.myalbum;

import java.io.Serializable;

public class MyAlbumListVO implements Serializable{
	
	private String myalb_no;
	private String mus_no;
	private String myalb_list_indate;
	
	public String getMyalb_no() {
		return myalb_no;
	}
	public void setMyalb_no(String myalb_no) {
		this.myalb_no = myalb_no;
	}
	public String getMus_no() {
		return mus_no;
	}
	public void setMus_no(String mus_no) {
		this.mus_no = mus_no;
	}
	public String getMyalb_list_indate() {
		return myalb_list_indate;
	}
	public void setMyalb_list_indate(String myalb_list_indate) {
		this.myalb_list_indate = myalb_list_indate;
	}

}
