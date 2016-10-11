package com.sds.thread;

import java.awt.Dimension;

import javax.swing.JProgressBar;

public class ThreadTest extends JProgressBar implements Runnable {
	int n;
	long t;
	public ThreadTest(long t) {
		setPreferredSize(new Dimension(400, 180));
		this.t=t;
	}
	@Override
	public void run() {
		
			while(true){
				try {
					Thread.sleep(t);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				setValue(n++);
			}
	
		
	}
	
}
