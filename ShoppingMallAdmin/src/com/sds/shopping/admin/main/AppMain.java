/*쇼핑몰의 관리자측 메인 프레임
 * 따라서 공유할 데이터가 있다면 이 클래스에 선언하면 좋다...*/
package com.sds.shopping.admin.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.sds.shopping.admin.member.MemberMain;
import com.sds.shopping.admin.order.OrderMain;
import com.sds.shopping.admin.partner.PartnerMain;
import com.sds.shopping.admin.product.ProductMain;
import com.sds.shopping.admin.sales.SalesMain;
import com.sds.shopping.admin.staff.StaffMain;

public class AppMain extends JFrame{
	private static Connection con;
	JPanel p_north;
	JButton[] menu;
	String[] menuTitle={"상품관리","주문관리","회원관리","매출관리","업체관리","관리자정보"};
	public static final int CONTENT_WIDTH=700;
	public static final int CONTENT_HEIGHT=500;
	//서쪽 구성물
	//센터 구성물
	JPanel p_container;//모든 컨테이너를 위치 시킬 패널
	JPanel[] p_contents;//각 패널들을 가리킬 상위 자료형
	public AppMain() {
		connect();
		p_north=new JPanel();
		menu=new JButton[menuTitle.length];
		for(int i=0;i<menu.length;i++){
			menu[i]=new JButton(menuTitle[i]);
			p_north.add(menu[i]);
		}
		
		add(p_north,BorderLayout.NORTH);
		
		addWindowListener(new WindowAdapter() {
			//데이터 베이스 접속 끊기 프로세스 종료
			@Override
			public void windowClosing(WindowEvent e) {
				//db접속 해제
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
		
		p_container=new JPanel();
		p_container.setBackground(Color.yellow);
		p_contents=new JPanel[menuTitle.length];
		
		p_contents[0]=new ProductMain(); 
		p_contents[1]=new OrderMain();
		p_contents[2]=new MemberMain();
		p_contents[3]=new SalesMain();
		p_contents[4]=new PartnerMain();
		p_contents[5]=new StaffMain();
		
		for(int i=0;i<p_contents.length;i++){
			p_container.add(p_contents[i]);
		}
		p_contents[0].setVisible(true);		
		
		add(p_container);
		
		setSize(700	, 600);
		setVisible(true);
		
	}
	public void connect(){
		//자바ㅏ 소스 코드 외부에 둔 특정 파일의 내용을  읽어와서
		//데이터베이스 정보를 구성하면, 유지보수성이 높아진다.
		//왜?? db 정보가 변경되어도 자바소스를 바꾸지 않아도 된다.
		
		FileInputStream fis=null;
		URL url=this.getClass().getClassLoader().getResource("com/sds/shopping/db/db.data");
		try {
			fis=new FileInputStream(url.getPath());
			setTitle("");
			
			//key value 로 이루어진 데이터를 해석해주는 객체
			Properties props=new Properties();
			props.load(fis);//파일을 인식한 시점
			String driver=props.getProperty("driver");
			String host=props.getProperty("url");
			String user=props.getProperty("user");
			String password=props.getProperty("password");
			Class.forName(driver);
			con=DriverManager.getConnection(host, user, password);
			if(con!=null){
				setTitle("접속성공");
			}else{
				setTitle("접속 실패!!!!!!!!!!");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {			
			e.printStackTrace();
		} catch (ClassNotFoundException e) {			
			e.printStackTrace();
		} catch (SQLException e) {			
			e.printStackTrace();
		}finally{
			if(fis!=null){
				try {
					fis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}
	public static Connection getConnection(){
		
		return con;
	}
	public static void main(String[] args) {
		new  AppMain();

	}
	
}
