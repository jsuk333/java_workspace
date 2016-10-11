/*�ڹپ��� ��Ʈ��ũ ���α׷��� �ۼ��� ��������
 * java.net ��Ű������ ��κ� �����Ѵ�.
 * ip ���ͳݰ� ����� ��ǻ�͵��� �ּ�
 * ���� 
 * */
package com.sds.network;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;

public class ChatServer extends JFrame{
	ServerSocket server;
	int port=9997;//1~1024������ �ý����� ������ ��Ʈ�̹Ƿ� ����ؼ� �ȵȴ�.
	//����Ŭ, mysql�� ������ ����Ʈ���� ���α׷� ��ȣ�� ���ؾ� �Ѵ�.
	InputStreamReader isr;
	BufferedReader buffr;
	OutputStreamWriter osw;
	BufferedWriter buffw;
	
	public ChatServer() {
		try {
			server=new ServerSocket(port);//���� ����
			System.out.println("�������� ����");
			Socket client=server.accept();//Ŭ���̾�Ʈ�� ������ �޾� ���̴� �޼���, ����) ������ ���� ������ ��� ���¿� ������.
			System.out.println("������ �߰�"+client.getInetAddress().getHostAddress());
			/*���Ͽ��� ���� Ŭ���̾�Ʈ�� ���õ� ������ ���ԵǾ� �����Ƿ�*/
			InputStream is=client.getInputStream();//������ �Է¿�
			OutputStream os=client.getOutputStream();//������ ��¿�
			isr=new InputStreamReader(is);
			buffr=new BufferedReader(isr);
			osw=new OutputStreamWriter(os);
			buffw=new BufferedWriter(osw);
			String data;
			while(true){
				data=buffr.readLine();//�����Ͱ� �Էµ� ������ ����
				System.out.print(data);
				buffw.write(data);
				buffw.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new ChatServer();
	}

}