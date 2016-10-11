package com.sds.shopping.admin.sales;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

import com.sds.shopping.admin.main.AppMain;

public class SalesMain extends JPanel {
	public SalesMain() {
		setBackground(Color.blue);
		setPreferredSize(new Dimension(AppMain.CONTENT_WIDTH, AppMain.CONTENT_HEIGHT));
		setVisible(false);
	}
}
