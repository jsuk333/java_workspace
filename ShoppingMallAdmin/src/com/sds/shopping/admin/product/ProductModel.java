/*jtable ���� ������ �����͸� �������� ����*/
package com.sds.shopping.admin.product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import com.sds.shopping.admin.main.AppMain;

public class ProductModel extends AbstractTableModel{
	String[] columns={"��ǰ�ڵ�","����ī�װ�","��ǰ��","����","���","�󼼼���","�̹���"};
	ArrayList<String[]> list=new ArrayList<String[]>();
	
	public ProductModel() {
		selectAll();
	}
	//��� ���ڵ� ��������\
	public void selectAll(){
		Connection con=AppMain.getConnection();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql="select product_id,s.title as subtitle,product_name,price,stock,detail,img "
				+ "from product p,subcategory s where p.subcategory_id=s.subcategory_id order by product_id asc";
		
		try {
			list.removeAll(list);
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			
			while(rs.next()){
				String[] record=new String[columns.length];
				record[0]=Integer.toString(rs.getInt("product_id"));
				record[1]=rs.getString("subtitle");
				record[2]=rs.getString("product_name");
				record[3]=Integer.toString(rs.getInt("price"));
				record[4]=Integer.toString(rs.getInt("stock"));
				record[5]=rs.getString("detail");
				record[6]=rs.getString("img");
				list.add(record);
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
	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return columns.length;
	}
	@Override
	public String getColumnName(int col) {
		// TODO Auto-generated method stub
		return columns[col];
	}
	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getValueAt(int row, int col) {
		// TODO Auto-generated method stub
		
		return ((String[])list.get(row))[col];
	}
	public void setValueAt(Object value, int row, int col) {
		
		String[] record=list.get(row);
		record[col]=(String)value;
		//�����Ͱ� ��ȭ �Ǿ����� �˷��ִ� �޼���
		fireTableCellUpdated(row, col);
	}
	
	@Override
	public boolean isCellEditable(int row, int col) {
		boolean cellFlag=false; 
		if(col>=2&&col<=5){
			cellFlag=true;
		}
		return cellFlag;
	}
	
	
}
