package com.sds.serverJoin;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.imageio.ImageIO;

import com.sds.UI.ScreenUI;

public class GuestThread extends Thread{
	ObjectOutputStream out;
	ObjectInputStream in;
	GuestStream guestStream;
	Object inputObject;
	ScreenUI scrennUI;

	public GuestThread(GuestStream guestStream,ScreenUI scrennUI) throws IOException {
		this.scrennUI=scrennUI;
		this.guestStream=guestStream;
		in=new ObjectInputStream(guestStream.client.getInputStream());
		out=new ObjectOutputStream(guestStream.client.getOutputStream());
		
		
		
	}
	public void readServer(){
		
		byte buffer[];
		try {
		    inputObject=in.readObject();
		    if(inputObject!=null)
		    {
		    	buffer = (byte[]) inputObject;
				BufferedImage bImg = ImageIO.read(new ByteArrayInputStream(buffer));
				scrennUI.img=bImg;
				scrennUI.screen.updateUI();
			   // System.out.println("�о��");
		    }
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}	
	public void run() {
		while(true)
		{
			readServer();
		}
	}
}
