/*
 게임의 stage 로써 실제적으로 모든 그래픽이 처리되는 영역!!
 */
package com.sds.game;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;
public class GamePanel extends JPanel implements Runnable{
	public static final int WIDTH=1024;
	public static final int HEIGHT=768;
	Thread gameThread; //게임의 심장부!!!
	boolean flag=true;
	
	//오브젝트 메니져!
	ObjectManager objectManager;
	
	//주인공 선언 
	Hero hero;
	
	//적군들 선언 
	Enemy[] enemy=new Enemy[5];
	
	public GamePanel() {
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		
		objectManager = new ObjectManager();
		createHero(); //주인공 생성
		createEnemy(); //적군생성
	}
	
	//주인공 등장 메서드 정의!!
	public void createHero(){
		hero = new Hero(100, 200, 50, 50, "Hero");
		
		//오브젝트 리스트 등록 ( 데이터베이스에 등록) 
		objectManager.addObject(hero);
	}
	
	//적군 등장 메서드 정의 !!
	public void createEnemy(){
		enemy[0] = new Enemy(600,100,30,30,"Enemy");
		enemy[1] = new Enemy(650,150,30,30,"Enemy");
		enemy[2] = new Enemy(700,250,30,30,"Enemy");
		enemy[3] = new Enemy(900,130,30,30,"Enemy");
		enemy[4] = new Enemy(800, 270,30,30,"Enemy");
		
		objectManager.addObject(enemy[0]);
		objectManager.addObject(enemy[1]);
		objectManager.addObject(enemy[2]);
		objectManager.addObject(enemy[3]);
		objectManager.addObject(enemy[4]);
	}
	
	@Override
	public void paint(Graphics g) {
		//모든 게임오프젝트들이 들어있는 명단을 이용하여
		//모든 게임오브젝트의 tick, render 호출하게 하자!!
		for(int i=0;i<objectManager.objectList.size();i++){
			GameObject obj=objectManager.objectList.get(i);
			obj.tick();
			obj.render(g);
		}
	}
	
	//게임 시작 메서드 
	public void startGame(){
		if(gameThread !=null){ //이미 가동중이라면...
			return;
		}
		flag=true;
		gameThread = new Thread(this);
		gameThread.start();
	}
	//일시 정지 메서드 
	public void pauseGame(){
		flag=false;
		gameThread=null;
	}
	//게임종료 메서드 
	public void exitGame(){
		System.exit(-1); //프로세스 종료, 현재 자바프로그램 종료
	}
	
	public void run() {
		while(flag){
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			//System.out.println("게임 엔진 작동 중...");
			repaint();
		}
	}
	
}









