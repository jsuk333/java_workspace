package com.sds.shopping.admin.partner;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

import com.sds.shopping.admin.main.AppMain;

public class PartnerMain extends JPanel{
	public PartnerMain() {
		setBackground(Color.green);
		setPreferredSize(new Dimension(AppMain.CONTENT_WIDTH, AppMain.CONTENT_HEIGHT));
		setVisible(false);
	}
}
