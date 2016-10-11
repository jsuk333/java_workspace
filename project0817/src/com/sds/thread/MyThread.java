package com.sds.thread;

public class MyThread extends Thread{
	
	int count=0;
	CounterApp app;
	
	public MyThread(CounterApp app) {
		// TODO Auto-generated constructor stub
		
		this.app=app;
	}
	
	@Override
	public void run() {
		while(true){
			try {
				sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			count();
		}
	}
	public void count(){
		app.l.setText(Integer.toString(count++));
	}
}
