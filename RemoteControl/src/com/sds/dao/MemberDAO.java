package com.sds.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import com.sds.dto.MemberDTO;
import com.sds.server.MainServer;

public class MemberDAO {

	Connection con;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	MainServer mainServer;

	public MemberDAO(Connection con, MainServer mainServer) {
		this.con = con;
		this.mainServer = mainServer;
	}

	public boolean registUser(String[] data){
		try {
			int gender = 0;
			if(data[4].equals("man")){
				gender = 0;
			}else if(data[4].equals("woman")){
				gender = 1;
			}
			String sql = "insert into member(member_id, id, password, name, nick_name, gender, e_mail) "
					+ "values (seq_member.nextval, ?, ?, ?, ?, ?, ?)";
			mainServer.log_area.append("[ Debug ] : RegisterUser method requested.\n");
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, data[0]);
			pstmt.setString(2, data[1]);
			pstmt.setString(3, data[2]);
			pstmt.setString(4, data[3]);
			pstmt.setInt(5, gender);
			pstmt.setString(6, data[5]);
			mainServer.log_area.append(sql+"\n");
			int result = pstmt.executeUpdate();
			if( result == 1 ){
				return true;
			}
		} catch (SQLException e) {
			mainServer.log_area.append("[ Debug ] : RegisterUser method occured error.\n");
		}
		
		return false;
	}
	
	public MemberDTO loginCheck(String sql) {

		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			mainServer.log_area.append("[ Debug ] : loginCheck method requested.\n");
			MemberDTO dto = new MemberDTO();
			if (rs.next()) {
				
				dto.setId(rs.getString("id"));
				dto.setName(rs.getString("name"));
				dto.setNick_name(rs.getString("nick_name"));
				dto.setE_mail(rs.getString("e_mail"));
				mainServer.log_area.append(rs.getString("id"));
				mainServer.log_area.append(rs.getString("name"));
				mainServer.log_area.append(rs.getString("nick_name"));
				mainServer.log_area.append(rs.getString("e_mail"));
			}
			return dto;
			
		} catch (SQLException e) {
			return null;
			
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public ArrayList<MemberDTO> listUser(){//db저장된 데이터 불러오기
		String sql="select * from member";
		ArrayList<MemberDTO> list=new ArrayList<MemberDTO>();
		try {
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				MemberDTO dto=new MemberDTO();
				dto.setMember_id(rs.getInt("member_id"));
				dto.setId(rs.getString("id"));
				dto.setPassword(rs.getString("password"));
				dto.setName(rs.getString("name"));
				dto.setNick_name(rs.getString("nick_name"));
				dto.setGender(rs.getInt("gender"));
				dto.setE_mail(rs.getString("e_mail"));
				dto.setRegdate(rs.getString("regdate"));
				dto.setIsConnection(rs.getInt("isconnecting"));
				dto.setRoom_num(rs.getInt("room_num"));
				dto.setIsAdmin(rs.getInt("isAdmin"));
				dto.setUser_ip(rs.getString("user_ip"));
				list.add(dto);				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return list;
	}
	public ArrayList<MemberDTO> searchUser(String id){//아이디 검색해서 얻은 자료 저장
		String sql="select * from member where id=?";
		ArrayList<MemberDTO> list=new ArrayList<MemberDTO>();
		try {
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, id);
			rs=pstmt.executeQuery();
			while(rs.next()){
				MemberDTO dto=new MemberDTO();
				dto.setMember_id(rs.getInt("member_id"));
				dto.setId(rs.getString("id"));
				dto.setPassword(rs.getString("password"));
				dto.setName(rs.getString("name"));
				dto.setNick_name(rs.getString("nick_name"));
				dto.setGender(rs.getInt("gender"));
				dto.setE_mail(rs.getString("e_mail"));
				dto.setRegdate(rs.getString("regdate"));
				dto.setIsConnection(rs.getInt("isconnecting"));
				dto.setRoom_num(rs.getInt("room_num"));
				dto.setIsAdmin(rs.getInt("isAdmin"));
				dto.setUser_ip(rs.getString("user_ip"));
				list.add(dto);	
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return list;
	}
	public boolean editUser(MemberDTO dto){//관리자 창에서 요청된 자료 수정하고 결과 반환
		boolean flag=false;
		String sql="update member set "
				+ " id=?, "
				+ "password=?, "
				+ "name=?, "
				+ "nick_name=?,"
				+ "gender=?,"
				+ "e_mail=?,"
				+ "isconnecting=?,"
				+ "room_num=?, "
				+ "isAdmin=?, "
				+ "user_ip=? "
				+ "where member_id=?";
		System.out.println(sql);
		try {
				pstmt=con.prepareStatement(sql);
				pstmt.setString(1, dto.getId());
				pstmt.setString(2, dto.getPassword());
				pstmt.setString(3, dto.getName());
				pstmt.setString(4, dto.getNick_name());
				pstmt.setInt(5, dto.getGender());
				pstmt.setString(6, dto.getE_mail());
				pstmt.setInt(7, dto.getIsConnection());
				pstmt.setInt(8, dto.getRoom_num());
				pstmt.setInt(9, dto.getIsAdmin());
				pstmt.setString(10, dto.getUser_ip());
				pstmt.setInt(11, dto.getMember_id());
				if(pstmt.executeUpdate()==1){
					flag=true;
				}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if(pstmt!=null){
				try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
		return flag;
	}
	public boolean deleteUser(String id){//관리자 창에서 요청한 삭제 작업 후 결과 반환
		System.out.println(id);
		String sql="delete from member where id=?";
		boolean result=false;
		System.out.println(sql);
		try {
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, id);
			int num=pstmt.executeUpdate();
			if(num!=0){
				result=true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
}
