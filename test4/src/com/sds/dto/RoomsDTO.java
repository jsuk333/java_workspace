package com.sds.dto;

public class RoomsDTO {
	private int num;
	private String title;
	private int isPw;
	private String pw;
	private int max_join;
	private int cur_join;
	private int isUsing;
	private String host_id;
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getIsPw() {
		return isPw;
	}
	public void setIsPw(int isPw) {
		this.isPw = isPw;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public int getMax_join() {
		return max_join;
	}
	public void setMax_join(int max_join) {
		this.max_join = max_join;
	}
	public int getCur_join() {
		return cur_join;
	}
	public void setCur_join(int cur_join) {
		this.cur_join = cur_join;
	}
	public int getIsUsing() {
		return isUsing;
	}
	public void setIsUsing(int isUsing) {
		this.isUsing = isUsing;
	}
	public String getHost_id() {
		return host_id;
	}
	public void setHost_id(String host_id) {
		this.host_id = host_id;
	}
	
}
