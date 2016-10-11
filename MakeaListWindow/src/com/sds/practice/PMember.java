package com.sds.practice;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class PMember extends JPanel {
	//------------------------------------------------------
	JLabel la_iscon,la_id,la_nick,la_gender;
	int member_id;
	String id;
	String nick_name;
	int dectgen;
	String gen;
	//------------------------------------------------------
	public PMember(int member_id,String id, String nick_name,int dectgen) {
		//------------------------------------------------------
		this.member_id=member_id;
		this.id=id;
		this.nick_name=nick_name;
		this.dectgen=dectgen;
		if(dectgen==1){
			gen="남성";
		}else{
			gen="여성";
		}
		la_iscon=new JLabel("iscon");
		la_id=new JLabel(id);
		la_nick=new JLabel(nick_name);
		la_gender=new JLabel(gen);
		//------------------------------------------------------
		setBackground(Color.blue);
		setPreferredSize(new Dimension(440, 80));
		//------------------------------------------------------
		add(la_iscon);
		add(la_id);
		add(la_nick);
		//------------------------------------------------------
		/*addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				
			}
		});*/
		//------------------------------------------------------
	}
}
