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
	//Ŭ���� �� �����ϴ� �̹��������� , Image��ü�� ��ȯ���ִ� �޼���!!
	public Image getImage(String name){
		Image img=null;//���������� �����ڰ� ������� �ʱ�ȭ ������
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
	//���ޱ� ��ü�� ������ �Ǿ����� ���� ���������� �����Ƿ� ���߽ø� ���ð�ȭ ��ų �� �ִ� �׼��� ����
	//�����Ŀ��� ȣ�� x
	public void showRect(Graphics g){
		Graphics2D g2=(Graphics2D)g;//�׷��Ƚ��� ��ɿ� 2���� �׷��� ����� ��ȭ
		g2.setColor(Color.yellow);
		
		g2.draw(rect);
	}
	
}
