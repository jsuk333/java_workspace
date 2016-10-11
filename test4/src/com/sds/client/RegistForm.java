package com.sds.client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class RegistForm extends JLabel implements ActionListener{
	JButton bt_signup, bt_cancel, bt_check_id;

	JLabel la_design1,welcome;
	JPasswordField txt_pwd, txt_pwdch;
	JTextField txt_id, txt_name, txt_nick, txt_email;
	String url, user, o_pwd;
	String id, pwd, name, nick, email;
	boolean man, woman;

	ClientMain clientMain;
	StringBuffer sb = new StringBuffer();

	@Override
	protected void paintComponent(Graphics g) {
		g.drawImage(clientMain.icon.getImage(), 0, 0, getParent());
		setOpaque(false);
	}

	public RegistForm(ClientMain clientMain) {
		this.clientMain = clientMain;
		this.transferFocus();
		setLayout(new FlowLayout());
		
		// 버튼 선언부
		bt_signup = new JButton();
		bt_signup.addActionListener(this);

		bt_check_id = new JButton();
		bt_check_id.setPreferredSize(new Dimension(300,30));
		bt_check_id.addActionListener(this);

		bt_cancel = new JButton();
		bt_cancel.addActionListener(this);

		// 라벨 선언부
		la_design1 = new JLabel();
		la_design1.setPreferredSize(new Dimension(400, 250));



		// 텍스트필드 선언부
		txt_id = new MyText();
		txt_id.setText("Username");
		txt_id.setForeground(Color.gray);
		txt_id.setFont(new Font("고딕", Font.BOLD, 18));
		txt_id.setPreferredSize(new Dimension(300, 30));
		txt_id.addFocusListener(new FocusListener() {
			public void focusLost(FocusEvent e) {

				if (txt_id.getText().equals("")) {
					txt_id.setText("Username");
					txt_id.setForeground(Color.gray);
				}
			}

			public void focusGained(FocusEvent e) {
				{
					if (txt_id.getText().equals("Username")) {
						txt_id.setText("");
						txt_id.setForeground(Color.DARK_GRAY);
					}
				}
			}
		});

		txt_pwd = new MyPassword();
		txt_pwd.setEchoChar((char) 0);
		txt_pwd.setText("Password");
		txt_pwd.setForeground(Color.gray);

		txt_pwd.setFont(new Font("고딕", Font.BOLD, 18));
		txt_pwd.setPreferredSize(new Dimension(150, 30));
		txt_pwd.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				JPasswordField jf = (JPasswordField) e.getSource();
				String password = new String(jf.getPassword());
				if (password.length() >= 9) {
					e.consume();
				}
			}
		});
		txt_pwd.addFocusListener(new FocusListener() {

			public void focusLost(FocusEvent e) {

				String str = new String(txt_pwd.getPassword());

				if (str.equals("")) {
					txt_pwd.setEchoChar((char) 0);
					txt_pwd.setText("Password");
					txt_pwd.setForeground(Color.GRAY);
				}

			}

			public void focusGained(FocusEvent e) {
				{
					String str = new String(txt_pwd.getPassword());

					if (str.equals("Password")) {
						txt_pwd.setEchoChar('*');
						txt_pwd.setText("");
						txt_pwd.setForeground(Color.DARK_GRAY);
					}
				}
			}
		});
		txt_pwdch = new MyPassword();
		txt_pwdch.setEchoChar((char) 0);
		txt_pwdch.setText("Confirm Password");
		txt_pwdch.setForeground(Color.gray);
		txt_pwdch.setFont(new Font("고딕", Font.BOLD, 14));
		txt_pwdch.setPreferredSize(new Dimension(148, 30));
		txt_pwdch.addFocusListener(new FocusListener() {

			public void focusLost(FocusEvent e) {

				String str = new String(txt_pwdch.getPassword());

				if (str.equals("")) {
					txt_pwdch.setEchoChar((char) 0);
					txt_pwdch.setFont(new Font("고딕", Font.BOLD, 14));
					txt_pwdch.setText("Confirm Password");
					txt_pwdch.setForeground(Color.GRAY);
				}

			}

			public void focusGained(FocusEvent e) {
				{
					String str = new String(txt_pwdch.getPassword());

					if (str.equals("Confirm Password")) {
						txt_pwdch.setEchoChar('*');
						txt_pwdch.setText("");
						txt_pwdch.setFont(new Font("고딕", Font.BOLD, 18));
						txt_pwdch.setForeground(Color.DARK_GRAY);
					}
				}
			}
		});
		txt_pwdch.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				JPasswordField jf = (JPasswordField) e.getSource();
				String password = new String(jf.getPassword());
				if (password.length() >= 9) {
					e.consume();
				}
			}
		});
		txt_name = new MyText();
		txt_name.setText("Name");
		txt_name.setForeground(Color.gray);
		txt_name.setFont(new Font("고딕", Font.BOLD, 18));
		txt_name.setPreferredSize(new Dimension(148, 30));

		txt_name.addFocusListener(new FocusListener() {
			public void focusLost(FocusEvent e) {
				if (txt_name.getText().equals("")) {
					txt_name.setText("Name");
					txt_name.setForeground(Color.gray);
				}
			}

			public void focusGained(FocusEvent e) {
				{
					if (txt_name.getText().equals("Name")) {
						txt_name.setText("");
						txt_name.setForeground(Color.DARK_GRAY);
					}
				}
			}
		});

		txt_nick = new MyText();
		txt_nick.setText("Nickname");
		txt_nick.setForeground(Color.gray);
		txt_nick.setFont(new Font("고딕", Font.BOLD, 18));
		txt_nick.setPreferredSize(new Dimension(148, 30));

		txt_nick.addFocusListener(new FocusListener() {
			public void focusLost(FocusEvent e) {
				if (txt_nick.getText().equals("")) {
					txt_nick.setText("Nickname");
					txt_nick.setForeground(Color.gray);
				}
			}

			public void focusGained(FocusEvent e) {
				{
					if (txt_nick.getText().equals("Nickname")) {
						txt_nick.setText("");
						txt_nick.setForeground(Color.DARK_GRAY);
					}
				}
			}
		});	

		txt_email = new MyText();
		txt_email.setText("Email");
		txt_email.setForeground(Color.gray);
		txt_email.setFont(new Font("고딕", Font.BOLD, 18));
		txt_email.setPreferredSize(new Dimension(300, 30));

		txt_email.addFocusListener(new FocusListener() {
			public void focusLost(FocusEvent e) {
				if (txt_email.getText().equals("")) {
					txt_email.setText("Email");
					txt_email.setForeground(Color.gray);
				}
			}

			public void focusGained(FocusEvent e) {
				{
					if (txt_email.getText().equals("Email")) {
						txt_email.setText("");
						txt_email.setForeground(Color.DARK_GRAY);
					}
				}
			}
		});	

		setPreferredSize(new Dimension(450, 550));
		// setBackground(new Color(255, 0, 0,0));

		// 붙이기
		welcome=new JLabel();
		welcome.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource("welcome.png")));
		
		bt_check_id.setBorderPainted(false);
		bt_signup.setBorderPainted(false);
		bt_cancel.setBorderPainted(false);
		bt_check_id.setBackground(new Color(0, 0, 0, 0));
		bt_check_id.setRolloverIcon(new ImageIcon(this.getClass().getClassLoader().getResource("check2.png")));
		bt_check_id.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource("check1.png")));
		bt_check_id.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				updateUI();
				repaint();
			}

			@Override
			public void mouseExited(MouseEvent e) {
				updateUI();
				repaint();
			}
		});	
		bt_signup.setBackground(new Color(0, 0, 0, 0));
		bt_signup.setRolloverIcon(new ImageIcon(this.getClass().getClassLoader().getResource("reg_signup2.png")));
		bt_signup.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource("reg_signup1.png")));
		bt_signup.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				updateUI();
				repaint();
			}

			@Override
			public void mouseExited(MouseEvent e) {
				updateUI();
				repaint();
			}
		});
		bt_cancel.setBackground(new Color(0, 0, 0, 0));
		bt_cancel.setRolloverIcon(new ImageIcon(this.getClass().getClassLoader().getResource("reg_cancel2.png")));
		bt_cancel.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource("reg_cancel1.png")));
		bt_cancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				updateUI();
				repaint();
			}

			@Override
			public void mouseExited(MouseEvent e) {
				updateUI();
				repaint();
			}
		});		
		add(la_design1);
		add(welcome);
		add(txt_id);
		add(bt_check_id);

		add(txt_pwd);
		add(txt_pwdch);
		add(txt_name);

		add(txt_nick);

		add(txt_email);
		
		add(bt_signup);
		bt_signup.setPreferredSize(new Dimension(148,32));
		add(bt_cancel);
		bt_cancel.setPreferredSize(new Dimension(148,32));

		setPreferredSize(new Dimension(400, 550));
		setVisible(true);
		

	}

	public void registUser() {
		String id = txt_id.getText();
		String password = new String(txt_pwd.getPassword());
		String name = txt_name.getText();
		String nick_name = txt_nick.getText();

		String e_mail = txt_email.getText();
		sb.delete(0, sb.length());
		sb.append("{");
		sb.append("\"request\" : \"regist\",");
		sb.append("\"data\" : {");
		sb.append("\"id\" : \"" + id + "\",");
		sb.append("\"password\" : \"" + password + "\",");
		sb.append("\"name\" : \"" + name + "\",");
		sb.append("\"nick_name\" : \"" + nick_name + "\",");
		sb.append("\"gender\" : \"1\",");
		sb.append("\"e_mail\" : \"" + e_mail + "\"");
		sb.append("}}");
		clientMain.clientThread.sendMsg(sb);
	}

	public void checkPwd() {
		String pwd = new String(txt_pwd.getPassword());
		String chpwd = new String(txt_pwdch.getPassword());

		if (!pwd.equals(chpwd)) {
			JOptionPane.showMessageDialog(this, "비밀번호가 일치하지 않습니다");
			txt_pwdch.setText("");
			txt_pwd.setText("");
			return;
		}
	}



	// 공백 발견시 실행되는 메서드
	// 재광이가한거~~~~~~~~~~~~~~~~~~~~~//재광이가한거~~~~~~~~~~~~~~~~~~~~~
	public boolean space(String txt) {
		boolean result = false;
		for (int i = 0; i < txt.length(); i++) {
			char a = txt.charAt(i);
			if (a == ' ') {
				result = true;
			}
		}
		return result;
	}

	public void checkSpace() {
		ArrayList<JTextField> a = new ArrayList<JTextField>();
		a.add(txt_id);
		a.add(txt_pwd);
		a.add(txt_name);
		a.add(txt_nick);
		a.add(txt_email);
		String[] b = { "아이디에는 ", "비밀번호에 ", "이름에 ", "닉네임에 ", "이메일에 " };
		for (int i = 0; i < a.size(); i++) {
			boolean result = space(a.get(i).getText().toString());
			if (result) {
				JOptionPane.showMessageDialog(this, b[i] + "공백을 넣을 수 없습니다.");
				a.get(i).setText("");
				return;
			} else if (a.get(i).getText().toString().equals("")) {
				JOptionPane.showMessageDialog(this, b[i] + "공백을 넣을 수 없습니다.");
				return;
			}
		}
		checkPwd();
		registUser();
	}

	// 재광이가한거~~~~~~~~~~~~~~~~~~~~~//재광이가한거~~~~~~~~~~~~~~~~~~~~~
	public void checkId() {
		sb.delete(0, sb.length());
		sb.append("{");
		sb.append("\"request\":\"checkId\",");
		sb.append("\"id\":\"" + txt_id.getText() + "\"");
		sb.append("}");
		System.out.println(sb.toString());
		boolean result = space(txt_id.getText());
		if (result) {
			JOptionPane.showMessageDialog(this, "아이디에는 공백을 넣을 수 없습니다.");
			txt_id.setText("");
			return;
		} else if (txt_id.getText().equals("")) {
			JOptionPane.showMessageDialog(this, "아이디에는 공백을 넣을 수 없습니다.");
			txt_id.setText("");
			return;
		}
		clientMain.clientThread.sendMsg(sb);

	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == bt_signup) {
			System.out.println("눌리나");
			checkSpace();

		} else if (e.getSource() == bt_cancel) {
			this.setVisible(false);
			clientMain.p_center.setVisible(true);
		} else if (e.getSource() == bt_check_id) {
			checkId();
		}
	}

}
