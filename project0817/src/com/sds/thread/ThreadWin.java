package com.sds.thread;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

public class ThreadWin extends JFrame{
	JButton bt;
	JLabel l;
	
	Thread thread1;
	Thread thread2;
	Thread thread3;
	ThreadBar bar1;
	ThreadBar bar2;
	int n1;
	int n2;
	int n3;
	
	public ThreadWin() {
		bt=new JButton("Ω√¿€");
		l=new JLabel("0");
		
		
		setLayout(new FlowLayout());
		bt.setPreferredSize(new Dimension(300, 50));
		l.setPreferredSize(new Dimension(400, 50));
		l.setFont(new Font(Font.DIALOG, Font.BOLD, 30));
		
		
		
		bt.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				thread1.start();
				thread2.start();
				thread3.start();
			}
		});
		
		thread1=new Thread(){
			@Override
			public void run() {
				while(true){
					try {
						sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					l.setText(Integer.toString(n1++));
				}
			}
		};
		bar1=new ThreadBar(this, 300);
		bar2=new ThreadBar(this, 500);
		
		thread2=new Thread(bar1);
		thread3=new Thread(bar2);
		
		add(bt);
		add(l);
		add(bar1);
		add(bar2);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(500	,600);
		setVisible(true);
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new ThreadWin();
	}

}
