/*접속자마다 1대1로 요청을 처리하기 위한 
 * 서버측의 쓰레드!소켓을 보관한다.
 * 왜??각종 요청에 대응 하기 위해*/
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
			//msg는 클라이언트의 요청 타입에 따라 서버측의 업무 내용은 달라질 수 있따. (즉, 언제나 채팅은 아니다....);
			JSONParser jsonParser=new JSONParser();
			//파싱한 이후 시점에 문자열에 불과했던 제이슨형식을 객체처럼 취급할 수 있따.
			JSONObject jsonObject=(JSONObject) jsonParser.parse(msg);
			;
			//클라이언트의 요청이 로그인이라면
			if(jsonObject.get("request").equals("logIn")){
				serverMain.area.append("로그인을 원하는 군요");
				//쿼리문 수행
				String sql="select * from chatmember where ";
				sql+="id=? and password=?";
				pstmt=serverMain.con.prepareStatement(sql);
				pstmt.setString(1, (String)jsonObject.get("id"));
				pstmt.setString(2, (String)jsonObject.get("pwd"));
				rs=pstmt.executeQuery();
				//rs를 문자열로 가공하여 보내 주자
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
			}else if(jsonObject.get("request").equals("chat")){	//클라이언트의 요청이 대화라면
				serverMain.area.append("챗을 원하는 군요");
				
			}else if(jsonObject.get("request").equals("regit")){	//클라이언트의 요청이 회원 가입이라면
				serverMain.area.append("등록을 원하는 군요");
				
			}else if(jsonObject.get("request").equals("userlist")){
				String sql="select * from chatmember";
				pstmt=serverMain.con.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
				rs=pstmt.executeQuery();

				//rs의 커서 위치를 레코드의 제일 마지막으로 옮기기
				rs.last();
				int total=rs.getRow();//현재 레코드의 위치 반환
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
	//데이터베이스 관련 객체 닫는 메서드
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
