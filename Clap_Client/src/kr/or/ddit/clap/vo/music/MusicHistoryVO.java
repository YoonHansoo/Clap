package kr.or.ddit.clap.vo.music;

import java.io.Serializable;

public class MusicHistoryVO implements Serializable{
	
	private String histo_no;
	private String histo_indate;
	private String mus_no;
	private String mem_id;
	private String name;
	
	public String getHisto_no() {
		return histo_no;
	}
	public void setHisto_no(String histo_no) {
		this.histo_no = histo_no;
	}
	public String getHisto_indate() {
		return histo_indate;
	}
	public void setHisto_indate(String histo_indate) {
		this.histo_indate = histo_indate;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}
