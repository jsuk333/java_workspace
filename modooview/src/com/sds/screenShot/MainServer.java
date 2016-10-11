package com.sds.screenShot;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class MainServer implements Runnable {


	Thread acceptThread;
	ServerSocket server;
	//스크린생성 포트
	int port = 8888;
	Vector<ServerThread> list=new Vector<ServerThread>();
	public MainServer() {
		acceptThread = new Thread(this);
		acceptThread.start();
	}


	public void run() {
		// 쓰레드에서 서버를 가동한다
		startServer();
	}

	public void startServer() {
		try {
			server = new ServerSocket(port);	
			System.out.println("서버생성완료");

			while (true) {
				Socket client = server.accept();
				// client가 접속할때 까지 대기 상태
				// client별로 소켓을 새로만들고 각자의 쓰레드에서 통신하도록 한다
				ServerThread st=new ServerThread(client,this);
				st.start();
				list.add(st);
				System.out.println("접속자감지");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
