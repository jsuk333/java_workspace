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

public class Profile extends JPanel {
	JPanel p_west;//캔버스 들어갈
	JPanel p_center;//내용 들어갈
	Canvas can;
	Toolkit kit;
	Image img;
	JLabel l_name,l_age,l_gender;
	
	public Profile(String path) {
		// TODO Auto-generated constructor stub
		l_name=new JLabel("이름");
		l_gender=new JLabel("성별");
		l_age=new JLabel("나이");
		p_center=new JPanel();
		p_west=new JPanel();
		kit=Toolkit.getDefaultToolkit();
		img=kit.getImage(path);
//		System.out.println(this);
		can=new Canvas(){
			@Override
			public void paint(Graphics g) {
				// TODO Auto-generated method stub
				g.drawImage(img, 0, 0, 100, 100, this);
			}
		};
		
		setLayout(new BorderLayout());
		can.setPreferredSize(new Dimension(100, 100));
		//can.setBackground(Color.black);
		p_west.setPreferredSize(new Dimension(120, 120));
		//p_west를 서쪽에 부착
		p_west.add(can);
		add(p_west, BorderLayout.WEST);
		//센터 패널에 그리드 레이아웃 적용
		p_center.setLayout(new GridLayout(3, 1));
		p_center.add(l_name);
		p_center.add(l_age);
		p_center.add(l_gender);
		add(p_center);
		p_center.setBackground(Color.CYAN);
		setPreferredSize(new Dimension(700, 120));
		System.out.println(isValid());
		
		
		
		
		
	}
}
