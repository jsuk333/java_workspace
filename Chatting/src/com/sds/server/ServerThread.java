/*�����ڸ��� 1��1�� ��û�� ó���ϱ� ���� 
 * �������� ������!������ �����Ѵ�.
 * ��??���� ��û�� ���� �ϱ� ����*/
package com.sds.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ServerThread extends Thread{
	Socket client;
	BufferedReader buffr;
	BufferedWriter buffw;
	ServerMain serverMain;
	PreparedStatement pstmt;
	ResultSet rs;
	StringBuffer sb;
	public ServerThread(ServerMain serverMain,Socket client) {
		this.serverMain=serverMain;
		this.client=client;
		sb=new StringBuffer();
		try {
			buffr=new BufferedReader(new InputStreamReader(client.getInputStream()));
			buffw=new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void listen(){
		try {
			String msg=buffr.readLine();
			serverMain.area.append(msg+"\n");
			//msg�� Ŭ���̾�Ʈ�� ��û Ÿ�Կ� ���� �������� ���� ������ �޶��� �� �ֵ�. (��, ������ ä���� �ƴϴ�....);
			JSONParser jsonParser=new JSONParser();
			//�Ľ��� ���� ������ ���ڿ��� �Ұ��ߴ� ���̽������� ��üó�� ����� �� �ֵ�.
			JSONObject jsonObject=(JSONObject) jsonParser.parse(msg);
			;
			//Ŭ���̾�Ʈ�� ��û�� �α����̶��
			if(jsonObject.get("request").equals("logIn")){
				serverMain.area.append("�α����� ���ϴ� ����");
				//������ ����
				String sql="select * from chatmember where ";
				sql+="id=? and password=?";
				pstmt=serverMain.con.prepareStatement(sql);
				pstmt.setString(1, (String)jsonObject.get("id"));
				pstmt.setString(2, (String)jsonObject.get("pwd"));
				rs=pstmt.executeQuery();
				//rs�� ���ڿ��� �����Ͽ� ���� ����
				sb.delete(0, sb.length());
				sb.append("{");
				sb.append(	"\"response\":\"logIn\",");
				if(rs.next()){
					String name=rs.getString("name");
					int chatmember_id=rs.getInt("chatmember_id");
					sb.append(	"\"result\":\"ok\",");
					sb.append(	"\"data\": {");
					sb.append(	"\"name\": \""+name+"\",");
					sb.append(	"\"chatmember_id\": "+chatmember_id);
					sb.append(	"}");
				}else{
					sb.append(	"\"result\":\"fail\",");
					sb.append(	"\"data\": {");
					sb.append(	"}");
				}
				sb.append(	"}");
				release(pstmt, rs);
			}else if(jsonObject.get("request").equals("chat")){	//Ŭ���̾�Ʈ�� ��û�� ��ȭ���
				serverMain.area.append("ê�� ���ϴ� ����");
				
			}else if(jsonObject.get("request").equals("regit")){	//Ŭ���̾�Ʈ�� ��û�� ȸ�� �����̶��
				serverMain.area.append("����� ���ϴ� ����");
				
			}else if(jsonObject.get("request").equals("userlist")){
				String sql="select * from chatmember";
				pstmt=serverMain.con.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
				rs=pstmt.executeQuery();

				//rs�� Ŀ�� ��ġ�� ���ڵ��� ���� ���������� �ű��
				rs.last();
				int total=rs.getRow();//���� ���ڵ��� ��ġ ��ȯ
				sb.delete(0, sb.length());
				sb.append("{");
				sb.append("\"response\" : \"userlist\",");
				sb.append("\"result\" : \"ok\",");
				sb.append("\"data\" : [");
				rs.beforeFirst();
				while(rs.next()){
					long chatmember_id=rs.getLong("chatmember_id");
					String id=rs.getString("id");
					String password=rs.getString("password");
					String name=rs.getString("name");
					String pofile=rs.getString("pofile");
					String status=rs.getString("status");
					sb.append("{");
					sb.append("\"chatmember\" : "+chatmember_id+",");
					sb.append("\"id\" : \""+id+"\",");
					sb.append("\"password\" : \""+password+"\",");
					sb.append("\"name\" : \""+name+"\",");
					sb.append("\"profile\" : \""+pofile+"\",");
					sb.append("\"status\" : \""+status+"\"");
					if(rs.getRow()<total){
						sb.append("},");
					}else{
						sb.append("}");
					}
				}
				sb.append("]");
				sb.append("}");	
			}
				sendMsg(sb.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void sendMsg(String msg){
		try {
			buffw.write(msg+"\n");
			buffw.flush();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void run() {
		while(true){
			listen();
		}
	}
	//�����ͺ��̽� ���� ��ü �ݴ� �޼���
	public void release(PreparedStatement pstmt){
		if(pstmt!=null){
			try {
				pstmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public void release(PreparedStatement pstmt,ResultSet rs){
		if(pstmt!=null){
			try {
				pstmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(rs!=null){
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
