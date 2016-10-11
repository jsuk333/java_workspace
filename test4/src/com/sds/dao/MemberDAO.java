package com.sds.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.sds.dto.MemberDTO;
import com.sds.dto.RoomsDTO;
import com.sds.server.MainServer;

public class MemberDAO {

	Connection con;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	MainServer mainServer;

	int room_number;
	
	public MemberDAO(Connection con, MainServer mainServer) {
		this.con = con;
		this.mainServer = mainServer;
	}

	//���� ��� �޼���
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
			int result = pstmt.executeUpdate();
			if( result == 1 ){
				return true;
			}
		} catch (SQLException e) {
			mainServer.log_area.append("[ Debug ] : RegisterUser method occured error.\n");
		} finally{
			release(pstmt);
		}
		
		return false;
	}
	
	//member���̺� ip������Ʈ
	public boolean updateUserIp(String ip, String id){
		String sql = "update member set user_ip=?, isconnecting=1 where id=?";
		
		try {
			mainServer.log_area.append("[ Debug ] : updateUserIp method requested.\n");
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, ip);
			pstmt.setString(2, id);
			int result = pstmt.executeUpdate();
			
			if( result == 1){
				
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			mainServer.log_area.append("[ Debug ] : updateUserIp method occured error.\n");
		} finally {
			release(pstmt);
		}
		return false;
	}
	
	//�α���
	public boolean haveLogedIn(String id){
		if( !id.equals("admin")){
			String sql = "select isconnecting from member where id=?";
			try {
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, id);
				rs = pstmt.executeQuery();
				mainServer.log_area.append("[ Debug ] : Have Loged in method requested.\n");
				
				if (rs.next()) {
					int isConnect = rs.getInt("isconnecting");
					if(isConnect == 1)
						return false;
					else 
						return true;
				}
				else {
					return true;	
				}
			} catch (SQLException e) {
				mainServer.log_area.append("[ Debug ] : Error occured on Have Loged in method.\n");
				return false;
				
			} finally {
				release(pstmt, rs);
			}
		}
		else{
			return true;
		}
	}
	
	public MemberDTO loginCheck(String id, String pwd) {
		
		String sql = "select * from member where id='"+id+"' and password='"+pwd+"'";
		MemberDTO dto = new MemberDTO();
		
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			mainServer.log_area.append("[ Debug ] : loginCheck method requested.\n");
			
			if (rs.next()) {
				dto.setId(rs.getString("id"));
				dto.setName(rs.getString("name"));
				dto.setNick_name(rs.getString("nick_name"));
				dto.setE_mail(rs.getString("e_mail"));
				return dto;
			}
			else {
				dto = null;
				return dto;	
			}
			
		} catch (SQLException e) {
			mainServer.log_area.append("[ Debug ] : Error occured loginCheck method.\n");
			return null;
			
		} finally {
			release(pstmt, rs);
		}
	}
	//ȣ��Ʈ ���̵� �ߺ� �˻�
	public boolean duplicatedHostID(String id){
		String sql = "select * from rooms where host_id=?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			mainServer.log_area.append("[ Debug ] : DuplicatedHostID method requested.\n");
			
			if (rs.next()) {
				return false;
			}else{
				return true;
			}
			
		} catch (SQLException e) {
			mainServer.log_area.append("[ Debug ] : Error occured on DuplicatedHostID method.\n");
			return false;
			
		} finally {
			release(pstmt, rs);
		}
	}
	
	
	//��� �˻�
	public int checkEmptyRoom(){
		String sql = "select num from rooms where isusing=0 order by num asc";
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			mainServer.log_area.append("[ Debug ] : CheckEmptyRoom method requested.\n");
			int room_num = 0;
			if (rs.next()) {
				room_num = rs.getInt("num");
			}else{
				room_num = 0;
			}
			return room_num;
			
		} catch (SQLException e) {
			mainServer.log_area.append("[ Debug ] : Error occured on CheckEmptyRoom method.\n");
			return 0;
			
		} finally {
			release(pstmt, rs);
		}
	}
	
	//�游��� ���ʹ�ȣ ��� ���� ��
	public boolean createRoom(String[] data){
		try {
			String sql = "insert into rooms (num, title, ispw, pw, max_join, cur_join, isusing, host_id) "
					+ "values (seq_room.nextval, ?, ?, ?, ?, 1, 1, ?)";
			mainServer.log_area.append("[ Debug ] : CreateRoom method requested.\n");
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, data[0]);		//title
			pstmt.setInt(2, Integer.parseInt(data[1]));		//ispw
			pstmt.setString(3, data[2]);			//pw
			pstmt.setInt(4, Integer.parseInt(data[3]));			//max
			pstmt.setString(5, data[4]);			//host_id
			int result = pstmt.executeUpdate();
			if( result == 1 ){
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			mainServer.log_area.append("[ Debug ] : CreateRoom method occured error.\n");
		} finally{
			release(pstmt);
		}
		return false;
	}
	
	//�游��� ���ʹ�ȣ ��� ���� ��
	public boolean createRoom(String[] data, int room_num){
		try {
			String sql = "update rooms set title=?, ispw=?, pw=?, max_join=?, "
					+ "cur_join=1, isusing=1, host_id=? where num=?";
			mainServer.log_area.append("[ Debug ] : UpdateRoom method requested.\n");
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, data[0]);		//title
			pstmt.setInt(2, Integer.parseInt(data[1]));		//ispw
			pstmt.setString(3, data[2]);			//pw
			pstmt.setInt(4, Integer.parseInt(data[3]));			//max
			pstmt.setString(5, data[4]);			//host_id
			pstmt.setInt(6, room_num);
			int result = pstmt.executeUpdate();
			if( result == 1 ){
				return true;
			}
		} catch (SQLException e) {
			mainServer.log_area.append("[ Debug ] : UpdateRoom method occured error.\n");
		} finally{
			release(pstmt);
		}
		return false;
	}
	
	//member�� ȣ��Ʈ�� �� ���̺� �� ��ȣ �־��ֱ�
	public boolean updateMemberRoomNumHost(String id){
		try {
			String sql = "update member set room_num=(select num from rooms where host_id=?) where id=?";
			mainServer.log_area.append("[ Debug ] : UpdateMemberRoomNum method requested.\n");
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);	
			pstmt.setString(2, id);	
			
			int result = pstmt.executeUpdate();
			if( result == 1 ){
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			mainServer.log_area.append("[ Debug ] : UpdateMemberRoomNum method occured error.\n");
		} finally{
			release(pstmt);
		}
		return false;
	}
	
	//������ ���� ���� ���� ���� Ȯ��
	public int numberOfCurrentJoin(int room_num){
		String sql = "select cur_join from rooms where num=?";
		try {
			mainServer.log_area.append("[ Debug ] : Number Of Current Join method requested.\n");
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, room_num);
			rs = pstmt.executeQuery();
			int result = 0;
			if (rs.next()) {
				result = rs.getInt("cur_join");
			}
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			mainServer.log_area.append("[ Debug ] : Error occured on Number Of Current Join method.\n");
			return 0;
			
		} finally {
			release(pstmt, rs);
		}
	}

	//join�� �� ��� ���� Ȯ��
	public int checkIsPw(int room_num){
		String sql = "select ispw from rooms where num=?";
		try {
			mainServer.log_area.append("[ Debug ] : Check Is Pw method requested.\n");
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, room_num);
			rs = pstmt.executeQuery();
			int result = 0;
			if (rs.next()) {
				result = rs.getInt("ispw");
			}
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			mainServer.log_area.append("[ Debug ] : Error occured on Check Is Pw method.\n");
			return 0;
			
		} finally {
			release(pstmt, rs);
		}
	}
	
	//�н����� ��ġ ����
	public boolean checkCorrectPwd(String pw, int room_num){
		String sql = "select * from rooms where num=? and pw=?";
		try {
			mainServer.log_area.append("[ Debug ] : Check Correct Pw method requested.\n");
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, room_num);
			pstmt.setString(2, pw);
			rs = pstmt.executeQuery();
			boolean result = false;
			if (rs.next()) {
				result = true;
			}
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			mainServer.log_area.append("[ Debug ] : Error occured on Check Correct Pw method.\n");
			return false;
			
		} finally {
			release(pstmt, rs);
		}
	}
	
	//join �� member�� �� ��ȣ �־��ֱ�
	public boolean joinRoom(String id, int room_num){
		String sql = "update member set room_num=? where id=?";
		try {
			mainServer.log_area.append("[ Debug ] : JoinRoom method requested.\n");
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, room_num);
			pstmt.setString(2, id);
			int result = pstmt.executeUpdate();
			boolean canjoin = false;
			if(result == 1){
				canjoin = true;
			}
			return canjoin;
		} catch (SQLException e) {
			e.printStackTrace();
			mainServer.log_area.append("[ Debug ] : JoinRoom method occured error.\n");
			return false;
			
		} finally {
			release(pstmt);
		}
	}
	
	//join�� rooms�� cur_join����
	public void plusCurrnetJoin(int room_num, int people_num){
		String sql = "update rooms set cur_join=? where num=?";
		try{
			mainServer.log_area.append("[ Debug ] : PlusCurrentJoin method requested.\n");
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, people_num);
			pstmt.setInt(2, room_num);
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			mainServer.log_area.append("[ Debug ] : PlusCurrentJoin method occured error.\n");
		
		} finally {
			release(pstmt);
		}
	}
	
	//join �� ȣ��Ʈ ������ ������ ����
	public String getHostIp(int room_num){
		String sql = "select user_ip from member where id=(select host_id from rooms where num=?)";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, room_num);
			rs = pstmt.executeQuery();
			mainServer.log_area.append("[ Debug ] : GetHostIP method requested.\n");
			String ip = "";
			if (rs.next()) {
				ip = rs.getString("user_ip");
			}
			return ip;
			
		} catch (SQLException e) {
			mainServer.log_area.append("[ Debug ] : Error occured on GetHostIP method.\n");
			return null;
		} finally {
			release(pstmt, rs);
		}
	}
	
	
	//�α׿��� �� user_ip, isconnecting 0���� �ٲ��ֱ�
	public void disConnection(String id){
		String sql = "update member set isconnecting=0, user_ip='', room_num=0 where id=?";
		try {
			mainServer.log_area.append("[ Debug ] : Disconnection method requested.\n");
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);

			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			mainServer.log_area.append("[ Debug ] : Disconnection method occured error.\n");
		} finally {
			release(pstmt);
		}
	}
	
	public void closeRoom(String id){
		String sql = "update rooms set title='', isusing=0, ispw=0, pw='', max_join=0, cur_join=0, host_id=''"
				+ "where host_id=?";
		try {
			mainServer.log_area.append("[ Debug ] : CloseRoom method requested.\n");
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);

			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			mainServer.log_area.append("[ Debug ] : CloseRoom method occured error.\n");
		} finally {
			release(pstmt);
		}
	}
	
	
	//������� ���� �ۼ�
	public ArrayList<MemberDTO> listUser(){//db����� ������ �ҷ�����
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
	public ArrayList<MemberDTO> searchUser(String id){//���̵� �˻��ؼ� ���� �ڷ� ����
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
	public boolean editUser(MemberDTO dto){//������ â���� ��û�� �ڷ� �����ϰ� ��� ��ȯ
		boolean flag=false;
		String sql="update member set "
				+ " id=?, "
				+ "password=?, "
				+ "name=?, "
				+ "nick_name=?,"
				+ "gender=?,"
				+ "e_mail=?,"
				+ "isconnecting=?,"
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
				pstmt.setInt(8, dto.getIsAdmin());
				pstmt.setString(9, dto.getUser_ip());
				pstmt.setInt(10, dto.getMember_id());
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
	public boolean deleteUser(String id){//������ â���� ��û�� ���� �۾� �� ��� ��ȯ
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
	}//�������
	public boolean checkId(String ch_id){
		boolean flag=false;
		
		String sql="select * from member where id=?";
		try {
			mainServer.log_area.append("[ Debug ] : checkid method is requested.\n");
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, ch_id);
			rs=pstmt.executeQuery();
			if(rs.next()){
				flag=true;
			}else{
				flag=false;
			}
						
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return flag;
	}
	
	public void release(PreparedStatement pstmt, ResultSet rs){
		try {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
		} catch (SQLException e) {
		}
	}
	
	public ArrayList<RoomsDTO> listRoom(){
		String sql="select * from rooms";
		ArrayList<RoomsDTO> list=new ArrayList<RoomsDTO>();
		try {
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				RoomsDTO dto=new RoomsDTO();
				dto.setNum(rs.getInt("num"));
				dto.setTitle(rs.getString("title"));
				dto.setIsPw(rs.getInt("ispw"));
				dto.setPw(rs.getString("pw"));
				dto.setMax_join(rs.getInt("max_join"));
				dto.setCur_join(rs.getInt("cur_join"));
				dto.setIsUsing(rs.getInt("isusing"));
				dto.setHost_id(rs.getString("host_id"));
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
	
	public ArrayList<RoomsDTO> refreshRooms(){
		String sql="select * from rooms";
		ArrayList<RoomsDTO> list=new ArrayList<RoomsDTO>();
		try {
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				RoomsDTO dto=new RoomsDTO();
				dto.setNum(rs.getInt("num"));
				dto.setTitle(rs.getString("title"));
				dto.setIsPw(rs.getInt("ispw"));
				dto.setPw(rs.getString("pw"));
				dto.setMax_join(rs.getInt("max_join"));
				dto.setCur_join(rs.getInt("cur_join"));
				dto.setIsUsing(rs.getInt("isusing"));
				dto.setHost_id(rs.getString("host_id"));
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
	
	public ArrayList<MemberDTO> showConnector(){
		String sql="select * from member where isconnecting=1";
		ArrayList<MemberDTO> list=new ArrayList<MemberDTO>();
		try {
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				MemberDTO dto=new MemberDTO();
				dto.setNick_name(rs.getString("nick_name"));
				dto.setGender(rs.getInt("gender"));
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
	
	//------------------------------------------------------------------------------------------------------------
	
		public String getHostIP(int room_no){//ȣ��Ʈ ������ ������ ����
			String sql="select user_ip as ip from member where member_id=(select host_id from rooms where room_num=?)";
			String hostip=null;
			try {
				pstmt=con.prepareStatement(sql);
				pstmt.setInt(1, room_no);
				rs=pstmt.executeQuery();
				
				if(rs.next()){
					hostip=rs.getString("ip");
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
			
			return hostip;
			
		}

		//�ֽ� �� �˻�
		public int checkLastRoom(){
			String sql = "select num from rooms where isusing=1 order by num asc";
			try {
				pstmt = con.prepareStatement(sql);
				rs = pstmt.executeQuery();
				mainServer.log_area.append("[ Debug ] : CheckEmptyRoom method requested.\n");
				int room_num = 0;
				if (rs.next()) {
					room_num = rs.getInt("num");
				}else{
					room_num = 0;
				}
				return room_num;
				
			} catch (SQLException e) {
				mainServer.log_area.append("[ Debug ] : Error occured on CheckEmptyRoom method.\n");
				return 0;
				
			} finally {
				release(pstmt, rs);
			}
		}
		//------------------------------------------------------------------------------------
	
	public void release(PreparedStatement pstmt){
		try {
			
			if (pstmt != null) {
				pstmt.close();
			}
		} catch (SQLException e) {
		}
	}
}
