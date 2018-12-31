package kr.or.ddit.clap.vo.music;

import java.io.Serializable;

public class PlayListVO implements Serializable{
	
	private String play_no;
	private String play_indate;
	private String mus_no;
	private String mem_id;
	
	public String getPlay_no() {
		return play_no;
	}
	public void setPlay_no(String play_no) {
		this.play_no = play_no;
	}
	public String getPlay_indate() {
		return play_indate;
	}
	public void setPlay_indate(String play_indate) {
		this.play_indate = play_indate;
	}
	public String getMus_no() {
		return mus_no;
	}
	public void setMus_no(String mus_no) {
		this.mus_no = mus_no;
	}
	public String getMem_id() {
		return mem_id;
	}
	public void setMem_id(String mem_id) {
		this.mem_id = mem_id;
	}

}
