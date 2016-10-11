/*Ű���带 ġ�� �ʰ� ������ �޽����� ������ û���ؾ��ϹǷ� ������ ������� �� �۾��� �±���*/
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
	boolean flag;//������ ����� �����带 ������·� ���� ���� ����
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
		System.out.println("cli�׾���");
	}
	
}
