/*
 * 게임에 사용될 키보드 이벤트를 전담하여 처리하는 객체 
 * */
package com.sds.game;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
public class KeyBoard extends KeyAdapter{
	ObjectManager objectManager ;
	
	//기존 오브젝트 메니져 얻어오기!!
	public KeyBoard(ObjectManager objectManager){
		this.objectManager=objectManager;
	}
	
	public void keyPressed(KeyEvent e) {
		int key=e.getKeyCode();
		
		for(int i=0; i<objectManager.objectList.size();i++){
			GameObject obj=objectManager.objectList.get(i);
			
			if(obj.name.equals("Hero")){
				//주인공의 경우만...
				switch(key){
					case KeyEvent.VK_LEFT: 	obj.velX=-2 ;break; 
					case KeyEvent.VK_UP: 		obj.velY=-2 ;break; 
					case KeyEvent.VK_RIGHT: obj.velX=2 ;break; 
					case KeyEvent.VK_DOWN: obj.velY=2 ;break; 
					case KeyEvent.VK_SPACE:  ;break; 
				}
			}
		}
	}
	
	public void keyReleased(KeyEvent e) {
		int key=e.getKeyCode();
		
		for(int i=0; i<objectManager.objectList.size();i++){
			GameObject obj=objectManager.objectList.get(i);
			
			if(obj.name.equals("Hero")){
				//주인공의 경우만...
				switch(key){
					case KeyEvent.VK_LEFT: 	obj.velX=0 ;break; 
					case KeyEvent.VK_UP: 		obj.velY=0 ;break; 
					case KeyEvent.VK_RIGHT: obj.velX=0 ;break; 
					case KeyEvent.VK_DOWN: obj.velY=0 ;break; 
				}
			}
		}		
	}
}









