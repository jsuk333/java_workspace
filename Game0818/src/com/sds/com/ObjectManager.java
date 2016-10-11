/*게임에 등장할 객체를 관리하는 클래스 정의
 * 
 * 객체에 대한 정보를 데이터베이스화 시켜서 저장하되,
 * 메모리상에,,, 객체를 모아서 관리 컬렉션 프레임웍*/

package com.sds.com;

import java.util.ArrayList;
import java.util.HashMap;

public class ObjectManager {

	ArrayList<GameObject> objList;
	public ObjectManager() {
		objList=new ArrayList<GameObject>();
	}
	public void addObject(GameObject gameObject){//오브젝트 추가
		objList.add(gameObject);
	}
	public void removeObject(GameObject gameObject){//오브젝트 제거
		objList.remove(gameObject);
	}
}
