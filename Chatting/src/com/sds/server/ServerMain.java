/*서버관리자가 쉽게 모니터링 할 수 있도록 하기 위해 gui기반 서버로 구축 */
package com.sds.server;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ServerMain extends JFrame implements Runnable, ActionListener{
	JPanel p_north;
	JTextField t_port;
	JButton bt;
	JTextArea area;
	JScrollPane scroll;
	int port=7777;
	//메인 쓰레드는 프로세스를 운영하는 목적으로 실행되어야 하는 실행부이므로, 절대 무한 루프나 대기상태로 빠뜨리면 안된다.
	//안드로이드나 아이폰은 에러
	//다수의 접속자를 실시간으로 받아들이기 위해서는 별도의 시ㅏㄹ행부를 생성하여 While 돌리자 ==쓰레드 이용
	Thread acceptThread;
	ServerSocket server;
	Connection con;
	//드라이버 경로 설정
	String driver="oracle.jdbc.driver.OracleDriver";
	String url="jdbc:oracle:thin:@localhost:1521:XE";
	String user="java0819";
	String password="java0819";
	
	public ServerMain() {
		p_north=new JPanel();
		t_port=new JTextField(Integer.toString(port),7);
		bt=new JButton("가동");
		area=new JTextArea();
		scroll=new JScrollPane(area);
		
		area.setBackground(Color.yellow);
		
		p_north.add(t_port);
		p_north.add(bt);
		
		add(p_north,BorderLayout.NORTH);
		add(scroll);
		
		bt.addActionListener(this);
		
		setBounds(400, 300, 300, 400);
		setVisible(true);
		addWindowListener(new WindowAdapter() {
			
			public void windowClosing(WindowEvent e) {
				//데이터 베이스 연결 해제 하자
				if(con!=null){
					try {
						con.close();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				//프로세스 종료
				System.exit(0);
			}
		});
		
	}
	//접속자 감지 무한 루프로
	public void run() {
		startServer();
	}
	//서버 가동 메서드
	public void startServer(){
		try {
			server=new ServerSocket(port);
			area.append("서버 생성 완료 \n");
			//오라클 접속
			
			try {
				Class.forName(driver);
				con=DriverManager.getConnection(url, user, password);
				if(con!=null){
					setTitle("접속 성공");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			while(true){
				Socket client=server.accept();
				ServerThread st=new ServerThread(this,client);
				st.start();
				area.append("접속자 감지\n");
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void actionPerformed(ActionEvent arg0) {
		acceptThread=new Thread(this);
		acceptThread.start();
		
	}
	public static void main(String[] args) {
		new ServerMain();
	}

}
