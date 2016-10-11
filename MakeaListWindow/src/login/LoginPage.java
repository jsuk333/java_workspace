package login;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.sds.practice.FMainList;

public class LoginPage extends JFrame implements ActionListener {
	JLabel title, id, pwd;
	JPanel p_north, p_center, p_south, p_id, p_pwd;
	JTextField txt_id;
	JPasswordField txt_pwd;
	JButton bt_login, bt_cencel,bt_regit;
	Connection con;
	String o_url="jdbc:oracle:thin:@localhost:1521:XE";
	String o_id="java0819";
	String o_pwd="java0819";
	PreparedStatement pstmt;
	ResultSet rs;
	public LoginPage() {
		
		title = new JLabel("회원가입");
		id = new JLabel("ID");
		pwd = new JLabel("PasssWord");
		txt_id = new JTextField(10);

		txt_pwd = new JPasswordField(10);
		
		bt_login = new JButton("LONGIN");
		bt_login.addActionListener(this);
		bt_cencel = new JButton("CENCEL");
		bt_cencel.addActionListener(this);
		bt_regit=new JButton("Regit");
		bt_regit.addActionListener(this);
		p_north = new JPanel();
		p_north.setPreferredSize(new Dimension(500, 50));
		p_center = new JPanel();
		p_south = new JPanel();
		p_id = new JPanel();
		p_id.setPreferredSize(new Dimension(500, 50));
		p_pwd = new JPanel();
		p_pwd.setPreferredSize(new Dimension(400, 25));

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
		p_south.add(bt_regit);
		add(p_south, BorderLayout.SOUTH);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(500, 300);
		setVisible(true);

	}

	public void actionPerformed(ActionEvent e) {
		Object obj=e.getSource();
		if(obj.equals(bt_login)){
			connection();
			join();
		}else if(obj.equals(bt_cencel)){
			out();
		}else if(obj.equals(bt_regit)){
			new Member();
		}
	}
	public void connection(){
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("성공");
			System.out.println(txt_pwd.getText());
			try {
				con=DriverManager.getConnection(o_url,o_id,o_pwd);
				String sql="select * from member where id=? and pwd=?";
				pstmt=con.prepareStatement(sql);
				pstmt.setString(1, txt_id.getText());
				pstmt.setString(2, txt_pwd.getText());
				rs=pstmt.executeQuery();
				if(rs.next()){
					JOptionPane.showMessageDialog(this, "성공입니당");
					new FMainList();
				}else{
					JOptionPane.showMessageDialog(this, "실패입니당");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		}
	}
	
	public void join(){
		
	}
	public void out(){
		
	}
	
	public static void main(String[] args) {
		new LoginPage();

	}

}
