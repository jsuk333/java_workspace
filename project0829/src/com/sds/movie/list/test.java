package com.sds.movie.list;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.microsoft.schemas.office.visio.x2012.main.CellType;

public class test {
	public static void main(String[] args) {
		//엑셀파일을 제어하기 위한 객체
		Connection con=null;
		PreparedStatement pstmt=null;
		File file=new File("C:/movie.xlsx");
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "java0819", "java0819");
		
			if(con!=null){
				System.out.println(con);
				
			}else{
				
			}
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			XSSFWorkbook workbook=new XSSFWorkbook(file);
			//열린파일에 대해 시트로 접근해보자
			XSSFSheet sheet=workbook.getSheet("movie");
			
			for(int i=1;i<sheet.getLastRowNum()+1;i++){
				XSSFRow row=sheet.getRow(i);
				String sql="insert into movie(movie_id,title,genre_id,show_time,openday) ";
				sql+="values(seq_movie.nextval,?,?,?,?)";
				try {
					pstmt=con.prepareStatement(sql);
					for(int j=0;j<row.getLastCellNum();j++){
						XSSFCell cell= row.getCell(j);
						if(cell.getCellType()==Cell.CELL_TYPE_STRING){
							pstmt.setString(j+1, cell.getStringCellValue());
						}else{
							pstmt.setInt(j+1, (int)cell.getNumericCellValue());
						}						
					}
					System.out.println(sql);
					pstmt.executeUpdate();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally{
					if(pstmt==null){
						try {
							pstmt.close();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				//하나의 레코드가 끝나느 시점
				System.out.println("");
			}
			
		} catch (InvalidFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
