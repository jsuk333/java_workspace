/*다량의 이미지를 웹서버의 공유된 디렉토리로부터 가져오자.
 * 이 방법을 이ㅏ용하면, 일일이 소켓고 스트림 처리를 할 필요가 없다.*/
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
		//라벨을 여러개 만들되 , 라벨에 이미지 아이콘을 붙이는데 그 경로를 url로 가져온다.
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
