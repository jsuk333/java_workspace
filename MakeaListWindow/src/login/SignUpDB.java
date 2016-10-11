package login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SignUpDB {
	String sql;
	String id, pwd, name, nick, email;
	PreparedStatement pstmt;
	boolean man, woman;
	Member member;
	Connection con;
	int gen;

	public SignUpDB(Member member) {
		this.id = member.id;
		this.pwd = member.pwd;
		this.name = member.name;
		this.nick = member.nick;
		this.email = member.email;
		this.man=member.man;
		this.woman=member.woman;
		this.con = member.con;
		if(man==true){
			gen=1;
		}else if(woman==true){
			gen=0;
		}
		System.out.println(gen);
		sql = "insert into member(MEMBER_ID,ID,PWD,NAME,NICK_NAME,GENDER,E_MAIL)";
		sql += " values(seq_member.nextval,?,?,?,?,?,?)";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, pwd);
			pstmt.setString(3, name);
			pstmt.setString(4, nick);
			pstmt.setInt(5, gen);
			pstmt.setString(6, email);
			System.out.println(sql);
			int result=pstmt.executeUpdate();
			if(result!=0){
				System.out.println("성공");
			}else{
				System.out.println("실패");
			}
			
		} catch (SQLException e) {
			System.out.println("안들어갔다");
			e.printStackTrace();
		}
	}
}
