package com.sds.practice;

import java.awt.BorderLayout;
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
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ClientMain extends JFrame implements ActionListener{

	Socket socket;
	String host = "70.12.112.117";
	int port = 5555;
	
	JButton bt;
	JTextArea area;
	JScrollPane scroll;
	
	BufferedWriter buffw;
	BufferedReader buffr;
	
	public ClientMain() {
		try {
			socket = new Socket(host, port);
			setupUI();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("[ Debug ] : Connection is failed ");
		}
	}
	
	public void setupUI(){
		bt = new JButton("Àü¼Û");
		bt.addActionListener(this);
		area = new JTextArea();
		scroll = new JScrollPane(area);
		add(bt, BorderLayout.NORTH);
		add(scroll, BorderLayout.CENTER);
		
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
			String response = buffr.readLine();
			area.append(response+"\n");
			
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
