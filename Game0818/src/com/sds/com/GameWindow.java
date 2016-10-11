package com.sds.com;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class GameWindow extends JFrame implements ActionListener{
	//���� �ɼ�  -> ���� ����, ����, ���� 
	//����
	JMenuBar bar;
	JMenu[] menu=new JMenu[2];
	JMenuItem[] item=new JMenuItem[3];
	String[] itemTitle={"���ӽ���","�Ͻ�����","��������"};
	
	GamePanel gamePanel;
	KeyBoard keyBoard;
	
	public GameWindow() {
		
		bar=new JMenuBar();
		menu[0]=new JMenu("���� �ɼ�");
		menu[1]=new JMenu("����");
		for(int i=0;i<item.length;i++){
			item[i]=new JMenuItem(itemTitle[i]);
			item[i].addActionListener(this);
		}
		gamePanel=new GamePanel();
		
		addKeyListener(keyBoard=new KeyBoard(gamePanel.objectManager));
		
		for(int i=0;i<item.length;i++){
			menu[0].add(item[i]);
		}
		bar.add(menu[0]);
		bar.add(menu[1]);
		setJMenuBar(bar);
		add(gamePanel);
		pack();
		
		
		
		setBackground(Color.BLACK);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj=e.getSource();
		if(obj==item[0]){
			gamePanel.startGame();
		}else if(obj==item[1]){
			gamePanel.pauseGame();
		}else if(obj==item[2]){
			gamePanel.ExitGame();
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new GameWindow();
	}



}
