/*영화상영정보*/
package com.sds.movie.list;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.sds.main.AppMain;

public class MovieList extends JPanel implements ActionListener{
	//jfilechooser 추가
	JFileChooser chooser;
	//서쪽
	JPanel p_west;
	ImageIcon icon;
	JLabel la_img;//영화의 프로필 사진
	Choice ch_genre;
	JTextField t_title,t_movie_id,t_openday,t_showtime;
	JButton bt_edit,bt_delete,bt_img;
	//센터
	JTable table;
	MovieModel model;
	JScrollPane scroll;
	FileInputStream fis;
	FileOutputStream fos;
	public MovieList() {
		p_west=new JPanel();
		p_west.setBackground(Color.yellow);
		p_west.setPreferredSize(new Dimension(150, 500));
		
		//서쪽 영역의 컴포넌트 들
		URL url=this.getClass().getClassLoader().getResource("default.png");
		icon=new ImageIcon(url);
		la_img=new JLabel(icon);
		
		ch_genre=new Choice();
		getGenre();
		
		t_title=new JTextField(10);
		t_openday=new JTextField(10);
		t_showtime=new JTextField(10);
		t_movie_id=new JTextField(10);
		bt_edit=new JButton("수정");
		bt_delete=new JButton("삭제");
		bt_img=new JButton("이미지 등록");
		chooser=new JFileChooser("C:/Users/efro2/Downloads");
		
		p_west.add(la_img);
		p_west.add(bt_img);
		p_west.add(ch_genre);
		p_west.add(t_movie_id);
		p_west.add(t_title);
		p_west.add(t_openday);
		p_west.add(t_showtime);
		p_west.add(bt_edit);
		p_west.add(bt_delete);
		
		table=new JTable(model=new MovieModel());
		scroll=new JScrollPane(table);
		setLayout(new BorderLayout());
		//서쪽
		add(p_west, BorderLayout.WEST);
		//센터
		add(scroll);
		
		//테이블과 마우스 리스너 연결
		table.addMouseListener(new MouseAdapter() {
			
			public void mouseClicked(MouseEvent e) {
				String title=(String) table.getValueAt(table.getSelectedRow(), 1);
				String movie_id=(String) table.getValueAt(table.getSelectedRow(),0 );
				String showtime=(String) table.getValueAt(table.getSelectedRow(), 2);
				String openday=(String) table.getValueAt(table.getSelectedRow(), 3);
				String genre=(String) table.getValueAt(table.getSelectedRow(), 4);
				String img=(String) table.getValueAt(table.getSelectedRow(), 5);
				t_title.setText(title);
				t_movie_id.setText(movie_id);
				
				t_openday.setText(openday.substring(0,10));
				t_showtime.setText(showtime);
				ch_genre.select(genre);
				
				URL url=this.getClass().getClassLoader().getResource(img);
				icon=new ImageIcon(url);
				la_img.setIcon(icon);
				p_west.updateUI();
				
			}
		});
		//버튼과 연결 이미지 변경
		bt_img.addActionListener(this);
		bt_delete.addActionListener(this);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj=e.getSource();
		if(obj==bt_img){
			//파일 복사 및 db업데이트
			setProfileImg();
		}else if(obj==bt_delete){
			deleteImg();
		}else if(obj==bt_edit){
			
		}
		
	}
	public void deleteImg(){
		Connection con=AppMain.getConnection();
		String sql="delete from movie where movie_id=?";
		System.out.println("fff");
		try {
			File file=new File("C:/java_workspace/project0829/res/"+table.getValueAt(table.getSelectedRow(), 5));
			System.out.println(file.getAbsolutePath());
			boolean flag=file.delete();
			if(flag){
				System.out.println("파일이 지워졌습니다.");
			}
			PreparedStatement pstmt=con.prepareStatement(sql);
			pstmt.setInt(1,Integer.parseInt(t_movie_id.getText()));
			int result=pstmt.executeUpdate();
			if(result>0){
				System.out.println(result+"개의 데이터가 지워졌습니다.");
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//파일 복사 및 db업데이트
	public void setProfileImg(){
		int result=chooser.showOpenDialog(this);
		if(result==JFileChooser.APPROVE_OPTION){
			//유저가 보유한 로컬 드라이브에 있는 파일을 현재 프로젝트안에 res로 복사하자
			try {
				File file=chooser.getSelectedFile();
				fis=new FileInputStream(file.getAbsolutePath());
				//URL url=this.getClass().getClassLoader().getResource(".");
				//String path=url.toString().replaceAll("\\", "/");
				String dir="C:/java_workspace/project0829/res/";
				fos=new FileOutputStream(dir+file.getName());
				int data=0;
				byte[] b=new byte[1024];
				while((data=fis.read(b))!=-1){
					System.out.println(data);
					fos.write(b);
					fos.flush();
				}
				//db에 파일명 업데이트
				String sql="update movie set img=? where movie_id=?";
				String img=file.getName();
				int movie_id=Integer.parseInt(t_movie_id.getText());
				Connection con=AppMain.getConnection();
				PreparedStatement pstmt=con.prepareStatement(sql);
				pstmt.setString(1, img);
				pstmt.setLong(2, movie_id);
				int updateCount=pstmt.executeUpdate();
				if(updateCount>0){
					JOptionPane.showMessageDialog(this, updateCount+"개의 데이터가 변경 되었습니다.");
					model.selectAll();
					model.fireTableDataChanged();
					table.updateUI();
				}
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				if(fis!=null){
					try {
						fis.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if(fos!=null){
					try {
						fos.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
		}
	}
	//초이스 컴포넌트를 장르에서 불러오자
	public void getGenre(){
		Connection con=AppMain.getConnection();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql="select * from genre";
		try {
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				ch_genre.add(rs.getString("title"));
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
