package com.sds.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.sds.server.ServerMain;

public class LoginForm extends JFrame implements ActionListener{
	JTextField tf;
	JPasswordField tf2;
	JButton bt;
	JButton bt2;
	JPanel pl;
	JPanel pl_ct;
	JPanel pl_ct2;
	JPanel pl_ct3;
	JLabel lb;
	JLabel lb2;
	JLabel lb3;
	//로그인 이전에 이미 소켓 접속을 확보해 놓자
	String ip="70.12.112.106";
	int port=7777;
	Socket client;
	BufferedReader buffr;
	BufferedWriter buffw;
	public LoginForm() {
		super("LOGIN");
		lb = new JLabel("로그인",0);
		lb2 = new JLabel("ID");
		lb3 = new JLabel("PW");
		bt = new JButton("Login");
		bt2 = new JButton("Sign");
		tf = new JTextField(15);
		tf2 = new JPasswordField(15);
		pl = new JPanel();
		pl_ct = new JPanel();
		pl_ct2 = new JPanel();
		pl_ct3 = new JPanel();
		
		tf.setBackground(Color.yellow);
		tf2.setBackground(Color.yellow);
		
		pl.add(bt);
		pl.add(bt2);		
		
		pl_ct.add(lb2);
		pl_ct.add(tf);
		pl_ct2.add(lb3);
		pl_ct2.add(tf2);
		pl_ct3.add(pl_ct);
		pl_ct3.add(pl_ct2);
		
		lb.setPreferredSize(new Dimension(130, 20));
		lb2.setPreferredSize(new Dimension(70, 20));
		lb3.setPreferredSize(new Dimension(70, 20));
		bt.setPreferredSize(new Dimension(80, 20));
		bt2.setPreferredSize(new Dimension(80, 20));
				
		add(lb,BorderLayout.NORTH);
		add(pl_ct3,BorderLayout.CENTER);
		add(pl,BorderLayout.SOUTH);
				
				
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(350,180);
		setVisible(true);
		connect();
		bt.addActionListener(this);
		
	}
	//프로그램이 가동되면, 서버에 소켓 연결을 시도하자
	public void connect(){
		try {
			client=new Socket(ip, port);
			System.out.println(client);
			setTitle("Server에 접속");
			
			buffr=new BufferedReader(new InputStreamReader(client.getInputStream()));
			buffw=new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
				
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void logInCheck(){//소켓서버에 인증을 요청한다.
		String id=tf.getText();
		String pwd=new String(tf2.getPassword());
		//아이디와 패스 워드 전송
		
		StringBuffer sb=new StringBuffer();
		sb.delete(0,sb.length());
		sb.append("{\"request\" : \"logIn\",");
		sb.append("\"id\" : \""+id+"\",");
		sb.append("\"pwd\" : \""+pwd+"\"}");
		try {
			buffw.write(sb.toString()+"\n");
			buffw.flush();
			//서버로부터 전송되어온 요청 처리 결과 제이스!!
			String msg=buffr.readLine();
			//제이슨 파싱
			JSONParser jsonParser=new JSONParser();
			JSONObject jsonObject=(JSONObject)jsonParser.parse(msg);
			
			
			
			if(jsonObject.get("result").equals("ok")){
				JSONObject object=(JSONObject) jsonObject.get("data");
				String name=(String)object.get("name");
				JOptionPane.showMessageDialog(this,name+"님 반갑습니다." );
				long chatmember_id=(Long)object.get("chatmember_id");
				//채팅 메인 띄우기
				AppMain appMain=new AppMain(buffr,buffw,chatmember_id);
				setVisible(false);
			}else if(jsonObject.get("result").equals("fail")){
				JOptionPane.showMessageDialog(this, "올바르지 않는 정보입니다.\n다시 시도해 주세요");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		logInCheck();
	}
	
	public static void main(String[] args) {
		new LoginForm();

	}

}
