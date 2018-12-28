package com.clap.vo.album;

import java.io.Serializable;

public class AlbumReviewVO implements Serializable{
	
	private String alb_re_no;
	private String alb_re_content;
	private String alb_re_indate;
	private String mem_id;
	private String alb_no;
	
	public String getAlb_re_no() {
		return alb_re_no;
	}
	public void setAlb_re_no(String alb_re_no) {
		this.alb_re_no = alb_re_no;
	}
	public String getAlb_re_content() {
		return alb_re_content;
	}
	public void setAlb_re_content(String alb_re_content) {
		this.alb_re_content = alb_re_content;
	}
	public String getAlb_re_indate() {
		return alb_re_indate;
	}
	public void setAlb_re_indate(String alb_re_indate) {
		this.alb_re_indate = alb_re_indate;
	}
	public String getMem_id() {
		return mem_id;
	}
	public void setMem_id(String mem_id) {
		this.mem_id = mem_id;
	}
	public String getAlb_no() {
		return alb_no;
	}
	public void setAlb_no(String alb_no) {
		this.alb_no = alb_no;
	}
	
}
