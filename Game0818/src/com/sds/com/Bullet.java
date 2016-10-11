package com.sds.com;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

public class Bullet extends GameObject{
	Image img;
	GameObject obj;
	public Bullet(int x, int y, int width, int height, String name,ObjectManager objectManager) {
		super(x, y, width, height, name, objectManager);
		img=getImage("bullet.png");
		velX=4;
		
	}

	@Override
	public void tick() {
		x+=velX;
		y+=velY;
		rect.setBounds(x, y, width, height);
		if(hitTest()){
			
		}
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(img, x, y, width, height, null);
		showRect(g);
		
	}
	public boolean hitTest(){
		//총알과 적군과 충돌 검사
		for(int i=0;i<objectManager.objList.size();i++){
			if(objectManager.objList.get(i).name.equals("Enemy")){
				GameObject obj=objectManager.objList.get(i);
				if(rect.intersects(obj.rect)){	
					objectManager.removeObject(obj);
					objectManager.removeObject(this);
					return true;
				}
			}
		}
		return false;
	}

	
}
