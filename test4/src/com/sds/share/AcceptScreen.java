package com.sds.share;

import java.awt.Image;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.swing.JFrame;

import com.sds.client.ControllUI;
import com.sds.client.ScreenUI;

public class AcceptScreen extends JFrame {

	public String ip;
	int port = 9999;
	public Socket client;
	Thread screenThread;
	Image img;
	Object inputObject;
	AcceptThread gt;
	ScreenUI screenUI;
	public ControllUI controllUI;
	public ArrayList<AcceptThread> guestStreamList = new ArrayList<AcceptThread>();
	public AcceptScreen(ScreenUI screenUI, String ip,ControllUI controllUI) {
		this.controllUI=controllUI;
		this.screenUI = screenUI;
		this.ip = ip;
		System.out.println("ip");
		connect();
		
	}
	public void setIp(String ip){
		this.ip=ip;
	}

	public void connect() {
		try {
			client = new Socket(ip, port);
			System.out.println("서버접속성공");

			gt = new AcceptThread(this, screenUI);
			guestStreamList.add(gt);
			gt.start();

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void exitAccept() {
		for (int i = 0; i < guestStreamList.size(); i++) {
			guestStreamList.get(i).screenInputFlag = false;
			try {
				guestStreamList.get(i).out.close();
				guestStreamList.get(i).in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		guestStreamList.removeAll(guestStreamList);

	}
}
