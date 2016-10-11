package com.sds.echo;

import java.awt.BorderLayout;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class EchoServer {
	JTextArea ta;
	JTextField tf;
	JButton bt;
	JPanel p;
	JScrollPane scroll;
	//---------------------------
	ServerSocket server;
	BufferedReader buffr;
	BufferedWriter buffw;
	public EchoServer() {
		
		try {
			server=new ServerSocket(8888);
			System.out.println("서버 생성");
			Socket client=server.accept();//접속자 감지(전화받기)
			//소켓이란?네트워크와 전방의 기능을 추상화 한 객체
			//따라서 개발자는 네트웍지식이 없ㄷ어도 되며, 어떠한 플랫폼에서도 가능
			String ip=client.getInetAddress().getHostAddress();
			System.out.println(ip+"님 접속");
			buffr=new BufferedReader(new InputStreamReader(client.getInputStream()));
			buffw=new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
			String data=null;
			while(true){//입력과 출력을 무한 루프로 설정
				data=buffr.readLine();
				System.out.println(data);
				buffw.write(data+"\n");
				buffw.flush();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(buffr!=null){
				try {
					buffr.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(buffw!=null){
				try {
					buffw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		/*
		ta=new JTextArea();
		tf=new JTextField(15);
		bt=new JButton("전송");
		p=new JPanel();
		scroll=new JScrollPane(ta);
		
		
		
		p.add(tf);
		p.add(bt);
		
		add(scroll,BorderLayout.CENTER);
		add(p,BorderLayout.SOUTH);
		
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(300, 400);
		setVisible(true);*/
	}
	

	public static void main(String[] args) {
		new EchoServer();
	}

}
