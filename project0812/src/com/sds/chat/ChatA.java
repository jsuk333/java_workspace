package com.sds.chat;

import javax.swing.JButton;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ChatA extends JFrame{
	
	JButton bt;
	JTextArea ta;
	JTextField tf;
	JPanel p;
	ChatOther frame;
	HashSet<ChatOther> frameArr;
	int cre_num=0;
	 public ChatA() {
		// TODO Auto-generated constructor stub
		 bt=new JButton("»ý¼º");
		 ta=new JTextArea();
		 tf=new JTextField(15);
		 p=new JPanel();
		 frameArr=new HashSet<ChatOther>();
		 
		 bt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				create();
			}
		 });
		 
		 tf.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {				
			}
			public void keyReleased(KeyEvent e) {
				int key=e.getKeyCode();
				if(key==KeyEvent.VK_ENTER){
					String data=tf.getText();
					tf.setText("");
					ta.append(data+"\n");
					Iterator<ChatOther> it=frameArr.iterator();
					while(it.hasNext()){
						ChatOther other=it.next();
						other.ta.append(data+"\n");
					}
				}
			}
			public void keyPressed(KeyEvent e) {
			}
		});
		 
		 p.add(tf);
		 p.add(bt);
		 
		 add(ta, BorderLayout.CENTER);
		 add(p,BorderLayout.SOUTH);
		 setDefaultCloseOperation(EXIT_ON_CLOSE);
		 setSize(400, 500);
		 setVisible(true);
	 }
	 public void create(){
		 if(2>frameArr.size()){
			 frame=new ChatOther(this);
			 frameArr.add(frame);
		 }
	 }
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new ChatA();
	}

}
