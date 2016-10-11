package com.sds.client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ChatClient extends JFrame {
	JPanel p_center;
	JPanel p_south;
	JTextArea area;
	JScrollPane scroll;
	JTextField txt;
	JButton bt;
	
	StringBuffer sb;
	ClientMain clientMain;
	int room_num;
	Socket client;
	String user_id;
	String user_nickName;
	public ChatClient(ClientMain clientMain,int room_num) {
		this.clientMain=clientMain;
		this.room_num=room_num;
		this.client=clientMain.socket;
		this.user_id=clientMain.user_id;
		this.user_nickName=clientMain.user_nickname;
		sb=new StringBuffer();
		p_center=new JPanel();
		area=new JTextArea();
		area.setPreferredSize(new Dimension(450, 600));
		area.setEditable(false);
		scroll=new JScrollPane(area);
		p_south=new JPanel();
		txt=new JTextField(15);
		//setTitle(Integer.toString(room_num));
		txt.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				int key=e.getKeyCode();
				if(key==KeyEvent.VK_ENTER){
					sb.delete(0, sb.length());
					sb.append("{\"request\" : \"chatting\",");
					sb.append("\"data\" : [{");
					sb.append("\"msg\" : \""+user_nickName+"("+user_id+")"+":"+txt.getText()+"\",");
					sb.append("\"room_num\" : \""+room_num+"\"");
					sb.append("}]}");
					clientMain.clientThread.sendMsg(sb);
					txt.setText("");
								
				}
			}
		});
		bt=new JButton("ют╥б");
		
		
		
		p_center.add(scroll);
		//p_center.setPreferredSize(new Dimension(500, 500));
		add(p_center);
		
		p_south.add(txt);
		p_south.add(bt);
		add(p_south,BorderLayout.SOUTH);
		
		setSize(500, 700);
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				disConnection();
				
			}

		});
		
	}
	
	public void disConnection(){
		System.out.println("2222");
		sb.delete(0, sb.length());
		sb.append("{\"request\" : \"disconnectchat\",");
		sb.append("\"data\" : {");
		sb.append("\"room_num\" : \""+room_num+"\"");
		sb.append("}}");
		clientMain.clientThread.sendMsg(sb);
		System.out.println("3333");
	}
	
	
	
}
