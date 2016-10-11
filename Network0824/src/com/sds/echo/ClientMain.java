package com.sds.echo;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;

public class ClientMain extends JFrame implements ActionListener{

	Socket socket;
	String host = "70.12.112.117";
	int port = 5555;
	
	JButton bt;
	BufferedWriter buffw;
	BufferedReader buffr;
	
	public ClientMain() {
		try {
			socket = new Socket(host, port);
			setupUI();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("[ Debug ] : Connection is failed ");
		}
	}
	
	public void setupUI(){
		bt = new JButton("Àü¼Û");
		bt.addActionListener(this);
		add(bt);
		setSize(new Dimension(200, 200));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public void sendMsg(){
		try {
			buffw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			buffw.write("selectAll\n");
			buffw.flush();
			
			buffr = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			buffr.readLine();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("[ Debug ] : IOException");
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == bt){
			sendMsg();
		}
		
	}
	
	public static void main(String[] args) {
		new ClientMain();
	}

	

}
