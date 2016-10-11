package com.sds.practice;

import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

public class PRoom extends JPanel {
	//------------------------------------------------------
	String title;
	int mxpt;
	int pt;
	boolean ispw;
	String host;
	JLabel la_title,la_mxpt,la_pt,la_ispw,la_host;
	FMainList mainList;
	//------------------------------------------------------
	public PRoom(FMainList mainList,String title,int mxpt,int pt,boolean ispw, String host) {
		//------------------------------------------------------
		this.mainList=mainList;
		this.title=title;
		this.mxpt=mxpt;
		this.pt=pt;
		this.ispw=ispw;
		this.host=host;
		la_title=new JLabel(title);
		la_mxpt=new JLabel(Integer.toString(mxpt));
		la_pt=new JLabel(Integer.toString(pt));
		la_ispw=new JLabel(Boolean.toString(ispw));
		la_host=new JLabel(host);
		//------------------------------------------------------
		setPreferredSize(new Dimension(480, 80));
		//------------------------------------------------------
		add(la_title);
		add(la_pt);
		add(la_mxpt);
		add(la_ispw);
		add(la_host);
		//------------------------------------------------------
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				new FWait(mainList);
			}
		});
		//------------------------------------------------------
	}
}
