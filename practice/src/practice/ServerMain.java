package practice;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ServerMain extends JFrame implements Runnable {
	
	JPanel p_north;
	JTextArea ta;
	JTextField tf;
	JButton bt;
	Thread acceptThread;
	public ServerMain() {
		
		p_north=new JPanel();
		ta=new JTextArea();
		tf=new JTextField("7777",8);
		bt=new JButton("서버가동");
		
		p_north.add(tf);
		p_north.add(bt);
		
		add(p_north,BorderLayout.NORTH);
		add(ta);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(500, 600);
		setVisible(true);
		
		bt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				acceptThread.start();
			}
		});
	}
	
	public void run() {
		serverStart();
	}
	public void serverStart(){
		try {
			ServerSocket server=new ServerSocket(7777);
			System.out.println("서버 생성");
			Socket client=server.accept();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new ServerMain();

	}


}
