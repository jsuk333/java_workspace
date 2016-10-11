package com.sds.UI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class ScreenUI extends JFrame{
	public JPanel screen;
	public Image img=null;
	public ScreenUI() {
		
		screen=new JPanel(){
			public void paint(Graphics g) {
				g.drawImage(img, 0, 0, null);
			}
		};
		screen.setPreferredSize(new Dimension(800, 750));
		add(screen);
		setTitle("Screen");
		setUndecorated(true);
		//DropShadowBorder b = new DropShadowBorder(Color.BLACK, 0, 10, 0.2f, 10, true, true, true, true);
		//this.getRootPane().setBorder(b);
		//setOpacity((float) 0.9);
		setBounds(100, 50, 800, 750);
		setVisible(true);
		
	}
}
