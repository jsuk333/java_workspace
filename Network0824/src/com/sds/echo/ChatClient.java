package com.sds.echo;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ChatClient extends JFrame implements ActionListener{
	JPanel p_north;
	Choice ch_ip;
	JButton bt;
	JTextArea area;
	JScrollPane scroll;
	JTextField t_input,t_port;
	Socket client;
	BufferedReader buffr;
	BufferedWriter buffw;
	//--------------------------------------------------
	public ChatClient() {
		p_north=new JPanel();
		ch_ip=new Choice();
		bt=new JButton("����");
		area=new JTextArea();
		scroll=new JScrollPane(area);
		t_input=new JTextField();
		t_port=new JTextField("8888", 4);
		//-----------------------------------------
		for(int i=90;i<=121;i++){
			ch_ip.add("70.12.112."+i);
		}
		p_north.add(ch_ip);
		p_north.add(t_port);
		p_north.add(bt);
		//----------------------------------------
		add(p_north, BorderLayout.NORTH);
		add(scroll);
		add(t_input,BorderLayout.SOUTH);
		//---------------------------------------------
		//��ư�� �̺�Ʈ ����
		bt.addActionListener(this);
		t_input.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				int key=e.getKeyCode();
				if(key==KeyEvent.VK_ENTER){
					sendMsg();//������ �޽��� ����
					listen();//�������� �޽��� �ޱ�
					t_input.setText("");
				}
			}
		});
		//---------------------------------------------
		setBounds(300,100,300,400);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		//-------------------------------------------
	}
	//--------------------------------------------------
	//���ڼ����� ����
	public void connect(){
		String ip=ch_ip.getSelectedItem();
		String port=t_port.getText();
		try {
			client=new Socket(ip,Integer.parseInt(port));
			buffr=new BufferedReader(new InputStreamReader(client.getInputStream()));
			buffw=new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
			
		} catch (NumberFormatException e) {
			showMsg("��Ʈ��ȣ�� ������ �Է��ؾ� �մϴ�.");
			e.printStackTrace();
		} catch (UnknownHostException e) {
			showMsg("�ùٸ��� ���� �ּ� �Դϴ�.");
			e.printStackTrace();
		} catch (IOException e) {
			showMsg("���� ���� ����");
			e.printStackTrace();
		}
	}
	//--------------------------------------------------
	public void sendMsg(){
		
		String data=t_input.getText();
		try {
			buffw.write(data+"\n");
			buffw.flush();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	//--------------------------------------------------
	public void listen(){
		String msg;
		try {
			msg = buffr.readLine();
			area.append(msg+"\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	//--------------------------------------------------
	public void showMsg(String message){
		JOptionPane.showMessageDialog(this, message);
	}
	//--------------------------------------------------
	public void actionPerformed(ActionEvent e) {
		connect();
		bt.setEnabled(false);
		
	}
	//--------------------------------------------------
	public static void main(String[] args) {
		new ChatClient();

	}


}
