package com.optimum.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;

import com.optimum.dao.Dao;
import com.optimum.dao.EmailSupport;
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
		sq.add("Favourite Drink");
		}
	
			
	public Dao getLoginDao() {
		return loginDao;
	}

	/* main register method - requests user input for name, NRIC, email, DoB, Mobile in the following order. Each sequence has its own verification step using
	 * support static methods from RegVerifiers in some cases. When complete a List<String> is formulated and passed into Dao for db input.  */
	public void register () throws SQLException {
			System.out.println("For registration, You will be expected to key the following particulars in sequence:");
			System.out.println("Name, NRIC, Date of Birth (DD/MM/YYYY), Email, Mobile");
			
			String name;
			Name:
			while (true) {
				System.out.println("Enter Name:");
				name = consc.nextLine();
				// Checks input for numbers or symbols.
				if (!RegVerifiers.isValidName(name)) {
					System.out.println("Names should not contain numbers or symbols. Try again");
					continue;
				} else {
					name = name.trim().replaceAll("\\s+"," "); //Replaces multiple spaces with just one space. ie "Many       spaces" -> "Many spaces"
//					System.out.println("debug: print name: " + name);
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
				// Perform letter verification of first and last char
//				System.out.println("debug: print input NRIC: " + nric);
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
			
			String email;
			Email:
			while (true) {
				System.out.println("Enter email address: ");
				email = consc.next();
				consc.nextLine();
				// According to BA, need not check valid registered email or domain, so only basic @ symbol is checked
				if (email.contains("@") | email.contains(" ")) {
					email = email.trim();
					break Email;
				} else {
					System.out.println("Invalid email address. Try again");
				}
			}
			
			String dob;
			DOB:
			while (true) {
				System.out.println("Enter date of birth (DD/MM/YYYY):");
				dob = consc.nextLine();
				dob = dob.trim();
				String dobt = dob.replace("/", "");
				// Check valid date using Java DateFormat library. Conversion to mysql YY/MM/DD format is later done at the Dao level. Though it won't matter if done here.
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
			
			
			String mobile;
			Mobile:
			while (true) {
				System.out.println("Enter mobile number: ");
				String input = consc.next();
				consc.nextLine();
				// Checks for number length (must be = 8) and ensure no letters or symbols.
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
		
		loginDao.dbRegister(reghold);
	}
	
	/*main login method, invoked using option 1 from main app.
	 * it will encompass user and pw step by step verification
	 * includes support for firstlogin, user type redirects, consectutive 3 times wrong pw user locking
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
		
		/* get input for pw, setting it to pojo and performing a check from the database, if wrong password, start the count and perform check on acc status */
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
					/* If user fails to correctly enter password, it is logged onto database tagged against user. This persists for the remainder of 
					 * application session and is only reset on successful login or pwrecovery. */
//					loginDao.getAttempts(refUPojo);
					loginDao.failLock(refUPojo);
//					System.out.println("debug: refUPojo.failed after = " + refUPojo.getFailed());
					
					if (loginDao.islocked(refUPojo)) {
						System.out.println("Account locked, please contact adminstrator for support.");
						return;
					}
				}
			}
		
		 
		
		
		System.out.println("Welcome, "+loginDao.getName(refUPojo)+".");
		
		
//		System.out.println("debug: loginmethod() username: " + refUPojo.getUser() + " password: " + refUPojo.getPassword());
		
		
		/* check user status */
		if (loginDao.isadmin(refUPojo)) {
//			System.out.println("debug: before adminUI");
			adminUI(refUPojo,loginDao);
		} else {
			/* If this is the first time user logins */
			if (loginDao.isfirstlogin(refUPojo)) {
//				System.out.println("debug: first login trigger");
				firstlogin(refUPojo, loginDao);
			}
			/* else go in directly */
//			System.out.println("debug: UI");
			loginDao.restoreLock(refUPojo);
			userUI(refUPojo,loginDao);
			
		}

		return;
		
		
		
	}
		
	//provides the admin UI, including the option select
	private void adminUI(UserPojo refUPojo, Dao loginDao) throws SQLException {
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
						if (loginDao.changestatus(refUPojo, userid)) {
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

	/* provides the user UI, such as the logout Y/N. Putting it in seperate method provides room for expansion. */
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


	/* first time login sequence, forces pw change and setting of Security Question and Answer, the primary method is shared with pw recovery hence is
	 * consolidated into its own method: inputPwS(). */
	public void firstlogin (UserPojo refUPojo, Dao loginDao) throws ClassNotFoundException, SQLException {
		System.out.println("Users are required to change password on their first login.");
		
		List<String> uinput = inputPwS(refUPojo);
		loginDao.changePwS(refUPojo, uinput);
		
		
	}
	
	/* method for pw recovery option - enters userid then correct SA to allow user to change password SQ,SA etc. No checks are done vs previous SQ. 
	 * Admin accounts cannot be recovered.*/
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
		
		if (loginDao.isadmin(refUPojo)) {
			System.out.println("Admin accounts cannot be recovered.");
			return;
		} else {
		
			String pw = loginDao.getPW(refUPojo);
			refUPojo.setPassword(pw);
			
			String SQ = loginDao.getSQ(refUPojo);
//			System.out.println("debug: String SQ " + SQ);
			if (SQ != null) {
				System.out.println("Security Question: " + SQ);
				SA:
				while (true) {
					System.out.println("Enter Answer:");
					String sainput = consc.nextLine();
					String actualsa = loginDao.getSA(refUPojo);
					
					
					if (sainput.equalsIgnoreCase(actualsa)) {
						List<String> uinput = inputPwS(refUPojo);
						loginDao.changePwS(refUPojo, uinput);
						loginDao.restoreLock(refUPojo);
						break SA;
					} else {
						System.out.println("incorrect Security Answer. Try Again");
					}
				}
				
			} else {
				System.out.println("Refer to email for information about the account's current password.");
			}
		
		
		
		}
	}
	
	/* Consolidated this into seperate method since both firstlogin() and pwrecovery() will invoke this process of setting new pw and SQ/SA */
	public List<String> inputPwS (UserPojo refUPojo) {
		String npw;
		Newpass:
		while (true) {
			System.out.println("Enter new password: ");
			npw = consc.next();
			consc.nextLine();
			if (RegVerifiers.isValidPW(npw) & !npw.equals(refUPojo.getPassword())) { //PW verification, ensure no symbols and != old password
				break Newpass;
			} else {
				System.out.println("new password must not contain symbols or be same as current password");
				continue;
				
				
			}
		} //end newpass while loop
		
		String rtpw;
		RTpass:
		while (true) {
			System.out.println("Retype new password: ");
			rtpw = consc.next();
			consc.nextLine();
			if (!rtpw.equals(npw)) { //PW verification: ensure same as just typed pw.
				System.out.println("passwords must be identical");
				continue;
			} else {
				break RTpass;
			}
		}
		
		
		
		String sqsel;
		SQ:			
		while (true) {
			System.out.println("Select number of your preferred security question"); //The list is preconstructed at the Controller constructor. Maybe can do it elsewhere
			System.out.println("1 " + sq.get(0));
			System.out.println("2 " + sq.get(1));
			System.out.println("3 " + sq.get(2));
			String sqinput = consc.next();
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
			System.out.println("Enter 1 word security answer to question '" + sqsel + "':");
			sqa = consc.nextLine();
//			System.out.println("debug print sqa: " + sqa);
			if(sqa.contains(" ")) {
				System.out.println("Security answers should not contain any white spaces");
				continue;
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
