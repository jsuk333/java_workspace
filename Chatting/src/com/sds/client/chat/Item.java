/*ȸ�� �Ѹ���  ǥ���ϴ� ������ Ŭ���� ����*/
package com.sds.client.chat;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.sds.client.AppMain;

public class Item extends JPanel implements ActionListener{
	ImageIcon icon;
	JLabel la_profile,la_status;
	JButton bt;
	long you;
	// ��ü ������ �̹��� ��ο� ���� �޽����� �Ѱ� ����
	public Item(String path,String status,long you) {
		this.you=you;
		setPreferredSize(new Dimension(400, 60));
		URL url=this.getClass().getClassLoader().getResource(path);
		bt=new JButton("ģ���α�");
		icon=new ImageIcon(url);
		la_profile=new JLabel(icon);
		la_status=new JLabel(status);
		setLayout(new BorderLayout());
		add(la_profile,BorderLayout.WEST);
		add(la_status,BorderLayout.CENTER);
		add(bt,BorderLayout.EAST);
		
		bt.addActionListener(this);
	}
	public void makeFriend(){
		System.out.println(AppMain.me+"��"+you+"ģ�� �α�");
	}
	public void actionPerformed(ActionEvent e) {
		makeFriend();
		
	}
}
