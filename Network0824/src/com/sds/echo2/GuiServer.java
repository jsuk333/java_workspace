/*
 * 1. echo 시스템
 * 		단점 : 오직 1인의 접속자만 처리할 수 있다.
 * 2. uni casting
 * 		단점 메세지를 보낼 대상이 한명이다. 혼자 대화 한다.
 * 3. multi casting
 * 		
 * 
 * */
package com.sds.echo2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class GuiServer extends JFrame implements ActionListener,Runnable{
	
	JPanel p_north;
	JTextField t_port;
	JButton bt;
	JTextArea ta;
	JScrollPane scroll;
	ServerSocket server;//접속 감지용 소켓
	Thread acceptThread;//서버 접속 감지 쓰레드, 다중 쓰레드
	Vector<ServerThread> list;//arraylist와 동일하나 동기화를 지원하므로 쓰레드에 안전하다.
	//BufferedReader buffr;
	//BufferedWriter buffw;
	public GuiServer() {
		p_north=new JPanel();
		t_port=new JTextField("8888",7);
		bt=new JButton("가동");
		ta=new JTextArea();
		scroll=new JScrollPane(ta);
		list=new Vector<ServerThread>();
		//------------------------------------------------
		ta.setBackground(Color.yellow);
		//------------------------------------------------
		p_north.add(t_port);
		p_north.add(bt);
		//------------------------------------------------
		add(p_north,BorderLayout.NORTH);
		add(scroll);
		//------------------------------------------------
		bt.addActionListener(this);
		//------------------------------------------------
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBounds(600, 100, 300, 400);
		setVisible(true);
		//------------------------------------------------
	}
	public void startServer(){
		try {
			server=new ServerSocket(Integer.parseInt(t_port.getText()));
			ta.append("서버생성 완료\n");
			//접속자 감지하기 위해 
			while(true){
				Socket client=server.accept();//무한대기에 빠진다.!!
				ta.append(client.getInetAddress().getHostAddress()+"님 접속\n");//클라이언트의 접속이 감지되면 소켓을 얻어서 보관 해놓아야 한다.
				ServerThread ct=new ServerThread(this,client);
				ct.start();
				//생성된 쓰레드를 접속자 명단에 추가
				list.add(ct);
				System.out.println("현재 몇"+list.size()+"명 접속 중");
			}
			
			
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(this, "포트번호는 숫자만 넣어주세요");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("가동\n");
	}
	public void run() {
		startServer();
		
	}
	public void actionPerformed(ActionEvent e) {
		acceptThread=new Thread(this);
		acceptThread.start();
		//버튼 비활성화
		bt.setEnabled(false);
	}
	
	public static void main(String[] args) {
		new GuiServer();

	}

	

}
