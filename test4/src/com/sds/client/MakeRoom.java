package com.sds.client;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class MakeRoom extends JFrame implements ActionListener, ChangeListener, ItemListener{

	JLabel l_title, l_ispw, l_pw, l_max;
	JTextField title;
	JPasswordField pw;
	JCheckBox ispw, nopw;
	JCheckBox two, three, four;
	JButton bt_create, bt_cancel;
	JPanel p_title, p_ispw, p_pw, p_max, p_bt;
	boolean openflag = false;
	
	ClientMain clientMain;
	StringBuffer sb = new StringBuffer();
	
	WaitingRoom waitingRoom;
	
	public MakeRoom(ClientMain clientMain, WaitingRoom waitingRoom) {
		this.clientMain = clientMain;
		this.waitingRoom = waitingRoom;
		
		l_title = new JLabel("방 제목 : ");
		l_title.setPreferredSize(new Dimension(65, 25));
		l_ispw = new JLabel("암호여부 : ");
		l_ispw.setPreferredSize(new Dimension(130, 25));
		l_pw = new JLabel("비밀번호 : ");
		l_pw.setPreferredSize(new Dimension(65, 25));
		l_max = new JLabel("최대인원 : ");
		l_max.setPreferredSize(new Dimension(90, 25));
		
		title = new JTextField(15);
		
		pw = new JPasswordField(15);

		ispw = new JCheckBox("예", true);
		ispw.addItemListener(this);
		nopw = new JCheckBox("아니오");
		nopw.addItemListener(this);
		
		two = new JCheckBox("2명", true);
		three = new JCheckBox("3명");
		four = new JCheckBox("4명");
		two.addItemListener(this);
		three.addItemListener(this);
		four.addItemListener(this);
		
		bt_create = new JButton("만들기");
		bt_create.addActionListener(this);
		bt_cancel = new JButton("취소");
		bt_cancel.addActionListener(this);
		
		p_title = new JPanel();
		p_ispw = new JPanel();
		p_pw = new JPanel();
		p_max = new JPanel();
		p_bt = new JPanel();
		
		p_title.add(l_title);
		p_title.add(title);
		
		p_ispw.add(l_ispw);
		p_ispw.add(ispw);
		p_ispw.add(nopw);
		
		p_pw.add(l_pw);
		p_pw.add(pw);
		
		p_max.add(l_max);
		p_max.add(two);
		p_max.add(three);
		p_max.add(four);
		
		p_bt.add(bt_create);
		p_bt.add(bt_cancel);

		setLayout(new FlowLayout());
		add(p_title);
		add(p_ispw);
		add(p_pw);
		add(p_max);
		add(p_bt);
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				openflag = false;
				setVisible(false);
			}
		});
		setTitle("방 만들기");
		setUndecorated(true);
		setSize(300, 250);
		setLocationRelativeTo(null);
		setVisible(false);
	}

	public void checkCreateRoom(){
		String isPw = null;
		String maxPeople = null;
		String pwd = new String(pw.getPassword());
		
		if(ispw.isSelected()){
			isPw = "true";
		}else if(nopw.isSelected()){
			isPw = "false";
		}else{
			JOptionPane.showMessageDialog(this, "비밀방 여부를 설정해 주세요.");
			title.setText("");
			pw.setText("");
			openflag = false;
			waitingRoom.checkFlag();
			return ;
		}
		
		if( checkPassword(pwd, isPw)  == false){
			title.setText("");
			pw.setText("");
			openflag = false;
			waitingRoom.checkFlag();
			return ;
		}		
		
		
		if(two.isSelected()){
			maxPeople = "2";
		}else if (three.isSelected()){
			maxPeople = "3";
		}else if (four.isSelected()){
			maxPeople = "4";
		}
		
		if(maxPeople == "2" || maxPeople == "3" || maxPeople == "4"){
			createRoom(isPw, pwd, maxPeople);
		}else{
			JOptionPane.showMessageDialog(this, "최대인원수를 확인해 주세요.", "방 생성 실패", JOptionPane.CANCEL_OPTION);
			title.setText("");
			pw.setText("");
			openflag = false;
			waitingRoom.checkFlag();
			return;
		}
	}
	
	public void createRoom(String isPw, String pwd, String maxPeople){
		sb.delete(0, sb.length());
		sb.append("{\"request\" : \"create_room\",");
		sb.append("\"data\" : {");
		sb.append("\"title\" : \""+title.getText()+"\",");
		sb.append("\"ispw\" : \""+isPw+"\",");
		sb.append("\"password\" : \""+pwd+"\",");
		sb.append("\"max\" : \""+maxPeople+"\",");
		sb.append("\"host\" : \""+clientMain.user_id+"\"");
		sb.append("}}");
		
		clientMain.clientThread.sendMsg(sb);
	}
	
	public void stateChanged(ChangeEvent e) {
		if(e.getSource() == ispw){
			nopw.setSelected(false);
		}else if(e.getSource() == nopw){
			ispw.setSelected(false);
		}
	}
	
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == bt_cancel){
			openflag = false;
			waitingRoom.checkFlag();
			setVisible(false);
		}
		else if(e.getSource() == bt_create){
			checkCreateRoom();
			setVisible(false);
		}
			
	}

	public boolean checkPassword(String pw, String ispw){
		if(ispw.equals("true")){
			int result = pw.length();
			if( result < 4){
				JOptionPane.showMessageDialog(this, "비밀번호는 4글자 이상을 입력해 주세요!");
				return false;
			}
		}
		return true;
	}
	
	public void itemStateChanged(ItemEvent e) {
		
		if(e.getStateChange() == ItemEvent.SELECTED){
			if(e.getSource() == ispw){
				nopw.setSelected(false);
				pw.setEnabled(true);
			}else if(e.getSource() == nopw){
				ispw.setSelected(false);
				pw.setEnabled(false);
			}
			
			if(e.getSource() == two){
				three.setSelected(false);
				four.setSelected(false);
			}else if(e.getSource() == three){
				two.setSelected(false);
				four.setSelected(false);
			}else if(e.getSource() == four){
				three.setSelected(false);
				two.setSelected(false);
			}
			
		}
	}
}
