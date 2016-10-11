package com.sds.thread;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class CounterApp extends JFrame implements Runnable{
	JButton bt;
	JLabel l;
	Thread thread;
	Thread thread2;
	MyThread myThread;
	int count=0;
	CounterApp me;
	public CounterApp() {
		// TODO Auto-generated constructor stub
		bt=new JButton("½ºÅ¸Æ®");
		l=new JLabel("0");
		me=this;
		
		
		bt.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//myThread=new MyThread(me);
				//myThread.start();
				thread2=new Thread(me);
				thread2.start();
				
			}
		});
		
		l.setFont(new Font(Font.DIALOG , Font.BOLD, 100));
		l.setHorizontalAlignment(JLabel.CENTER);
		
		
		
		add(bt,BorderLayout.NORTH);
		add(l,BorderLayout.CENTER);
		/*thread=new Thread(){
			public void run() {
				while(true){
					try {
						sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					l.setText(Integer.toString(count++));
					repaint();
				}
			};
		};*/
		setSize(300, 400);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new CounterApp();
	}
	@Override
	public void run() {
		while(true){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			l.setText(Integer.toString(count++));
		}		
	}

}
