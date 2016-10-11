package com.sds.chat;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ChatOther extends JFrame{
	JTextArea ta;
	JTextField tf;
	JPanel p;
	 public ChatOther(ChatA ori) {
		// TODO Auto-generated constructor stub
		 ta=new JTextArea();
		 tf=new JTextField(15);
		 p=new JPanel();
		 
		 tf.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {				
			}
			public void keyReleased(KeyEvent e) {
				int key=e.getKeyCode();
				if(key==KeyEvent.VK_ENTER){
					String data=tf.getText();
					tf.setText("");
					ori.ta.append(data+"\n");
					Iterator<ChatOther> it=ori.frameArr.iterator();
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
		 
		 add(ta, BorderLayout.CENTER);
		 add(p,BorderLayout.SOUTH);
		 setDefaultCloseOperation(EXIT_ON_CLOSE);
		 setBounds((ori.frameArr.size()+1)*400, 0, 400, 500);
		 setVisible(true);
	 }
}
