package com.sds.thread;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JProgressBar;

public class MultiDownload extends JFrame{
	JButton bt;
	Long[] mil={(long) 3,(long) 2,(long) 2};
	JProgressBar bar1,bar2,bar3;
	ThreadTest thread1,thread2,thread3;
	Thread t1,t2,t3;
	public MultiDownload() {
		setLayout(new FlowLayout());
		// TODO Auto-generated constructor stub
		bt=new JButton("Download");
		thread1=new ThreadTest(500);
		thread2=new ThreadTest(2000);
		thread3=new ThreadTest(1000);
		//runable로 run 메서드를 재정의 할 경우
		//thread 생성시 runnable객체를 인수로 넣어야
		//runnable의 run 메서드가 동작하게 된다.
		//언제 쓰나?thread 클래스 가 다른 클래스를 상속해서 상속이 불가능 하므로
		//이때 사용한다.
		t1=new Thread(thread1);
		t2=new Thread(thread2);
		t3=new Thread(thread3);
		
		
		bt.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				t1.start();
				t2.start();
				t3.start();
				
			}
		});
		
		add(bt);
		add(thread1);
		add(thread2);
		add(thread3);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(500, 700);
		setVisible(true);
		
		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new MultiDownload();
	}

}
