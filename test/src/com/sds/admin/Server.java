package com.sds.admin;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
public class Server extends JFrame implements Runnable{

	ServerSocket server;
	Thread acceptThead;
	Vector<ServerThread> serverList=new Vector<ServerThread>();
	InetAddress inetAddress;
	ArrayList<Socket> socketList=new ArrayList<Socket>();
	String ip;
	public Server(String ip) {
		this.ip = ip;
		System.out.println("서버생성");
		
		acceptThead=new Thread(this);					
		acceptThead.start();
	}
	
	public void startServer(){
		try {
			server=new ServerSocket(8888);
			Join join =  new Join(ip, true);	
			while (true) {
				Socket client = server.accept();
				socketList.add(client);
				ServerThread st = new ServerThread(client, this);
				st.start();
				serverList.add(st);
				String ip=client.getInetAddress().getHostAddress();
			}			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}
	@Override
	public void run() {
		startServer();
	}	

}
