/*�ϳ��� ���μ��������� ���������� ����Ǵ� ���� ���� ����
 * ������� �ϸ� Thread Ŭ������ �����Ѵ�.
 * ����) ������� �ڹ��� ������ �ƴϴ�!! �����ϴ� ��κ���
 * �������α׷��� �����尡 �����ȴ�.
 * C,C#, javascript(=setTimeout���� ����)
 * */
package com.sds.auto;

public class MyThread extends Thread {
	int count;
	/*�����ڴ� ���������� �����ϰ� ���� �ڵ尡 �ֵ��� run()�޼��带 ������ �ϸ� �ȴ�.*/
	@Override
	public void run() {
		while(true){
			try {
				Thread.sleep(1000);
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(count++);
			
		}
	}
}
