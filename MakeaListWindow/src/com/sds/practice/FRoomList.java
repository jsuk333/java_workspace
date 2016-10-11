package com.sds.practice;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class FRoomList extends JFrame{
	//------------------------------------------------------
	JLabel la_title;
	JPanel p_title,p_center;
	JScrollPane scroll;
	PRoom room;
	FMainList mainList;
	PreparedStatement pstmt;
	ResultSet rs;
	//------------------------------------------------------
	public FRoomList(FMainList mainList) {
		//------------------------------------------------------
		this.mainList=mainList;
		la_title=new JLabel("Á¦¸ñ");
		p_title=new JPanel();
		p_center=new JPanel();
		scroll=new JScrollPane(p_center);
		//------------------------------------------------------
		p_title.setPreferredSize(new Dimension(450,60));
		p_center.setPreferredSize(new Dimension(450, 650));
		p_title.setBackground(Color.GRAY);
		p_center.setBackground(Color.RED);
		//------------------------------------------------------
		p_title.add(la_title);
		//------------------------------------------------------
		add(p_title,BorderLayout.NORTH);
		add(scroll,BorderLayout.CENTER);
		
		
		//------------------------------------------------------
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(500, 0, 500, 800);
		setVisible(true);
		//------------------------------------------------------
		searchRoom();
		//sendQuery();
	}
	public void searchRoom(){
		for(int i=0;i<mainList.rList.size();i++){
			p_center.add(mainList.rList.get(i));
		}
		p_center.updateUI();
	}
	
	/*public void sendQuery(){
		String sql="select * from roomlist";
		try {
			pstmt=mainList.con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
					room=new PRoom(mainList);
					p_center.add(room);
					p_center.updateUI();
					
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if(rs!=null){
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(pstmt!=null){
				try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}*/
}
