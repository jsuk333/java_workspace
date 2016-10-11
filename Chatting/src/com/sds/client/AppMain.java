package com.sds.client;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.net.URI;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.sds.client.chat.UserList;

public class AppMain extends JFrame{
	JPanel p_north,p_center;
	JPanel[] p_content=new JPanel[4];
	JButton[] bt=new JButton[4];
	String[] iconPath={"chat.png","game.png","profile.png","stock.png"};
	Color[] bg={Color.red,Color.yellow,Color.blue,Color.green};
	JScrollPane scroll;
	public static long me;
	
	static public BufferedReader buffr;
	static public BufferedWriter buffw;
	public AppMain(BufferedReader buffr,BufferedWriter buffw,Long me) {
		this.me=me;
		this.buffr=buffr;
		this.buffw=buffw;
		p_north=new JPanel(new GridLayout(1, 4));
		p_north.setPreferredSize(new Dimension(500, 50));
		p_center=new JPanel(new BorderLayout());
		p_center.setPreferredSize(new Dimension(400, 450));
		for(int i=0;i<bt.length;i++){
			URL url=this.getClass().getClassLoader().getResource(iconPath[i]);
			ImageIcon icon=new ImageIcon(url);
			Image img=icon.getImage().getScaledInstance(80, 40, Image.SCALE_SMOOTH);
			icon.setImage(img);
			bt[i]=new JButton(icon);
			p_north.add(bt[i]);
			/*p_content[i]=new JPanel();
			p_content[i].setBackground(bg[i]);
			p_content[i].setPreferredSize(new Dimension(400, 500));
			p_center.add(p_content[i]);*/
		}
		//채팅 목록 나오기
		UserList friendList=new UserList();
		scroll=new JScrollPane(friendList);
		p_center.add(scroll);
		add(p_center,BorderLayout.CENTER);
		add(p_north,BorderLayout.NORTH);
		setSize(500, 500);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}
	
}
