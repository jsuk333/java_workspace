package com.sds.practice;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class FWait extends JFrame{
	//------------------------------------------------------
	JLabel la_inpw;
	JTextField tf_inpw;
	JPanel p_inpw;
	JButton bt_pt;
	FMainList mainList;
	//------------------------------------------------------
	public FWait(FMainList mainList) {
		//------------------------------------------------------
		la_inpw=new JLabel("la_inpw");
		tf_inpw=new JTextField(5);
		bt_pt=new JButton("¿‘¿Â");
		p_inpw=new JPanel();
		this.mainList=mainList;
		//------------------------------------------------------
		p_inpw.add(la_inpw);
		p_inpw.add(tf_inpw);
		//------------------------------------------------------
		add(p_inpw);
		add(bt_pt,BorderLayout.SOUTH);
		//------------------------------------------------------
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(0, 800, 400, 250);
		setVisible(true);
		//------------------------------------------------------
	}
	
	

}
