package kr.or.ddit.clap.vo.myalbum;

import java.io.Serializable;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

public class MyAlbumVO extends RecursiveTreeObject<MyAlbumVO> implements Serializable{
	
	private String myalb_no;
	private String myalb_name;
	private String myalb_indate;
	private String mem_id;
	private String mus_count;
	
	public String getMyalb_no() {
		return myalb_no;
	}
	public void setMyalb_no(String myalb_no) {
		this.myalb_no = myalb_no;
	}
	public String getMyalb_name() {
		return myalb_name;
	}
	public void setMyalb_name(String myalb_name) {
		this.myalb_name = myalb_name;
	}
	public String getMyalb_indate() {
		return myalb_indate;
	}
	public void setMyalb_indate(String myalb_indate) {
		this.myalb_indate = myalb_indate;
	}
	public String getMem_id() {
		return mem_id;
	}
	public void setMem_id(String mem_id) {
		this.mem_id = mem_id;
	}
	public String getMus_count() {
		return mus_count;
	}
	public void setMus_count(String mus_count) {
		if (Integer.parseInt(mus_count) != 0) {
			this.myalb_name = this.myalb_name + ("(" + mus_count + ")");
		}
		
		this.mus_count = mus_count;
	}
	

}
