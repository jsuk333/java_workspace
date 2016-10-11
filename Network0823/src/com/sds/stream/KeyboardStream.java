/*
	1.Stream 이란? 데이터의 흐름
	
	2.분류
	(1)방향성(실행중인 프로그램을 기준=프로세스)
		-입력(Input)
		-출력(Output)
		
	(2) 데이터 처리 방법
	-기본 스트림이며 1byte씩 이해
	
	-문자기반 스트림
	2btye씩 이해하는 스트림
	문자를 표현할 수 있다.(비영어권 문자도 처리 가능)
	
	 -buffered 스트림
*/
package com.sds.stream;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;

public class KeyboardStream {
	InputStream is;
	InputStreamReader ir;
	BufferedReader buffr;
	OutputStreamWriter iw;
	BufferedWriter buffw;
	OutputStream os;
	StringBuffer buff;
	String data;
	FileOutputStream fos;
	Byte b;
	String data2;
	public KeyboardStream() {//키보드같은 시스템이 제어하는 표준 입력 스트림은 개발자가 원하시는 시점에 마음대로 생성할 수 없다.
		//따라서 시스템으로 부터 얻어와야한다. System.in
		is=System.in;
		ir=new InputStreamReader(is);
		buff=new StringBuffer();
		buffr=new BufferedReader(ir);
		buff=new StringBuffer();
		try {
			fos=new FileOutputStream(new File("C:/java_workspace/project0820/test.txt"));
			iw=new OutputStreamWriter(fos);
			buffw=new BufferedWriter(iw);
			
			while(true){
				data=buffr.readLine();
				if((data).equals("EXIT")){
					break;
				}
				buffw.write(data+"\r\n");		
				buffw.flush();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(buffr!=null){
				try {
					buffr.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(buffw!=null){
				try {
					buffw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	public static void main(String[] args) {
		new KeyboardStream();
	}

}
