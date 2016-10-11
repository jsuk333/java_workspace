package com.sds.admin;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.sds.client.ControllUI;

public class JoinThread extends Thread {
	Join join;
	BufferedReader buffr;
	BufferedWriter buffw;
	ControllUI ui;

	public JoinThread(Join join, ControllUI ui) {
		this.join = join;
		this.ui = ui;
		// 버튼의 조작을 변경하기 위해 ui를 넘겨받아서 쓰레드 안에서 제어를 한다.
		try {
			buffr = new BufferedReader(new InputStreamReader(join.client.getInputStream()));
			buffw = new BufferedWriter(new OutputStreamWriter(join.client.getOutputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void readRequest() {
		String msg;
		// area.append("리드요청\n");
		try {
			msg = buffr.readLine();
			JSONParser parser = new JSONParser();
			JSONObject jsonObejct = (JSONObject) parser.parse(msg);
			if (jsonObejct.get("response").equals("admin")) {
				if (jsonObejct.get("result").equals("ok")) {
					String hostIp=null;
					join.guestUI.btn_sreenServer.setEnabled(true);
					join.guestUI.btn_joinServer.setEnabled(false);
					join.guestUI.btn_requestServer.setEnabled(false);
					if(jsonObejct.get("host").toString()!=null)
						hostIp = jsonObejct.get("host").toString();
					if(ui==null)
					{
						return; 
					}
					else
					{
						if(ui.acceptScreen!=null)
						{
							ui.acceptScreen.setIp(hostIp);
						}
					}
						
					JOptionPane.showMessageDialog(ui.acceptScreen, "호스트 권한을 받았습니다.");

					// 그다음 보게될 화면에 ip를 변경해준다.
				} else if (jsonObejct.get("result").equals("false")) {
					if (jsonObejct.get("alarm").equals("true")) {
						String hostIp=null;
						join.guestUI.btn_sreenServer.setEnabled(false);
						join.guestUI.btn_joinServer.setEnabled(true);
						join.guestUI.btn_requestServer.setEnabled(true);
						// 누군가가 권한을 양도 받았다는 의미이므로
						// 권한을 양도받은 사람 외에 다른 사람은
						// 권한이 양도되었다는 메세지와 그 변경된 호스트의 ip를 받아야한다.
						if(jsonObejct.get("host").toString()!=null)
							hostIp = jsonObejct.get("host").toString();
						JOptionPane.showMessageDialog(ui.acceptScreen, hostIp + " 로 권한이 변경되었습니다.");
						if(ui==null)
						{
							return; 
						}
						else
						{
							if(ui.acceptScreen!=null)
							{
								ui.acceptScreen.setIp(hostIp);
							}
						}
						// 서버로부터 받은 게스트의 모든 ip를 출력해본다!!
						JSONArray jsonArray = (JSONArray) jsonObejct.get("data");
						for (int i = 0; i < jsonArray.size(); i++) {
							JSONObject obj = (JSONObject) jsonArray.get(i);
							String guestIp = obj.get("guest").toString();
							System.out.println(guestIp);
						}
						join.guestUI.screenShot.exitSocket();
						join.guestUI.screenShot.searchServer();
						join.guestUI.acceptScreen.exitAccept();
						
					}
				}
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void requestHost() throws ParseException {
		StringBuffer sb = new StringBuffer();
		sb.append("{");

		sb.append("\"request\":\"admin\",");
		try {
			sb.append("\"ip\":\"" + join.client.getInetAddress().getLocalHost().getHostAddress() + "\",");
			sb.append("\"access\":\"" + join.flag + "\"");
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		sb.append("}");

		try {
			buffw.write(sb.toString() + "\n");
			buffw.flush();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void run() {
		while (true)
			readRequest();
	}

}
