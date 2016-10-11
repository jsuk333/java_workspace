package com.sds.share;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.ArrayList;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JOptionPane;

import com.sds.admin.Join;

public class ScreenShotServer implements Runnable {


	public Thread acceptThread;
	public SrceenShotThread st;
	public ServerSocket server;
	//스크린생성 포트
	int port = 9999;
	public Vector<SrceenShotThread> screenlist=new Vector<SrceenShotThread>(); ;
	public ArrayList<Socket> joinList=new ArrayList<Socket>();
	public boolean flag=true;
	Join join;
	public ScreenShotServer(Join join) {
		this.join=join;
		acceptThread = new Thread(this);
		acceptThread.start();
	}


	public void run() {
		// 쓰레드에서 서버를 가동한다
		startServer();
	}


	public void startServer() {
		try {
			server = new ServerSocket(port);	

			while (flag) {
				Socket client = server.accept();
				System.out.println(client);
				joinList.add(client);
				
				// client가 접속할때 까지 대기 상태
				// client별로 소켓을 새로만들고 각자의 쓰레드에서 통신하도록 한다
				st=new SrceenShotThread(client,this);
				st.start();
				screenlist.add(st);
			}
		} catch (IOException e) {
			e.printStackTrace();
			
		}
	}
	public void searchServer(){
		for(int i=0;i<joinList.size();i++)
		{
			String guestIp=joinList.get(i).getInetAddress().getHostAddress();
			System.out.println(guestIp);
			screenlist.get(i).flagScreen=false;
			try {
				screenlist.get(i).out.close();
				screenlist.get(i).in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			screenlist.remove(screenlist);	
		}
		try {
			if(server!=null)
			{
				server.close();
				flag=false;
				JOptionPane.showMessageDialog(join, "서버가 닫힘");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void exitSocket(){
		for(int i=0;i<screenlist.size();i++)
		{
			screenlist.get(i).flagScreen=false;
		}
	}
}
