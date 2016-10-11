package com.sds.client;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class MemberList extends JFrame{
	WaitingRoom waitingRoom;
	MemberModel memberModel;
	JTable table;
	JScrollPane scroll;
	boolean openflag = false;
	ClientMain clientMain;
	
	public MemberList(ClientMain clientMain, WaitingRoom waitingRoom) {
		this.clientMain = clientMain;
		this.waitingRoom =  waitingRoom;
		int x, y;
		x = this.waitingRoom.getX();
		y = this.waitingRoom.getY();
		
		memberModel = new MemberModel(this);
		table = new JTable(memberModel);
		scroll = new JScrollPane(table);
		
		add(scroll);
		setTitle("접속자 리스트");
		setUndecorated(true);
		setSize(300, 500);
		setLocation(x+510, y);
		setResizable(false);
		setVisible(false);
	}
}
