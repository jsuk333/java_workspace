package com.sds.thread;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

public class FrameWin extends JFrame implements KeyListener{
	int x;
	//Oval oval;
	Canvas can;
	//FrameWin me;
	
	public FrameWin() {
		
		can=new Canvas(){
			@Override
			public void paint(Graphics g) {
				g.drawOval(x, 100, 50, 50);
			}
		};
		this.addKeyListener(this);
		can.setBackground(Color.YELLOW);
		add(can);
		setSize(800, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
	public void keyPressed(KeyEvent e) {
	}
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		int key=e.getKeyCode();
		if(key==KeyEvent.VK_SPACE){
			//oval=new Oval(me);
			//me.add(oval);	
			Thread thread=new Thread(){
				@Override
				public void run() {
					while(true){
						try {
							sleep(10);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						x+=5;
						can.repaint();
					}
				}
			};
			thread.start();
		}
	}
	public void keyTyped(KeyEvent e) {
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new FrameWin();
	}


}
