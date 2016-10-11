package com.sds.admin;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import com.sds.client.ClientMain;

public class ManageModel extends AbstractTableModel{
	String[] columnTitle={"member_id","id","password","name","nick_name","gender","e_mail","regdate","isConnection"
			,"room_num","isAdimin","user_ip"};
	ClientMain clientMain;
	public ManageModel(ClientMain clientMain) {
		this.clientMain=clientMain;
	}
	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return columnTitle.length;
	}
	
	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return clientMain.memberList.size();
	}
	@Override
	public String getColumnName(int column) {
		return columnTitle[column];
	}
	@Override
	public Object getValueAt(int row, int col) {
		// TODO Auto-generated method stub
		return clientMain.memberList.get(clientMain.memberList.size()-row-1)[col];
	}
	@Override
	public boolean isCellEditable(int row, int col) {
		boolean flag=true;
		if(col==0&&col==1){
			flag=false;
		}
		return flag;
	}
	
	

}
