package com.optimum.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import com.optimum.connection.Connect;
import com.optimum.controller.RegVerifiers;
import com.optimum.pojo.UserPojo;

/** This class will feature loginAuth for sql query
 * */

public class Dao {
	
	private boolean status;
	public Connect connect;
	public Connection con;
	private PreparedStatement st;
	private ResultSet rs;
	
	public Dao () throws ClassNotFoundException, SQLException {
		connect = Connect.getConnectObj();
		con = connect.init();
	}
	
	/* Retrieve name from DB. Only used for Welcome screen...*/
	public String getName(UserPojo refUPojo) throws SQLException {
		
		String name = null;
		String sql = ("SELECT Name from a4db where Email=?;"); //input the sql query
		st = con.prepareStatement(sql);
		st.setString(1, refUPojo.getUser());
		rs = st.executeQuery(); //execute the string into the sql server
		
		  //if entry found
		if (rs.next()) {
			name = rs.getString("Name");
		}			
		
		return name;
	}
	
	/* Retrieve pw from DB. used for app sections where pw is not input by user*/
	public String getPW(UserPojo refUPojo) throws SQLException {
		
		String pw = null;
		String sql = ("SELECT Password from a4db where Email=?;"); //input the sql query
		st = con.prepareStatement(sql);
		st.setString(1, refUPojo.getUser());
		rs = st.executeQuery(); //execute the string into the sql server
		
		  //if entry found 
		if (rs.next()) {
			pw = rs.getString("Password");
		}
		
		return pw;
	}
	
	/* Verifies user input pw against DB */
	public boolean checkPW(UserPojo refUPojo) throws SQLException {
		String pw = refUPojo.getPassword();
		
		try {
			String sql = ("SELECT Password from a4db where Email=?;"); //input the sql query
			st = con.prepareStatement(sql);
			st.setString(1, refUPojo.getUser());
			rs = st.executeQuery(); //execute the string into the sql server
			
			if (rs.next()) { //if entry found 
				String dbpw = rs.getString("Password");
				if (pw.equals(dbpw)) {
					status = true;
				} else {
					status = false;
				}
				
			} else {
				status = false;
				
				
			}
		} catch (SQLException e) {
			status = false;
		}
		
				
		
		return status;
	}
	
	/* DB update no of attempts for each failed login at pw stage */
	public void failLock (UserPojo refUPojo) throws SQLException {
		
		if (!isadmin(refUPojo)) {
			
			refUPojo.setFailed(refUPojo.getFailed() + 1);
//			System.out.println("debug: inside failLock(), refUPojo var = " + refUPojo.getFailed());
			
			String sql = ("UPDATE a4db SET No_of_Attempts =? where Email = ?;");
			st = con.prepareStatement(sql);
			st.setInt(1, refUPojo.getFailed());
			st.setString(2, refUPojo.getUser());
			st.executeUpdate(); //execute the string into the sql server
			if (refUPojo.getFailed() >= 3) {
				sql = ("UPDATE a4db SET Status = ? where Email = ?;");
				st = con.prepareStatement(sql);
				st.setString(1, "lock");
				st.setString(2, refUPojo.getUser());
				st.executeUpdate(); //execute the string into the sql server
			}
		}
	}
	
	/* Somehow i couldn't get this to work but if it does, failed login attempts will be preserved across "sessions" will practically make brute forcing impossible
	* Atm a user can restart login attempts by restarting the app, but subsequent failed attempts will still correct trigger lock after 3 failed attempts */
	
	/* public void getAttempts (UserPojo refUPojo) throws SQLException {
		String sql = ("SELECT No_of_Attempts from a4db where Email = ?;");
		st = con.prepareStatement(sql);
		st.setString(1, refUPojo.getUser());
		System.out.println("debug: getAttempts()");
		if (rs.next()) {
			int attempts = rs.getInt(1);
			refUPojo.setFailed(attempts);
			System.out.println("debug: getAttempts(): refUPojo var set-ted");
		}
		
	} */
	
	/* Reset no of attempts to 0 on a successful login/pw reset */
	public void restoreLock (UserPojo refUPojo) throws SQLException {
		
		refUPojo.setFailed(0);
		
		String sql = ("UPDATE a4db SET No_of_Attempts =? where Email = ?;");
		st = con.prepareStatement(sql);
		st.setInt(1, 0);
		st.setString(2, refUPojo.getUser());
//		System.out.println("debug: restoreLock() set No of attempts to 0");
		st.executeUpdate(); //execute the string into the sql server
		
	}
	
	/* Verify input EMail against the db email (user id) */
	public boolean checkUser (UserPojo refUPojo) throws ClassNotFoundException, SQLException {
		
		String sql = ("SELECT Email from a4db where Email =?;"); //input the sql query
		st = con.prepareStatement(sql);
		st.setString(1, refUPojo.getUser());
		rs = st.executeQuery(); //execute the string into the sql server
		
		if (rs.next()) { //if entry found 
			status = true;
		} else {
			status = false;
		}

		
		return status;
	}
	
	/* Admin change user status between lock/unlock. If user is "lock", it will change to unlock and vice versa */
	public boolean changestatus (UserPojo refUPojo, String userid) throws SQLException {
		
		System.out.println("Changing account status for userid " + userid + "...");
		String sql = ("SELECT Email, Role, Status from a4db where Serial_No = ?;");
		st = con.prepareStatement(sql);
		st.setString(1, userid);
		rs = st.executeQuery(); //execute the string into the sql server
		if (rs.next()) {			
			String accstatus = rs.getString("Status");
			String role = rs.getString("Role");
			String email = rs.getString("Email");
			
			if (role.toLowerCase().equals("user")) { //if is user account
				if (accstatus.toLowerCase().equals("unlock")) {
					sql = ("UPDATE a4db SET Status =? where Serial_No = ?;");
					st = con.prepareStatement(sql);
					st.setString(1, "lock");
					st.setString(2, userid);
					st.executeUpdate(); //execute the string into the sql server
					System.out.println("account user " + email + " now locked.");
					status = true;
				} else {
					sql = ("UPDATE a4db SET Status =?, No_of_Attempts =? where Serial_No = ?;");
					st = con.prepareStatement(sql);
					st.setString(1, "unlock");
					st.setInt(2, 0);
					st.setString(3, userid);
					st.executeUpdate(); //execute the string into the sql server
					System.out.println("account user " + email + " now unlocked.");
					status = true;
					
				}
				
			} else { //if is admin account
				System.out.println("Unable to change status of admin accounts. Try another Serial No.");
			} 
		} else { // if no results returned
			System.out.println("Invalid Userid. Verify user Serial No. and try again.");
			status = false;
		}
			
			
			
		return status;	
	}
	
	
	/* check against db whether user is logging in for the first time */
	
	public boolean isfirstlogin (UserPojo refUPojo) throws SQLException {
		
		String sql = ("SELECT First_Login from a4db where Email = ?;"); //input the sql query
		st = con.prepareStatement(sql);
		st.setString(1, refUPojo.getUser());
		rs = st.executeQuery(); //execute the string into the sql server
		
		if (rs.next()) { //if entry found
			String priorlogin = rs.getString("First_Login");
			if (priorlogin.toLowerCase().equals("true")) {
				status = true;
			} else
				status = false;			
		} 
		return status;	
	}
	
	/* Verify against db that user is locked or not */
	
	public boolean islocked (UserPojo refUPojo) throws SQLException {
		
		String sql = ("SELECT Status from a4db where Email = ?;"); //input the sql query
		st = con.prepareStatement(sql);
		st.setString(1, refUPojo.getUser());
		rs = st.executeQuery(); //execute the string into the sql server
		
		if (rs.next()) { //if entry found
			String key = rs.getString("Status");
			if (key.toLowerCase().equals("lock")) {
				status = true;
			} else
				status = false;			
		} 
		return status;
	}
	
	/* Check user role against db */
	
	public boolean isadmin (UserPojo refUPojo) throws SQLException {
			
			String sql = ("SELECT Role from a4db where Email = ?;"); //input the sql query
			st = con.prepareStatement(sql);
			st.setString(1, refUPojo.getUser());
			rs = st.executeQuery(); //execute the string into the sql server
			
			if (rs.next()) { //if entry found
				String user = rs.getString("Role");
//				System.out.println("debug: isadmin() user = " + user);
				if (user.toLowerCase().equals("admin")) {
					status = true;
				} else
					status = false;			
			} 
			return status;
		}
	
	/* Method to update db for user pw and security Q/A. Invoked during first login and forget password selections.*/
	public void changePwS (UserPojo refUPojo, List<String> uinput) throws SQLException {
		System.out.println("Updating Database...");
		
		refUPojo.setPassword(uinput.get(0));
		
		String sql = ("UPDATE a4db set Password=?, Security_Question=?, Security_Answer=?, First_Login =? where Email =?;"); //input the sql query
		st = con.prepareStatement(sql);
		st.setString(1, uinput.get(0));
		st.setString(2, uinput.get(1));
		st.setString(3, uinput.get(2));
		st.setString(4, "false");
		st.setString(5, refUPojo.getUser());
		st.executeUpdate(); //execute the string into the sql server
		
			
		System.out.println("Update Successful.");
	}
	
	
	/* Retrive Security Question from DB */
	public String getSQ (UserPojo refUPojo) throws SQLException {
		String sql = ("SELECT Security_Question from a4db where Email = ?;"); //input the sql query
		st = con.prepareStatement(sql);
		st.setString(1, refUPojo.getUser());
		rs = st.executeQuery(); //execute the string into the sql server
		
		if (rs.next()) {
//			System.out.println("debug: getSQ() returned rs.next");
			String sq = rs.getString("Security_Question");
			return sq;
		} else {
			System.out.println("No Security question has been set yet for this user. Please refer to registered email inbox for temporary password.");
			return null;
		}
	}
	
	/* Retrive Security ANswer from DB */
	public String getSA(UserPojo refUPojo) throws SQLException {
		
		String sa = null;
		String sql = ("SELECT Security_Answer from a4db where Email = ?;"); //input the sql query
		st = con.prepareStatement(sql);
		st.setString(1, refUPojo.getUser());
		rs = st.executeQuery(); //execute the string into the sql server
		
		if (rs.next()) {
			sa = rs.getString("Security_Answer");
		} 		

		return sa;
		}
	
	/* Write to db during admin registration of new users. List from controller is passed here */
	public void dbRegister(List<String> entry) throws SQLException {
		
		/* Generate the password, using first 4 digits of nric and last 4 digits of mobile */
		System.out.println("Generating Password...");
		String nric = entry.get(1);
		String mobile = entry.get(4);
		String date = entry.get(3);
		date = RegVerifiers.convertToSqlDate(date);
//		System.out.println("debug: Dao date: " + date);
		StringBuilder sb = new StringBuilder();
		for (int i = 1; i < 5; i++) {
			sb.append(nric.charAt(i));
		}
		for (int i = 4; i < 8; i++) {
			sb.append(mobile.charAt(i));
		}
		String pw = sb.toString();
//		System.out.println("debug: pw is " + pw);
		
		//Update DB
		System.out.println("Updating Database...");
		
		String sql = "INSERT into a4db (Name, NRIC, Email, DoB, Mobile, Password, Role, First_Login, Status, No_of_Attempts) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
		st = con.prepareStatement(sql);
		st.setString(1, entry.get(0));
		st.setString(2, entry.get(1));
		st.setString(3, entry.get(2));
		st.setString(4, date);
		st.setInt(5, Integer.parseInt(entry.get(4)));
		st.setString(6, pw);
		st.setString(7, "user");
		st.setString(8, "true");
		st.setString(9, "unlock");
		st.setInt(10, 0);
		st.executeUpdate(); //execute the string into the sql server 
		
		System.out.println("New user "+entry.get(3)+" registered...");
		EmailSupport sendmail = new EmailSupport();
		sendmail.SendFromGMail(entry.get(2), entry.get(0), pw);
		
		
		
	}

	/* Method to access db and show user list when prompted by any admin*/
	public void getUserList() throws SQLException {
		System.out.println("Getting data from Database...");
		String sql = ("SELECT * from a4db WHERE Role =?;"); //input the sql query
		st = con.prepareStatement(sql);
		st.setString(1, "user");
		ResultSet rs = st.executeQuery(); //execute the string into the sql server
		
		System.out.println("Serial No Name NRIC DoB Status No of Attempts");
		
		while (rs.next()) {			

			String row = rs.getString("Serial_No");
			String row1 = rs.getString("Name");
			String row2 = rs.getString("NRIC");
			String row3 = rs.getString("Email");
			String row4 = rs.getString("Status");
			String row5 = rs.getString("No_of_Attempts");
			System.out.print(row + " ");
			System.out.print(row1 + " ");
			System.out.print(row2 + " ");
			System.out.print(row3 + " ");
			System.out.print(row4 + " ");
			System.out.println(row5 + " ");
			
		}
		System.out.println("Returning to menu...");
		
	}
	

} // end of dao class
