package com.clap.vo.singer;

import java.io.Serializable;

public class SingerReviewVO implements Serializable{
	
	private String sing_re_no;
	private String sing_re_conntent;
	private String sing_re_indate;
	private String mem_id;
	private String sing_no;
	
	public String getSing_re_no() {
		return sing_re_no;
	}
	public void setSing_re_no(String sing_re_no) {
		this.sing_re_no = sing_re_no;
	}
	public String getSing_re_conntent() {
		return sing_re_conntent;
	}
	public void setSing_re_conntent(String sing_re_conntent) {
		this.sing_re_conntent = sing_re_conntent;
	}
	public String getSing_re_indate() {
		return sing_re_indate;
	}
	public void setSing_re_indate(String sing_re_indate) {
		this.sing_re_indate = sing_re_indate;
	}
	public String getMem_id() {
		return mem_id;
	}
	public void setMem_id(String mem_id) {
		this.mem_id = mem_id;
	}
	public String getSing_no() {
		return sing_no;
	}
	public void setSing_no(String sing_no) {
		this.sing_no = sing_no;
	}

}
