/* 영화 예매
 * 사이즈 600X550
 * 영화목록 영화등록 예매내역 좌석선택
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
	
	String[] menuTitle={"상영중영화","영화등록","예매내역","좌석선택"};
	JButton[] menu;
	JPanel[] content;
	JPanel center,p_north;
	Color[] bg={Color.RED,Color.yellow,Color.green,Color.pink};
	//데이터 베이스 접속 관련
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
		//클래스 명은 틀리지만 모두 jpanel의 자식이다.
		//컨텐츠 패널들이 대체될 센터 영역
		center=new JPanel();
		
		add(p_north,BorderLayout.NORTH);
		
		//가운데 영역에 컨텐츠 패널을 미리 붙이자
		for(int i=0;i<menu.length;i++){
			content[i].setPreferredSize(new Dimension(600, 500));
			//content[i].setBackground(bg[i]);
			center.add(content[i]);
			content[i].setVisible(false);
		}
		
		add(center);
		//첫번째 content 보이기
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
	//데이터베이스 접속 함수
	public void connectDB(){
		try {
			Class.forName(driver);
			con=DriverManager.getConnection(url, user, password);
			if(con!=null){
				setTitle("DB 접속 성공");
			}else{
				setTitle("DB 접속 실패");
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//원하는 화면 보이기
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
