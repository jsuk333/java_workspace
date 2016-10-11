package com.sds.practice;

import java.awt.Choice;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class FCreateRoom extends JFrame{
	//------------------------------------------------------
	JLabel la_room_title,la_ispw,la_inpw,la_setmxpt;
	JTextField tf_room_title,tf_ispw,tf_inpw;
	Choice ch_setmxpt;
	JButton bt_create,bt_cancel;
	JRadioButton jr_haspw,jr_hasnpw;
	ButtonGroup bg;
	JPanel p_ispw;
	FMainList mainList;
	PreparedStatement pstmt;
	ResultSet rs;
	PRoom pRoom;
	String title;
	int mxpt;
	int pt;
	boolean ispw;
	String host;
	//------------------------------------------------------
	public FCreateRoom(FMainList mainList) {
		//------------------------------------------------------
		this.mainList=mainList;
		initComponent();
		setLayout(new GridLayout(5, 2));
		addComponent();
		//------------------------------------------------------
		setBounds(500, 800, 800, 500);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
		//------------------------------------------------------
		bt_create.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//방설정 쿼리에 넣고 방송 킴
				//sendQuery();
				addRoom();
				dispose();
			}
		});
		//------------------------------------------------------
		bt_cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//이 화면을 끈다.
				dispose();
			}
		});
		//------------------------------------------------------
	}
	public void addRoom(){
		if(jr_haspw.isSelected()){
			ispw=true;
		}else{
			ispw=false;
		}
		title=tf_room_title.getText();
		mxpt=Integer.parseInt(ch_setmxpt.getSelectedItem());
		pt=1;
		host="localhost";
		pRoom=new PRoom(mainList,title,mxpt,pt,ispw,host);
		mainList.rList.add(pRoom);
		
	}
	public void initComponent(){
		la_room_title=new JLabel("title");
		la_ispw=new JLabel("ispw");
		p_ispw=new JPanel();
		la_inpw=new JLabel("inpw");
		la_setmxpt=new JLabel("setmxpt");
		tf_room_title=new JTextField(3);
		jr_haspw=new JRadioButton("haspw");
		jr_hasnpw=new JRadioButton("hasnpw");
		bg=new ButtonGroup();
		bg.add(jr_haspw);
		bg.add(jr_hasnpw);
		jr_haspw.setSelected(true);
		tf_inpw=new JTextField(3);
		ch_setmxpt=new Choice();
		for(int i=1;i<5;i++){
			ch_setmxpt.add(Integer.toString(i));
		}
		bt_create=new JButton("생성");
		bt_cancel=new JButton("취소");
		
	}
	//------------------------------------------------------
	public void addComponent(){
		add(la_room_title);
		add(tf_room_title);
		p_ispw.add(jr_haspw);
		p_ispw.add(jr_hasnpw);
		add(la_ispw);
		add(p_ispw);
		add(la_inpw);
		add(tf_inpw);
		add(la_setmxpt);
		add(ch_setmxpt);
		add(bt_create);
		add(bt_cancel);
	}
	
	
}

/*public void sendQuery(){
		String sql="insert into roomlist(room_num,room_title,room_ispw,room_pw,room_mxpt,room_crpt) ";
		sql+="values(seq_roomno.nextval,?,?,?,?,?)";
		int ibool=0;
		//System.out.println(sql);
		if(jr_haspw.isSelected()){
			ibool=1;
		}else{
			ibool=0;
		}
		try {
			pstmt=mainList.con.prepareStatement(sql);
			pstmt.setString(1, tf_room_title.getText());
			pstmt.setInt(2,ibool);
			pstmt.setString(3, tf_inpw.getText());
			pstmt.setInt(4, Integer.parseInt(ch_setmxpt.getSelectedItem()));
			pstmt.setInt(5, 0);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/