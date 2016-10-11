package com.sds.client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class RoomInfo extends JPanel implements ActionListener{
	JLabel l_room_title, l_room_host, l_room_current;		//고정 라벨
	JLabel l_num, l_title, l_host, l_current;												//값 넣어주는 라벨
	JLabel l_ispw;
	
	JPanel p_num, p_title, p_host, p_ispw;
	
	JButton bt_join;
	
	WaitingRoom waitingRoom;
	
	StringBuffer sb = new StringBuffer();
	TitledBorder tb;
	
	
	JFrame inputPwd;
	JLabel l_pwd;
	JTextField t_pwd;
	JButton bt_ok;
	JButton bt_cancel;
	
	String room_pw;
	int no;
	
	public RoomInfo(WaitingRoom waitingRoom, int no) {
		this.waitingRoom = waitingRoom;
		this.no = no;
		l_room_title = new JLabel("방 제목 : ");
		l_room_host = new JLabel("방장 : ");
		l_room_current = new JLabel("참여인원 : ");

		tb = new TitledBorder(new LineBorder(Color.BLACK), "No. "+waitingRoom.clientMain.roomList.get(no)[0]);
		
		l_title = new JLabel(waitingRoom.clientMain.roomList.get(no)[1]);///
		l_title.setPreferredSize(new Dimension(320, 20));
		
		l_host = new JLabel(waitingRoom.clientMain.roomList.get(no)[2]);//
		l_host.setPreferredSize(new Dimension(100, 20));
		
		l_current = new JLabel(waitingRoom.clientMain.roomList.get(no)[3]);//
		l_current.setPreferredSize(new Dimension(100, 20));
		
		if(waitingRoom.clientMain.roomList.get(no)[4].equals("1")){
			l_ispw = new JLabel("비번방");	
		}else{
			l_ispw = new JLabel("");
		}
		
		l_ispw.setPreferredSize(new Dimension(375, 20));
		
		p_num = new JPanel();
		p_num.setPreferredSize(new Dimension(450, 25));
		p_num.setBackground(Color.WHITE);
		
		p_title = new JPanel();
		p_title.setPreferredSize(new Dimension(450, 25));
		p_title.setBackground(Color.WHITE);
		
		p_host = new JPanel();
		p_host.setPreferredSize(new Dimension(450, 35));
		p_host.setBackground(Color.WHITE);
		
		p_ispw = new JPanel();
		p_ispw.setPreferredSize(new Dimension(450, 25));
		p_ispw.setBackground(Color.WHITE);
		
		bt_join = new JButton("참여");
		bt_join.addActionListener(this);
				
		p_title.add(l_room_title);
		p_title.add(l_title);
		
		p_ispw.add(l_ispw);
		
		p_host.add(l_room_host);
		p_host.add(l_host);
		
		p_host.add(l_room_current);
		p_host.add(l_current);
		p_host.add(bt_join);
		
		setBorder(tb);
		
		add(p_title);
		add(p_ispw);
		add(p_host);
		
		if( Integer.parseInt(l_current.getText()) == Integer.parseInt(waitingRoom.clientMain.roomList.get(no)[5])){
			bt_join.setEnabled(false);
		}
		
		setBackground(Color.WHITE);
		setPreferredSize(new Dimension(450, 160));
	}
	//디비에서 방 목록을 불러왔을 때 참여 버튼을 누르면 아래에 있는 제이슨을 완성 시켜야 됨
	
	//참여 버튼을 누르면
	public void join(){
		
		String ispw = "";
		
		if(waitingRoom.clientMain.roomList.get(no)[4].equals("1")){
			ispw = "true";
		}else{
			ispw = "false";
		}
		
		sb.delete(0, sb.length());
		sb.append("{\"request\" : \"join\",");
		sb.append("\"data\" : {");
		sb.append("\"id\" : \""+l_host.getText()+"\",");
		sb.append("\"room_num\" : \""+waitingRoom.clientMain.roomList.get(no)[0]+"\",");
		sb.append("\"isPw\" : \""+ispw+"\",");
		sb.append("\"pw\" : \""+room_pw+"\"");
		sb.append("}}");
		waitingRoom.clientMain.clientThread.sendMsg(sb);
	}
	
	public void inputPassword(){
	
			inputPwd = new JFrame("비밀번호 입력");
			l_pwd = new JLabel("비밀번호 : ");
			t_pwd = new JTextField(15);
			bt_ok = new JButton("확인");
			bt_ok.addActionListener(this);
			bt_cancel = new JButton("취소");
			bt_cancel.addActionListener(this);
			
			inputPwd.setLayout(new FlowLayout());
			inputPwd.add(l_pwd);
			inputPwd.add(t_pwd);
			inputPwd.add(bt_ok);
			inputPwd.add(bt_cancel);
			inputPwd.setSize(200, 200);
			inputPwd.setLocationRelativeTo(null);
			inputPwd.setResizable(false);
			inputPwd.setVisible(true);
		
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == bt_join){
			if(waitingRoom.clientMain.roomList.get(no)[4].equals("1")){
				inputPassword();
			}else{
				join();
			}
		}
		else if(e.getSource() == bt_ok){
			room_pw = t_pwd.getText();
			join();
			inputPwd.setVisible(false);
			t_pwd.setText("");
		}
		else if( e.getSource() == bt_cancel){
			inputPwd.setVisible(false);
			t_pwd.setText("");
		}
		
	}
}
