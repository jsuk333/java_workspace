package com.sds.com;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyBoard extends KeyAdapter{
	ObjectManager objectManager;
	GameObject ship;
	public KeyBoard(ObjectManager objectManager) {
		this.objectManager=objectManager;
		for(int i=0;i<this.objectManager.objList.size();i++){
			if(this.objectManager.objList.get(i).name.equals("Ship")){
				ship=this.objectManager.objList.get(i);
				break;
			}
		}
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if(objectManager!=null){
			int key=e.getKeyCode();
			switch(key){
			case KeyEvent.VK_LEFT: ship.velX=-5; break;
			case KeyEvent.VK_UP: ship.velY=-5; break;
			case KeyEvent.VK_RIGHT: ship.velX=5; break;
			case KeyEvent.VK_DOWN: ship.velY=5; break;
			case KeyEvent.VK_SPACE: ((Ship)ship).fire();break;
			}
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
		if(objectManager!=null){
			int key=e.getKeyCode();
			switch(key){
			case KeyEvent.VK_LEFT: ship.velX=0; break;
			case KeyEvent.VK_UP: ship.velY=0; break;
			case KeyEvent.VK_RIGHT: ship.velX=0; break;
			case KeyEvent.VK_DOWN: ship.velY=0; break;
			}
		}
	}
	
	
}
