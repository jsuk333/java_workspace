package com.sds.dto;

import com.sds.server.ClientThread;

public class ClientThreadDTO {
	private String user_ip;
	private String user_id;
	private ClientThread cs;
	public String getUser_ip() {
		return user_ip;
	}
	public void setUser_ip(String user_ip) {
		this.user_ip = user_ip;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public ClientThread getCs() {
		return cs;
	}
	public void setCs(ClientThread cs) {
		this.cs = cs;
	}
	
	
	
}
