package com.clap.vo.ticket;

import java.io.Serializable;

public class TicketBuyListVO implements Serializable{
	
	private String ticket_buy_no;
	private String ticket_buydate;
	private String card_account_no;
	private String ticket_buy_type;
	private String card_bank_name;
	private String refund_date;
	private String refund_tf;
	private String account_holder;
	private String ticket_no;
	private String mem_id;
	
	public String getTicket_buy_no() {
		return ticket_buy_no;
	}
	public void setTicket_buy_no(String ticket_buy_no) {
		this.ticket_buy_no = ticket_buy_no;
	}
	public String getTicket_buydate() {
		return ticket_buydate;
	}
	public void setTicket_buydate(String ticket_buydate) {
		this.ticket_buydate = ticket_buydate;
	}
	public String getCard_account_no() {
		return card_account_no;
	}
	public void setCard_account_no(String card_account_no) {
		this.card_account_no = card_account_no;
	}
	public String getTicket_buy_type() {
		return ticket_buy_type;
	}
	public void setTicket_buy_type(String ticket_buy_type) {
		this.ticket_buy_type = ticket_buy_type;
	}
	public String getCard_bank_name() {
		return card_bank_name;
	}
	public void setCard_bank_name(String card_bank_name) {
		this.card_bank_name = card_bank_name;
	}
	public String getRefund_date() {
		return refund_date;
	}
	public void setRefund_date(String refund_date) {
		this.refund_date = refund_date;
	}
	public String getRefund_tf() {
		return refund_tf;
	}
	public void setRefund_tf(String refund_tf) {
		this.refund_tf = refund_tf;
	}
	public String getAccount_holder() {
		return account_holder;
	}
	public void setAccount_holder(String account_holder) {
		this.account_holder = account_holder;
	}
	public String getTicket_no() {
		return ticket_no;
	}
	public void setTicket_no(String ticket_no) {
		this.ticket_no = ticket_no;
	}
	public String getMem_id() {
		return mem_id;
	}
	public void setMem_id(String mem_id) {
		this.mem_id = mem_id;
	}

}
