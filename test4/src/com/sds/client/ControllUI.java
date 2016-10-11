package com.sds.client;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.json.simple.parser.ParseException;

import com.sds.admin.Join;
import com.sds.share.AcceptScreen;
import com.sds.share.ScreenShotServer;


public class ControllUI extends JFrame implements ActionListener{
	
	public JPanel controller;
	public JButton btn_sreenServer;
	public JButton btn_requestServer;
	public JButton btn_joinServer;
	public JButton btn_waitingroom;

	public ScreenUI screenUI;
	public String ip;
	public ScreenShotServer screenShotserver;
	public AcceptScreen acceptScreen;
	public Join join;
	//���μ��� �����ϱ� ���� ��ü
	boolean first=false;

	public ControllUI(String ip, boolean host,Join join) {
		this.ip = ip;
		this.join=join;
		
		screenUI=new ScreenUI();
		controller=new JPanel();
		controller.setPreferredSize(new Dimension(800, 150));
		btn_sreenServer=new JButton("ȭ�� ����");
		btn_joinServer=new JButton("����");
		btn_requestServer=new JButton("���ѿ�û");
		btn_waitingroom=new JButton("����");

		controller.add(btn_sreenServer);
		controller.add(btn_joinServer);
		controller.add(btn_requestServer);
		controller.add(btn_waitingroom);

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
			btn_joinServer.setEnabled(false);
			btn_requestServer.setEnabled(true);
		}
		btn_sreenServer.addActionListener(this);
		btn_joinServer.addActionListener(this);
		btn_requestServer.addActionListener(this);
		btn_waitingroom.addActionListener(this);
		//setDefaultCloseOperation(EXIT_ON_CLOSE);
		//setExtendedState(JFrame.MAXIMIZED_BOTH);

		setBounds(100, 800, 800, 150);
		setTitle("Controller");
		setVisible(true);
	}
	public void setIp(String ip){
		this.ip=ip;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj=e.getSource();
		if(obj.equals(btn_sreenServer))
		{	
			screenShotserver=new ScreenShotServer(join);
			acceptScreen=new AcceptScreen(screenUI,ip,this);
			join.joinCreateRoom();
			btn_sreenServer.setEnabled(false);
			screenUI.setVisible(false);
			
		}
		else if(obj.equals(btn_joinServer))
		{
			acceptScreen=new AcceptScreen(screenUI,ip,this);
			screenUI.setVisible(true);
			btn_joinServer.setEnabled(false);
		}
		else if(obj.equals(btn_requestServer))
		{
			//������ ������ ��û�Ѵ�.
			System.out.println("��ư��Ŭ��");
			join.joinRequest();
			
		}
		else if(obj.equals(btn_waitingroom))
		{
			//���Ƿ� ���ư���
			join.gt.toWaitingRoom();
			
		}
	
	}	
}
