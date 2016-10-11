/*�ٷ��� �̹����� �������� ������ ���丮�κ��� ��������.
 * �� ����� �̤����ϸ�, ������ ���ϰ� ��Ʈ�� ó���� �� �ʿ䰡 ����.*/
package com.sds.web;

import java.awt.Color;
import java.awt.Dimension;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class ImageLoad extends JFrame{
	JScrollPane scroll;
	JPanel p_container;
	
	public ImageLoad() {
		p_container=new JPanel();
		scroll=new JScrollPane(p_container);
		
		p_container.setBackground(Color.cyan);
		p_container.setPreferredSize(new Dimension(250, 100*20));
		//���� ������ ����� , �󺧿� �̹��� �������� ���̴µ� �� ��θ� url�� �����´�.
		URL url;
		try {
			for(int i=0;i<20;i++){
				url = new URL("http://70.12.112.106:9090/images/default.png");
				ImageIcon icon=new ImageIcon(url);
				JLabel la_img=new JLabel(icon);
				p_container.add(la_img);
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		add(scroll);
		
		setBounds(300, 100, 300, 400);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	
	public static void main(String[] args) {
		new ImageLoad();

	}

}