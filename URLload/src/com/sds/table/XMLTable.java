/*xml���� �ҷ����� ����� jtable�� ����غ���.*/
package com.sds.table;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class XMLTable extends JFrame{
	JTable table;
	JScrollPane scroll;
	MyModel myModel;
	MyHandler myHandler;
	public XMLTable() {
		
		myModel=new MyModel();
		table=new JTable(myModel);
		scroll=new JScrollPane(table);
		
		
		add(scroll);
		
		
		setSize(300, 400);
		
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args) {
		new XMLTable();
	}

}
