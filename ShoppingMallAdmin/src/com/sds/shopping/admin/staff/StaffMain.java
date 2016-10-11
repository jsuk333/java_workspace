package com.sds.shopping.admin.staff;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

import com.sds.shopping.admin.main.AppMain;

public class StaffMain extends JPanel{
	public StaffMain() {
		setBackground(Color.MAGENTA);
		setPreferredSize(new Dimension(AppMain.CONTENT_WIDTH, AppMain.CONTENT_HEIGHT));
		setVisible(false);
	}
}
