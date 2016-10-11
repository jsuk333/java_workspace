package com.sds.client;

import java.awt.BorderLayout;
import java.awt.Dimension;
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
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.sds.dto.MemberDTO;

public class ClientMain extends JFrame implements ActionListener {

	Socket socket = null;
	String host = "localhost";
	int port = 5555;

	JLabel title, id, pwd;
	JPanel p_north, p_center, p_south, p_id, p_pwd;
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
	RegistForm registForm;
	//-----------------------
	ManageMain searchId;
	ArrayList list;

	public ClientMain() {
		//---------------------------
		list=new ArrayList();
		try {
			socket = new Socket(host, port);
			setupUI();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("[ Debug ] : Connection is failed ");
			JOptionPane.showMessageDialog(this, "��Ʈ��ũ ������ Ȯ���� �ּ���.");
		}
	}

	public void setupUI() {

		title = new JLabel("�α���");

		id = new JLabel("ID");
		id.setPreferredSize(new Dimension(65, 50));
		txt_id = new JTextField(10);

		pwd = new JLabel("PasssWord");
		id.setPreferredSize(new Dimension(65, 50));

		txt_pwd = new JPasswordField(10);

		bt_login = new JButton("Log in");
		bt_login.addActionListener(this);

		bt_cencel = new JButton("Cancel");
		bt_cencel.addActionListener(this);

		bt_regist = new JButton("Regist");
		bt_regist.addActionListener(this);

		p_north = new JPanel();
		p_north.setPreferredSize(new Dimension(500, 50));
		p_center = new JPanel();
		p_south = new JPanel();
		p_id = new JPanel();
		p_id.setPreferredSize(new Dimension(500, 50));
		p_pwd = new JPanel();
		p_pwd.setPreferredSize(new Dimension(400, 50));

		p_north.add(title);
		add(p_north, BorderLayout.NORTH);

		p_id.add(id);
		p_id.add(txt_id);
		p_pwd.add(pwd);
		p_pwd.add(txt_pwd);
		p_center.add(p_id);
		p_center.add(p_pwd);

		add(p_center, BorderLayout.CENTER);

		p_south.add(bt_login);
		p_south.add(bt_cencel);
		p_south.add(bt_regist);
		add(p_south, BorderLayout.SOUTH);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent arg0) {
				if (socket != null) {
					try {
						socket.close();
					} catch (IOException e) {

					}
				}
				System.exit(0);
			}
		});
		setTitle("�α���");
		setSize(new Dimension(400, 300));
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public void sendMsg(StringBuffer sb) {
		try {
			buffw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			buffw.write(sb + "\n");
			buffw.flush();

			buffr = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String response = buffr.readLine();
			result(response);

		} catch (IOException e) {
			System.out.println("[ Debug ] : IOException");
		}
	}

	public void login() {
		String buff = new String(txt_pwd.getPassword());
		sb.delete(0, sb.length());
		sb.append("{\"request\" : \"login\",");
		sb.append("\"data\" : {");
		sb.append("\"id\" : \"" + txt_id.getText() + "\",");
		sb.append("\"password\" : \"" + buff + "\"");
		sb.append("}}");
		sendMsg(sb);
	}

	public void registUser() {
		this.setVisible(false);
		registForm = new RegistForm(this);
	}

	public void exitWindow() {
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent arg0) {
				if (socket != null) {
					try {
						socket.close();
					} catch (IOException e) {
					}
				}
				System.exit(0);
			}
		});
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == bt_login) {
			login();
		} else if (e.getSource() == bt_cencel) {
			exitWindow();
		} else if (e.getSource() == bt_regist) {
			registUser();
		}
	}

	public void result(String response) {

		try {
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(response);
			if (jsonObject.get("response").equals("login")) {
				JSONObject obj = (JSONObject) jsonObject.get("data");
				if (obj.get("success").equals("true")) {
					user_id = obj.get("id").toString();
					user_name = obj.get("name").toString();
					user_nickname = obj.get("nick_name").toString();
					user_email = obj.get("e_mail").toString();
					JOptionPane.showMessageDialog(this, user_name + "�� �ݰ����ϴ�!!!");
					searchId=new ManageMain(this);
				} else if (jsonObject.get("success").equals("false")) {
					JOptionPane.showMessageDialog(this, "���̵�� ��й�ȣ�� Ȯ���� �ּ���!!!");
				}
			}

			else if (jsonObject.get("response").equals("regist")) {
				if (jsonObject.get("success").equals("true")) {
					JOptionPane.showMessageDialog(registForm, "ȸ�������� �Ϸ�Ǿ����ϴ�.\n�α����� �� �ּ���.");
					registForm.setVisible(false);
					this.setVisible(true);
				} else {
					JOptionPane.showMessageDialog(registForm, "ȸ������ ����.\n�ٽ� ������ �õ��� �ּ���.");
				}
			} else if (jsonObject.get("response").equals("list")) {//�������� ���̺� �����͸� �Ѹ���� ����� ����
				if (jsonObject.get("success").equals("true")) {
					saveList(jsonObject);
				}else {
					JOptionPane.showMessageDialog(searchId, "��� �ҷ����� ����");
				}
			} else if (jsonObject.get("response").equals("search")) {//�������� ���̺� �����͸� �Ѹ���� ����� ����
				if (jsonObject.get("success").equals("true")) {
					saveList(jsonObject);
				}else {
					JOptionPane.showMessageDialog(searchId, "��� �ҷ����� ����");
				}
			} else if (jsonObject.get("response").equals("edit")) {//�������� ������ �����͸� ������ ���̺� �Ѹ���� ���
				if (jsonObject.get("success").equals("true")) {
					saveList(jsonObject);
				}else {
					JOptionPane.showMessageDialog(searchId, "��� ���� ����");
				}
			}else if (jsonObject.get("response").equals("delete")) {//�������� �����Ͱ� �������� ���ŵ� �����͸� ���̺� �Ѹ���� ���
				if (jsonObject.get("success").equals("true")) {
					saveList(jsonObject);
				}else{
					
						JOptionPane.showMessageDialog(searchId, "��� ���� ����");
				}
			}else {
				JOptionPane.showMessageDialog(registForm, "db�˻� ����");
			}

		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	public void saveList(JSONObject jsonObject){//json���� ���� data�� ����Ʈ�� �ű�
		JSONArray jsonArray = (JSONArray) jsonObject.get("data");
		String[] title = { "member_id", "id", "password", "name", "nick_name", "gender", "e_mail",
				"regdate", "isConnection", "room_num", "isAdimin", "user_ip" };
		list = new ArrayList();
		for (int i = 0; i < jsonArray.size(); i++) {
			ArrayList member = new ArrayList();
			JSONObject obj = (JSONObject) jsonArray.get(i);
			for (int j = 0; j < title.length; j++) {
				if (obj.get(title[j]) != null) {
					member.add(j, (String) obj.get(title[j]));
				} else {
					member.add(j, (String) obj.get("0"));
				}
			}
			list.add(member);
		}
	}

	public static void main(String[] args) {
		new ClientMain();
	}

}
