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

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.sds.dao.MemberDAO;
import com.sds.dao.TestDAO;
import com.sds.dto.MemberDTO;
import com.sds.dto.TestDTO;

public class ClientThread extends Thread {

	Socket socket;
	MainServer mainServer;
	BufferedWriter buffw;
	BufferedReader buffr;
	Connection con;
	boolean flag = true;
	StringBuffer sb = new StringBuffer();

	String sql;

	public ClientThread(Socket socket, Connection con, MainServer mainServer) {
		this.socket = socket;
		this.mainServer = mainServer;
		this.con = con;
		try {
			buffr = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			buffw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		} catch (IOException e) {
			this.mainServer.log_area.append("[ Debug ] : Thread IOException\n");
		}
	}

	public void listen() {
		try {

			String msg = buffr.readLine();
			mainServer.request = msg;
			analyzeProtocol(msg);

		} catch (IOException e) {
			if (socket.isClosed()) {
				if (con != null) {
					try {
						con.close();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
			flag = false;
			mainServer.log_area.append("[ Debug ] : Listen error\n");
		}
	}

	public void speak(String msg) {
		try {
			buffw.write(msg + "\n");
			buffw.flush();
		} catch (IOException e) {
			mainServer.log_area.append("[ Debug ] : Speak error\n");
		}
		System.out.println(msg);
	}

	// �������� �м� �� ��ɾ� ó�� �żҵ�
	public void analyzeProtocol(String msg) {
		try {
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(msg);
			if (jsonObject.get("request").equals("login")) {
				JSONObject obj = (JSONObject) jsonObject.get("data");
				String id = (String) obj.get("id");
				String pwd = (String) obj.get("password");
				MemberDTO dto = getLoginCheck(id, pwd);
				if (dto != null) {
					sb.delete(0, sb.length());
					sb.append("{\"response\" : \"login\"");
					sb.append("\"data\" : {");
					sb.append("\"id\" : \"" + dto.getId() + "\",");
					sb.append("\"name\" : \"" + dto.getName() + "\",");
					sb.append("\"nick_name\" : \"" + dto.getNick_name() + "\",");
					sb.append("\"e_mail\" : \"" + dto.getE_mail() + "\",");
					sb.append("\"success\" : \"true\"");
					sb.append("}}");
					speak(sb.toString());
				} else {
					sb.delete(0, sb.length());
					sb.append("{\"response\" : \"login\"");
					sb.append("\"success\" : \"false\"");
					sb.append("}");
					speak(sb.toString());
				}
			} else if (jsonObject.get("request").equals("regist")) {
				JSONObject obj = (JSONObject) jsonObject.get("data");
				String[] data = new String[6];
				String id = (String) obj.get("id");
				data[0] = id;
				String password = (String) obj.get("password");
				data[1] = password;
				String name = (String) obj.get("name");
				data[2] = name;
				String nick_name = (String) obj.get("nick_name");
				data[3] = nick_name;
				String gender = (String) obj.get("gender");
				data[4] = gender;
				String e_mail = (String) obj.get("e_mail");
				data[5] = e_mail;
				boolean result = registUser(data);
				if (result) {
					sb.delete(0, sb.length());
					sb.append("{\"response\" : \"regist\",");
					sb.append("\"success\" : \"true\"");
					sb.append("}");
					speak(sb.toString());
				} else {
					sb.delete(0, sb.length());
					sb.append("{\"response\" : \"regist\",");
					sb.append("\"success\" : \"false\"");
					sb.append("}");
					speak(sb.toString());
				}
			} else if (jsonObject.get("request").equals("list")) {//-----�߰���----------------������ â�� ���̺� ������ �ڷ� ��û��------
				ArrayList<MemberDTO> list = listUser();//db���� ���� �ڷḦ ���� ����Ʈ
				if (list != null) {
					sb.delete(0, sb.length());
					sb.append("{\"response\" : \"list\",");
					sb.append("\"success\" : \"true\",");
					sb.append("\"data\" : [");
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
					sb.append("]}");
					speak(sb.toString());
				} else {
					sb.delete(0, sb.length());
					sb.append("{\"response\" : \"list\",");
					sb.append("\"success\" : \"false\"");
					sb.append("}");
					speak(sb.toString());
				}

			} else if (jsonObject.get("request").equals("search")) {//������ â���� ���̵� �˻� ��û��
				JSONObject object = (JSONObject) jsonObject.get("data");
				String id = (String) object.get("id");
				ArrayList<MemberDTO> list = searchUser(id);//db���� ���̵�  �˻� �� ���� ������

				if (list != null) {
					sb.delete(0, sb.length());
					sb.append("{\"response\" : \"search\",");
					sb.append("\"success\" : \"true\",");
					sb.append("\"data\" : [");
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
					sb.append("]}");
					speak(sb.toString());
				} else {
					sb.delete(0, sb.length());
					sb.append("{\"response\" : \"list\",");
					sb.append("\"success\" : \"false\"");
					sb.append("}");
					speak(sb.toString());
				}

			} else if (jsonObject.get("request").equals("edit")) {//������ â���� ������ �����ͷ� db���� ��û
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
				/*dto.setIsConnection(Integer.parseInt(((String)obj.get("isconnecting"))));
				dto.setRoom_num(Integer.parseInt(((String)obj.get("room_num"))));
				dto.setIsAdmin(Integer.parseInt(((String)obj.get("isadmin")))); dto.setUser_ip((String)obj.get("user_ip"));*/
				
				boolean result = editUser(dto);//db ���� �� ��� ��ȯ
				if (result) {
					ArrayList<MemberDTO> list = listUser();//���ŵ� ������ ����
					if (list != null) {
						sb.delete(0, sb.length());
						sb.append("{\"response\" : \"edit\",");
						sb.append("\"success\" : \"true\",");
						sb.append("\"data\" : [");
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
			} else if (jsonObject.get("request").equals("delete")) {//������ â���� �ڷ� ���� ��û
				JSONObject object = (JSONObject) jsonObject.get("data");
				String id = (String) object.get("id");
				boolean result = deleteUser(id);//�ڷ� ���� �� ��� ��ȯ
				System.out.println(id);
				if (result) {//�ڷ� ���� ����
					ArrayList<MemberDTO> list = listUser();//���ŵ� ������ ����
					sb.delete(0, sb.length());
					sb.append("{\"response\" : \"delete\",");// �ӽ÷� list �̿�
					sb.append("\"success\" : \"true\",");
					sb.append("\"data\" : [");
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

		} catch (ParseException e) {
		}

	}

	public MemberDTO getLoginCheck(String id, String pwd) {
		sql = "select * from member where id='" + id + "' and password='" + pwd + "'";
		MemberDAO dao = new MemberDAO(con, mainServer);
		return dao.loginCheck(sql);
	}

	public boolean registUser(String[] data) {
		MemberDAO dao = new MemberDAO(con, mainServer);
		return dao.registUser(data);
	}
//-------------------------------------------------------------------------------------------------------------
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

	public void run() {
		while (true) {
			listen();
		}
	}

}
