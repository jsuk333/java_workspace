package com.sds.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JOptionPane;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.sds.dao.MemberDAO;
import com.sds.dto.ClientThreadDTO;
import com.sds.dto.MemberDTO;
import com.sds.dto.RoomsDTO;

public class ClientThread extends Thread{

	Socket socket;
	MainServer mainServer;
	BufferedWriter buffw;
	BufferedReader buffr;
	Connection con;
	boolean flag = true;
	StringBuffer sb = new StringBuffer();
	
	String sql;
	String ip;
	String id;

	String user_ip;//접속한 유저의 아이피 담을 변수
	int room_no;/////////////////////////////////////////////////////////////////
	public ClientThread(Socket socket, Connection con, String ip, MainServer mainServer) {
		this.socket = socket;
		this.mainServer = mainServer;
		this.con = con;
		this.ip = ip;
		try {
			buffr = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			buffw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			user_ip=socket.getInetAddress().getHostAddress();//소켓을 통해 아이피 획득
		} catch (IOException e) {
			this.mainServer.log_area.append("[ Debug ] : Thread IOException\n");
		}
	}
	
	public void listen(){
		try{
			String msg = buffr.readLine();
			mainServer.request = msg;
			analyzeProtocol(msg);
			
		}catch(IOException e){
			if(socket.isClosed()){
				if( con != null){
					try {
						con.close();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		}
	}
	
	public void speak(String msg){
		try{
				mainServer.log_area.append(msg+"\n");
				buffw.write(msg+"\n");
				buffw.flush();
		}catch(IOException e){
		}
	}
	
	//프로토콜 	분석 및 명령어 처리 매소드
	public void analyzeProtocol(String msg){
		try {
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject=(JSONObject)jsonParser.parse(msg);
			mainServer.log_area.append(msg+"\n");
			if( jsonObject.get("request").equals("login")){
				JSONObject obj=(JSONObject)jsonObject.get("data");
				id = (String)obj.get("id");
				if( haveLogedIn(id) ){
						String pwd = (String)obj.get("password");
						MemberDTO dto = new MemberDTO();
						dto = getLoginCheck(id, pwd);
						if (dto == null){
							sb.delete(0, sb.length());
							sb.append("{\"response\" : \"login\",");
							sb.append("\"data\" : {");
							sb.append("\"success\" : \"false\"");
							sb.append("}}");
							speak(sb.toString());
						}
						else {
							updateUserIp(ip, id);
							mainServer.log_area.append("[ Debug ] : "+id+" loged in\n");
							sb.delete(0, sb.length());
							sb.append("{\"response\" : \"login\",");
							sb.append("\"data\" : {");
							sb.append("\"id\" : \""+dto.getId()+"\",");
							sb.append("\"name\" : \""+dto.getName()+"\",");
							sb.append("\"nick_name\" : \""+dto.getNick_name()+"\",");
							sb.append("\"e_mail\" : \""+dto.getE_mail()+"\",");
							sb.append("\"success\" : \"true\"");
							sb.append("}}");
							speak(sb.toString());
						}
				}
				
				else {
						sb.delete(0, sb.length());
						sb.append("{\"response\" : \"logedin\",");
						sb.append("\"data\" : {");
						sb.append("\"success\" : \"false\"");
						sb.append("}}");
						speak(sb.toString());
				}				
			}
			
			else if(jsonObject.get("request").equals("regist")){
				JSONObject obj=(JSONObject)jsonObject.get("data");
				String[] data = new String[6];
				String id  = (String)obj.get("id");
				data[0] = id;
				String password = (String)obj.get("password");
				data[1] = password;
				String name = (String)obj.get("name");
				data[2] = name;
				String nick_name = (String)obj.get("nick_name");
				data[3] = nick_name;
				String gender = (String)obj.get("gender");
				data[4] = gender;
				String e_mail = (String)obj.get("e_mail");
				data[5] = e_mail;
				boolean result = registUser(data);
				if( result ){
					sb.delete(0, sb.length());
					sb.append("{\"response\" : \"regist\",");
					sb.append("\"success\" : \"true\"");
					sb.append("}");
					speak(sb.toString());
				}else {
					sb.delete(0, sb.length());
					sb.append("{\"response\" : \"regist\",");
					sb.append("\"success\" : \"false\"");
					sb.append("}");
					speak(sb.toString());
				}
			}
			else if (jsonObject.get("request").equals("create_room")){
				JSONObject obj=(JSONObject)jsonObject.get("data");
				String[] data = new String[5];
				data[0] = (String)obj.get("title");
				if(obj.get("ispw").equals("true")){
					data[1] = "1";	
				}else if(obj.get("ispw").equals("false")){
					data[1] = "0";
				}
				data[2] = (String)obj.get("password");
				data[3] = (String)obj.get("max");
				data[4] = (String)obj.get("host");
				boolean create_result = createRoom(data);
				if( create_result ){
					sb.delete(0, sb.length());
					sb.append("{\"response\" : \"create_room\",");
					sb.append("\"data\" : {");
					sb.append("\"success\" : \"true\",");
					sb.append("\"host_ip\" : \""+ip+"\",");
					sb.append("\"room_num\" : \""+room_no+"\"");///////////////////////////
					sb.append("}}");
					updateMemberRoomNumHost(data[4]);
					
					//JOptionPane.showMessageDialog(mainServer, mainServer.req_room.get(48).get(0)[0]);

					speak(sb.toString());
				}else {
					sb.delete(0, sb.length());
					sb.append("{\"response\" : \"create_room\",");
					sb.append("\"data\" : {");
					sb.append("\"success\" : \"false\"");
					sb.append("}}");
					speak(sb.toString());
				}
			}
			else if(jsonObject.get("request").equals("disconnect")){
				JSONObject obj=(JSONObject)jsonObject.get("data");
				disConnection((String)obj.get("id"));
				
				sb.delete(0, sb.length());
				sb.append("{\"response\" : \"disconnect\",");
				sb.append("\"data\" : {");
				sb.append("\"success\" : \"true\"");
				sb.append("}}");
				speak(sb.toString());
			}
			
			else if (jsonObject.get("request").equals("list")) {//-----추가된----------------관리자 창의 테이블에 나열할 자료 요청된------
				ArrayList<MemberDTO> list = listUser();//db에서 받은 자료를 넣은 리스트
				if (list != null) {
					sb.delete(0, sb.length());
					sb.append("{\"response\" : \"list\",");
					sb.append("\"success\" : \"true\",");
					sb.append("\"data\" : [");
					saveList(list);
					sb.append("]}");
					speak(sb.toString());
				} else {
					sb.delete(0, sb.length());
					sb.append("{\"response\" : \"list\",");
					sb.append("\"success\" : \"false\"");
					sb.append("}");
					speak(sb.toString());
				}

			} else if (jsonObject.get("request").equals("search")) {//관리자 창에서 아이디 검색 요청됨
				JSONObject object = (JSONObject) jsonObject.get("data");
				String id = (String) object.get("id");
				ArrayList<MemberDTO> list = searchUser(id);//db에서 아이디  검색 후 받은 데이터

				if (list != null) {
					sb.delete(0, sb.length());
					sb.append("{\"response\" : \"search\",");
					sb.append("\"success\" : \"true\",");
					sb.append("\"data\" : [");
					saveList(list);
					sb.append("]}");
					speak(sb.toString());
				} else {
					sb.delete(0, sb.length());
					sb.append("{\"response\" : \"list\",");
					sb.append("\"success\" : \"false\"");
					sb.append("}");
					speak(sb.toString());
				}

			} else if (jsonObject.get("request").equals("edit")) {//관리자 창에서 수정된 데이터로 db변경 요청
				JSONObject obj = (JSONObject) jsonObject.get("data");
				MemberDTO dto = new MemberDTO(); 
				dto.setMember_id(Integer.parseInt((String) obj.get("member_id")));
				dto.setId((String) obj.get("id"));
				dto.setPassword((String) obj.get("password"));
				dto.setName((String) obj.get("name"));
				dto.setNick_name((String) obj.get("nick_name"));
				dto.setGender(Integer.parseInt((String) obj.get("gender")));
				dto.setE_mail((String) obj.get("e_mail"));
				dto.setRegdate((String) obj.get("regdate"));
				dto.setIsConnection(Integer.parseInt(((String)obj.get("isconnecting"))));
				dto.setRoom_num(Integer.parseInt(((String)obj.get("room_num"))));
				dto.setIsAdmin(Integer.parseInt(((String)obj.get("isadmin")))); dto.setUser_ip((String)obj.get("user_ip"));
				
				boolean result = editUser(dto);//db 수정 후 결과 반환
				if (result) {
					ArrayList<MemberDTO> list = listUser();//갱신된 데이터 저장
					if (list != null) {
						sb.delete(0, sb.length());
						sb.append("{\"response\" : \"edit\",");
						sb.append("\"success\" : \"true\",");
						sb.append("\"data\" : [");
						saveList(list);
						sb.append("]}");
						speak(sb.toString());
					} else {
						sb.delete(0, sb.length());
						sb.append("{\"response\" : \"edit\",");
						sb.append("\"success\" : \"false\"");
						sb.append("}");
						speak(sb.toString());
					}
				}
			} else if (jsonObject.get("request").equals("delete")) {//관리자 창에서 자료 삭제 요청
				JSONObject object = (JSONObject) jsonObject.get("data");
				String id = (String) object.get("id");
				boolean result = deleteUser(id);//자료 삭제 후 결과 반환
				if (result) {//자료 삭제 성공
					ArrayList<MemberDTO> list = listUser();//갱신된 데이터 저장
					sb.delete(0, sb.length());
					sb.append("{\"response\" : \"delete\",");// 임시로 list 이용
					sb.append("\"success\" : \"true\",");
					sb.append("\"data\" : [");
					saveList(list);
					sb.append("]}");
					speak(sb.toString());
				} else {
					sb.delete(0, sb.length());
					sb.append("{\"response\" : \"delete\",");
					sb.append("\"success\" : \"false\"");
					sb.append("}");
					System.out.println(sb.toString());
					speak(sb.toString());
					
				} 
			}
			
			else if (jsonObject.get("request").equals("checkId")) {
				String ch_id = null;
				ch_id = (String) jsonObject.get("id");
				boolean result = checkId(ch_id);
				if (result) {
					sb.delete(0, sb.length());
					sb.append("{\"response\" : \"checkId\",");
					sb.append("\"success\" : \"true\"");
					sb.append("}");
					speak(sb.toString());
				} else {
					sb.delete(0, sb.length());
					sb.append("{\"response\" : \"checkId\",");
					sb.append("\"success\" : \"false\"");
					sb.append("}");
					speak(sb.toString());
				}
				
			}
			
			else if(jsonObject.get("request").equals("join")){
				JSONObject obj = (JSONObject) jsonObject.get("data");
				String id = (String)obj.get("id");
				int room_num = Integer.parseInt((String)obj.get("room_num"));
				System.out.println(id+room_num);
				if( joinRoom(id, room_num,user_ip) == true){
					String ip = getHostIp(room_num);
					sb.delete(0, sb.length());
					sb.append("{\"response\" : \"join\",");
					sb.append("\"success\" : \"true\",");
					sb.append("\"room_num\" : \""+room_num+"\",");
					sb.append("\"host_ip\" : \""+ip+"\"");
					sb.append("}");
					speak(sb.toString());
					ClientThreadDTO dto=new ClientThreadDTO();
					dto.setUser_id(id);
					dto.setUser_ip(user_ip);
					dto.setCs(this);
					mainServer.req_room.get(room_num).add(dto);
					
				}else {
					sb.delete(0, sb.length());
					sb.append("{\"response\" : \"join\",");
					sb.append("\"success\" : \"false\"");
					sb.append("}");
					speak(sb.toString());
				}
			}
			
			else if(jsonObject.get("request").equals("listrooms")){
				
				ArrayList<RoomsDTO> roomsList=listRooms();
				if(roomsList!=null){
					sb.delete(0, sb.length());
					sb.append("{\"response\" : \"listrooms\",");
					sb.append("\"success\" : \"true\",");
					sb.append("\"data\" : [");
					saveRooms(roomsList);
					sb.append("]}");
					speak(sb.toString());
				}else {
					sb.delete(0, sb.length());
					sb.append("{\"response\" : \"listrooms\",");
					sb.append("\"success\" : \"false\"");
					sb.append("}");
					speak(sb.toString());
				}
			}
			
			else if(jsonObject.get("request").equals("refresh")){
				
				ArrayList<RoomsDTO> list=refreshRooms();
				if(list!=null){
					sb.delete(0, sb.length());
					sb.append("{\"response\" : \"refresh\",");
					sb.append("\"success\" : \"true\",");
					sb.append("\"data\" : [");
					saveRooms(list);
					sb.append("]}");
					speak(sb.toString());
				}else {
					sb.delete(0, sb.length());
					sb.append("{\"response\" : \"refresh\",");
					sb.append("\"success\" : \"false\"");
					sb.append("}");
					speak(sb.toString());
				}
			}
			
			else if(jsonObject.get("request").equals("showconnector")){
				
				ArrayList<MemberDTO> list=showConnector();
				if(list!=null){
					sb.delete(0, sb.length());
					sb.append("{\"response\" : \"showconnector\",");
					sb.append("\"success\" : \"true\",");
					sb.append("\"data\" : [");
					for(int i=0;i<list.size();i++){
						MemberDTO dto=list.get(i);
						sb.append("{");
						sb.append("\"nick_name\" : \"" + list.get(i).getNick_name() + "\",");
						sb.append("\"gender\" : \"" + list.get(i).getGender() + "\"");
						if (i < (list.size() - 1)) {
							sb.append("},");
						} else {
							sb.append("}");
						}
					}
					sb.append("]}");
					speak(sb.toString());
				}else {
					sb.delete(0, sb.length());
					sb.append("{\"response\" : \"showconnector\",");
					sb.append("\"success\" : \"false\"");
					sb.append("}");
					speak(sb.toString());
				}
			}
			//-------------------------------------------------------------------------------지석 재광
			else if(jsonObject.get("request").equals("chatting")){
				
				JSONArray jsonArray=(JSONArray)jsonObject.get("data");
				JSONObject obj=(JSONObject)jsonArray.get(0);
				
				sb.delete(0, sb.length());
				
				sb.append("{\"response\" : \"chatting\",");
				sb.append("\"success\" : \"true\",");
				sb.append("\"data\" : ");
				sb.append("\""+(String)obj.get("msg")+"\"");
				sb.append("}");
				System.out.println((String)obj.get("room_num"));
				Vector<ClientThreadDTO> list=mainServer.req_room.get(Integer.parseInt((String)obj.get("room_num")));
				for(int i=0;i<list.size();i++){
					list.get(i).getCs().speak(sb.toString());
				}
			}
			
			else if(jsonObject.get("request").equals("disconnectchat")){
				
				JSONObject obj=(JSONObject)jsonObject.get("data");
				Vector<ClientThreadDTO> vector=mainServer.req_room.get(Integer.parseInt((String)obj.get("room_num")));
				for(int i=0;i<vector.size();i++){
					mainServer.log_area.append(vector.get(i).getUser_id());
					if(vector.get(i).getUser_id().equals(id)){
						vector.remove(i);
					}
				}
				mainServer.log_area.append(Boolean.toString(vector.isEmpty()));
				for(int i=0;i<vector.size();i++){
					mainServer.log_area.append(vector.get(i).getUser_id());
				}
				sb.delete(0, sb.length());
				sb.append("{\"response\" : \"disconnectchat\",");
				sb.append("\"success\" : \"true\",");
				sb.append("}");				
			}
			
			//지석 재광-----------------------------------------------------------------------------	
				
		} catch (ParseException e) {
			mainServer.log_area.append("[ Debug ] : JSON Parsing Error.\n");
		}
		
	}
	
	public String getHostIp(int room_num){
		MemberDAO dao = new MemberDAO(con, mainServer);
		String result = dao.getHostIp(room_num);
		return result;
	}
	
	public boolean joinRoom(String id, int room_num,String user_ip){
		MemberDAO dao = new MemberDAO(con, mainServer);
		boolean result = false;
		int num = dao.numberOfCurrentJoin(room_num);
		
		if ( num == 4){
			result = false;
		} else if( num < 4 ){
			result = dao.joinRoom(id, room_num);
			if(result){
				dao.plusCurrnetJoin(room_num, ++num);
			}
		}
		return result;
	}
	
	public void updateMemberRoomNumHost(String data){
		MemberDAO dao = new MemberDAO(con, mainServer);
		dao.updateMemberRoomNumHost(data);
	}
	
	public void disConnection(String id){
		MemberDAO dao = new MemberDAO(con, mainServer);
		dao.disConnection(id);
		dao.closeRoom(id);
	}
	
	
	public boolean createRoom(String[] data){
		MemberDAO dao = new MemberDAO(con, mainServer);
		boolean result = false;
		
		if(dao.duplicatedHostID(id)){
			int room_num = dao.checkEmptyRoom();
			if(room_num != 0){
				result = dao.createRoom(data, room_num);
				//-----------------------------------------------------생성된 방에서 네트워크할 리스트 생성 및 호스트 입력 지석
				Vector<ClientThreadDTO> req_member=new Vector<ClientThreadDTO>();
				ClientThreadDTO dto=new ClientThreadDTO();
				dto.setUser_id(id);
				dto.setUser_ip(user_ip);
				dto.setCs(this);
				req_member.add(dto);
				room_no=room_num;
				System.out.println(room_no+"ss");
				mainServer.req_room.put(room_no, req_member);
				//--------------------------------------------------------지석
				
			}else if(room_num == 0){
				result = dao.createRoom(data);
				//-----------------------------------------------------생성된 방에서 네트워크할 리스트 생성 및 호스트 입력 지석
				room_no=dao.checkLastRoom();
				Vector<ClientThreadDTO> req_member=new Vector<ClientThreadDTO>();
				ClientThreadDTO dto=new ClientThreadDTO();
				dto.setUser_id(id);
				dto.setUser_ip(user_ip);
				dto.setCs(this);
				req_member.add(dto);
				System.out.println(room_no+"ff");
				room_num=room_no;
				mainServer.req_room.put(room_no, req_member);
				//--------------------------------------------------------지석
			}
			room_no=room_num;
			System.out.println(room_no+"bnn");
		}
		return result;
	}
	
	public void updateUserIp(String ip, String id){
		MemberDAO dao = new MemberDAO(con, mainServer);
		boolean result = dao.updateUserIp(ip, id);
		mainServer.log_area.append("[ Debug ] : "+result+"\n");
		if(result){
			mainServer.log_area.append("[ Debug ] : User Ip is updated.\n");
		}else {
			mainServer.log_area.append("[ Debug ] : User Ip updating is failed.\n");
		}
	}
	public boolean haveLogedIn(String id){
		MemberDAO dao = new MemberDAO(con, mainServer);
		return dao.haveLogedIn(id);
	}
	
	public MemberDTO getLoginCheck(String id, String pwd){
		
		MemberDAO dao = new MemberDAO(con, mainServer);
		return dao.loginCheck(id, pwd);
	}
	
	public boolean registUser(String[] data){
		MemberDAO dao = new MemberDAO(con, mainServer);
		return dao.registUser(data);
	}
	
	public void saveList(ArrayList<MemberDTO> list){
		for (int i = 0; i < list.size(); i++) {
			sb.append("{");
			sb.append("\"member_id\" : \"" + list.get(i).getMember_id() + "\",");
			sb.append("\"id\" : \"" + list.get(i).getId() + "\",");
			sb.append("\"password\" : \"" + list.get(i).getPassword() + "\",");
			sb.append("\"name\" : \"" + list.get(i).getName() + "\",");
			sb.append("\"nick_name\" : \"" + list.get(i).getNick_name() + "\",");
			sb.append("\"gender\" : \"" + list.get(i).getGender() + "\",");
			sb.append("\"e_mail\" : \"" + list.get(i).getE_mail() + "\",");
			sb.append("\"regdate\" : \"" + list.get(i).getRegdate() + "\",");
			sb.append("\"isconnecting\" : \"" + list.get(i).getIsConnection() + "\",");
			sb.append("\"room_num\" : \"" + list.get(i).getRoom_num() + "\",");
			sb.append("\"isadmin\" : \"" + list.get(i).getIsAdmin() + "\",");
			sb.append("\"user_ip\" : \"" + list.get(i).getUser_ip() + "\"");
			if (i < (list.size() - 1)) {
				sb.append("},");
			} else {
				sb.append("}");
			}
		}
	}
	
	public void saveRooms(ArrayList<RoomsDTO> roomsList){
		for(int i=0;i<roomsList.size();i++){
			RoomsDTO dto=roomsList.get(i);
			sb.append("{");
			sb.append("\"num\" : \"" + roomsList.get(i).getNum() + "\",");
			sb.append("\"title\" : \"" + roomsList.get(i).getTitle() + "\",");
			sb.append("\"ispw\" : \"" + roomsList.get(i).getIsPw() + "\",");
			sb.append("\"pw\" : \"" + roomsList.get(i).getPw() + "\",");
			sb.append("\"max_join\" : \"" + roomsList.get(i).getMax_join() + "\",");
			sb.append("\"cur_join\" : \"" + roomsList.get(i).getCur_join() + "\",");
			sb.append("\"isusing\" : \"" + roomsList.get(i).getIsUsing() + "\",");
			sb.append("\"host_id\" : \"" + roomsList.get(i).getHost_id() + "\"");
			if (i < (roomsList.size() - 1)) {
				sb.append("},");
			} else {
				sb.append("}");
			}
		}
	}
	
	public ArrayList<MemberDTO> listUser() {
		MemberDAO dao = new MemberDAO(con, mainServer);
		return dao.listUser();
	}

	public ArrayList<MemberDTO> searchUser(String id) {
		MemberDAO dao = new MemberDAO(con, mainServer);
		return dao.searchUser(id);
	}

	public boolean editUser(MemberDTO dto) {
		MemberDAO dao = new MemberDAO(con, mainServer);
		return dao.editUser(dto);
	}

	public boolean deleteUser(String id) {
		MemberDAO dao = new MemberDAO(con, mainServer);
		return dao.deleteUser(id);
	}
	
	public boolean checkId(String data) {
		MemberDAO dao = new MemberDAO(con, mainServer);
		return dao.checkId(data);
	}
	
	public ArrayList<RoomsDTO> listRooms(){
		MemberDAO dao=new MemberDAO(con,mainServer);
		return dao.listRoom();
		
	}
	
	public ArrayList<RoomsDTO> refreshRooms(){
		MemberDAO dao=new MemberDAO(con,mainServer);
		return dao.refreshRooms();
		
	}
	
	public ArrayList<MemberDTO> showConnector(){
		MemberDAO dao=new MemberDAO(con,mainServer);
		return dao.showConnector();
		
	}
	public int checkLastRoom(){
		MemberDAO dao=new MemberDAO(con,mainServer);
		return dao.checkLastRoom();
		
	}

	
	public void run() {
		while(true){
			listen();
		}
	}
	
}
