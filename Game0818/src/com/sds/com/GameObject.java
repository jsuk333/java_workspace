package com.sds.com;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

abstract public class GameObject {
	int x;
	int y;
	int velX;
	int velY;
	int width;
	int height;
	String name;
	ObjectManager objectManager;
	Rectangle rect;
	//Toolkit kit;
	public GameObject(int x, int y, int width, int height, String name,ObjectManager objectManager) {
		this.x=x;
		this.y=y;
		this.width=width;
		this.height=height;
		this.name=name;
		this.objectManager=objectManager;
		//kit=Toolkit.getDefaultToolkit();
		rect=new Rectangle(this.x, this.y, this.width, this.height);
	}	
	abstract public void tick();
	abstract public void render(Graphics g);
	//클래스 상에 존재하는 이미지명만으로 , Image객체를 반환해주는 메서드!!
	public Image getImage(String name){
		Image img=null;//지역변수는 개발자가 사용전에 초기화 해주자
		Class myClass=this.getClass();
		URL url=myClass.getClassLoader().getResource(name);
		try {
			img=ImageIO.read(url);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return img;
	}
	//렉텡글 개체가 생성이 되었지만 눈에 보여지지는 않으므로 개발시만 ㅏ시각화 시킬 수 있는 네서드 저의
	//개발후에는 호출 x
	public void showRect(Graphics g){
		Graphics2D g2=(Graphics2D)g;//그래픽스의 기능에 2차원 그래픽 기능이 강화
		g2.setColor(Color.yellow);
		
		g2.draw(rect);
	}
	
}
