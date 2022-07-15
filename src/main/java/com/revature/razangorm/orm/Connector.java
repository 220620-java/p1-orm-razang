package com.revature.razangorm.orm;

import java.sql.Connection;

/**
 * functional interface
 * @author razaghulam
 *
 */
public interface Connector {

	/**
	 * takes database url, user name and password and returns connection object
	 * @param url
	 * @param dbUser
	 * @param pwd
	 * @return connection object
	 */
	public Connection AccessDatabase(String url, String dbUser, String pwd); 
}

