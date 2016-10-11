/*
 * 윈도우의 멀티태스킹
 * 하나의 윈도우 내에 동시에 여러 프로세스를 실행 시키는 방법
 * 
 * 멀티쓰레딩
 * 하나의 프로세스내에서 독립적으로 실행될 수 있는 세부 실행 단위를 
 * 쓰레드라 하며 이러한 쓰레드를 여러개 수행 시키는  방법을 
 * 멀티 쓰레드라 한다.
 * */
package com.sds.auto;

public class CounterApp {
	//우리가 지금까지 알고 있었던 실행부의 정확한 명칭은
	//메인 쓰레드 였따. 즉 자바는 쓰레드 단위이다.
	public static void main(String[] args) {
		//분신 생성
		MyThread mt=new MyThread();
		//StarThread st=new StarThread();
		//jvm에 맡긴다. 
		mt.start();
		//st.start();
	}

}
