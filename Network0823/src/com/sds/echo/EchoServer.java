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
			System.out.println("���� ����");
			Socket client=server.accept();//������ ����(��ȭ�ޱ�)
			//�����̶�?��Ʈ��ũ�� ������ ����� �߻�ȭ �� ��ü
			//���� �����ڴ� ��Ʈ�������� ����� �Ǹ�, ��� �÷��������� ����
			String ip=client.getInetAddress().getHostAddress();
			System.out.println(ip+"�� ����");
			buffr=new BufferedReader(new InputStreamReader(client.getInputStream()));
			buffw=new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
			String data=null;
			while(true){//�Է°� ����� ���� ������ ����
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
		bt=new JButton("����");
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
