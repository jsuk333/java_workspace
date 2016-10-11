package com.sds.shopping.client.main;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class ClientMain extends JFrame implements ActionListener{
	JPanel p_north;
	JPanel p_container;//화면 전환용
	JScrollPane scroll;
	JButton[] menu;
	String[] menuTitle={"상의","하의","액세서리","신발","MyPage","장바구니"};
	
	
	ProductMain productMain;
	public ClientMain() {
		p_container=new JPanel();
		p_north=new JPanel();
		scroll=new JScrollPane(p_container);
		menu=new JButton[menuTitle.length];
		for(int i=0;i<menu.length;i++){
			menu[i]=new JButton(menuTitle[i]);
			menu[i].addActionListener(this);
			p_north.add(menu[i]);
		}
		
		productMain=new ProductMain();
		p_container.add(productMain);
		
		
		add(p_north,BorderLayout.NORTH);
		add(scroll);
		
		setSize(800, 750);
		setVisible(true);
		
	}
	public static Connection getConnection(){
		String driver="oracle.jdbc.driver.OracleDriver";
		String url="jdbc:oracle:thin:@localhost:1521:XE";
		String user="shop";
		String password="shop";
		Connection con=null;
		
		try {
			Class.forName(driver);
			con=DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return con;
	}
	
	public void actionPerformed(ActionEvent e) {
	
		
	}
	public static void main(String[] args) {
		new ClientMain();

	}

}
