package com.sds.client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class RoomInfo extends JPanel implements ActionListener{
	JLabel l_room_title, l_room_host, l_room_current;		//고정 라벨
	JLabel l_num, l_title, l_host, l_current;												//값 넣어주는 라벨
	
	JPanel p_num, p_title, p_host;
	
	JButton bt_join;
	
	WaitingRoom waitingRoom;
	
	StringBuffer sb = new StringBuffer();
	int no;
	public RoomInfo(WaitingRoom waitingRoom,int no) {
		this.waitingRoom = waitingRoom;
		this.no=no;
		l_room_title = new JLabel("방 제목 : ");
		l_room_host = new JLabel("방장 : ");
		l_room_current = new JLabel("참여인원 : ");

		TitledBorder tb = new TitledBorder(new LineBorder(Color.BLACK), waitingRoom.clientMain.roomList.get(no)[0]);
		
		l_title = new JLabel(waitingRoom.clientMain.roomList.get(no)[1]);///
		l_title.setPreferredSize(new Dimension(320, 20));
		
		l_host = new JLabel(waitingRoom.clientMain.roomList.get(no)[2]);//
		l_host.setPreferredSize(new Dimension(100, 20));
		
		l_current = new JLabel(waitingRoom.clientMain.roomList.get(no)[3]);//
		l_current.setPreferredSize(new Dimension(100, 20));
		
		p_num = new JPanel();
		p_num.setPreferredSize(new Dimension(450, 40));
		p_num.setBackground(Color.WHITE);
		
		p_title = new JPanel();
		p_title.setPreferredSize(new Dimension(450, 40));
		p_title.setBackground(Color.WHITE);
		
		p_host = new JPanel();
		p_host.setPreferredSize(new Dimension(450, 40));
		p_host.setBackground(Color.WHITE);
		
		bt_join = new JButton("참여");
		bt_join.addActionListener(this);
				
		p_title.add(l_room_title);
		p_title.add(l_title);
		
		p_host.add(l_room_host);
		p_host.add(l_host);
		
		p_host.add(l_room_current);
		p_host.add(l_current);
		p_host.add(bt_join);
		
		setBorder(tb);
		add(p_title);
		add(p_host);
		setBackground(Color.WHITE);
		setPreferredSize(new Dimension(450, 120));
		
		
	}
	//디비에서 방 목록을 불러왔을 때 참여 버튼을 누르면 아래에 있는 제이슨을 완성 시켜야 됨
	
	//참여 버튼을 누르면
	public void join(){
		sb.delete(0, sb.length());
		sb.append("{\"request\" : \"join\",");
		sb.append("\"data\" : {");
		sb.append("\"id\" : \" "+l_host.getText()+"\",");
		sb.append("\"room_num\" : \""+waitingRoom.clientMain.roomList.get(no)[0]+"\"");
		sb.append("}}");
		waitingRoom.clientMain.sendMsg(sb);
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == bt_join){
			join();	
		}
		
	}
}
