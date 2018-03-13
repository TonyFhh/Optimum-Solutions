package com.optimum.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.optimum.pojo.UserPojo;

public class Connect {
	
	private static Connect ConnectObj;
	
//	private Connect () throws ClassNotFoundException, SQLException {
//		init();
//	}
	
	public static Connect getConnectObj() throws ClassNotFoundException, SQLException {
		if (ConnectObj==null) { //since its a memory location and not object, we use ==
			ConnectObj = new Connect();
		}
			return ConnectObj; //return memory address
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
