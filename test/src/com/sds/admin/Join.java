package com.sds.admin;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.json.simple.parser.ParseException;

import com.sds.client.ControllUI;

public class Join extends JFrame{
	Socket client;
	public String ip;
	int port = 8888;
	BufferedReader buffr;
	BufferedWriter buffw;
	public JoinThread gt;
	boolean flag = false;
	ControllUI guestUI;
	boolean isHost;
	public Join(String ip, boolean isHost) {
		this.ip = ip;
		this.isHost = isHost;
		
		if(isHost){
			guestUI=new ControllUI(true,this);
			connect();
		}else{
			guestUI=new ControllUI(false,this);
			connect();
		}
		//각각 조인하는 거지 따로따로 하는 것이 아니다.
		//그러므로 조인은 고유의 값을 가질수 있다.
	}

	public void connect() {
		try {
			client = new Socket(ip, port);
			System.out.println(client);
			System.out.println(isHost);
			gt=new JoinThread(this,guestUI);
			gt.start();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void joinRequest(){
		try {
			gt.requestHost();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void joinCreateRoom()
	{
		//gt.requestCreateRoom();
	}

}
