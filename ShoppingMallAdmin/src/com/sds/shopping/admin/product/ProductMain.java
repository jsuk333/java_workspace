package com.sds.shopping.admin.product;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputMethodEvent;
import java.awt.event.InputMethodListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import com.sds.shopping.admin.main.AppMain;

public class ProductMain extends JPanel implements ItemListener, ActionListener,TableModelListener{
	//서쪽 영역
	JPanel p_west;
	Choice ch_top, ch_sub;
	ImageIcon icon;
	JLabel la_img;
	JTextField t_productname;
	JTextField t_price;
	JTextField t_stock;
	JTextArea detail;
	JScrollPane scroll;
	JButton bt_regist;
	
	//센터영역 
	JPanel p_center;//얹혀질 곳
	JPanel p_north;//프로모션 초이스 컴포넌트와 버튼
	JTable table;
	JScrollPane scroll2;
	ProductModel model;
	HashMap<String,Integer> topMap; //상위 카테고리 정보를 담아놓을 맵
	HashMap<String,Integer> subMap; //하위 카테고리 정보를 담아놓을 맵
	HashMap<String,Integer>	promotionMap;
	JFileChooser chooser;
	String savePath="C:/product_img"; //이미지 파일이 저장될 경로 
	FileInputStream fis;
	FileOutputStream fos;
	File file;
	Choice ch_promotion;
	JButton bt_send;
	
	public ProductMain() {
		p_west = new JPanel();
		ch_top = new Choice();
		ch_sub = new Choice();
		icon = new ImageIcon(this.getClass().getClassLoader().getResource("stock.png"));
		la_img=new JLabel(icon);
		t_productname = new JTextField(10);
		t_price = new JTextField("0",10);
		t_stock  =new JTextField("0",10);
		detail = new JTextArea(6,12);
		scroll = new JScrollPane(detail);
		bt_regist = new JButton("등록");
		
		la_img.setPreferredSize(new Dimension(128, 128));
		//숫자형 텍스트필드의 정렬을 우측으로...
		t_price.setHorizontalAlignment(SwingConstants.RIGHT);
		t_stock.setHorizontalAlignment(SwingConstants.RIGHT);
		
		ch_top.add("카테고리 선택▼");
		ch_sub.add("카테고리 선택▼");
		
		this.setLayout(new BorderLayout());
		
		p_west.add(ch_top);
		p_west.add(ch_sub);
		p_west.add(la_img);
		p_west.add(t_productname);
		p_west.add(t_price);
		p_west.add(t_stock);
		p_west.add(scroll);
		p_west.add(bt_regist);
		
		p_west.setPreferredSize(new Dimension(150, 550));
		add(p_west, BorderLayout.WEST);
		
		//센터영역 구성 
		p_center=new JPanel();
		p_north=new JPanel();
		bt_send=new JButton("선택");//프로모션으로 등록하기
		ch_promotion=new Choice();//
		
		ch_promotion.add("선택해 주세요");
		
		p_north.add(ch_promotion);
		p_north.add(bt_send);
		
		p_center.setLayout(new BorderLayout());
		p_center.add(p_north, BorderLayout.NORTH);
		
		
		
		model = new ProductModel();
		table = new JTable(model); //테이블 모델이 와야함!
		scroll2 = new JScrollPane(table);
		
		p_center.add(scroll2);
		
		chooser = new JFileChooser("C:/Users/student/Downloads");
		add(p_center);
		
		this.setPreferredSize(new Dimension(
				AppMain.CONTENT_WIDTH, 
				AppMain.CONTENT_HEIGHT));
		
		//초이스 컴포넌트와 리스너연결
		ch_top.addItemListener(this);
		la_img.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e) {
				int result=chooser.showOpenDialog(getParent());
				if(result == JFileChooser.APPROVE_OPTION){
					//c드라이브의 공유폴더에 이미지를 복사하자!!
					
					//입력스트림 생성!!
					try {
						
						fis = new FileInputStream(file=chooser.getSelectedFile());
						
						//빈 (empty) 파일 생성!!
						fos=new FileOutputStream(savePath+"/"+file.getName());
						byte[] b = new byte[1024];
						int data;
						while((fis.read(b))!=-1){
							fos.write(b);
							fos.flush();
						}
						JOptionPane.showMessageDialog(getParent(), "등록완료");
						
						BufferedImage img=ImageIO.read(file);
						icon.setImage(img);
						la_img.updateUI();//컴포넌트 갱신
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					} catch (IOException e1) {
						e1.printStackTrace();
					}finally{
						if(fos!=null){
							try {
								fos.close();
							} catch (IOException e1) {
								e1.printStackTrace();
							}
						}
						if(fis!=null){
							try {
								fis.close();
							} catch (IOException e1) {
								e1.printStackTrace();
							}
						}
					}
				}
			}
		});
		
		bt_regist.addActionListener(this);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row=table.getSelectedRow();
				int col=table.getSelectedColumn();
				System.out.println(row+","+col);
			}
		});
		//테이블과 모델 리스너 연결
		table.getModel().addTableModelListener(this);
		topMap = new HashMap<String,Integer>();
		subMap = new HashMap<String,Integer>();
		promotionMap=new HashMap<String,Integer>();
		
		bt_send.addActionListener(this);
		
		getTopCategory();
		getPromotionList();
		
	}
	
	//최상위 카테고리 목록 가져오기!!
	public void getTopCategory(){
		Connection con=AppMain.getConnection();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql="select * from topcategory";
		
		//초이스 컴포넌트에 데이터 채우기!
		try {
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			
			while(rs.next()){
				ch_top.add(rs.getString("title"));
				topMap.put(rs.getString("title"),rs.getInt("topcategory_id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(rs!=null){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(pstmt!=null){
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
		}
		
	}
	
	//하위 카테고리 목록 가져오기!
	public void getSubCategory(int subcategory_id){
		Connection con=AppMain.getConnection();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		String sql="select * from subcategory where topcategory_id=?";
		try {
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, subcategory_id);
			rs=pstmt.executeQuery();
			
			//기존 초이스의 아이템,  subMap  지우고..
			ch_sub.removeAll();
			subMap.clear();
			
			//하위 카테고리 초이스 컴포넌트를 채우기!!
			ch_sub.add("카테고리 선택▼");
			
			while(rs.next()){
				ch_sub.add(rs.getString("title"));
				subMap.put(rs.getString("title"),rs.getInt("subcategory_id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//상품 등록 
	public void regist(){
		Connection con=AppMain.getConnection();
		PreparedStatement pstmt=null;
		
		//입력을 제대로 했는지 여부를 검사하자(=유효성 체크)
		//왜?? 데이터베이스에 들어갈 데이터의 무결성때문에..
		if(ch_top.getSelectedIndex()==0){
			JOptionPane.showMessageDialog(this, "상위카테고리를 선택해주세요!!");
		}else if(ch_sub.getSelectedIndex()==0){
			JOptionPane.showMessageDialog(this, "서브카테고리를 선택해주세요!!");
		}else if(t_productname.getText().length()==0){
			JOptionPane.showMessageDialog(this, "상품명을 입력해 주세요");
			t_productname.requestFocus();
		}else if(t_price.getText().equals("0")){
			JOptionPane.showMessageDialog(this, "가격을 입력해 주세요!!");
			t_price.requestFocus();
		}else if(t_stock.getText().equals("0")){
			JOptionPane.showMessageDialog(this, "재고량을 입력해 주세요!!");
			t_stock.requestFocus();
		}else if(detail.getText().length()==0){
			JOptionPane.showMessageDialog(this, "상세설명을 입력해 주세요!!");
			detail.requestFocus();
		}else{
			JOptionPane.showMessageDialog(this, "DB에 넣을께요!");
			
			String sql="insert into product(product_id,subcategory_id,product_name,price,stock,detail,img)";
			sql=sql+" values(seq_product.nextval ,?,?,?,?,?,?)";
			try {
				pstmt=con.prepareStatement(sql);
				pstmt.setInt(1, subMap.get(ch_sub.getSelectedItem()));
				pstmt.setString(2, t_productname.getText());
				pstmt.setInt(3, Integer.parseInt(t_price.getText()));
				pstmt.setInt(4, Integer.parseInt(t_stock.getText()));
				pstmt.setString(5, detail.getText());
				pstmt.setString(6, file.getName());
				
				int result=pstmt.executeUpdate();
				if(result!=0){
					JOptionPane.showMessageDialog(this, "등록완료!");
					//jtable 갱신
					model.selectAll();
					table.updateUI();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				if(pstmt !=null){
					try {
						pstmt.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}	
		}
	}
	public void update(){
		int row=table.getSelectedRow();
		int col=0;
		Connection con=AppMain.getConnection();
		PreparedStatement pstmt=null;
		String product_id=(String)table.getValueAt(row, col);
		String sql="update product set product_name=?,price=? ,stock=?, detail=? "
				+ "where product_id=?";
		try {
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, (String)table.getValueAt(row, 2));
			pstmt.setInt(2, (Integer.parseInt((String)table.getValueAt(row, 3))));
			pstmt.setInt(3, (Integer.parseInt((String)table.getValueAt(row, 4))));
			pstmt.setString(4, (String)table.getValueAt(row, 5));
			pstmt.setInt(5, (Integer.parseInt((String)table.getValueAt(row, 0))));
			int result=pstmt.executeUpdate();
			if(result!=0){
				JOptionPane.showMessageDialog(this, "업데이트 성공");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
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
	
	//프로모션 목록 가져오기
	public void getPromotionList(){
		String sql="select * from promotion";
		Connection con=AppMain.getConnection();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		try {
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				promotionMap.put(rs.getString("title"),rs.getInt("promotion_id"));
				ch_promotion.add(rs.getString("title"));
				
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
	
	//프로모션 상품 등록하기
	
	public void registryPromotionProduct(){
		String sql="insert into promotion_product(promotion_product_id,product_id,promotion_id) "
				+ "values(seq_promotion_product.nextval,?,?)";
		Connection con=AppMain.getConnection();
		PreparedStatement pstmt=null;
		int product_id=Integer.parseInt(((String)table.getValueAt(table.getSelectedRow(), 0)));
		try {
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, product_id);
			pstmt.setInt(2, promotionMap.get((ch_promotion.getSelectedItem())));
			int result=pstmt.executeUpdate();
			if(result>0){
				JOptionPane.showMessageDialog(this,"상품코드" +product_id+"번을" +ch_promotion.getSelectedItem()+"에 입력성공");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
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
	
	public void itemStateChanged(ItemEvent e) {
		//초이스의 아이템을 바꿀때, topcategory_id 출력!!
		getSubCategory(topMap.get(ch_top.getSelectedItem()));
	}

	public void actionPerformed(ActionEvent e) {
		Object obj=e.getSource();
		if(obj==bt_regist){
			regist();
		}else if(obj==bt_send){
			//프로모션 등록 메서드
			if(ch_promotion.getSelectedIndex()!=0){
				registryPromotionProduct();
			}
		}
				
	}

	@Override
	public void tableChanged(TableModelEvent e) {
		update();
		System.out.println("테이블 정보 바뀌었어?");
	}
	
}
















