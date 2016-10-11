/* ��ȭ ����
 * ������ 600X550
 * ��ȭ��� ��ȭ��� ���ų��� �¼�����
 * 
 * */
package com.sds.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.sds.movie.list.MovieList;
import com.sds.movie.regist.MovieRegist;
import com.sds.movie.reservation.Reservation;
import com.sds.movie.seat.SeatChoice;

public class AppMain extends JFrame implements ActionListener {
	
	String[] menuTitle={"���߿�ȭ","��ȭ���","���ų���","�¼�����"};
	JButton[] menu;
	JPanel[] content;
	JPanel center,p_north;
	Color[] bg={Color.RED,Color.yellow,Color.green,Color.pink};
	//������ ���̽� ���� ����
	String driver="oracle.jdbc.driver.OracleDriver";
	String url="jdbc:oracle:thin:@localhost:1521:XE";
	String user="java0819";
	String password="java0819";
	private static Connection con;
	public AppMain() {
		connectDB();
		menu=new JButton[4];
		p_north=new JPanel(new GridLayout(1, menu.length));
		content=new JPanel[4];
		for(int i=0;i<menu.length;i++){
			menu[i]=new JButton(menuTitle[i]);
			p_north.add(menu[i]);
			menu[i].addActionListener(this);
		}
		content[0]=new MovieList();
		content[1]=new MovieRegist();
		content[2]=new Reservation();
		content[3]=new SeatChoice();
		//Ŭ���� ���� Ʋ������ ��� jpanel�� �ڽ��̴�.
		//������ �гε��� ��ü�� ���� ����
		center=new JPanel();
		
		add(p_north,BorderLayout.NORTH);
		
		//��� ������ ������ �г��� �̸� ������
		for(int i=0;i<menu.length;i++){
			content[i].setPreferredSize(new Dimension(600, 500));
			//content[i].setBackground(bg[i]);
			center.add(content[i]);
			content[i].setVisible(false);
		}
		
		add(center);
		//ù��° content ���̱�
		showContent(0);
		setSize(600, 550);
		setVisible(true);
		
		
		addWindowListener(new WindowAdapter() {
	
			public void windowClosing(WindowEvent e) {
				if(con!=null){
					try {
						con.close();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
				System.exit(-1);
			}
		});
	}
	//�����ͺ��̽� ���� �Լ�
	public void connectDB(){
		try {
			Class.forName(driver);
			con=DriverManager.getConnection(url, user, password);
			if(con!=null){
				setTitle("DB ���� ����");
			}else{
				setTitle("DB ���� ����");
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//���ϴ� ȭ�� ���̱�
	public void showContent(int current){
		for(int i=0;i<menu.length;i++){
			if(current!=i){
				content[i].setVisible(false);
			}else{
				content[current].setVisible(true);
			}
		}
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj=e.getSource();
		for(int i=0;i<menu.length;i++){
			if(menu[i]==obj){
				showContent(i);
			}
		}
	}
	public static Connection getConnection(){
		return con;
	}
	public static void main(String[] args) {
		new AppMain();
	}
}
