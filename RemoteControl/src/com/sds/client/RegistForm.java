package com.sds.client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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

public class RegistForm extends JFrame implements ActionListener{
	JButton bt_signup, bt_cancel;
	JLabel la_title, la_id, la_pwd, la_pwdch, la_name, la_nick, la_email;
	JCheckBox male, female;
	JPasswordField txt_pwd, txt_pwdch;
	JTextField txt_id, txt_name, txt_nick, txt_email;
	JPanel p_north, p_center, p_south, p_id, p_pwd, p_pwdch, p_name, p_nick, p_email, p_gen;

	String url, user, o_pwd;
	String id, pwd, name, nick, email;
	boolean man, woman;

	ClientMain clientMain;
	StringBuffer sb = new StringBuffer();
	
	public RegistForm(ClientMain clientMain) {
		this.clientMain = clientMain;
		
		//버튼 선언부
		bt_signup = new JButton("SIGNUP");
		bt_signup.addActionListener(this);
		
		bt_cancel = new JButton("CANCEL");
		bt_cancel.addActionListener(this);
		
		//라벨 선언부
		la_title = new JLabel("회원가입");
		
		la_id = new JLabel("아이디");
		la_pwd = new JLabel("비밀번호");
		
		la_pwdch = new JLabel("비밀번호확인");
		
		la_name = new JLabel("이름");
		
		la_nick = new JLabel("별명");
		
		male = new JCheckBox("남");
		female = new JCheckBox("여");
		
		la_email = new JLabel("이메일");
		
		//텍스트필드 선언부
		txt_id = new JTextField(10);
		
		txt_pwd = new JPasswordField(10);
		
		txt_pwd.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				JPasswordField jf=(JPasswordField)e.getSource();
				String password = new String(jf.getPassword());
				if(password.length()>=9){
					e.consume();	
				}
			}
		});
		
		txt_pwdch = new JPasswordField(10);
		
		txt_pwdch.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				JPasswordField jf=(JPasswordField)e.getSource();
				String password = new String(jf.getPassword());
				if(password.length()>=9){
					e.consume();	
				}
			}
		});
		
		txt_name = new JTextField(10);

		txt_nick = new JTextField(10);
		
		txt_email = new JTextField(10);
		
		//패널 선언부
		p_north = new JPanel();
		p_north.setPreferredSize(new Dimension(400, 50));
		p_center = new JPanel();
		p_south = new JPanel();
		p_id = new JPanel();
		p_pwd = new JPanel();
		p_pwdch = new JPanel();
		p_name = new JPanel();
		p_nick = new JPanel();
		p_email = new JPanel();
		p_gen = new JPanel();

		//붙이기
		p_north.add(la_title);
		add(p_north, BorderLayout.NORTH);
		
		p_id.add(la_id);
		p_id.add(txt_id);
		p_id.setPreferredSize(new Dimension(150, 50));
		
		p_pwd.add(la_pwd);
		p_pwd.add(txt_pwd);
		p_pwd.setPreferredSize(new Dimension(150, 50));
		
		p_pwdch.add(la_pwdch);
		p_pwdch.add(txt_pwdch);
		p_pwdch.setPreferredSize(new Dimension(150, 50));
		
		p_name.add(la_name);
		p_name.add(txt_name);
		p_name.setPreferredSize(new Dimension(130, 50));
		
		p_nick.add(la_nick);
		p_nick.add(txt_nick);
		p_nick.setPreferredSize(new Dimension(130, 50));
		
		p_email.add(la_email);
		p_email.add(txt_email);
		p_email.setPreferredSize(new Dimension(150, 50));
		
		p_gen.add(male);
		p_gen.add(female);
		
		p_center.add(p_id);
		p_center.add(p_pwd);
		p_center.add(p_pwdch);
		p_center.add(p_name);
		p_center.add(p_nick);
		p_center.add(p_gen);
		p_center.add(p_email);

		add(p_center, BorderLayout.CENTER);
		p_south.add(bt_signup);
		p_south.add(bt_cancel);
		add(p_south, BorderLayout.SOUTH);
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				setVisible(false);
				clientMain.setVisible(true);
			}
		});
		setTitle("회원가입");
		setLocationRelativeTo(this.clientMain);
		setSize(250, 550);
		setVisible(true);
		
	}

	public void registUser(){
		String id  = txt_id.getText();
		String password = new String(txt_pwd.getPassword());
		String name = txt_name.getText();
		String nick_name = txt_nick.getText();
		String gender = null;
		if(male.isSelected()){
			gender = "man";
		}else if( female.isSelected()){
			gender = "woman";
		}
		String e_mail = txt_email.getText();
		sb.delete(0, sb.length());
		sb.append("{");	
		sb.append("\"request\" : \"regist\",");
		sb.append("\"data\" : {");
		sb.append("\"id\" : \""+id+"\",");
		sb.append("\"password\" : \""+password+"\",");
		sb.append("\"name\" : \""+name+"\",");
		sb.append("\"nick_name\" : \""+nick_name+"\",");
		sb.append("\"gender\" : \""+gender+"\",");
		sb.append("\"e_mail\" : \""+e_mail+"\"");
		sb.append("}}");
		clientMain.sendMsg(sb);
	}

	public void checkPwd() {
		String pwd = new String(txt_pwd.getPassword());
		String chpwd = new String(txt_pwdch.getPassword());

		if (!pwd.equals(chpwd)) {
			JOptionPane.showMessageDialog(this, "비밀번호가일치하지않습니다");
			txt_pwdch.setText("");
			txt_pwd.setText("");
			return;
		} else {
			registUser();
		}
	}
	

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == bt_signup) {
			checkPwd();

		} else if (e.getSource() == bt_cancel) {
			cancel();
		}
	}

	public void cancel() {
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				setVisible(false);
				clientMain.setVisible(true);
			}
		});
	}

}
