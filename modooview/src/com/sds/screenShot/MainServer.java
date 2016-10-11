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
	//��ũ������ ��Ʈ
	int port = 8888;
	Vector<ServerThread> list=new Vector<ServerThread>();
	public MainServer() {
		acceptThread = new Thread(this);
		acceptThread.start();
	}


	public void run() {
		// �����忡�� ������ �����Ѵ�
		startServer();
	}

	public void startServer() {
		try {
			server = new ServerSocket(port);	
			System.out.println("���������Ϸ�");

			while (true) {
				Socket client = server.accept();
				// client�� �����Ҷ� ���� ��� ����
				// client���� ������ ���θ���� ������ �����忡�� ����ϵ��� �Ѵ�
				ServerThread st=new ServerThread(client,this);
				st.start();
				list.add(st);
				System.out.println("�����ڰ���");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
