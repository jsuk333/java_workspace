package com.sds.screenShot;

import java.awt.AWTException;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.LinkedList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class ServerThread extends Thread {
	Socket client;
	MainServer mainServer;
	ObjectInputStream in;
	ObjectOutputStream out;
	boolean flag=true;//접속이 끊기면 쓰레드를 데드상태로 놓기위한 변수
	LinkedList<byte[]> list=new LinkedList<byte[]>();
	byte[] buffer;

	public ServerThread(Socket client, MainServer mainServer) {
		this.client = client;
		this.mainServer = mainServer;

		try {
			out=new ObjectOutputStream(client.getOutputStream());
			
			in = new ObjectInputStream(client.getInputStream());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void listen() {
		makeImage();
		list.add(buffer);
		
		//mainServer.area.append(buffer.toString() + "\n");
		for (int i = 0; i < mainServer.list.size(); i++)
			mainServer.list.get(i).writeServer();
		//writeServer();
		//list.removeFirst();
	}

	public void writeServer() {
		try {
			
			out.reset();
			out.writeObject(buffer);
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public BufferedImage screenShot(){
		Rectangle rect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
		ImageIcon icon=null;
		Image img=null;
		BufferedImage screenCapture=null;
		try {
			Robot robot=new Robot();
			screenCapture = robot.createScreenCapture(rect);
			img=screenCapture.getScaledInstance(800, 750, Image.SCALE_SMOOTH);
			screenCapture=toBufferedImage(img);
			Class myClass=this.getClass();
			
			URL url=myClass.getClassLoader().getResource("cursor.png");
			//마우스 포인터를 생성
			Image cursor = ImageIO.read(url);
			int x = MouseInfo.getPointerInfo().getLocation().x;
			int y = MouseInfo.getPointerInfo().getLocation().y;

			Graphics2D graphics2D = screenCapture.createGraphics();
			graphics2D.drawImage(cursor, x, y, 20, 20, null); // cursor.gif is 16x16 size.
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return screenCapture;
	}
	//서버에 스크린샷을 보내는 메소드
	public void makeImage(){
	    ByteArrayOutputStream bScrn = new ByteArrayOutputStream();
	    try {
			ImageIO.write(screenShot(), "jpg", bScrn);
		    bScrn.flush();
		    buffer = bScrn.toByteArray();
		    bScrn.close();
		    bScrn=null;
		    //System.out.println(buffer);
		    //in.notify();
		    
		   
		    /*inputObject=in.readObject();
		    if(inputObject!=null)
		    {
			    byte buffer2[] = (byte[]) inputObject;
				BufferedImage bImg = ImageIO.read(new ByteArrayInputStream(buffer2));
			    img=bImg;
			    p2.repaint();
			    System.out.println("읽어옴");
		    }*/
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public BufferedImage toBufferedImage(Image img)
	{
	    if (img instanceof BufferedImage)
	    {
	        return (BufferedImage) img;
	    }

	    // Create a buffered image with transparency
	    BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

	    // Draw the image on to the buffered image
	    Graphics2D bGr = bimage.createGraphics();
	    bGr.drawImage(img, 0, 0, null);
	    bGr.dispose();

	    // Return the buffered image
	    return bimage;
	}
	public void run() {
		while (flag) {
			listen();
		}
	}
}
