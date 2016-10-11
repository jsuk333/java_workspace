package com.sds.practice;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class FMainList extends JFrame {
	//------------------------------------------------------
	JPanel p_room, p_people, p_menu;
	JButton bt_room,bt_people,bt_menu;
	Connection con;
	String url="jdbc:oracle:thin:@localhost:1521:XE";
	String user="java0819";
	String password="java0819";
	PreparedStatement pstmt;
	FMainList me;
	
	ArrayList<PRoom> rList;
	//------------------------------------------------------
	public FMainList() {
		rList=new ArrayList<PRoom>();
		this.me=this;
		p_room=new JPanel();//�������� �� �г�
		p_people=new JPanel();//������ ������� �� �г�
		p_menu=new JPanel();//�޴��� �� �г�
		bt_room=new JButton("room");
		bt_people=new JButton("people");
		bt_menu=new JButton("menu");
		//------------------------------------------------
		p_room.setBackground(Color.CYAN);
		p_people.setBackground(Color.GRAY);
		p_menu.setBackground(Color.magenta);
		//------------------------------------------------------
		setLayout(new GridLayout(3, 1));
		//------------------------------------------------------
		p_room.add(bt_room);
		p_people.add(bt_people);
		p_menu.add(bt_menu);
		//-----------------------------------------------------
		add(p_room);
		add(p_people);
		add(p_menu);
		//------------------------------------------------------
		setBounds(0, 0, 500, 800);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		//------------------------------------------------------
		p_room.addMouseListener(new MouseAdapter() {
			
			public void mouseReleased(MouseEvent e) {
				new FRoomList(me);//������ �� ����Ʈ\
			}
		});
		
		p_people.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				new FMemberList(me);//������ ��� ����
			}
		});
		
		p_menu.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				new FCreateRoom(me);//�Ҽӵ� Ŭ������ ����� �ۿ� ��� �ϴ� �ϳ��� ����
			}
		});
		//------------------------------------------------------
		connectDB();
	}
	//------------------------------------------------------
	public void connectDB(){
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			try {
				con=DriverManager.getConnection(url, user, password);
				if(con!=null){
					System.out.println("DB���Ἲ��");
					
				}else{
					System.out.println("DB�������");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//------------------------------------------------------
	public static void main(String[] args) {
		
		new FMainList();

	}

}
