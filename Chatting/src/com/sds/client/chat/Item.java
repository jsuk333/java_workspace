/*회원 한명을  표현하는 아이템 클래스 정리*/
package com.sds.client.chat;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.sds.client.AppMain;

public class Item extends JPanel implements ActionListener{
	ImageIcon icon;
	JLabel la_profile,la_status;
	JButton bt;
	long you;
	// 객체 생성시 이미지 경로와 상태 메시지를 넘겨 받자
	public Item(String path,String status,long you) {
		this.you=you;
		setPreferredSize(new Dimension(400, 60));
		URL url=this.getClass().getClassLoader().getResource(path);
		bt=new JButton("친구맺기");
		icon=new ImageIcon(url);
		la_profile=new JLabel(icon);
		la_status=new JLabel(status);
		setLayout(new BorderLayout());
		add(la_profile,BorderLayout.WEST);
		add(la_status,BorderLayout.CENTER);
		add(bt,BorderLayout.EAST);
		
		bt.addActionListener(this);
	}
	public void makeFriend(){
		System.out.println(AppMain.me+"와"+you+"친구 맺기");
	}
	public void actionPerformed(ActionEvent e) {
		makeFriend();
		
	}
}
