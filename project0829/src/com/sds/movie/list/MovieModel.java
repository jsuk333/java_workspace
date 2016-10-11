package com.sds.movie.list;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import com.sds.main.AppMain;

public class MovieModel extends AbstractTableModel{
	ArrayList<String[]> list= new ArrayList<String[]>();
	String[] columnName={"������ȣ","����","�帣","������","�󿵽ð�","�̹������"};
	//��� ���ڵ� ��������
	public MovieModel() {
		selectAll();
	}
	@Override
	public int getColumnCount() {
		
		return columnName.length;
	}
	@Override
	public String getColumnName(int col) {
	
		return columnName[col];
	}

	@Override
	public int getRowCount() {
	
		return list.size();
	}

	@Override
	public Object getValueAt(int row, int col) {
		String[] record=list.get(row);
		return record[col];
	}
	
	//��ȭ��� ��������
	
	public void selectAll(){
		Connection con=AppMain.getConnection();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		//���ν� ��������, ���� ��� ���̺� �÷� ���� ������ ��� ��� �ؾ� �Ѵ�.
		String sql="select m.movie_id as movie_id, m.title as movie_title,g.title as genre,openday, show_time,img from movie m,genre g ";
		sql+="where g.genre_id=m.genre_id";
		try {
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				//���ڵ� 1�Ǵ� 1���� �迭 1���� ��ƾ� �Ѵ�.
				String[] record=new String[6];
				record[0]=Integer.toString(rs.getInt("movie_id"));
				record[1]=rs.getString("movie_title");
				record[2]=rs.getString("genre");
				record[3]=rs.getString("openday");
				record[4]=Integer.toString(rs.getInt("show_time"));
				record[5]=rs.getString("img");
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
	
}
