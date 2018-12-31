package kr.or.ddit.clap.vo.music;

import java.io.Serializable;

public class MusicReviewVO implements Serializable{
	
	private String mus_re_no;
	private String mus_re_content;
	private String mus_re_indate;
	private String mem_id;
	private String mus_no;
	
	public String getMus_re_no() {
		return mus_re_no;
	}
	public void setMus_re_no(String mus_re_no) {
		this.mus_re_no = mus_re_no;
	}
	public String getMus_re_content() {
		return mus_re_content;
	}
	public void setMus_re_content(String mus_re_content) {
		this.mus_re_content = mus_re_content;
	}
	public String getMus_re_indate() {
		return mus_re_indate;
	}
	public void setMus_re_indate(String mus_re_indate) {
		this.mus_re_indate = mus_re_indate;
	}
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
	
	
}
