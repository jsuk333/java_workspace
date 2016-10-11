package com.sds.share;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.imageio.ImageIO;

import com.sds.client.ScreenUI;

public class AcceptThread extends Thread{
	ObjectOutputStream out;
	ObjectInputStream in;
	AcceptScreen guestStream;
	Object inputObject;
	ScreenUI scrennUI;
	public boolean screenInputFlag=true;
	public AcceptThread(AcceptScreen guestStream,ScreenUI scrennUI) throws IOException {
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
			   // System.out.println("ÀÐ¾î¿È");
		    }
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
		}	
	}	
	public void run() {
		while(screenInputFlag)
		{
			readServer();
		}
	}
}
