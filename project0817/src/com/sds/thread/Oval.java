package com.sds.thread;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;

public class Oval extends Canvas{
	FrameWin app;
	int x;
	int y;
	Thread thread;
	public Oval(FrameWin app) {
		this.app=app;
		setPreferredSize(new Dimension(30, 30));
		thread=new Thread(){
			public void run() {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				x++;
				repaint();

			}
		};
		thread.start();
	
	}
	@Override
	public void paint(Graphics g) {
		g.drawOval(x, 50, 20, 20);
	}
	
}
