/*
 * 게임의 윈도우를 정의한다 1024 * 768
 * 
 * 구현할 인터페이스가 3개이상의 재정의 메서드를 보유할 경우
 * 사용하지도 않는 메서드를 코드상으로 존재시켜야 하는 불합리
 * 한 경우가 발생하게 된다..
 * 해결책)  sun 에서는 어댑터를 클래스를 통해 이 문제를 해결
 * 
 * */
package com.sds.game;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class GameWindow extends JFrame implements ActionListener{
	GamePanel gamePanel;
	JMenuBar bar;
	JMenu[] menu= new JMenu[2];
	JMenuItem[] item = new JMenuItem[3];
	String[] itemTitle={"게임시작","일시중지","게임종료"};
	
	//우리대신 KeyListener 를 구현한 추상클래스!
	//우리는 따라서 keyReleased, keyPresses, keyTyped 에 
	//대한 의무가 더이상 없다!!
	KeyBoard keyBoard;
	
	public GameWindow() {
		add(gamePanel=new GamePanel());
		pack();
		
		//메뉴구성 
		bar = new JMenuBar();
		menu[0]=new JMenu("게임옵션");
		menu[1]=new JMenu("도움말");
		
		for(int i=0;i<item.length;i++){
			item[i] = new JMenuItem(itemTitle[i]);
			menu[0].add(item[i]);
			item[i].addActionListener(this);//리스너와 연결!!
		}
		bar.add(menu[0]);
		bar.add(menu[1]);
		
		setJMenuBar(bar);
		
		//윈도우와 키리스너연결
		this.addKeyListener(keyBoard = new KeyBoard(gamePanel.objectManager));
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
		setBackground(Color.BLACK);
	}

	public void actionPerformed(ActionEvent e) {
		Object obj=e.getSource();
		
		if(obj==item[0]){ //게임시작!!
			gamePanel.startGame();
		}else if(obj==item[1]){//일시중지!!
			gamePanel.pauseGame();
		}else if(obj.equals(item[2])){//게임종료!!
			gamePanel.exitGame();
		}
	}
	
	public static void main(String[] args) {
		new GameWindow();	
	}
}



