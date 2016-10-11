package com.sds.admin;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.sds.client.ClientMain;


public class ManageMain extends JFrame implements ActionListener{
	
	JTable table;
	JScrollPane scroll;
	JButton bt_del,bt_alt,bt_search,bt_refresh;
	JLabel la_member_id,la_id,la_regdate,la_user_ip,la_isconnection,la_room_num;
	JLabel la_member_id_title,la_id_title,la_password_title,la_name_title,la_nick_name_title,la_gender_title,la_e_mail_title,la_regdate_title,
	la_isconnetion_title,la_room_num_title,la_isAdmin_title,la_user_ip_title;
	JTextField t_password,t_name,t_nick_name,t_e_mail;
	JRadioButton r_male,r_female,r_manager,r_user;
	ButtonGroup btg_sex,btg_author;
	JPanel p_west,p_north,p_data;
	JPanel p_sex,p_author;
	JTextField t_search_id;
	StringBuffer sb;
	ManageModel model;
	ClientMain clientMain;
	int gender;
	int admin;
	
	public ManageMain(ClientMain clientMain) {
		this.clientMain=clientMain;
		table=new JTable(model=new ManageModel(this.clientMain));
		scroll=new JScrollPane(table);
		p_west=new JPanel();
		p_data=new JPanel(new GridLayout(12,2));
		p_north=new JPanel();
		bt_del=new JButton("삭제");
		bt_alt=new JButton("수정");
		bt_search=new JButton("검색");
		bt_refresh=new JButton("새로고침");
		t_search_id=new JTextField(15);
		sb=new StringBuffer();
		
		la_member_id=new JLabel("멤버번호");
		la_id=new JLabel("아이디");
		t_password=new JTextField(10);
		t_name=new JTextField(10);
		t_nick_name=new JTextField(10);
		r_male=new JRadioButton("1");
		r_female=new JRadioButton("0");
		btg_sex=new ButtonGroup();
		btg_sex.add(r_male);
		btg_sex.add(r_female);
		if(r_male.isSelected()){
			gender=1;
		}else if(r_female.isSelected()){
			gender=0;
		}
		p_sex=new JPanel();
		p_sex.add(r_male);
		p_sex.add(r_female);
		t_e_mail=new JTextField(10);
		la_regdate=new JLabel("가입날짜");
		la_isconnection=new JLabel();
		la_room_num=new JLabel();
		r_manager=new JRadioButton("1");
		r_user=new JRadioButton("0");
		btg_author=new ButtonGroup();
		btg_author.add(r_manager);
		btg_author.add(r_user);
		if(r_manager.isSelected()){
			admin=1;
		}else if(r_user.isSelected()){
			admin=0;
		}
		p_author=new JPanel();
		p_author.add(r_manager);
		p_author.add(r_user);
		la_user_ip=new JLabel("아이피");
		
		la_member_id_title=new JLabel("멤버번호");
		la_id_title=new JLabel("아이디");
		la_password_title=new JLabel("비밀번호");
		la_name_title=new JLabel("이름");
		la_nick_name_title=new JLabel("별명");
		la_gender_title=new JLabel("성별(남/여)");
		la_e_mail_title=new JLabel("이메일");
		la_regdate_title=new JLabel("가입날짜");
		la_isconnetion_title=new JLabel("접속여부");
		la_room_num_title=new JLabel("방번호");
		la_isAdmin_title=new JLabel("권한(Manager/user)");
		la_user_ip_title=new JLabel("아이피");
		
		la_member_id.setPreferredSize(new Dimension(120, 40));
		la_id.setPreferredSize(new Dimension(120, 30));
		la_regdate.setPreferredSize(new Dimension(120, 40));
		la_user_ip.setPreferredSize(new Dimension(120, 40));
		
		p_data.add(la_member_id_title);
		p_data.add(la_member_id);
		p_data.add(la_id_title);
		p_data.add(la_id);
		p_data.add(la_password_title);
		p_data.add(t_password);
		p_data.add(la_name_title);
		p_data.add(t_name);
		p_data.add(la_nick_name_title);
		p_data.add(t_nick_name);
		p_data.add(la_gender_title);
		p_data.add(p_sex);
		p_data.add(la_e_mail_title);
		p_data.add(t_e_mail);
		p_data.add(la_regdate_title);
		p_data.add(la_regdate);
		p_data.add(la_isconnetion_title);
		p_data.add(la_isconnection);
		p_data.add(la_room_num_title);
		p_data.add(la_room_num);
		p_data.add(la_isAdmin_title);
		p_data.add(p_author);
		p_data.add(la_user_ip_title);
		p_data.add(la_user_ip);
		
		p_data.setPreferredSize(new Dimension(160, 400));
		p_west.setPreferredSize(new Dimension(200, 550));
		p_north.setPreferredSize(new Dimension(600, 50));
		
		p_west.add(p_data);
		p_north.add(t_search_id);
		p_north.add(bt_search);
		p_north.add(bt_refresh);
		p_west.add(bt_alt);
		p_west.add(bt_del);
		
		add(p_north,BorderLayout.NORTH);
		add(p_west,BorderLayout.WEST);
		add(scroll);
		
		showDB();
		
		setSize(1100, 800);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		
		bt_search.addActionListener(this);
		bt_alt.addActionListener(this);
		bt_del.addActionListener(this);
		bt_refresh.addActionListener(this);
		
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				la_member_id.setText((String)table.getValueAt(table.getSelectedRow(),0));
				la_id.setText((String)table.getValueAt(table.getSelectedRow(),1));
				t_password.setText((String)table.getValueAt(table.getSelectedRow(),2));
				t_name.setText((String)table.getValueAt(table.getSelectedRow(),3));
				t_nick_name.setText((String)table.getValueAt(table.getSelectedRow(),4));
				gender=Integer.parseInt((String)table.getValueAt(table.getSelectedRow(),5));
				if(gender==0){
					r_female.setSelected(true);
				}else{
					r_male.setSelected(true);
				}
				t_e_mail.setText((String)table.getValueAt(table.getSelectedRow(),6));
				la_regdate.setText((String)table.getValueAt(table.getSelectedRow(),7));
				la_isconnection.setText((String)table.getValueAt(table.getSelectedRow(),8));
				la_room_num.setText((String)table.getValueAt(table.getSelectedRow(),9));
				if(table.getValueAt(table.getSelectedRow(),10)!=null){
					admin=Integer.parseInt(((String)table.getValueAt(table.getSelectedRow(),10)));
					if(admin==0){
						r_manager.setSelected(true);
					}else{
						r_user.setSelected(true);
					}
				};
				la_user_ip.setText((String)table.getValueAt(table.getSelectedRow(),11));
			}
		});
		
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {//액션 이벤트
		Object obj=e.getSource();
		if(obj==bt_search){
			search();
		}else if(obj==bt_alt){
			if(la_id!=null){//
				edit();
			}
		}else if(obj==bt_del){
			if(la_id!=null){
				delete();
			}
		}else if(obj==bt_refresh){
			showDB();
		}
	}
	
	public void showDB(){//db자료 테이블에 뿌리기 위해 서버에 요청
		sb.delete(0, sb.length());
		sb.append("{\"request\" : \"list\",");
		sb.append("\"data\" : {");
		sb.append("}}");
		clientMain.sendMsg(sb);
		table.updateUI();
	}
	
	public void search(){//db자료 테이블에 뿌리기 위해 서버에 요청
		sb.delete(0, sb.length());
		sb.append("{\"request\" : \"search\",");
		sb.append("\"data\" : {");
		sb.append("\"id\":\""+t_search_id.getText()+"\"");
		sb.append("}}");
		clientMain.sendMsg(sb);
		table.updateUI();
	}
	
	public void edit(){//db수정하기 위해 서버에 요청 (수정된 데이터 첨부)
		sb.delete(0, sb.length());
		sb.append("{\"request\" : \"edit\",");
		sb.append("\"data\" : ");
		sb.append("{");
		sb.append("\"member_id\":\""+la_member_id.getText()+"\",");
		sb.append("\"id\":\""+la_id.getText()+"\",");
		sb.append("\"password\":\""+t_password.getText()+"\",");
		sb.append("\"name\":\""+t_name.getText()+"\",");
		sb.append("\"nick_name\":\""+t_nick_name.getText()+"\",");
		sb.append("\"gender\":\""+gender+"\",");
		sb.append("\"e_mail\":\""+t_e_mail.getText()+"\",");
		sb.append("\"isConnection\":\""+la_isconnection.getText()+"\",");
		sb.append("\"room_num\":\""+la_room_num.getText()+"\",");
		sb.append("\"isAdimin\":\""+admin+"\",");
		sb.append("\"user_ip\":\""+la_user_ip.getText()+"\"");
		sb.append("}");
		sb.append("}");
		System.out.println(sb.toString());
		clientMain.sendMsg(sb);
		table.updateUI();
	}
	
	public void delete(){//데이터 제거 하기 위해 서버에 요청(제거할 데이터 아이디 전송)
		String id=(String)table.getValueAt(table.getSelectedRow(), 1);
		sb.delete(0, sb.length());
		sb.append("{\"request\" : \"delete\",");
		sb.append("\"data\" : {");
		sb.append("\"id\":\""+id+"\"");
		sb.append("}}");
		
		clientMain.sendMsg(sb);
		table.updateUI();
	}
	
	
}
