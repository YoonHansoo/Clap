package com.clap.vo.music;

import java.io.Serializable;

public class MusicLikeVO implements Serializable{
	
	private String mem_id;
	private String mus_no;
	private String mus_like_date;
	
	public String getMem_id() {
		return mem_id;
	}
	public void setMem_id(String mem_id) {
		this.mem_id = mem_id;
	}
	public String getMus_no() {
		return mus_no;
	}
	public void setMus_no(String mus_no) {
		this.mus_no = mus_no;
	}
	public String getMus_like_date() {
		return mus_like_date;
	}
	public void setMus_like_date(String mus_like_date) {
		this.mus_like_date = mus_like_date;
	}
	
}
