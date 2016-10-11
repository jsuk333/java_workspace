/*
 * 1. echo �ý���
 * 		���� : ���� 1���� �����ڸ� ó���� �� �ִ�.
 * 2. uni casting
 * 		���� �޼����� ���� ����� �Ѹ��̴�. ȥ�� ��ȭ �Ѵ�.
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
	ServerSocket server;//���� ������ ����
	Thread acceptThread;//���� ���� ���� ������, ���� ������
	Vector<ServerThread> list;//arraylist�� �����ϳ� ����ȭ�� �����ϹǷ� �����忡 �����ϴ�.
	//BufferedReader buffr;
	//BufferedWriter buffw;
	public GuiServer() {
		p_north=new JPanel();
		t_port=new JTextField("8888",7);
		bt=new JButton("����");
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
			ta.append("�������� �Ϸ�\n");
			//������ �����ϱ� ���� 
			while(true){
				Socket client=server.accept();//���Ѵ�⿡ ������.!!
				ta.append(client.getInetAddress().getHostAddress()+"�� ����\n");//Ŭ���̾�Ʈ�� ������ �����Ǹ� ������ �� ���� �س��ƾ� �Ѵ�.
				ServerThread ct=new ServerThread(this,client);
				ct.start();
				//������ �����带 ������ ��ܿ� �߰�
				list.add(ct);
				System.out.println("���� ��"+list.size()+"�� ���� ��");
			}
			
			
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(this, "��Ʈ��ȣ�� ���ڸ� �־��ּ���");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("����\n");
	}
	public void run() {
		startServer();
		
	}
	public void actionPerformed(ActionEvent e) {
		acceptThread=new Thread(this);
		acceptThread.start();
		//��ư ��Ȱ��ȭ
		bt.setEnabled(false);
	}
	
	public static void main(String[] args) {
		new GuiServer();

	}

	

}
