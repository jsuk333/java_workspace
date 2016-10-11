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
import javax.swing.JTextField;

public class FMemberList extends JFrame{
	//------------------------------------------------------
	JLabel la_title;
	JPanel p_cen,p_north;
	JScrollPane scroll;
	PMember[] member;
	FMainList mainList;
	PreparedStatement pstmt;
	ResultSet rs;
	//------------------------------------------------------
	public FMemberList(FMainList mainList) {
		//------------------------------------------------------
		this.mainList=mainList;
		la_title=new JLabel("접속자 리스트");
		p_cen=new JPanel();
		p_north=new JPanel();
		scroll=new JScrollPane(p_cen);
		//------------------------------------------------------
		p_north.setPreferredSize(new Dimension(450, 60));
		p_cen.setPreferredSize(new Dimension(450, 650));
		//------------------------------------------------------
		/*member=new PMember[5];
		for(int i=0;i<member.length;i++){
			member[i]=new PMember();
			p_cen.add(member[i]);
		}*/
		//------------------------------------------------------
		p_cen.setBackground(Color.yellow);
		//------------------------------------------------------
		p_north.add(la_title);
		//------------------------------------------------------
		add(p_north,BorderLayout.NORTH);
		add(scroll);
		//------------------------------------------------------
		setBounds(1000, 0, 500, 800);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
		//------------------------------------------------------
		searchMember();
	}
	//------------------------------------------------------
	public void searchMember(){
		String sql="select * from member ";
		
		try {
			pstmt=mainList.con.prepareStatement(sql);
			
			rs=pstmt.executeQuery();
			while(rs.next()){
				p_cen.add(new PMember(rs.getInt("member_id"), rs.getString("id"),rs.getString("nick_name"), rs.getInt("gender")));
				p_cen.updateUI();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
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
	}

}
