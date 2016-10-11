package com.sds.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Test {

	public static void main(String[] args) {
		// ������ ��ģ��.(�������� ����Ŭ ���ӽ� ��Ʈ���� ���� �Ǿ�� �Ѵ�.)
		// java sql��Ű������ ������ ���̽� ���� Ŭ������ �����Ѵ�.

		// ������ ���̽� ����!!
		String url = "jdbc:oracle:thin:@localhost:1521:XE";
		String user = "java0819";
		String password = "java0819";
		// � ������ ���̽� ��ǰ�� ������� ���� �� ����̹��� �ε��ؾ� �Ѵ�.
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("����̹� �ε� ����");
			// ���ӽõ� , connection interface�� ���� �õ� �� ������ ��츸 �޸𸮿� �ö�´�.
			con = DriverManager.getConnection(url, user, password);
			if (con != null) {
				System.out.println("���Ӽ���");
				// ���ϴ� ������ ����
				// ���� ���� �������̽� : �������� ��Ʈ������ �����Ѵ�.
				String sql = "select * from topcategory";
				pstmt = con.prepareStatement(sql);
				// ���� ���� �޼���
				rs = pstmt.executeQuery();
				// resultset�� Ŀ����� ����Ʈ�� ���������� ���ϴ� records�� �����Ϸ��� Ŀ���� �Űܰ��� �۵��ؾ�
				// �ϳ���.
				rs.next();
				rs.next();
				rs.next();
				rs.next();
				String title = rs.getString("title");
				int topcategory_id = rs.getInt("topcategory_id");
				System.out.println(topcategory_id + title);

			} else {
				System.out.println("���ӽ���");
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("����̹� �ε� ����");
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
