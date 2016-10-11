package com.sds.katalk;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.JTextField;

public class LoginForm extends JFrame implements ActionListener{
	JTextField tf;
	JTextField tf2;
	JButton bt;
	JButton bt2;
	JPanel pl;
	JPanel pl_ct;
	JPanel pl_ct2;
	JPanel pl_ct3;
	JLabel lb;
	JLabel lb2;
	JLabel lb3;
	String user="java0819";
	String url="jdbc:oracle:thin:@localhost:1521:XE";
	String password="java0819";
	Connection con;//접속 정보를 보유한 인터페이스
	PreparedStatement psmt;
	ResultSet rs;
	public LoginForm() {
		super("LOGIN");
		lb = new JLabel("로그인",0);
		lb2 = new JLabel("ID");
		lb3 = new JLabel("PW");
		bt = new JButton("Login");
		bt2 = new JButton("Sign");
		tf = new JTextField(15);
		tf2 = new JTextField(15);
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
		//add(pl_ct2,BorderLayout.CENTER);
		//add(lb2,BorderLayout.CENTER);
		//add(lb3,BorderLayout.CENTER);
		//add(tf,BorderLayout.CENTER);
		//add(tf2,BorderLayout.CENTER);
		add(pl,BorderLayout.SOUTH);
				
				
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(350,180);
		setVisible(true);
		
		bt.addActionListener(this);
		
	}
	public void logInCheck(){
		//드라이버 로드
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			try {
				con=DriverManager.getConnection(url, user, password);
				if(con!=null){
					System.out.println("성공");
					String sql="select * from chatmember ";
					sql=sql+"where id=? and password=?";
					psmt=con.prepareStatement(sql);
					psmt.setString(1, tf.getText());
					psmt.setString(2, tf2.getText());
					//쿼리 수행
					System.out.println(sql);
					rs=psmt.executeQuery();
					
					if(rs.next()){
						JOptionPane.showMessageDialog(this, "인증 성공");
						
					}else{
						JOptionPane.showMessageDialog(this, "인증 실패");
					}
					
				}else{
					System.out.println("실패");
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//드라이버 접속
		//쿼리문 수행
		//자원 닫기
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		logInCheck();
	}
	
	public static void main(String[] args) {
		new LoginForm();

	}

}
