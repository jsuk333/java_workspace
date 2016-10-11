package com.sds.db;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.plaf.synth.SynthSeparatorUI;

public class BookMain extends JFrame implements ItemListener,ActionListener{
	JPanel p_west;//좌측 입력폼
	Choice ch_top,ch_sub;//상,하위 카테고리
	JTextField t_name,t_publisher,t_bookname,t_author,t_price;
	JButton bt_regit;
	//북쪽 관련
	Choice ch_category;
	JTextField t_keyword;
	JButton bt_search;
	JPanel p_north;
	
	//센터관련
	JTable table;
	JScrollPane scroll;
	
	//접속 정보 (jdbc = java database connectivity)
	String url="jdbc:oracle:thin:@localhost:1521:XE";
	String user="java0819";
	String password="java0819";
	
	//sql 관련
	Connection con;//접속 정보를 가지고 있다. 따라서 접속을 끈으려면 이 객체를 이용해야 한다.
	PreparedStatement pstmt;//쿼리문 수행 객체
	ResultSet rs;//셀렉트문 수행시 결과 집합을 담는 역할을 수행하는 인터페이스
	
	public BookMain() {
		// TODO Auto-generated constructor stub
		
		p_west=new JPanel();
		ch_top=new Choice();
		ch_sub=new Choice();
		t_name=new JTextField(10);
		t_publisher=new JTextField(10);
		t_bookname=new JTextField(10);
		t_author=new JTextField(10);
		t_price=new JTextField(10);
		bt_regit=new JButton("등록");
		
		ch_category=new Choice();
		t_keyword=new JTextField(20);
		bt_search=new JButton("검색");
		p_north=new JPanel();
		
		table=new JTable();
		
		
		scroll=new JScrollPane(table);
		//-------------------------------------------------------------------
		
		ch_top.setPreferredSize(new Dimension(100, 30));
		ch_sub.setPreferredSize(new Dimension(100, 30));
		p_west.setPreferredSize(new Dimension(150, 550));
		p_west.setBackground(Color.WHITE);
		//--------------------------------------------------------------------
		ch_top.add("선택▼");
		ch_sub.add("선택▼");
		
		ch_category.setPreferredSize(new  Dimension(100, 20));
		ch_category.add("선택▼");
		ch_category.add("도서명");
		ch_category.add("출판사");
		ch_category.add("저자");
		
		p_west.add(ch_top);
		p_west.add(ch_sub);
		p_west.add(t_name);
		p_west.add(t_bookname);
		p_west.add(t_publisher);
		p_west.add(t_author);
		p_west.add(t_price);
		p_west.add(bt_regit);
		
		p_north.add(ch_category);
		p_north.add(t_keyword);
		p_north.add(bt_search);
		
		
		//----------------------------------------------------
		
		add(p_west,BorderLayout.WEST);
		
		add(p_north,BorderLayout.NORTH);
		
		add(scroll,BorderLayout.CENTER);
		//-----------------------------------------------------
		//상위 카테고리 초이스의 리스너 연결
		ch_top.addItemListener(this);
		
		//----------------------------------------------------
		//윈도우와 윈도우 리스너와 연결
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				//db 닫기
				if(con!=null){
					try {
						con.close();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				System.exit(-1);
				
			}
		});
		//---------------------------
		//버튼고ㅏ 리스너연결
		bt_regit.addActionListener(this);
		
		//---------------------------------
		
		setSize(800, 600);
		setLocationRelativeTo(null);
		setVisible(true);
		connect();
		getTopCategory();
		
	}
	
	//데이터 베이스 연결하기
	public void connect(){
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//JOptionPane.showMessageDialog(this, "드라이버 로드 성공");
			setTitle("드라이버 로드 성공");
			con=DriverManager.getConnection(url, user, password);
			if(con==null){
				JOptionPane.showMessageDialog(this, "접속실패");
				return;
			}
			//JOptionPane.showMessageDialog(this, "접속성공");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//상위 카테고리 가져오기
	public void getTopCategory(){
		//드라이버 로드
		try{
			String sql="select * from topcategory";
			this.setTitle("접속성공");
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			//초이스 컴포넌트에 데이터 채우기
			while(rs.next()){
				ch_top.add(rs.getString("title"));
			}
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(rs!=null){
				try {
					rs.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			if(pstmt!=null){
				try {
					pstmt.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
		}
	}
	//하위 카테고리 목록 가져오기
	public void getSubCategory(String top_title){
		StringBuffer sql=new StringBuffer();
		sql.append("select * from subcategory ");
		sql.append("where topcategory_id=(");
		sql.append("select topcategory_id ");
		sql.append("from topcategory where title='"+top_title+"')");
		
		System.out.println(sql.toString());
		try {
			pstmt=con.prepareStatement(sql.toString());
			rs=pstmt.executeQuery();
			ch_sub.removeAll();
			while(rs.next()){
				ch_sub.add(rs.getString("title"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(rs!=null){
				try {
					rs.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			if(pstmt!=null){
				try {
					pstmt.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		
	}
	@Override
	public void itemStateChanged(ItemEvent e) {
		Choice ch=(Choice)e.getSource();
		String top_title=ch.getSelectedItem();
		getSubCategory(top_title);
		//쿼리 수행
		
	}
	public void registBook(){}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		//book table에 레코드 한건 넣기 메서드
		String sql="insert into book(book_id,subcategory_id,bookname,publisher,author,price,regdate)";
		sql+=" values(seq_book.nextval,"+7+")";
		registBook();
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new BookMain();
	}


}
