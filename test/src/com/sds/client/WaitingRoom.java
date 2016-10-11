package com.sds.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class WaitingRoom extends JFrame implements ActionListener{

	ClientMain clientMain;
	JPanel p_north, p_list, p_center;
	JScrollPane scroll;
	JButton mem_list;
	JButton refresh;
	JButton make_room;
	JButton close_window;
	
	RoomInfo[] roomInfos = new RoomInfo[5];
	MemberList memberList;
	MakeRoom makeRoom;
	
	RoomInfo roomInfo;
	
	
	StringBuffer sb = new StringBuffer();
	
	public WaitingRoom(ClientMain clientMain) {
		this.clientMain = clientMain;
	
		p_north =  new JPanel();
		p_north.setLayout(new FlowLayout());
		
		mem_list = new JButton("접속자 리스트 보기");
		mem_list.addActionListener(this);
		
		refresh = new JButton("새로 고침");
		refresh.addActionListener(this);
		
		make_room = new JButton("방 만들기");
		make_room.setPreferredSize(new Dimension(110, 28));
		make_room.addActionListener(this);
		
		close_window = new JButton("종료");
		close_window.addActionListener(this);
		
		p_center = new JPanel();
		p_list = new JPanel();
	
		
		
		p_north.add(refresh);
		p_north.add(mem_list);
		p_north.add(make_room);
		p_north.add(close_window);
		
		
		getRoomList();
		
		p_center.add(p_list);
		
		scroll = new JScrollPane(p_center);
		setLayout(new BorderLayout());
		
		add(p_north, BorderLayout.NORTH);
		add(scroll, BorderLayout.CENTER);
		
		
		setResizable(false);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation( ((dim.width/2)-(getWidth()/2)-400), ((dim.height/2)-(getHeight()/2)-350));
		
		setUndecorated(true);
		setSize(500, 500);
		setTitle("대기실");
		setVisible(true);
		
		
		memberList = new MemberList(this.clientMain, this);
		makeRoom = new MakeRoom(this.clientMain, this);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				disConnection();
			}

		});
	}
	
	
	public void disConnection(){
		sb.delete(0, sb.length());
		sb.append("{\"request\" : \"disconnect\",");
		sb.append("\"data\" : {");
		sb.append("\"id\" : \""+clientMain.user_id+"\"");
		sb.append("}}");
		clientMain.sendMsg(sb);
	}
	
	public void getRoomList(){
		//디비에서 방 목록 가져오기
		sb.delete(0, sb.length());
		sb.append("{\"request\" : \"listrooms\",");
		sb.append("\"data\" : {");
		sb.append("\"id\" : \""+clientMain.user_id+"\"");
		sb.append("}}");
		clientMain.sendMsg(sb);;
		p_list.setPreferredSize(new Dimension(450, 400+130*clientMain.memberList.size()));
		for (int i=0; i<clientMain.roomList.size(); i++){
			roomInfo = new RoomInfo(this,i);
			p_list.add(roomInfo);
		}
		p_list.updateUI();
		
	}	

	public void refresh(){
		sb.delete(0, sb.length());
		sb.append("{\"request\" : \"refresh\",");
		sb.append("\"data\" : {");
		sb.append("\"id\" : \""+clientMain.user_id+"\"");
		sb.append("}}");
		clientMain.sendMsg(sb);
		p_list.removeAll();
		p_list.setPreferredSize(new Dimension(450, clientMain.roomList.size()*130));
		for (int i=0; i<clientMain.roomList.size(); i++){
			roomInfo = new RoomInfo(this,i);
			p_list.add(roomInfo);
		}
		p_list.updateUI();
	}
	
	public void showConnector(){
		sb.delete(0, sb.length());
		sb.append("{\"request\" : \"showconnector\",");
		sb.append("\"data\" : {");
		sb.append("\"id\" : \""+clientMain.user_id+"\"");
		sb.append("}}");
		clientMain.sendMsg(sb);
		memberList.table.updateUI();
		
	}
	
	public void checkFlag(){
		if(makeRoom.openflag){
			make_room.setText("만들기 취소");
		}else{
			make_room.setText("방 만들기");
		}
		p_north.updateUI();
	}
	
	public void actionPerformed(ActionEvent e) {
		/*Object obj = (Object)e.getSource();
		System.out.println(obj.getClass().);*/
	
		if(e.getSource() == mem_list){
			if(mem_list.getText().equals("접속자 리스트 보기")){
				showConnector();
				memberList.setVisible(true);
				mem_list.setText("접속자 리스트 닫기");
			}else{
					memberList.setVisible(false);
					mem_list.setText("접속자 리스트 보기");
			}
		}
		else if(e.getSource() == make_room){
			if(make_room.getText().equals("방 만들기")){
				makeRoom.setVisible(true);
				make_room.setText("만들기 취소");
			}else{
				makeRoom.setVisible(false);
				make_room.setText("방 만들기");
			}
		}else if(e.getSource()==refresh){
			refresh();
		}
		
		else if (e.getSource() == close_window){
			int result = JOptionPane.showConfirmDialog(this, "종료하시겠습니까?", "프로그램 종료", JOptionPane.YES_NO_OPTION);
			if(result == 0){
				disConnection();
			}
		}
	}
	
}
