package com.sds.client;

import javax.swing.table.AbstractTableModel;

public class MemberModel extends AbstractTableModel{
	String[] columnTitle = {
			"�г���(���̵�)",
			"����"
	};
	MemberList memberList;
	public MemberModel(MemberList memberList) {
		this.memberList=memberList;
	}
	
	public String getColumnName(int column) {
		return columnTitle[column];
	}
	
	public int getColumnCount() {
		return columnTitle.length;
	}

	public int getRowCount() {
		return memberList.waitingRoom.clientMain.conList.size();
	}

	public Object getValueAt(int row	, int col) {
		return memberList.waitingRoom.clientMain.conList.get(row)[col];
	}

}
