/*�󼼺���_. ���� �������Է�ȣ��-->����ȭ��-->���� �Ϸ�*/
package com.sds.shopping.client.purchase;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class PurchaseMain extends JFrame{
	JPanel p_container;
	DetailInfo detailInfo;
	public PurchaseMain(String filename,String product_name,int price,int stock) {
		p_container=new JPanel();
		detailInfo=new DetailInfo(filename, product_name, price	, stock);
		p_container.add(detailInfo);
		
		add(p_container);
		
		setSize(600,700);
		setVisible(true);
	}
	
	
}
