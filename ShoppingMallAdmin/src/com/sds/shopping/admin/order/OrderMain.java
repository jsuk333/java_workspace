package com.sds.shopping.admin.order;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

import com.sds.shopping.admin.main.AppMain;

public class OrderMain extends JPanel{
	public OrderMain() {
		setBackground(Color.CYAN);
		setPreferredSize(new Dimension(AppMain.CONTENT_WIDTH, AppMain.CONTENT_HEIGHT));
		setVisible(false);
	}
}
