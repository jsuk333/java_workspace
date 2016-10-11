package com.sds.admin;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JOptionPane;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.sds.share.AcceptThread;
import com.sds.share.SrceenShotThread;

public class ServerThread extends Thread {
	Socket client;
	BufferedReader buffr;
	BufferedWriter buffw;
	Server server;
	StringBuffer sb = new StringBuffer();
	StringBuffer sb_exit = new StringBuffer();
	PreparedStatement pstmt;
	ResultSet rs;
	boolean flag = true;
	boolean alarm = false;

	public ServerThread(Socket client, Server server) {
		this.client = client;
		this.server = server;
		try {
			buffr = new BufferedReader(new InputStreamReader(client.getInputStream()));
			buffw = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void listen() {
		try {
			String msg = buffr.readLine();
			JSONParser jsonParser = new JSONParser();
			JSONObject js = (JSONObject) jsonParser.parse(msg);
			if (js.get("request").equals("admin")) {
				// 클라이언트의 요청이 로그인
				String hostIp = js.get("ip").toString();
				// String access=js.get("access").toString();
				int result = JOptionPane.showConfirmDialog(server, hostIp + "가 권한을 요청 했습니다.\n 수행하시겠습니까?");

				sb.delete(0, sb.length());
				sb_exit.delete(0, sb_exit.length());

				sb.append("{");
				sb.append("\"response\":\"admin\",");

				sb_exit.append("{");
				sb_exit.append("\"response\":\"admin\",");
				if (result == JOptionPane.OK_OPTION) {
					alarm = true;
					sb.append("\"result\":\"ok\",");
					sb.append("\"alarm\":\"false\",");
					sb.append("\"host\":\"" + hostIp + "\"}");

				} else if (result == JOptionPane.NO_OPTION) {
					alarm = false;
					sb.append("\"result\":\"false\",");
					sb.append("\"alarm\":\"false\"}");
					sb.append("\"host\":\"" + hostIp + "\"}");
					// alarm제이슨은 권한이 양도되었다는 알림을 주는 제이슨이다.

				} else if (result == JOptionPane.CANCEL_OPTION) {
					alarm = false;
					sb.append("\"result\":\"false\",");
					sb.append("\"alarm\":\"false\"}");
					sb.append("\"host\":\"" + hostIp + "\"}");
				}
				if (alarm) {
					// 양도를 한다는 버튼을 눌렀을 경우!!!
					sb_exit.append("\"result\":\"false\",");
					sb_exit.append("\"alarm\":\"true\",");
					sb_exit.append("\"host\":\"" + hostIp + "\",");
					// 권한을 양도받지 않음
					sb_exit.append("\"data\":[");

					for (int i = 0; i < server.serverList.size(); i++) {
						String guestIp = server.serverList.get(i).client.getInetAddress().getHostAddress();
						sb_exit.append("{");
						// 게스트의 수를 넘기고
						if (i != server.serverList.size() - 1)
							sb_exit.append("\"guest\":\"" + guestIp + "\"},");
						// 게스트의 아이피를 같이 보낸다.
						else
							sb_exit.append("\"guest\":\"" + guestIp + "\"}");
					}
					sb_exit.append("]");
					sb_exit.append("}");
				} else {
					// 양도를 안한다는 버튼을 눌렀을 경우!!
					sb_exit.append("\"result\":\"false\",");
					sb_exit.append("\"alarm\":\"false\"}");
					sb_exit.append("\"host\":\"" + hostIp + "\",");
					// 권한을 양도받지 않음

					sb_exit.append("\"data\":[");
					// 모든 게스트의 수만큼 반복
					for (int i = 0; i < server.serverList.size(); i++) {
						String guestIp = server.serverList.get(i).client.getInetAddress().getHostAddress();
						sb_exit.append("{");
						sb_exit.append("\"guestNo\":\"" + (++i) + "\",");
						// 게스트의 수를 넘기고
						if (i != server.serverList.size() - 1)
							sb_exit.append("\"guest\":\"" + guestIp + "\"},");
						// 게스트의 아이피를 같이 보낸다.
						else
							sb_exit.append("\"guest\":\"" + guestIp + "\"}");
					}
					sb_exit.append("]");
					sb_exit.append("}");
				}

				for (int i = 0; i < server.serverList.size(); i++) {
					String myIp = server.serverList.get(i).client.getInetAddress().getHostAddress();
					if (myIp.equals(hostIp)) {
						server.serverList.get(i).sendMsg(sb.toString());

					} else {
						server.serverList.get(i).sendMsg(sb_exit.toString());
					}
				}
			} else if (js.get("request").equals("createRoom")) {
				// 방 생성 알림을 뿌려준다!
				String hostIp = js.get("ip").toString();

				for (int i = 0; i < server.serverList.size(); i++) {
					String myIp = server.serverList.get(i).client.getInetAddress().getHostAddress();
					sb.delete(0, sb.length());
					sb.append("{");
					sb.append("\"response\":\"createRoom\",");
					if (myIp.equals(hostIp))
					{

						sb.append("\"create\":\"true\",");
						sb.append("\"host\":\"" + hostIp + "\"}");
						
					}else
					{
						sb.append("\"create\":\"false\",");
						sb.append("\"host\":\"" + hostIp + "\"}");
						
					}
					System.out.println(sb.toString());
					server.serverList.get(i).sendMsg(sb.toString());
				}
			} else if (js.get("request").equals("towaitingroom")) {
				// 방 생성 알림을 뿌려준다!
				String guest_ip = js.get("ip").toString();
				String server_ip=server.join.ip;
				System.out.println(guest_ip+server_ip);
				if(!guest_ip.equals(server_ip)){
					System.out.println("guest not equals server ip");
					Vector<SrceenShotThread> screenList=server.join.guestUI.screenShotserver.screenlist;
					ArrayList<Socket> joinList=server.join.guestUI.screenShotserver.joinList;
					ArrayList<AcceptThread> guestStreamList=server.join.guestUI.acceptScreen.guestStreamList;
					
					for(int i=0;i<joinList.size();i++){
						System.out.println(joinList.get(i).getInetAddress().getHostAddress());
						if(joinList.get(i).getInetAddress().getHostAddress().equals(guest_ip)){
							screenList.get(i).flagScreen=false;
							server.join.guestUI.screenShotserver.screenlist.remove(i);
							server.join.guestUI.screenShotserver.joinList.remove(i);
						}
					}
					
					for(int i=0;i<server.serverList.size();i++){
						if(server.serverList.get(i).client.getInetAddress().getHostAddress().equals(guest_ip)){
							server.serverList.get(i).flag=false;
							server.serverList.remove(i);
							server.socketList.remove(i);
						}
					}
				}
				sb.delete(0, sb.length());
				sb.append("{");
				sb.append("\"response\":\"towaitingroom\",");
				sb.append("\"success\":\"true\",");
				String curr_ip=InetAddress.getLocalHost().getHostAddress();
				if(guest_ip.equals(server_ip)){
					System.out.println("server guest equals");
					sb.append("\"ishost\":\"true\"}");
				}else{
					sb.append("\"ishost\":\"false\"}");
				}
				System.out.println(sb.toString());
				sendMsg(sb.toString());
				
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void sendMsg(String msg) {
		try {
			buffw.write(msg + "\n");
			buffw.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while (flag)
			listen();
	}
}
