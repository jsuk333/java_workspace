package com.sds.client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.sds.admin.Join;
import com.sds.admin.ManageMain;
import com.sds.admin.Server;

public class ClientMain extends JFrame implements ActionListener{

	Socket socket = null;
	String host = "70.12.112.106";
	int port = 5555;
	
	JLabel title, id, pwd;
	JPanel p_north, p_center, p_south, p_id, p_pwd;
	JTextField txt_id;
	JPasswordField txt_pwd;
	JButton bt_login, bt_cencel,bt_regist;
	
	public BufferedWriter buffw;
	public BufferedReader buffr;
	
	StringBuffer sb = new StringBuffer();
	
	String user_id;
	String user_name;
	String user_nickname;
	String user_email;
	String host_ip;
	String host_room;
	RegistForm registForm;
	
	WaitingRoom waitingRoom;
	ViewWindow viewWindow;
	
	ManageMain searchId;
	
	ImageIcon icon;
	
	public ArrayList <String[]>memberList=new ArrayList<String[]>();
	public ArrayList<String[]> roomList=new ArrayList<String[]>();
	public ArrayList<String[]> conList=new ArrayList<String[]>();
	
	ChatClient chatClient;////////////////////////////////////////////////////////////////////////
	
	public ClientMain() {
		try {
			socket = new Socket(host, port);
			setupUI();
		} catch (IOException e) {
			System.out.println("[ Debug ] : Connection is failed ");
			JOptionPane.showMessageDialog(this, "네트워크 연결을 확인해 주세요.");
		}
	}
	
	public void setupUI(){
		icon = new ImageIcon(this.getClass().getClassLoader().getResource("back.png"));
		title = new JLabel("곧 모두의 뷰가 시작됩니다...");
		title.setPreferredSize(new Dimension(400, 100));
		title.setHorizontalAlignment(title.CENTER);
		title.setFont(new Font("Alien Encounters", Font.BOLD, 20));
		title.setForeground(Color.WHITE);
		
		id = new JLabel("ID");
		id.setPreferredSize(new Dimension(65, 50));
		id.setFont(new Font("돋움", Font.BOLD, 20));
		id.setForeground(Color.WHITE);
		txt_id = new JTextField(10);
		
		pwd = new JLabel("PasssWord");
		pwd.setFont(new Font("돋움", Font.BOLD, 20));
		pwd.setForeground(Color.WHITE);
		id.setPreferredSize(new Dimension(100, 50));
		
		txt_pwd = new JPasswordField(10);
		
		bt_login = new JButton("Log in");
		bt_login.addActionListener(this);
		
		bt_cencel = new JButton("Cancel");
		bt_cencel.addActionListener(this);
		
		bt_regist=new JButton("Regist");
		bt_regist.addActionListener(this);
		
		p_north = new JPanel();
		p_north.setPreferredSize(new Dimension(500, 150));
		p_north.setBackground(new Color(255, 0, 0, 0));
		
		p_center = new JPanel(){
			protected void paintComponent(Graphics g) {
				g.drawImage(icon.getImage(), 0, 0, null);
				setOpaque(false);
				super.paintComponent(g);
			}
		};
		p_center.setPreferredSize(new Dimension(400, 300));
		p_center.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		p_south = new JPanel();
		p_south.setBackground(new Color(255, 0, 0, 0));
		
		p_id = new JPanel();
		p_id.setPreferredSize(new Dimension(500, 50));
		p_id.setBackground(new Color(255, 0, 0, 0));
		
		p_pwd = new JPanel();
		p_pwd.setPreferredSize(new Dimension(400, 50));
		p_pwd.setBackground(new Color(255, 0, 0, 0));

		//p_north.add(title);
		
		setLayout(new FlowLayout());
		//add(p_north);

		p_id.add(id);
		p_id.add(txt_id);
		p_pwd.add(pwd);
		p_pwd.add(txt_pwd);
		
		p_center.add(title);
		p_center.add(p_id);
		p_center.add(p_pwd);
		p_center.add(bt_login);
		p_center.add(bt_cencel);
		p_center.add(bt_regist);
		
		add(p_center);

		
		//add(p_south);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent arg0) {
				exitWindow();
			}
		});
		setTitle("로그인");
		
		setUndecorated(true);
		setSize(new Dimension(400, 300));
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
		pack();
	}

	public void sendMsg(StringBuffer sb){
		try {
			buffw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			buffw.write(sb+"\n");
			buffw.flush();
			
			buffr = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String response = buffr.readLine();
			result(response);
			
		} catch (IOException e) {
			System.out.println("[ Debug ] : IOException");
		}
	}
	
	public void login(){
		String buff = new String(txt_pwd.getPassword());
		sb.delete(0, sb.length());
		sb.append("{\"request\" : \"login\",");
		sb.append("\"data\" : {");
		sb.append("\"id\" : \""+txt_id.getText()+"\",");
		sb.append("\"password\" : \""+buff+"\"");
		sb.append("}}");
		sendMsg(sb);
	}
	
	public void registUser(){
		this.setVisible(false);
		registForm = new RegistForm(this);
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
					JOptionPane.showMessageDialog(registForm, "회원가입이 완료되었습니다.\n로그인을 해 주세요.");
					registForm.setVisible(false);
					this.setVisible(true);
				}else {
					JOptionPane.showMessageDialog(registForm, "회원가입 실패.\n다시 가입을 시도해 주세요.");
				}
			}
			
			else if (jsonObject.get("response").equals("create_room")){
				JSONObject obj=(JSONObject)jsonObject.get("data");
				if(obj.get("success").equals("true")){
					JOptionPane.showMessageDialog(waitingRoom, "방 생성 완료");
					//방 입장 매서드 넣기
					host_ip = (String)obj.get("host_ip");
					int room_num=Integer.parseInt((String)obj.get("room_num"));
					System.out.println(host_ip);
					Server sub_server = new Server(host_ip);
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
					int room_num=Integer.parseInt((String)jsonObject.get("room_num"));
					/*Join join = new Join(ip, false);*/
					chatClient=new ChatClient(this, room_num);
				} else if (jsonObject.get("success").equals("false")) {
					JOptionPane.showMessageDialog(waitingRoom, "해당 방에 참여할 수 없습니다.");
					waitingRoom.p_center.updateUI();
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
					JOptionPane.showMessageDialog(registForm, "아이디가 존재합니다.");
					registForm.txt_id.setText("");
				} else if (jsonObject.get("success").equals("false")) {
					JOptionPane.showMessageDialog(registForm, "가입이가능한아이디입니다.");
					System.out.println(response);

					// JOptionPane.showMessageDialog(registForm,
					// "사용할수있는아이디입니다.");
				}
			}
			
			else if (jsonObject.get("response").equals("listrooms")) {//초기 방목록 
				if (jsonObject.get("success").equals("true")) {
					JSONArray jsonArray=(JSONArray)jsonObject.get("data");
					roomList=new ArrayList<String[]>();
					for(int i=0;i<jsonArray.size();i++){
						String[] data=new String[4];
						JSONObject obj=(JSONObject)jsonArray.get(i);
						data[0]=(String)obj.get("num");
						data[1]=(String)obj.get("title");
						data[2]=(String)obj.get("host_id");
						data[3]=(String)obj.get("cur_join");
						if( !data[3].equals("0")){
							roomList.add(data);
						}
					}
				} else if (jsonObject.get("success").equals("false")) {
					JOptionPane.showMessageDialog(waitingRoom, "방목록을 불러오는데 실패했습니다.");
					System.out.println(response);

				}
			}else if (jsonObject.get("response").equals("refresh")) {//방목록 업데이트
				if (jsonObject.get("success").equals("true")) {
					JSONArray jsonArray=(JSONArray)jsonObject.get("data");
					roomList=new ArrayList<String[]>();
					for(int i=0;i<jsonArray.size();i++){
						String[] data=new String[4];
						JSONObject obj=(JSONObject)jsonArray.get(i);
						data[0]=(String)obj.get("num");
						data[1]=(String)obj.get("title");
						data[2]=(String)obj.get("host_id");
						data[3]=(String)obj.get("cur_join");
						if( !data[3].equals("0")){
							roomList.add(data);
						}
						
					}
				} else if (jsonObject.get("success").equals("false")) {
					JOptionPane.showMessageDialog(waitingRoom, "방목록을 갱신하는데 실패했습니다.");
					System.out.println(response);

				}
			}
			
			else if (jsonObject.get("response").equals("showconnector")) {//접속자 목록
				if (jsonObject.get("success").equals("true")) {
					JSONArray jsonArray=(JSONArray)jsonObject.get("data");
					conList=new ArrayList<String[]>();
					for(int i=0;i<jsonArray.size();i++){
						String[] data=new String[2];
						JSONObject obj=(JSONObject)jsonArray.get(i);
						data[0]=(String)obj.get("nick_name");
						data[1]=(String)obj.get("gender");
						conList.add(data);
					}
					
				} else if (jsonObject.get("success").equals("false")) {
					JOptionPane.showMessageDialog(waitingRoom, "방목록을 갱신하는데 실패했습니다.");
					System.out.println(response);

				}
			}
//------------------------------------------------------------------
			else if (jsonObject.get("response").equals("chatting")) {//접속자 목록
				if (jsonObject.get("success").equals("true")) {
					String data=(String)jsonObject.get("data");
					chatClient.area.append(data);
				} else if (jsonObject.get("success").equals("false")) {
					chatClient.area.append("텍스트 전송에 실패하였습니다.");
					System.out.println(response);

				}
			}
			
			else if (jsonObject.get("response").equals("disconnectchat")) {//접속자 목록
				if (jsonObject.get("success").equals("true")) {
					
				} else if (jsonObject.get("success").equals("false")) {
					JOptionPane.showMessageDialog(chatClient, "채팅 접속 해제에 실패 했습니다.");
					System.out.println(response);

				}
			}
			//----------------------------------------------------------------------------
			else {
				JOptionPane.showMessageDialog(registForm, "db검색 실패");
			} // 여기까지
			
		} catch (ParseException e) {
			System.out.println("[ Debug ] : Error occured on JSON Parsing ");
		}
	}
	
	
	//지석 작성
	public void saveList(JSONObject jsonObject){//json으로 받은 data를 리스트로 옮김
		JSONArray jsonArray = (JSONArray) jsonObject.get("data");
		memberList.removeAll(memberList);
		String[] title = { "member_id", "id", "password", "name", "nick_name", "gender", "e_mail",
				"regdate", "isConnection", "room_num", "isAdimin", "user_ip" };
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
