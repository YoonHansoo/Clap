package kr.or.ddit.clap.vo.support;

import java.io.Serializable;

public class MessageVO implements Serializable{
	
	private String msg_no;
	private String msg_send_date;
	private String msg_title;
	private String msg_read_tf;
	private String msg_content;
	private String mem_send_id;
	private String mem_get_id;
	
	public String getMsg_no() {
		return msg_no;
	}
	public void setMsg_no(String msg_no) {
		this.msg_no = msg_no;
	}
	public String getMsg_send_date() {
		return msg_send_date;
	}
	public void setMsg_send_date(String msg_send_date) {
		this.msg_send_date = msg_send_date;
	}
	public String getMsg_title() {
		return msg_title;
	}
	public void setMsg_title(String msg_title) {
		this.msg_title = msg_title;
	}
	public String getMsg_read_tf() {
		return msg_read_tf;
	}
	public void setMsg_read_tf(String msg_read_tf) {
		this.msg_read_tf = msg_read_tf;
	}
	public String getMsg_content() {
		return msg_content;
	}
	public void setMsg_content(String msg_content) {
		this.msg_content = msg_content;
	}
	public String getMem_send_id() {
		return mem_send_id;
	}
	public void setMem_send_id(String mem_send_id) {
		this.mem_send_id = mem_send_id;
	}
	public String getMem_get_id() {
		return mem_get_id;
	}
	public void setMem_get_id(String mem_get_id) {
		this.mem_get_id = mem_get_id;
	}
	
}
