package com.sds.shopping.admin.member;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

import com.sds.shopping.admin.main.AppMain;

public class MemberMain extends JPanel{
	public MemberMain() {
		setBackground(Color.GRAY);
		setPreferredSize(new Dimension(AppMain.CONTENT_WIDTH, AppMain.CONTENT_HEIGHT));
		setVisible(false);
	}
}
