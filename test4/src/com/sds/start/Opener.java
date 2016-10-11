package com.sds.start;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.sds.client.ClientMain;

public class Opener extends JFrame implements Runnable {

	Image img;
	Toolkit kit;
	Thread th;
	ImageIcon icon;
	ImageIcon icon2;
	ImageIcon icon3;
	ImageIcon icon4;
	URL url;
	URL url2;
	URL url3;
	URL url4;
	JLabel label;

	float opacity = 0;
	boolean upOpacity = true;
	CardLayout card=new CardLayout();
	int cnt=0;
	public Opener() {
		
		url = this.getClass().getClassLoader().getResource("size.png");
		url2 = this.getClass().getClassLoader().getResource("size2.png");
		url3 = this.getClass().getClassLoader().getResource("size3.png");
		url4 = this.getClass().getClassLoader().getResource("size4.png");
		// System.out.println(url2);
		setLayout(card);
		icon = new ImageIcon(url);
		icon2 = new ImageIcon(url2);
		icon3=new ImageIcon(url3);
		icon4=new ImageIcon(url4);
		
		label = new JLabel(icon);
		label.setPreferredSize(new Dimension(400, 300));

		add(label);

		
		setUndecorated(true);
		th = new Thread(this);
		th.start();

		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		setUndecorated(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(400, 300);
		setLocation((d.width / 2) - getWidth() / 2, d.height / 2 - getHeight() / 2);
		setOpacity(opacity);
		setVisible(true);

	}

	public static void main(String[] args) {
		new Opener();

	}

	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (upOpacity) {
				opacity += 0.05;
				this.setOpacity(opacity);
				if (opacity >= 0.9) {
					cnt++;
					if(cnt==1)
					{
						opacity=(float)0.25;
						this.setOpacity(opacity);
						label.setIcon(icon2);
					}else if(cnt==2)
					{
						opacity=(float)0.25;
						this.setOpacity(opacity);
						label.setIcon(icon3);
					}
					else if(cnt==3)
					{
						opacity=(float)0.35;
						this.setOpacity(opacity);
						label.setIcon(icon4);
					}
					if(cnt==4)
					{
						this.setOpacity((float)0.9);
						upOpacity=false;
						try {
							Thread.sleep(1500);
							setVisible(false);
							new ClientMain();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
				}
				
			}

		}

	}

}
