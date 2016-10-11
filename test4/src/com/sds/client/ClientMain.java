package com.sds.client;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.sds.admin.Join;
import com.sds.admin.ManageMain;
import com.sds.admin.Server;

public class ClientMain extends JFrame implements ActionListener, KeyListener{

	Socket socket = null;
	String host = "192.168.123.117";
	int port = 5555;
	
	JPanel p_top, p_design1, p_design2, p_center, p_id, p_pwd;
	JPanel changePanel;
	JTextField txt_id;
	JPasswordField txt_pwd;
	JButton bt_login, bt_cencel, bt_regist;
	
	public BufferedWriter buffw;
	public BufferedReader buffr;
	
	StringBuffer sb = new StringBuffer();
	
	String user_id;
	String user_name;
	String user_nickname;
	String user_email;
	String host_ip;
	String host_room;
	
	WaitingRoom waitingRoom;
	ViewWindow viewWindow;
	
	ManageMain searchId;
	
	ImageIcon icon;
	RegistForm center2;
	public ArrayList <String[]>memberList=new ArrayList<String[]>();
	public ArrayList<String[]> roomList=new ArrayList<String[]>();
	public ArrayList<String[]> conList=new ArrayList<String[]>();
	public String user_ip;
	
	public ChatClient chatClient;
	public ClientThread clientThread;
	public ClientMain() {
		try {
			socket = new Socket(host, port);
			setupUI();
			clientThread=new ClientThread(this);
			clientThread.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			this.user_ip=InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setupUI(){

		icon = new ImageIcon(this.getClass().getClassLoader().getResource("back.png"));
		changePanel=new JPanel();
		changePanel.setPreferredSize(new Dimension(400, 550));
		p_top = new JPanel();
		p_design1 = new JPanel();
		p_design2 = new JPanel();
		p_top.setPreferredSize(new Dimension(400, 315));
		p_top.setBackground(new Color(255, 0, 0, 0));

		p_design1.setPreferredSize(new Dimension(400, 5));
		p_design1.setBackground(new Color(255, 0, 0, 0));
		p_design2.setPreferredSize(new Dimension(400, 15));
		p_design2.setBackground(new Color(255, 0, 0, 0));
		txt_id = new MyText();
		
		txt_id.setFont(new Font("고딕", Font.BOLD, 20));
		txt_id.setForeground(Color.DARK_GRAY);
		txt_id.setPreferredSize(new Dimension(250, 30));
		txt_id.addKeyListener(this);

		txt_id.addFocusListener(new FocusListener() {

			public void focusLost(FocusEvent e) {

				if (txt_id.getText().equals("")) {
					txt_id.setText("ID");
					txt_id.setForeground(Color.gray);
				}
			}

			public void focusGained(FocusEvent e) {
				{
					if (txt_id.getText().equals("ID")) {
						txt_id.setText("");
						txt_id.setForeground(Color.DARK_GRAY);
					}
				}
			}
		});

		txt_pwd = new MyPassword();
		txt_pwd.setEchoChar((char) 0);
		txt_pwd.setText("PASSWORD");
		txt_pwd.setForeground(Color.gray);

		txt_pwd.setFont(new Font("고딕", Font.BOLD, 20));
		txt_pwd.setPreferredSize(new Dimension(250, 30));
		txt_pwd.addFocusListener(new FocusListener() {

			public void focusLost(FocusEvent e) {

				String str=new String(txt_pwd.getPassword());
				
				if(str.equals(""))
				{
					txt_pwd.setEchoChar((char) 0);
					txt_pwd.setText("PASSWORD");
					txt_pwd.setForeground(Color.GRAY);
				}
				
			}

			public void focusGained(FocusEvent e) {
				{
					String str=new String(txt_pwd.getPassword());
					
					if(str.equals("PASSWORD"))
					{
						txt_pwd.setEchoChar('*');
						txt_pwd.setText("");
						txt_pwd.setForeground(Color.DARK_GRAY);
					}
				}
			}
		});
		bt_login = new JButton();
		bt_login.addActionListener(this);

		bt_cencel = new JButton();
		bt_cencel.addActionListener(this);

		bt_regist = new JButton();
		bt_regist.addActionListener(this);

		p_center = new JPanel() {
			protected void paintComponent(Graphics g) {
				g.drawImage(icon.getImage(), 0, 0, getParent());
				setOpaque(false);

			}
		};
		center2=new RegistForm(this);
		center2.setPreferredSize(new Dimension(400, 550));
		p_center.setPreferredSize(new Dimension(400, 550));

		// setLayout(new FlowLayout());

		bt_login.setBorderPainted(false);
		bt_cencel.setBorderPainted(false);
		bt_regist.setBorderPainted(false);

		bt_login.setPreferredSize(new Dimension(250, 30));
		bt_cencel.setPreferredSize(new Dimension(250, 30));
		bt_regist.setPreferredSize(new Dimension(250, 30));
		bt_login.setBackground(new Color(0, 0, 0, 0));
		bt_login.setRolloverIcon(new ImageIcon(this.getClass().getClassLoader().getResource("sign_in2.png")));
		bt_login.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource("sign_in1.png")));
		bt_login.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				p_center.updateUI();
				p_center.repaint();
			}

			@Override
			public void mouseExited(MouseEvent e) {
				p_center.updateUI();
				p_center.repaint();
			}
		});
		bt_regist.setBackground(new Color(0, 0, 0, 0));
		bt_regist.setRolloverIcon(new ImageIcon(this.getClass().getClassLoader().getResource("sign_up2.png")));
		bt_regist.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource("sign_up1.png")));
		bt_regist.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				p_center.updateUI();
				p_center.repaint();
			}

			@Override
			public void mouseExited(MouseEvent e) {
				p_center.updateUI();
				p_center.repaint();
			}
		});
		bt_cencel.setBackground(new Color(0, 0, 0, 0));
		bt_cencel.setRolloverIcon(new ImageIcon(this.getClass().getClassLoader().getResource("cancel2.png")));
		bt_cencel.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource("cancel1.png")));
		bt_cencel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				p_center.updateUI();
				p_center.repaint();
			}

			@Override
			public void mouseExited(MouseEvent e) {
				p_center.updateUI();
				p_center.repaint();
			}
		});
		p_center.setVisible(true);
		center2.setVisible(false);
		p_center.add(p_top);
		p_center.add(txt_id);
		p_center.add(p_design1);
		p_center.add(txt_pwd);
		p_center.add(p_design2);
		p_center.add(bt_login);
		p_center.add(bt_regist);
		p_center.add(bt_cencel);
		changePanel.setLayout(new CardLayout());
		changePanel.add(p_center);

		changePanel.add(center2);
		add(changePanel);
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent arg0) {
				exitWindow();
			}
			@Override
			public void windowOpened(WindowEvent e) {
				p_center.requestFocus(true);
				
			}
		});
		setTitle("로그인");

		
		setSize(new Dimension(400, 550));
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
		// pack();
	}

	
	
	public void login(){
		String buff = new String(txt_pwd.getPassword());
		sb.delete(0, sb.length());
		sb.append("{\"request\" : \"login\",");
		sb.append("\"data\" : {");
		sb.append("\"id\" : \""+txt_id.getText()+"\",");
		sb.append("\"password\" : \""+buff+"\"");
		sb.append("}}");
		clientThread.sendMsg(sb);
	}
	
	public void registUser(){
		p_center.setVisible(false);
		center2.setVisible(true);
	}
	
	public void exitWindow(){
				if( socket != null){
					try {
						socket.close();
						
					} catch (IOException e) {
					}
				}
				System.exit(0);
	}
	
	public void keyPressed(KeyEvent e) {
		p_center.repaint();
		p_center.updateUI();
	}

	public void keyReleased(KeyEvent e) {
		p_center.repaint();
		p_center.updateUI();
	}

	public void keyTyped(KeyEvent e) {

	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == bt_login){
			login();
		}else if(e.getSource() == bt_cencel){
			exitWindow();
		}else if(e.getSource() == bt_regist){
			registUser();
		}
	}
	
	public void result(String response){
		
		try {
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject=(JSONObject)jsonParser.parse(response);
			//System.out.println(response);

			if(jsonObject.get("response").equals("login")){
				JSONObject obj=(JSONObject)jsonObject.get("data");
				if(obj.get("success").equals("true")){
					user_id = obj.get("id").toString();
					user_name = obj.get("name").toString();
					user_nickname = obj.get("nick_name").toString();
					user_email = obj.get("e_mail").toString();
					JOptionPane.showMessageDialog(this, user_name+"님 반갑습니다!!!");
					if(user_id.equals("admin")){
						searchId=new ManageMain(this);
					}else{
						waitingRoom = new WaitingRoom(this);
					}
					this.setVisible(false);
				}
				else if(obj.get("success").equals("false")){
					JOptionPane.showMessageDialog(this, "아이디와 비밀번호를 확인해 주세요!!!");
				}
			}
			
			else if (jsonObject.get("response").equals("logedin")){
				JSONObject obj=(JSONObject)jsonObject.get("data");
				if(obj.get("success").equals("false")){
					JOptionPane.showMessageDialog(this, "해당 아이디는 이미 로그인 상태입니다.");
				}
			}
			
			else if(jsonObject.get("response").equals("regist")){
				if(jsonObject.get("success").equals("true")){
					JOptionPane.showMessageDialog(center2, "회원가입이 완료되었습니다.\n로그인을 해 주세요.");
					center2.setVisible(false);
					this.setVisible(true);
				}else {
					JOptionPane.showMessageDialog(center2, "회원가입 실패.\n다시 가입을 시도해 주세요.");
				}
			}
			
			else if (jsonObject.get("response").equals("create_room")){
				JSONObject obj=(JSONObject)jsonObject.get("data");
				if(obj.get("success").equals("true")){
					JOptionPane.showMessageDialog(waitingRoom, "방 생성 완료");
					//방 입장 매서드 넣기
					host_ip = (String)obj.get("host_ip");
					Server sub_server = new Server(host_ip,this);
					int room_num=Integer.parseInt((String)obj.get("room_num"));
					chatClient=new ChatClient(this, room_num);
				}else {
					JOptionPane.showMessageDialog(waitingRoom, "방 생성실패.\n다시 방 생성을시도해 주세요.");
				}
			}
			else if (jsonObject.get("response").equals("disconnect")){
				JSONObject obj=(JSONObject)jsonObject.get("data");
				if(obj.get("success").equals("true")){
					exitWindow();
				}
			}
			
			else if (jsonObject.get("response").equals("join")) {
				if (jsonObject.get("success").equals("true")) {
					String ip = (String)jsonObject.get("host_ip");
					Join join = new Join(ip, false,this);
					int room_num=Integer.parseInt((String)jsonObject.get("room_num"));
					chatClient=new ChatClient(this, room_num);
				} else if (jsonObject.get("success").equals("false")) {
					if(jsonObject.get("correct").equals("false")){
						JOptionPane.showMessageDialog(waitingRoom.roomInfo.inputPwd, "비밀번호가 틀립니다.");
					}else{
						JOptionPane.showMessageDialog(waitingRoom, "해당 방에 참여할 수 없습니다.");
						waitingRoom.p_center.updateUI();
					}
				}
			}
			
			//여기부터 지석 작성
			else if (jsonObject.get("response").equals("list")) {//서버에서 테이블에 데이터를 뿌리라는 명령을 내림
				if (jsonObject.get("success").equals("true")) {
					saveList(jsonObject);
				}else {
					JOptionPane.showMessageDialog(searchId, "목록 불러오기 실패");
				}
			} 
			
			else if (jsonObject.get("response").equals("search")) {//서버에서 테이블에 데이터를 뿌리라는 명령을 내림
				if (jsonObject.get("success").equals("true")) {
					saveList(jsonObject);
				}else {
					JOptionPane.showMessageDialog(searchId, "목록 불러오기 실패");
				}
			} 
			
			else if (jsonObject.get("response").equals("edit")) {//서버에서 수정된 데이터를 가져와 테이블에 뿌리라는 명령
				if (jsonObject.get("success").equals("true")) {
					saveList(jsonObject);
				}else {
					JOptionPane.showMessageDialog(searchId, "목록 수정 실패");
				}
			}
			
			else if (jsonObject.get("response").equals("delete")) {// 서버에서 데이터가
																	// 지워지고 갱신된
																	// 데이터를 테이블에
																	// 뿌리라는 명령
				if (jsonObject.get("success").equals("true")) {
					saveList(jsonObject);
				} else {

					JOptionPane.showMessageDialog(searchId, "목록 삭제 실패");
				}
			}else if (jsonObject.get("response").equals("checkId")) {
				if (jsonObject.get("success").equals("true")) {
					JOptionPane.showMessageDialog(center2, "아이디가 존재합니다.");
					center2.txt_id.setText("");
				} else if (jsonObject.get("success").equals("false")) {
					JOptionPane.showMessageDialog(center2, "가입이가능한아이디입니다.");

					// JOptionPane.showMessageDialog(registForm,
					// "사용할수있는아이디입니다.");
				}
			}
			
			else if (jsonObject.get("response").equals("listrooms")) {//초기 방목록 
				if (jsonObject.get("success").equals("true")) {
					JSONArray jsonArray=(JSONArray)jsonObject.get("data");
					roomList=new ArrayList<String[]>();
					for(int i=0;i<jsonArray.size();i++){
						String[] data=new String[6];
						JSONObject obj=(JSONObject)jsonArray.get(i);
						data[0]=(String)obj.get("num");
						data[1]=(String)obj.get("title");
						data[2]=(String)obj.get("host_id");
						data[3]=(String)obj.get("cur_join");
						data[4]=(String)obj.get("ispw");
						data[5]=(String)obj.get("max_join");
						if( !data[3].equals("0")){
							roomList.add(data);
						}
					}
				} else if (jsonObject.get("success").equals("false")) {
					JOptionPane.showMessageDialog(waitingRoom, "방목록을 불러오는데 실패했습니다.");

				}
			}else if (jsonObject.get("response").equals("refresh")) {//방목록 업데이트
				if (jsonObject.get("success").equals("true")) {
					JSONArray jsonArray=(JSONArray)jsonObject.get("data");
					roomList=new ArrayList<String[]>();
					for(int i=0;i<jsonArray.size();i++){
						String[] data=new String[6];
						JSONObject obj=(JSONObject)jsonArray.get(i);
						data[0]=(String)obj.get("num");
						data[1]=(String)obj.get("title");
						data[2]=(String)obj.get("host_id");
						data[3]=(String)obj.get("cur_join");
						data[4]=(String)obj.get("ispw");
						data[5]=(String)obj.get("max_join");
						if( !data[3].equals("0")){
							roomList.add(data);
						}
						
					}
				} else if (jsonObject.get("success").equals("false")) {
					JOptionPane.showMessageDialog(waitingRoom, "방목록을 갱신하는데 실패했습니다.");

				}
			}
			
			else if (jsonObject.get("response").equals("showconnector")) {//접속자 목록
				if (jsonObject.get("success").equals("true")) {
					JSONArray jsonArray=(JSONArray)jsonObject.get("data");
					conList=new ArrayList<String[]>();
					for(int i=0;i<jsonArray.size();i++){
						String[] data=new String[1];
						JSONObject obj=(JSONObject)jsonArray.get(i);
						data[0]=(String)obj.get("nick_name");
						conList.add(data);
					}
					
				} else if (jsonObject.get("success").equals("false")) {
					JOptionPane.showMessageDialog(waitingRoom, "방목록을 갱신하는데 실패했습니다.");

				}
			}
			
			else if (jsonObject.get("response").equals("chatting")) {//접속자 목록
				if (jsonObject.get("success").equals("true")) {
					String data=(String)jsonObject.get("data");
					chatClient.area.append(data + "\n");	
				}
			}

			else if (jsonObject.get("response").equals("disconnectchat")) {//채팅접속 해제
				if (jsonObject.get("success").equals("true")) {
					
				} else if (jsonObject.get("success").equals("false")) {
					JOptionPane.showMessageDialog(chatClient, "채팅 접속 해제에 실패 했습니다.");
					System.out.println(response);

				}
			}
			
			else {
				JOptionPane.showMessageDialog(center2, "db검색 실패");
			} // 여기까지
			
		} catch (ParseException e) {
			System.out.println("[ Debug ] : Error occured on JSON Parsing ");
		}
	}
	
	
	//지석 작성
	public void saveList(JSONObject jsonObject){//json으로 받은 data를 리스트로 옮김
		JSONArray jsonArray = (JSONArray) jsonObject.get("data");
		String[] title = { "member_id", "id", "password", "name", "nick_name", "gender", "e_mail",
				"regdate", "isConnection", "room_num", "isAdimin", "user_ip" };
		memberList.removeAll(memberList);
		for (int i = 0; i < jsonArray.size(); i++) {
			String[] member=new String[title.length];
			JSONObject obj = (JSONObject) jsonArray.get(i);
			for (int j = 0; j < title.length; j++) {
				
					member[j]= (String) obj.get(title[j]);
				
			}
			memberList.add(member);
		}
	}

}
