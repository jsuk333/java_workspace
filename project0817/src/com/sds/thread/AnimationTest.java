package com.sds.thread;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

public class AnimationTest extends JFrame implements KeyListener{
	Canvas can;
	int x;
	
	public AnimationTest() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		can = new Canvas(){
			public void paint(Graphics g) {
				g.drawOval(x, 100, 50, 50);
			}
		};
		
		//윈도우와 리스너 연결 
		this.addKeyListener(this);
		
		//켄버스 부착!!
		can.setBackground(Color.YELLOW);
		add(can);
		
		//윈도우를 모니터 크기와 상관없이 항상 가운데 띄우기!!
		setLocationRelativeTo(null);
		setSize(800,400);
		setVisible(true);
	}
	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
		if(e.getKeyCode() == KeyEvent.VK_SPACE){
			//쓰레드 한개를 생성하여 작동!!
			Thread thread = new Thread(){
				public void run() {
					while(true){
						try {
							Thread.sleep(10);
						} catch (InterruptedException e) {
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

	@Override
	public void keyTyped(KeyEvent e) {
		
	}
	public static void main(String[] args) {
		new AnimationTest();

	}



}
