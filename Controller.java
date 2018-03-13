package com.optimum.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;

import com.optimum.dao.Dao;
import com.optimum.pojo.UserPojo;

public class Controller {
	
	private Scanner consc;
	private Dao loginDao;
	private List<String> sq;
	
	public Controller() throws ClassNotFoundException, SQLException {
		consc = new Scanner (System.in);
		loginDao = new Dao();
		sq = new ArrayList<String>();
		sq.add("Favourite Colour");
		sq.add("Favourite Pet");
		sq.add("Favourite");
		}
	
			
	public Dao getLoginDao() {
		return loginDao;
	}

	//main register method - WIP
	public void register () throws SQLException {
			System.out.println("For registration, You will be expected to key the following particulars in sequence:");
			System.out.println("Name, NRIC, Date of Birth (DD/MM/YYYY), Email, Mobile");
			
			String name;
			Name:
			while (true) {
				System.out.println("Enter Name:");
				name = consc.nextLine();
				if (!RegVerifiers.isValidName(name)) {
					System.out.println("Names should not contain numbers or symbols. Try again");
					continue;
				} else {
					name = name.trim().replaceAll("\\s+"," ");
					System.out.println("debug: print name: " + name);
					break Name;
				}
					
			}
			
			String nric;
			NRIC:
			while (true) {
				System.out.println("Key in NRIC:");
				nric = consc.next();
				consc.nextLine();
				nric = nric.trim().toUpperCase();
				//Perform letter verification of first and last char
				System.out.println("debug: print input NRIC: " + nric);
				Character first = nric.charAt(0);
				Character last = nric.charAt(8);
				if (!Character.isLetter(first)  | !Character.isLetter(last)  ) {
					System.out.println("Invalid NRIC. Try again.");
					continue;
				}
				else {
					nric = nric.trim();
					break NRIC;
				}
				
			}
			
			String dob;
			DOB:
			while (true) {
				System.out.println("Enter date of birth (DD/MM/YYYY):");
				dob = consc.nextLine();
				dob = dob.trim();
				String dobt = dob.replace("/", "");
				try {
					Integer.parseInt(dobt);
					if(RegVerifiers.isValidDate(dob)) {
						break DOB;
					} else {
						System.out.println("Invalid Date of Birth. Try again");
						continue;
					}
					
				} catch (Exception e) {
					System.out.println("Date of Birth should not contain letters or other symbols. Try again");
					continue;
				}
			}
			
			String email;
			Email:
			while (true) {
				System.out.println("Enter email address: ");
				email = consc.next();
				consc.nextLine();
				if (email.contains("@") | email.contains(" ")) {
					email = email.trim();
					break Email;
				} else {
					System.out.println("Invalid email address. Try again");
				}
			}
			
			
			String mobile;
			Mobile:
			while (true) {
				System.out.println("Enter mobile number: ");
				String input = consc.next();
				consc.nextLine();
				if (input.length() != 8) {
					System.out.println("Mobile number should be 8 characters long. Try again");
					continue;
				}
				try {
					mobile = String.valueOf(Integer.parseInt(input));
					break Mobile;					
					
				} catch (Exception e) {
					System.out.println("Mobile numbers should only contain numbers. Try again");
					continue;
				}
			}
			
		List<String> reghold = new ArrayList<String>();
		reghold.add(name);
		reghold.add(nric);
		reghold.add(email);
		reghold.add(dob);		
		reghold.add(mobile); 
		
		System.out.println(reghold);
		loginDao.dbRegister(reghold);
		
		
	}
	
	/*main login method, invoked using option 1 from main app.
	 * it will encompass user and pw step by step verification
	 * includes support for firstlogin, user type redirects, consectutive wrong pw user locking
	 * method return if user attempts to login to a locked acc
	*/
	public void loginmethod () throws ClassNotFoundException, SQLException {
		
		String user;
		String pw;
		UserPojo refUPojo;
		

		refUPojo = new UserPojo();
		
		/* get input for user,setting it to pojo and performing a check from the database */
		LoginUser:
			while (true) {
				boolean status;
				System.out.println("Enter UserID (email): ");
				user = consc.next();
				user = user.trim();
				consc.nextLine();
				refUPojo.setUser(user);
				status = loginDao.checkUser(refUPojo);
				if (status) {
					break LoginUser;
				}
				else
					System.out.println("Invalid UserID. Try Again.");
			}
		
		/* if database checks that user is locked */ //**i think this could be integrated with previous segment
		if (loginDao.islocked(refUPojo)) {
			System.out.println("Account locked, please contact adminstrator for support.");
			return;
		}
		
		/* get input for pw, setting it to pojo and performing a check from the database */
		LoginPass:
			while (true) {
				boolean status = false;
				System.out.println("Enter Password: ");
				pw = consc.next();
				pw = pw.trim();
				consc.nextLine();
				refUPojo.setPassword(pw);					
				status = loginDao.checkPW(refUPojo);
				
				if (status) {
					break LoginPass;
				}
				else {
					System.out.println("Invalid Password. Try Again.");
					loginDao.failLock(refUPojo);
					
					if (loginDao.islocked(refUPojo)) {
						System.out.println("Account locked, please contact adminstrator for support.");
						return;
					}
				}
			}
		
		 //equals whatever user input
		
		
		System.out.println("Welcome, "+loginDao.getName(refUPojo)+".");
		
		
		System.out.println("debug: loginmethod() username: " + refUPojo.getUser() + " password: " + refUPojo.getPassword());
		
		
		/* check user status */
		if (loginDao.isadmin(refUPojo)) {
			System.out.println("debug: before adminUI");
			adminUI(refUPojo,loginDao);
		} else {
			/* If this is the first time user logins */
			if (loginDao.isfirstlogin(refUPojo)) {
				System.out.println("debug: first login trigger");
				firstlogin(refUPojo, loginDao);
			}
			/* else go in directly */
			System.out.println("debug: UI");
			userUI(refUPojo,loginDao);
			
		}
		
		loginDao.connect.end(loginDao.con);
		return;
		
		
		
	}
		
	//provides the admin UI, including the option select
	private void adminUI(UserPojo refUPojo, Dao loginDao) throws SQLException {
		// TODO Auto-generated method stub
			AUI:
			while (true) {
				System.out.println("Select option number :");
				System.out.println("1 Register New User");
				System.out.println("2 View User List");
				System.out.println("3 Change User Account Status");
				System.out.println("4 Logout");
				String auiinput = consc.next();
				
				consc.nextLine();
				switch (auiinput) {
				case "1":
					register();
					break;
				case "2":
					loginDao.getUserList();
					break;
				case "3":
					System.out.println("Before performing this operation it is advisable to have viewed the latest User List.");
					System.out.println("This operation will swap a user account's status to locked/unlocked from a prior status, selecting via their Serial_No shown in the DB.");
					Case:
					while (true) {
						System.out.println("Enter Serial No of user: ");
						String userid = consc.next();
						consc.nextLine();
						if (loginDao.triggerlock(refUPojo, userid)) {
							break Case;
						}
					}
					break;
									
					
				case "4":
					System.out.println("Exiting to main menu...");
					return;
				default:
					System.out.println("Invalid Option. Select again.");
					break;
				}
			}
	}

	//provides the user UI, such as the logout Y/N
	private void userUI(UserPojo refUPojo, Dao loginDao) {
		// TODO Auto-generated method stub
			while (true) {
				System.out.println("Logout? Y/N");
				String reply = consc.next();
				consc.nextLine();
				if (reply.toUpperCase().equals("Y")) {
					System.out.println("Exiting to main menu...");
					return;
				}
			}
	}


		//if first time user logging in do this
	public void firstlogin (UserPojo refUPojo, Dao loginDao) throws ClassNotFoundException, SQLException {
		System.out.println("Users are required to change password on their first login.");
		
		List<String> uinput = inputPwS(refUPojo);
		loginDao.changePwS(refUPojo, uinput);
		
		
	}
	
	//main forgotpw method
	public void pwrecovery () throws ClassNotFoundException, SQLException {
		UserPojo refUPojo;		

		refUPojo = new UserPojo();
		
		LoginUser:
		while (true) {
			boolean status;
			System.out.println("Enter UserID (email): ");
			String user = consc.next();
			consc.nextLine();
			refUPojo.setUser(user);
			status = loginDao.checkUser(refUPojo);
			if (status) {
				break LoginUser;
			}
			else
				System.out.println("Invalid UserID. Try Again.");
		}
		
		/* if database checks that user is locked */ //**i think this could be integrated with previous segment
		if (loginDao.islocked(refUPojo)) {
			System.out.println("Account locked, please contact adminstrator for support.");
			return;
		}
		
		String pw = loginDao.getPW(refUPojo);
		refUPojo.setPassword(pw);
		List<String> uinput = inputPwS(refUPojo);
		loginDao.changePwS(refUPojo, uinput);
		
		
	}
	
	public List<String> inputPwS (UserPojo refUPojo) {
		String npw;
		Newpass:
		while (true) {
			System.out.println("Enter new password: ");
			npw = consc.next();
			consc.nextLine();
			if (!npw.matches("^.*[^a-zA-Z0-9 ].*$")) { //if pw is alphanumeric
				System.out.println("new password must be alphanumberic");
				continue;
			}
			else if (npw.equals(refUPojo.getPassword())) {
				System.out.println("new password must not be identical to old password");
				continue;
			} else {
				break Newpass;
			}
		} //end newpass while loop
		
		String rtpw;
		RTpass:
		while (true) {
			System.out.println("Retype new password: ");
			rtpw = consc.next();
			consc.nextLine();
			if (!rtpw.equals(npw)) { //if pw is alphanumeric
				System.out.println("passwords must be identical");
				continue;
			} else {
				break RTpass;
			}
		}
		
		
		
		String sqsel;
		SQ:			
		while (true) {
			System.out.println("Select number of your preferred security question");
			System.out.println("1 " + sq.get(0));
			System.out.println("2 " + sq.get(1));
			System.out.println("3 " + sq.get(2));
			String sqinput = consc.next();
			consc.nextLine();
			
			consc.nextLine();
			switch (sqinput) {
			case "1":
				sqsel = sq.get(0);
				break SQ;
			case "2":
				sqsel = sq.get(1);
				break SQ;
			case "3":
				sqsel = sq.get(2);
				break SQ;
			default:
				System.out.println("Invalid Option. Select again.");
				break;
			}
		}
		
		String sqa;
		SA:
		while (true) {
			System.out.println("Enter security answer to question '" + sqsel + "':");
			sqa = consc.nextLine();
			consc.nextLine();
			if(sqa.matches(" ")) {
				System.out.println("Security answers should not contain any white spaces");
			} else {
				break SA;
			}
		}
		
		List<String> uinputs = new ArrayList<String>(); 
		uinputs.add(rtpw);
		uinputs.add(sqsel);
		uinputs.add(sqa);
		
		return uinputs;
		
	}
}
