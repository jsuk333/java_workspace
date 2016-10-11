package com.sds.serverJoin;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import com.sds.UI.*;
public class GuestStream extends JFrame{


	String ip="70.12.112.105";
	int port = 8888;
	Socket client;
	Thread screenThread;
	Image img;
	Object inputObject;
	GuestThread gt;
	ScreenUI screenUI;
	public GuestStream(ScreenUI screenUI) {
		this.screenUI=screenUI;
		connect();
		
	}
	public void connect()
	{
		
		try {	
			client=new Socket(ip, port);
			setTitle("접속성공");
			
			gt=new GuestThread(this,screenUI);
			gt.start();
		    
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
