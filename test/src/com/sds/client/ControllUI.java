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

	ScreenUI screenUI;
	public String ip;
	public ScreenShotServer screenShot;
	public AcceptScreen acceptScreen;
	Join join;
	//���μ��� �����ϱ� ���� ��ü
	boolean first=false;
	public ControllUI(boolean host,Join join) {
		this.ip="70.12.112.105";		//ȣ��Ʈ ������
		this.join=join;
		
		screenUI=new ScreenUI();
		controller=new JPanel();
		controller.setPreferredSize(new Dimension(800, 150));
		btn_sreenServer=new JButton("ȭ�� ����");
		btn_joinServer=new JButton("����");
		btn_requestServer=new JButton("���ѿ�û");

		controller.add(btn_sreenServer);
		controller.add(btn_joinServer);
		controller.add(btn_requestServer);

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
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj=e.getSource();
		if(obj.equals(btn_sreenServer))
		{	
			screenShot=new ScreenShotServer(join);
			acceptScreen=new AcceptScreen(screenUI,ip);
			screenUI.screen.setVisible(false);
			
		}
		else if(obj.equals(btn_joinServer))
		{
			acceptScreen=new AcceptScreen(screenUI,ip);
			screenUI.screen.setVisible(true);
		}
		else if(obj.equals(btn_requestServer))
		{
			//������ ������ ��û�Ѵ�.
			System.out.println("��ư��Ŭ��");
			join.joinRequest();
			
		}
	
	}	
}
