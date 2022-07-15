package com.revature.razangorm.orm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect implements Connector {

	/**
	 * Takes database url, name, and password and returns connection object
	 */
	@Override
	public Connection AccessDatabase(String dbUrl, String dbUser, String dbPwd) {
		// TODO Auto-generated method stub
		
		Connection conn = null;
		String url = dbUrl; 
		String user = dbUser; 
		String pwd = dbPwd; 
		
		try {
			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection(url, user, pwd); 
			System.out.println("Connected successfully");
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return conn;
	}

}
