package com.sds.thread;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JProgressBar;

public class ProgressTest extends JFrame{
	JButton bt;
	int n;
	Thread thread;
	JProgressBar bar;
	public ProgressTest() {
		setLayout(new FlowLayout());
		bt=new JButton("다운로드");
		bar=new JProgressBar();
		bar.setPreferredSize(new Dimension(350, 50));
		bar.setBackground(Color.yellow);
		thread=new Thread(){
			@Override
			public void run() {
				while(true){
					try {
						sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					fill();
				}
			}
		};
		bt.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				thread.start();
			}
		});
		
		add(bt);
		add(bar);
		
		setSize(400, 150);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
	public void fill(){
		bar.setValue(n++);
	}
	public static void main(String[] args) {
		new ProgressTest();

	}

}
