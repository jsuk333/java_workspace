/*���ӿ� ������ ��ü�� �����ϴ� Ŭ���� ����
 * 
 * ��ü�� ���� ������ �����ͺ��̽�ȭ ���Ѽ� �����ϵ�,
 * �޸𸮻�,,, ��ü�� ��Ƽ� ���� �÷��� �����ӿ�*/

package com.sds.com;

import java.util.ArrayList;
import java.util.HashMap;

public class ObjectManager {

	ArrayList<GameObject> objList;
	public ObjectManager() {
		objList=new ArrayList<GameObject>();
	}
	public void addObject(GameObject gameObject){//������Ʈ �߰�
		objList.add(gameObject);
	}
	public void removeObject(GameObject gameObject){//������Ʈ ����
		objList.remove(gameObject);
	}
}
