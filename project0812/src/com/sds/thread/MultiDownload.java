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
		//runable�� run �޼��带 ������ �� ���
		//thread ������ runnable��ü�� �μ��� �־��
		//runnable�� run �޼��尡 �����ϰ� �ȴ�.
		//���� ����?thread Ŭ���� �� �ٸ� Ŭ������ ����ؼ� ����� �Ұ��� �ϹǷ�
		//�̶� ����Ѵ�.
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
