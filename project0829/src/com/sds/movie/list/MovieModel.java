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
	String[] columnName={"고유번호","제목","장르","개봉일","상영시간","이미지경로"};
	//모든 레코드 가져오기
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
	
	//영화목록 가져오기
	
	public void selectAll(){
		Connection con=AppMain.getConnection();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		//조인시 주의할점, 조인 대상 테이블에 컬럼 명이 동일할 경우 명시 해야 한다.
		String sql="select m.movie_id as movie_id, m.title as movie_title,g.title as genre,openday, show_time,img from movie m,genre g ";
		sql+="where g.genre_id=m.genre_id";
		try {
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				//레코드 1건당 1차원 배열 1개로 담아야 한다.
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
