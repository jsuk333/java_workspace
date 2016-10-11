package com.sds.thread;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class AnimationApp extends JFrame {
	JButton bt;
	Canvas can;
	Toolkit kit;
	Image img;
	int x=0;
	Thread thread;
	public AnimationApp() {
		bt=new JButton("½ºÅ¸Æ®");
		kit=Toolkit.getDefaultToolkit();
		img=kit.getImage("C:/java_workspace/project0812/res/bullet.png");
		can=new Canvas(){
			@Override
			public void paint(Graphics g) {
				g.drawImage(img, x, 0, 100,50,this);
			}
		};
		thread=new Thread(){
			@Override
			public void run() {
				while(x<800){
					try {
						sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					move();
				}
			}
		};
		bt.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("d");
				thread.start();
			}
		});
		can.setPreferredSize(new Dimension(800, 700));
		add(bt,BorderLayout.NORTH);
		add(can,BorderLayout.CENTER);
		setVisible(true);
		setSize(900, 800);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		
		
	}
	public void move(){
		x+=10;
		can.repaint();
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new AnimationApp();
	}

}
