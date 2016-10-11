package com.sds.main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.UnknownHostException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class GuestThread extends Thread{
	Guest guest;
	BufferedReader buffr;
	BufferedWriter buffw;	
	public GuestThread(Guest guest) {
		this.guest=guest;
		try {
			buffr=new BufferedReader(new InputStreamReader(guest.client.getInputStream()));
			buffw=new BufferedWriter(new OutputStreamWriter(guest.client.getOutputStream()));
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
			guest.area.append(msg + "\n");
			JSONParser parser = new JSONParser();
			JSONObject jsonObejct = (JSONObject) parser.parse(msg);
			if (jsonObejct.get("result").equals("ok")) {

				guest.btn_admin.setEnabled(true);
				guest.btn_request.setEnabled(false);
				guest.p.updateUI();
			}
			else if(jsonObejct.get("result").equals("false"))
			{
				if(jsonObejct.get("access").equals("false"))
				{
					guest.btn_admin.setEnabled(false);
					guest.btn_request.setEnabled(true);
					guest.p.updateUI();
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
			sb.append("\"ip\":\"" + guest.client.getInetAddress().getLocalHost().getHostAddress() + "\",");
			sb.append("\"access\":\"" + guest.flag + "\"");
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
		while(true)
			readRequest();
	}

}
