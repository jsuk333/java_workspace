package login;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Member extends JFrame {
	JButton bt_signup, bt_cancel;
	JLabel la_title, la_id, la_pwd, la_pwdch, la_name, la_nick, la_email;
	JCheckBox male, female;
	JPasswordField txt_pwd, txt_pwdch;
	JTextField txt_id, txt_name, txt_nick, txt_email;
	JPanel p_north, p_center, p_south, p_id, p_pwd, p_pwdch, p_name, p_nick, p_email, p_gen;
	Connection con;

	String url, user, o_pwd;
	String id, pwd, name, nick, email;
	boolean man, woman;
	SignUpDB db;

	public Member() {
		url = "jdbc:oracle:thin:@localhost:1521:XE";
		user = "java0819";
		o_pwd = "java0819";
		connetion();
		bt_signup = new JButton("SIGNUP");
		bt_signup.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				Object obj = e.getSource();
				if (obj.equals(bt_signup)) {
					checkPwd();

				} else if (obj.equals(bt_cancel)) {
					cancel();
				}
			}
		});
		bt_cancel = new JButton("CANCEL");

		la_title = new JLabel("회원가입");
		la_id = new JLabel("아이디");
		la_pwd = new JLabel("비밀번호");
		la_pwdch = new JLabel("비밀번호확인");
		la_name = new JLabel("이름");
		la_nick = new JLabel("별명");
		male = new JCheckBox("남");
		female = new JCheckBox("여");
		la_email = new JLabel("이메일");
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
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(250, 550);
		setVisible(true);

	}

	public void connetion() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			try {
				con = DriverManager.getConnection(url, user, o_pwd);

				System.out.println("성공");
			} catch (SQLException e) {

				e.printStackTrace();
			}
		} catch (ClassNotFoundException e1) {

			e1.printStackTrace();
		}

	}

	public void sign() {
		id = txt_id.getText();
		pwd = txt_pwd.getText();
		name = txt_name.getText();
		nick = txt_nick.getText();
		email = txt_email.getText();
		boolean man = male.isSelected();
		boolean woman = female.isSelected();
		db = new SignUpDB(this);

	}

	public void checkPwd() {
		String pwd = txt_pwd.getText();
		String chpwd = txt_pwdch.getText();

		if (!pwd.equals(chpwd)) {
			JOptionPane.showMessageDialog(this, "비밀번호가일치하지않습니다");
			txt_pwdch.setText("");
			txt_pwd.setText("");
			return;
		} else {
			JOptionPane.showMessageDialog(this, "가입을 축하합니당");
			sign();
		}
	}

	public void cancel() {

	}

	public static void main(String[] args) {
		new Member();
	}

}
