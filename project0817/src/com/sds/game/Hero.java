//주인공을 정의한다!!!
package com.sds.game;
import java.awt.Color;
import java.awt.Graphics;

public class Hero extends GameObject{

	public Hero(int x, int y, int width, int height, String name) {
		super(x, y, width, height, name);
	}

	//일정한 물리량의 변화값을 결정하는 메서드!!
	public void tick(){	
		x+=velX;
		y+=velY;
	}
	
	//자신이 어떻게 그려져야할지를 결정하는 메서드!!
	public void render(Graphics g){
		//검정색으로 기존에 그려진 그림을 덮어버린다! (지우개)
		g.setColor(Color.BLACK);
		g.fillRect(0,0, GamePanel.WIDTH, GamePanel.HEIGHT);
		
		//주인공 그리기!
		g.setColor(Color.YELLOW);
		g.drawRect(x, y, 50,50);		
	}
	
}






