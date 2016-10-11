package com.sds.shopping.client.purchase;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class DetailInfo extends JPanel{
	JPanel p_center;
	JLabel la_img;
	ImageIcon icon;
	JPanel p_right;//��ǰ�� ������ ��µ�
	JLabel la_south;//�� ���� �� �ִ� ��
	JButton bt_buy,bt_cart;
	JLabel la_product_name,la_price,la_stock;
	JTextField t_ea;
	String path="c:/product_img/";
	public DetailInfo(String filename,String product_name,int price,int stock) {
		setLayout(new BorderLayout());
		p_center=new JPanel(new GridLayout(1, 2));
		icon=new ImageIcon(path+filename);
		la_img=new JLabel(icon);
		p_right=new JPanel();
		la_south=new JLabel("������");
		p_right=new JPanel();
		bt_buy=new JButton("����");
		bt_cart=new JButton("��ٱ���");
		la_product_name=new JLabel(product_name);
		la_price=new JLabel(Integer.toString(price));
		la_stock=new JLabel(Integer.toString(stock));
		t_ea=new JTextField(8);//� ���� ����
		
		p_right.add(la_product_name);
		p_right.add(la_price);
		p_right.add(la_stock);
		p_right.add(t_ea);
		p_right.add(bt_buy);
		p_right.add(bt_cart);
		
		p_center.add(la_img);
		p_center.add(p_right);
		
		add(p_center);
		add(la_south, BorderLayout.SOUTH);
		
		la_img.setPreferredSize(new Dimension(300, 300));
		
		
	}
	
}
