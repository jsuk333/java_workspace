package com.sds.echo2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class ServerThread extends Thread{
	Socket client;
	GuiServer server;
	BufferedReader buffr;
	BufferedWriter buffw;
	boolean flag;
	public ServerThread(GuiServer server,Socket client) {
		this.client=client;
		this.server=server;
		flag=true;
		try {
			buffr=new BufferedReader(new InputStreamReader(client.getInputStream()));
			buffw=new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@Override
	public void run() {
			listen();
		System.out.println("st죽었다.");
	}
	
	public void listen(){
		while(flag){
			try {
				String msg=buffr.readLine();
				server.ta.append(msg+"\n");
				for(int i=0;i<server.list.size();i++){
					server.list.get(i).sendMsg(msg);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				//유저가 빠져나간 처리를 해주어야 한다.
				flag=false;
				server.list.remove(this);
			}
		}
	}
	
	public void sendMsg(String msg){
		try {
			buffw.write(msg+"\n");
			buffw.flush();
		
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
