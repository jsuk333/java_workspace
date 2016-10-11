/*키보드를 치지 않고도 서버의 메시지는 언제나 청취해야하므로 별도의 쓰레드로 이 작업을 맞기자*/
package com.sds.echo2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class ClientThread extends Thread{
	BufferedReader buffr;
	BufferedWriter buffw;
	ChatClient chatClient;
	boolean flag;//접속이 끈기면 쓰레드를 데드상태로 놓기 위한 변수
	public ClientThread(ChatClient chatClient) {
		this.chatClient=chatClient;
		flag=true;
		try {
			buffr=new BufferedReader(new InputStreamReader(chatClient.client.getInputStream()));
			buffw=new BufferedWriter(new OutputStreamWriter(chatClient.client.getOutputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void listen(){
		String msg=null;
		try {
			msg = buffr.readLine();
			
			chatClient.area.append(msg+"\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			flag=false;
			
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
	@Override
	public void run() {
		while(flag){
			listen();
		}
		System.out.println("cli죽었다");
	}
	
}
