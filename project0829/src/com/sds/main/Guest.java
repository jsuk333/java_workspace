package com.sds.main;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Guest extends JFrame implements ActionListener{
	JPanel p;
	JButton btn_join;
	JButton btn_admin;
	JButton btn_request;
	JTextArea area;
	JScrollPane scroll;
	Socket client;
	String ip = "70.12.112.105";
	int port = 8888;
	BufferedReader buffr;
	BufferedWriter buffw;
	GuestThread gt;
	boolean flag = false;

	public Guest() {
		p = new JPanel();
		btn_admin = new JButton("서버생성");
		btn_join = new JButton("서버 접속");
		btn_request = new JButton("권한 요청");
		area = new JTextArea();
		scroll = new JScrollPane(area);

		p.add(btn_join);
		p.add(btn_admin);
		btn_admin.setEnabled(false);
		btn_admin.addActionListener(this);
		btn_join.addActionListener(this);
		btn_request.addActionListener(this);
		p.add(btn_request);
		add(p, BorderLayout.NORTH);
		add(scroll);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		setSize(400, 400);

	}

	public void connect() {
		try {
			client = new Socket(ip, port);
			setTitle("서버접속성공");
			gt=new GuestThread(this);
			gt.start();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();

		if (obj.equals(btn_join)) {
			// 서버 로긴
			connect();

		} else if (obj.equals(btn_request)) {
			// 서버 요청
			try {
				gt.requestHost();
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Guest();
	}
}
