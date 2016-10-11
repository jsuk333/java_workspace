package com.sds.echo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.peer.KeyboardFocusManagerPeer;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ChatClient extends JFrame implements ActionListener{
	JTextArea ta;
	JTextField tf;
	JButton bt;
	JPanel p;
	JScrollPane scroll;
	Socket client;//��ȭ�� ����!!(������ ��ȭ��)
	String ip="70.12.112.97";
	int port=8888;
	BufferedReader buffr;
	BufferedWriter buffw;
	StringBuffer sbuff;
	public ChatClient() {
		ta=new JTextArea();
		tf=new JTextField(15);
		bt=new JButton("����");
		p=new JPanel();
		scroll=new JScrollPane(ta);
		sbuff=new StringBuffer();
		
		bt.addActionListener(this);
		//�ؽ�Ʈ �ʵ�� ������ ����
		tf.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				int key=e.getKeyCode();
				if(key==KeyEvent.VK_ENTER){//����ĥ������ ������ �޽��� ������
					sendMessage();
				}
			}
		});
		
		p.add(tf);
		p.add(bt);
		
		add(scroll,BorderLayout.CENTER);
		add(p,BorderLayout.SOUTH);
		
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(300, 400);
		setVisible(true);
		
	}
	//������ �����ϴ� �޼��� ����
	public void connect(){
		try {
			client=new Socket(ip, port);
			buffr=new BufferedReader(new InputStreamReader(client.getInputStream()));
			buffw=new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			System.out.println("�����Ǹ� Ȯ���� �ּ���");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("��Ʈ��ũ ������ �߻��߽��ϴ�.");
			e.printStackTrace();
		}
	}
	//������ ���� �޼��� ������
	public void sendMessage(){
		String data2;
		try {
			buffw.write(tf.getText()+"\n");
			buffw.flush();
			tf.setText("");
			sbuff.append(buffr.readLine()+"\n");
			ta.setText(sbuff.toString());
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		connect();
		
	}
	public static void main(String[] args) {
		new ChatClient();

	}

}
