package com.optimum.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.optimum.pojo.UserPojo;

public class Connect {
	
	private static Connect ConnectObj;
	
	/* Singleton design for db connection*/
	
	public static Connect getConnectObj() throws ClassNotFoundException, SQLException {
		if (ConnectObj==null) { 
			ConnectObj = new Connect();
		}
			return ConnectObj; 
	}
	
	public Connection init() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver"); //create an object for the database class?
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/batch5","root","root"); 
		return con;
	}
	
	public void end(Connection con) throws SQLException {
		con.close();
	}
	
	
}
