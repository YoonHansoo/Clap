package com.clap.vo.support;

import java.io.Serializable;

public class EventBoardVO implements Serializable{
	
	private String event_no;
	private String event_title;
	private String event_sdate;
	private String event_edate;
	private String event_view_cnt;
	private String event_image;
	private String event_content;
	private String mem_id;
	
	public String getEvent_no() {
		return event_no;
	}
	public void setEvent_no(String event_no) {
		this.event_no = event_no;
	}
	public String getEvent_title() {
		return event_title;
	}
	public void setEvent_title(String event_title) {
		this.event_title = event_title;
	}
	public String getEvent_sdate() {
		return event_sdate;
	}
	public void setEvent_sdate(String event_sdate) {
		this.event_sdate = event_sdate;
	}
	public String getEvent_edate() {
		return event_edate;
	}
	public void setEvent_edate(String event_edate) {
		this.event_edate = event_edate;
	}
	public String getEvent_view_cnt() {
		return event_view_cnt;
	}
	public void setEvent_view_cnt(String event_view_cnt) {
		this.event_view_cnt = event_view_cnt;
	}
	public String getEvent_image() {
		return event_image;
	}
	public void setEvent_image(String event_image) {
		this.event_image = event_image;
	}
	public String getEvent_content() {
		return event_content;
	}
	public void setEvent_content(String event_content) {
		this.event_content = event_content;
	}
	public String getMem_id() {
		return mem_id;
	}
	public void setMem_id(String mem_id) {
		this.mem_id = mem_id;
	}
	
}
