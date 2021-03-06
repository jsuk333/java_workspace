package com.sds.client;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.sds.client.ScreenUI;
import com.sds.admin.Join;
import com.sds.share.AcceptScreen;
import com.sds.share.ScreenShotServer;

public class ViewWindow extends JFrame implements ActionListener{
	WaitingRoom waitingRoom;
	
	JButton bt_exit;
	
	public JPanel controller;
	public JButton btn_sreenServer;
	public JButton btn_requestServer;
	public JButton btn_joinServer;

	ScreenUI screenUI;
	public String ip;
	public ScreenShotServer screenShot;
	public AcceptScreen acceptScreen;
	Join join;
	
	public ViewWindow(WaitingRoom waitingRoom, boolean host, Join join ) {
		this.waitingRoom = waitingRoom;
		
		this.ip="70.12.112.105";
		this.join=join;
		
		screenUI=new ScreenUI();
		controller=new JPanel();
		controller.setPreferredSize(new Dimension(800, 150));
		btn_sreenServer=new JButton("화면 공유");
		btn_joinServer=new JButton("참여");
		btn_requestServer=new JButton("권한요청");

		controller.add(btn_sreenServer);
		controller.add(btn_joinServer);
		controller.add(btn_requestServer);

		add(bt_exit);

		add(controller);
		if(host)
		{
			btn_sreenServer.setEnabled(true);
			btn_joinServer.setEnabled(false);
			btn_requestServer.setEnabled(false);
		}
		else
		{
			btn_sreenServer.setEnabled(false);
			btn_joinServer.setEnabled(true);
			btn_requestServer.setEnabled(true);
		}
		btn_sreenServer.addActionListener(this);
		btn_joinServer.addActionListener(this);
		btn_requestServer.addActionListener(this);
		//setDefaultCloseOperation(EXIT_ON_CLOSE);
		//setExtendedState(JFrame.MAXIMIZED_BOTH);

		setBounds(100, 800, 800, 150);
		setTitle("Controller");
		setVisible(true);
		
		bt_exit = new JButton("종료");
		bt_exit.addActionListener(this);
			
		
		
		
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == bt_exit){
			waitingRoom.disConnection();
		}
		Object obj=e.getSource();
		if(obj.equals(btn_sreenServer))
		{	
			screenShot=new ScreenShotServer(join);
			acceptScreen=new AcceptScreen(screenUI,ip,null );
			screenUI.screen.setVisible(false);
			
		}
		else if(obj.equals(btn_joinServer))
		{
			acceptScreen=new AcceptScreen(screenUI,ip,null);
			screenUI.screen.setVisible(true);
		}
		else if(obj.equals(btn_requestServer))
		{
			//서버에 권한을 요청한다.
			System.out.println("버튼을클릭");
			join.joinRequest();
			
		}
		
	}
}
