package com.revature.razang.utilities;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

// Singleton class: This class can have only one instance 
public class ConnectionObject1 {
	
	private static ConnectionObject1 connObj; 
	private Properties property;
	
	
	//must have private constructor so no one can create it is instance
	private ConnectionObject1() {
		
		property = new Properties(); 
		
		InputStream propsFile = ConnectionObject1.class.getClassLoader()
				.getResourceAsStream("database.properties");
		try {
			property.load(propsFile);
		} catch (IOException e) {
			// Default database configuration
			property.setProperty("url", "jdbc:postgresql://localhost:5432/postgres");
			property.setProperty("usr", "postgres");
			property.setProperty("pwd", "");
			e.printStackTrace();
		}
	}
	
	public static synchronized ConnectionObject1 getConnectionUtil() {
		if (connObj == null) {
			connObj = new ConnectionObject1();
		}
		return connObj;
	}
	
	// factory: creates Connection objects and returns them
	public Connection getConnection() {
		// when connecting to the DB, we need:
		// JDBC driver
		// database URL
		// username
		// password
		Connection conn = null;
		
		// using environment variables
//		String dbUrl = System.getenv("DB_URL");
//		String dbUser = System.getenv("DB_USER");
//		String dbPass = System.getenv("DB_PASS");
		
		// using properties file
		String dbUrl = property.getProperty("url");
		String dbUser = property.getProperty("usr");
		String dbPass = property.getProperty("pwd");
		
		try {
			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection(
					dbUrl,
					dbUser,
					dbPass);
			//System.out.println("passed");
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return conn;
	}
}
