package com.sds.thread;

import java.awt.Dimension;

import javax.swing.JProgressBar;

public class ThreadBar extends JProgressBar implements Runnable{
	ThreadWin app;
	int n;
	int count;
	public ThreadBar(ThreadWin app,int n) {
		this.app=app;
		this.n=n;
		setPreferredSize(new Dimension(400, 50));
		
	}
	@Override
	public void run() {
		while(true){
			try {
				Thread.sleep(n);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			setValue(count++);
		}
		
	}

}
