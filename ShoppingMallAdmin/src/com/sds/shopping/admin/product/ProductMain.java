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
	//���� ����
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
	
	//���Ϳ��� 
	JPanel p_center;//������ ��
	JPanel p_north;//���θ�� ���̽� ������Ʈ�� ��ư
	JTable table;
	JScrollPane scroll2;
	ProductModel model;
	HashMap<String,Integer> topMap; //���� ī�װ� ������ ��Ƴ��� ��
	HashMap<String,Integer> subMap; //���� ī�װ� ������ ��Ƴ��� ��
	HashMap<String,Integer>	promotionMap;
	JFileChooser chooser;
	String savePath="C:/product_img"; //�̹��� ������ ����� ��� 
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
		bt_regist = new JButton("���");
		
		la_img.setPreferredSize(new Dimension(128, 128));
		//������ �ؽ�Ʈ�ʵ��� ������ ��������...
		t_price.setHorizontalAlignment(SwingConstants.RIGHT);
		t_stock.setHorizontalAlignment(SwingConstants.RIGHT);
		
		ch_top.add("ī�װ� ���á�");
		ch_sub.add("ī�װ� ���á�");
		
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
		
		//���Ϳ��� ���� 
		p_center=new JPanel();
		p_north=new JPanel();
		bt_send=new JButton("����");//���θ������ ����ϱ�
		ch_promotion=new Choice();//
		
		ch_promotion.add("������ �ּ���");
		
		p_north.add(ch_promotion);
		p_north.add(bt_send);
		
		p_center.setLayout(new BorderLayout());
		p_center.add(p_north, BorderLayout.NORTH);
		
		
		
		model = new ProductModel();
		table = new JTable(model); //���̺� ���� �;���!
		scroll2 = new JScrollPane(table);
		
		p_center.add(scroll2);
		
		chooser = new JFileChooser("C:/Users/student/Downloads");
		add(p_center);
		
		this.setPreferredSize(new Dimension(
				AppMain.CONTENT_WIDTH, 
				AppMain.CONTENT_HEIGHT));
		
		//���̽� ������Ʈ�� �����ʿ���
		ch_top.addItemListener(this);
		la_img.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e) {
				int result=chooser.showOpenDialog(getParent());
				if(result == JFileChooser.APPROVE_OPTION){
					//c����̺��� ���������� �̹����� ��������!!
					
					//�Է½�Ʈ�� ����!!
					try {
						
						fis = new FileInputStream(file=chooser.getSelectedFile());
						
						//�� (empty) ���� ����!!
						fos=new FileOutputStream(savePath+"/"+file.getName());
						byte[] b = new byte[1024];
						int data;
						while((fis.read(b))!=-1){
							fos.write(b);
							fos.flush();
						}
						JOptionPane.showMessageDialog(getParent(), "��ϿϷ�");
						
						BufferedImage img=ImageIO.read(file);
						icon.setImage(img);
						la_img.updateUI();//������Ʈ ����
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
		//���̺�� �� ������ ����
		table.getModel().addTableModelListener(this);
		topMap = new HashMap<String,Integer>();
		subMap = new HashMap<String,Integer>();
		promotionMap=new HashMap<String,Integer>();
		
		bt_send.addActionListener(this);
		
		getTopCategory();
		getPromotionList();
		
	}
	
	//�ֻ��� ī�װ� ��� ��������!!
	public void getTopCategory(){
		Connection con=AppMain.getConnection();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql="select * from topcategory";
		
		//���̽� ������Ʈ�� ������ ä���!
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
	
	//���� ī�װ� ��� ��������!
	public void getSubCategory(int subcategory_id){
		Connection con=AppMain.getConnection();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		String sql="select * from subcategory where topcategory_id=?";
		try {
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, subcategory_id);
			rs=pstmt.executeQuery();
			
			//���� ���̽��� ������,  subMap  �����..
			ch_sub.removeAll();
			subMap.clear();
			
			//���� ī�װ� ���̽� ������Ʈ�� ä���!!
			ch_sub.add("ī�װ� ���á�");
			
			while(rs.next()){
				ch_sub.add(rs.getString("title"));
				subMap.put(rs.getString("title"),rs.getInt("subcategory_id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//��ǰ ��� 
	public void regist(){
		Connection con=AppMain.getConnection();
		PreparedStatement pstmt=null;
		
		//�Է��� ����� �ߴ��� ���θ� �˻�����(=��ȿ�� üũ)
		//��?? �����ͺ��̽��� �� �������� ���Ἲ������..
		if(ch_top.getSelectedIndex()==0){
			JOptionPane.showMessageDialog(this, "����ī�װ��� �������ּ���!!");
		}else if(ch_sub.getSelectedIndex()==0){
			JOptionPane.showMessageDialog(this, "����ī�װ��� �������ּ���!!");
		}else if(t_productname.getText().length()==0){
			JOptionPane.showMessageDialog(this, "��ǰ���� �Է��� �ּ���");
			t_productname.requestFocus();
		}else if(t_price.getText().equals("0")){
			JOptionPane.showMessageDialog(this, "������ �Է��� �ּ���!!");
			t_price.requestFocus();
		}else if(t_stock.getText().equals("0")){
			JOptionPane.showMessageDialog(this, "����� �Է��� �ּ���!!");
			t_stock.requestFocus();
		}else if(detail.getText().length()==0){
			JOptionPane.showMessageDialog(this, "�󼼼����� �Է��� �ּ���!!");
			detail.requestFocus();
		}else{
			JOptionPane.showMessageDialog(this, "DB�� ��������!");
			
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
					JOptionPane.showMessageDialog(this, "��ϿϷ�!");
					//jtable ����
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
				JOptionPane.showMessageDialog(this, "������Ʈ ����");
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
	
	//���θ�� ��� ��������
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
	
	//���θ�� ��ǰ ����ϱ�
	
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
				JOptionPane.showMessageDialog(this,"��ǰ�ڵ�" +product_id+"����" +ch_promotion.getSelectedItem()+"�� �Է¼���");
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
		//���̽��� �������� �ٲܶ�, topcategory_id ���!!
		getSubCategory(topMap.get(ch_top.getSelectedItem()));
	}

	public void actionPerformed(ActionEvent e) {
		Object obj=e.getSource();
		if(obj==bt_regist){
			regist();
		}else if(obj==bt_send){
			//���θ�� ��� �޼���
			if(ch_promotion.getSelectedIndex()!=0){
				registryPromotionProduct();
			}
		}
				
	}

	@Override
	public void tableChanged(TableModelEvent e) {
		update();
		System.out.println("���̺� ���� �ٲ����?");
	}
	
}
















