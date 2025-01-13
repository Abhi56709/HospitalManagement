package com.kodnest.hms;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.security.auth.login.AccountNotFoundException;



public class DatabaseService {
public static Connection conn;

private static Connection createConn() throws AccountNotFoundException, SQLException, ClassNotFoundException {
	Class.forName("com.mysql.cj.jdbc.Driver");
	conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital", "root", "Abhi@123");
	System.out.println("Database connection creation successfully.");
	return conn;
	
}

public static Connection getConnection() throws ClassNotFoundException, SQLException, AccountNotFoundException {
	if (conn == null) {
		return createConn();
	}
	
	return conn;
	
}
	
}
