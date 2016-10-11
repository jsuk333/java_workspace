package com.sds.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import com.sds.dto.TestDTO;
import com.sds.server.MainServer;

public class TestDAO {
	Connection con;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	MainServer mainServer;
	public TestDAO(Connection con, MainServer mainServer) {
		this.con = con;
		this.mainServer = mainServer;
	}
	
	public Vector<TestDTO> selectAll(){
		String sql = "select * from test";
		Vector<TestDTO> v = new Vector<TestDTO>();
		
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			mainServer.log_area.append("[ Debug ] : selectAll method requested\n");
			while(rs.next()){
				TestDTO dto = new TestDTO();
				dto.setId(rs.getString("id"));
				dto.setPassword(rs.getString("password"));
				v.add(dto);
			}
			return v;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} finally{
				try {
					if(rs != null){
						rs.close();
					}
					if(pstmt != null){
						pstmt.close();
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
}
