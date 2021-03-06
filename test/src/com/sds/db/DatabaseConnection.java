package com.sds.db;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.SQLException;

import com.sds.server.MainServer;
import com.sds.server.*;

public class DatabaseConnection {
	public Connection con;
	
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String user = "java0819";
	String password = "java0819";
	MainServer mainServer;
	
	public DatabaseConnection(MainServer mainServer) {
		this.mainServer = mainServer;
	}
	
	public Connection getConnection(){
		try {
			con = DriverManager.getConnection(url, user, password);
			mainServer.log_area.append("[ Debug ] : Database connection is successed\n");
			
		} catch (SQLException e) {
			e.printStackTrace();
			mainServer.log_area.append("[ Debug ] : Database connection is failed");
		} 
			return con;
	}
}
