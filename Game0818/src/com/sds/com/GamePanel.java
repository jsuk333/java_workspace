package com.sds.com;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable{
	final public static int WIDTH=1024;
	final public static int HEIGHT=768;
	Thread GameThread;
	boolean running=true;
	Ship ship;
	Enemy enemy;
	ObjectManager objectManager;
	public GamePanel() {
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		objectManager=new ObjectManager();
		createShip();
		createEnemy();
		createBlock();
	}
	
	public void createShip(){
	
		ship=new Ship(100, 100, 100, 100, "Ship",objectManager);
		objectManager.objList.add(ship);
	}
	public void createEnemy(){
		Random random=new Random();
		
		for(int i=0;i<7;i++){
			int n=random.nextInt(7);
			enemy=new Enemy(900, 60+n*110, 100, 100, "Enemy", objectManager);
			objectManager.objList.add(enemy);
		}
	}
	//블럭 생성메서드
	public void createBlock(){
		//위 블럭
		for(int i=0;i<20;i++){
			Block block=new Block(i*50, 0, 50, 50, "Block", objectManager);
			objectManager.addObject(block);
		}
		//아래블럭
		for(int i=0;i<20;i++){
			Block block=new Block(i*50, HEIGHT-50, 50, 50, "Block", objectManager);
			objectManager.addObject(block);
		}
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		/*
		Image img=null;
		try {
			img = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("space.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		//g.drawImage(img, 0, 0, WIDTH, HEIGHT, this);
		for(int i=0;i<objectManager.objList.size();i++){
			objectManager.objList.get(i).render(g);
			objectManager.objList.get(i).tick();
			
		}
	}
	//게임 쓰레드 생성및 시작
	public void startGame(){
		if(GameThread!=null){
			return;
		}
		running=true;
		GameThread=new Thread(this);
		GameThread.start();//러너블 영역에 집어넣기
	}
	//게임 쓰레드 종료
	public void pauseGame(){
		running=false;
		GameThread=null;
	}
	//게임 종료
	public void ExitGame(){
		System.exit(0);
	}
	@Override
	public void run() {
		while(running){
			//여기서 게임의 모든 객체를 작동 시킨다.
			//tick render
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			repaint();
		}
		
	}
	
}
