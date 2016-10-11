package com.sds.com;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.Random;

public class Enemy extends GameObject{
	Image img;
	String[] imgPath={"e1.png","e2.png","e3.png","e4.png"};
	public Enemy(int x, int y, int width, int height, String name, ObjectManager objectManager) {
		super(x, y, width, height, name, objectManager);
		Random random=new Random();
		img=getImage(imgPath[random.nextInt(imgPath.length)]);
		//velX=-1;
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
	}

}
