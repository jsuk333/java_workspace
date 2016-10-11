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
	public ChatClient(ClientMain clientMain,int room_num) {
		this.clientMain=clientMain;
		this.room_num=room_num;
		sb=new StringBuffer();
		p_center=new JPanel();
		area=new JTextArea();
		area.setPreferredSize(new Dimension(450, 600));
		area.setEditable(false);
		scroll=new JScrollPane(area);
		p_south=new JPanel();
		txt=new JTextField(15);
		setTitle(Integer.toString(room_num));
		txt.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				int key=e.getKeyCode();
				if(key==KeyEvent.VK_ENTER){
					sb.delete(0, sb.length());
					sb.append("{\"request\" : \"chatting\",");
					sb.append("\"data\" : [{");
					sb.append("\"msg\" : \""+txt.getText()+"\",");
					sb.append("\"room_num\" : \""+room_num+"\"");
					sb.append("}]}");
					System.out.println(sb.toString());
					clientMain.sendMsg(sb);
					
					txt.setText("");
								
				}
			}
		});
		bt=new JButton("�Է�");
		
		
		
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
		sb.delete(0, sb.length());
		sb.append("{\"request\" : \"disconnectchat\",");
		sb.append("\"data\" : {");
		sb.append("\"room_num\" : \""+room_num+"\"");
		sb.append("}}");
		clientMain.sendMsg(sb);
	}
	
}
