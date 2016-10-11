package com.sds.shopping.client.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.sds.shopping.client.purchase.PurchaseMain;

public class ProductMain extends JPanel {
	ArrayList<String> promotion_title=new ArrayList<String>();;
	JLabel[] la_promotion_title;
	
	public ProductMain() {
		
		getPromotionList();//아래의 반복문을 수행하기전에 프로모션 목록들은 이미 구해와야 하므로
		la_promotion_title = new JLabel[promotion_title.size()];
		for(int i=0;i<promotion_title.size();i++){
			la_promotion_title[i] = new JLabel(promotion_title.get(i));
			la_promotion_title[i].setBackground(Color.orange);
			la_promotion_title[i].setPreferredSize(new Dimension(700, 40));
			add(la_promotion_title[i]);
			//상품 생성!!;
			getPromotionPrductList(i);
		}
		setBackground(Color.CYAN);
		setPreferredSize(new Dimension(750, 1000));
	}
	public void getPromotionList(){
		Connection con=ClientMain.getConnection();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		String sql="select * from promotion";
		
		try {
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				promotion_title.add(rs.getString("title"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(rs!=null){
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(pstmt!=null){
				try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}
	
	public void getPromotionPrductList(int no){
		Connection con=ClientMain.getConnection();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		StringBuffer sql=new StringBuffer();
		sql.append("select pd.img as img, pd.product_name as product_name,pd.price as price , pd.stock as stock "
				+ "from promotion_product pm,product pd ");
		sql.append(" where pd.product_id=pm.product_id and promotion_id=");
		sql.append(" (select promotion_id from promotion where title=?)");
		
		try {
			pstmt=con.prepareStatement(sql.toString());
			pstmt.setString(1, promotion_title.get(no));
			rs=pstmt.executeQuery();
			while(rs.next()){
				Product product=new Product(rs.getString("img"),rs.getString("product_name"), rs.getInt("price"));
				final String img=rs.getString("img");
				final String product_name=rs.getString("product_name");
				final int price=rs.getInt("price");
				final int stock=rs.getInt("stock");
				product.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						PurchaseMain purchaseMain=new PurchaseMain(img,product_name,price,stock);
					}
				});
				add(product);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(rs!=null){
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(pstmt!=null){
				try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}
	
}
