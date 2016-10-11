package com.sds.json;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class Member extends JPanel{
	JPanel p_west,p_cen,p;
	Canvas can;
	JLabel l_name,l_gender,l_age;
	Toolkit kit;
	Image img;
	public Member(String path) {
		setPreferredSize(new Dimension( 750,120 ));
		p_west=new JPanel();
		p_cen=new JPanel();
		l_name=new JLabel("이름");
		l_gender=new JLabel("성별");
		l_age=new JLabel("나이");
		kit=Toolkit.getDefaultToolkit();
		img=kit.getImage(path);
		p_west.setPreferredSize(new Dimension(120, 120));
		System.out.println("d");
		can=new Canvas(){
			@Override
			public void paint(Graphics arg0) {
				arg0.drawImage(img, 0, 0, 100, 100, this);
			}
		};
		can.setBackground(Color.BLACK);
		can.setPreferredSize(new Dimension(100, 100));
		
		setLayout(new BorderLayout());
		p_cen.setLayout(new GridLayout(3,1));
		
		p_west.add(can);
		
		p_cen.add(l_name);
		p_cen.add(l_age);
		p_cen.add(l_gender);
		
		
		add(p_west,BorderLayout.WEST);
		add(p_cen);
		
	}
}
