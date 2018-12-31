package kr.or.ddit.clap.vo.genre;

import java.io.Serializable;

public class GenreDetailVO implements Serializable{
	
	private String gen_detail_no;
	private String gen_detale_name;
	private String gen_no;
	
	public String getGen_detail_no() {
		return gen_detail_no;
	}
	public void setGen_detail_no(String gen_detail_no) {
		this.gen_detail_no = gen_detail_no;
	}
	public String getGen_detale_name() {
		return gen_detale_name;
	}
	public void setGen_detale_name(String gen_detale_name) {
		this.gen_detale_name = gen_detale_name;
	}
	public String getGen_no() {
		return gen_no;
	}
	public void setGen_no(String gen_no) {
		this.gen_no = gen_no;
	}
	
}
