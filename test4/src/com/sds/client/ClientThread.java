package com.sds.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ClientThread extends Thread{

	BufferedReader buffr;
	BufferedWriter buffw;
	StringBuffer sb = new StringBuffer();
	boolean flag;
	ClientMain clientMain;
	
	public ClientThread(ClientMain	clientMain) {
		flag=true;
		this.clientMain=clientMain;
		try {
			buffr = new BufferedReader(new InputStreamReader(clientMain.socket.getInputStream()));
			buffw = new BufferedWriter(new OutputStreamWriter(clientMain.socket.getOutputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sendMsg(StringBuffer sb) {
		try {
			buffw.write(sb.toString()+"\n");
			buffw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void listen() {
		try {
			String response = buffr.readLine();
			clientMain.result(response);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		while(flag){
			listen();
		}
	}
}
