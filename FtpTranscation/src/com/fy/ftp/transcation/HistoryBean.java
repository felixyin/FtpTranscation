package com.fy.ftp.transcation;


public class HistoryBean {

	private String type;
	private String desc;
	private String from;
	private String to;

	public HistoryBean() {
		super();
	}

	public HistoryBean(String type, String from, String to) {
		super();
		this.type = type;
		this.from = from;
		this.to = to;
	}

	public HistoryBean(String type, String desc, String from, String to) {
		super();
		this.type = type;
		this.desc = desc;
		this.from = from;
		this.to = to;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	
}
