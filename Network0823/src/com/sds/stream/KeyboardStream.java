/*
	1.Stream �̶�? �������� �帧
	
	2.�з�
	(1)���⼺(�������� ���α׷��� ����=���μ���)
		-�Է�(Input)
		-���(Output)
		
	(2) ������ ó�� ���
	-�⺻ ��Ʈ���̸� 1byte�� ����
	
	-���ڱ�� ��Ʈ��
	2btye�� �����ϴ� ��Ʈ��
	���ڸ� ǥ���� �� �ִ�.(�񿵾�� ���ڵ� ó�� ����)
	
	 -buffered ��Ʈ��
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
	public KeyboardStream() {//Ű���尰�� �ý����� �����ϴ� ǥ�� �Է� ��Ʈ���� �����ڰ� ���Ͻô� ������ ������� ������ �� ����.
		//���� �ý������� ���� ���;��Ѵ�. System.in
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
