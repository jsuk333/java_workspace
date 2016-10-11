package com.sds.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Test {

	public static void main(String[] args) {
		// 인증을 거친다.(원격지의 오라클 접속시 네트웍이 연결 되어야 한다.)
		// java sql패키지에서 데이터 베이스 관련 클래스를 지원한다.

		// 데이터 베이스 접속!!
		String url = "jdbc:oracle:thin:@localhost:1521:XE";
		String user = "java0819";
		String password = "java0819";
		// 어떤 데이터 베이스 제품을 사용할지 먼저 그 드라이버를 로드해야 한다.
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("드라이버 로드 성공");
			// 접속시도 , connection interface는 접속 시도 시 성공하 경우만 메모리에 올라온다.
			con = DriverManager.getConnection(url, user, password);
			if (con != null) {
				System.out.println("접속성공");
				// 원하는 쿼리문 수행
				// 쿼리 수행 인터페이스 : 쿼리문을 네트웍으로 전송한다.
				String sql = "select * from topcategory";
				pstmt = con.prepareStatement(sql);
				// 쿼리 수행 메서드
				rs = pstmt.executeQuery();
				// resultset은 커서라는 포인트를 지원함으로 원하는 records를 접근하려면 커서를 옮겨가며 작동해야
				// 하나다.
				rs.next();
				rs.next();
				rs.next();
				rs.next();
				String title = rs.getString("title");
				int topcategory_id = rs.getInt("topcategory_id");
				System.out.println(topcategory_id + title);

			} else {
				System.out.println("접속실패");
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("드라이버 로드 실패");
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(rs!=null){
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(pstmt!=null){
				try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(con!=null){
				try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

}
