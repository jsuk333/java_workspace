package com.sds.com;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

public class Ship extends GameObject{
	Image img;
	Bullet bullet;
	public Ship(int x, int y, int width, int height, String name,ObjectManager objectManager) {
		super(x, y, width, height, name,objectManager);
		//img=kit.getImage("C:/java_workspace/Game0818/res/spaceship.png");
		img=getImage("spaceship.png");
	}

	@Override
	public void tick() {	
		x+=velX;
		y+=velY;
		rect.setBounds(x, y, width, height);
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(img, x, y, width, height, null);
		showRect(g);
		g.translate(x, y);
	}
	public void fire(){
		bullet=new Bullet(x+width/2, y+height/2, 30, 20, "Bullet",objectManager);
		objectManager.objList.add(bullet);
	}
	
	
	
}
