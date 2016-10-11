package com.sds.com;

import java.awt.Graphics;
import java.awt.Graphics2D;

public class Block extends GameObject{

	public Block(int x, int y, int width, int height, String name, ObjectManager objectManager) {
		super(x, y, width, height, name, objectManager);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(Graphics g) {
		//벽돌을 이미지로 표현하는 것은 보류
		showRect(g);
	}

}
