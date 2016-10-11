package com.sds.UI;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import com.sds.screenShot.*;
import com.sds.serverJoin.*;
public class ControllUI extends JFrame implements ActionListener{
	
	JPanel controller;
	JButton btn_sreenServer;
	JButton btn_requestServer;
	JButton btn_joinServer;
	ScreenUI screenUI;
	GuestStream guestStream;
	public ControllUI() {
		screenUI=new ScreenUI();
		controller=new JPanel();
		controller.setPreferredSize(new Dimension(800, 150));
		btn_sreenServer=new JButton("화면 공유");
		btn_joinServer=new JButton("참여");
		btn_requestServer=new JButton("권한요청");
		controller.add(btn_sreenServer);
		controller.add(btn_joinServer);
		controller.add(btn_requestServer);
		add(controller);
		btn_sreenServer.addActionListener(this);
		btn_joinServer.addActionListener(this);
		btn_requestServer.addActionListener(this);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		//setExtendedState(JFrame.MAXIMIZED_BOTH);

		setBounds(100, 800, 800, 150);
		setTitle("Controller");
		setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj=e.getSource();
		if(obj.equals(btn_sreenServer))
		{
		
			//스크린 서버 개설
			MainServer mainserver=new MainServer();
		}
		else if(obj.equals(btn_requestServer))
		{
			//권한 요청 
			
		}
		else if(obj.equals(btn_joinServer))
		{
			//방 참여
			guestStream=new GuestStream(screenUI);
		
		}
		
	}	
	public static void main(String[] args) {
		new ControllUI();

	}


}
