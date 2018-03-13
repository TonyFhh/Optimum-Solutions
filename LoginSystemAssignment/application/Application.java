package com.optimum.application;

import java.sql.SQLException;
import java.util.Scanner;

import com.optimum.controller.Controller;

/** Login System Utilising Database
 * This application simulates a login system through the use a console. Admin, user accounts are well defined, as are their UI and functions.
 * New users can be registered and written into the database. Various steps of login authentication also reads from database. THe entire application
 * is split among 5 packages as follows:
 * 
 * com.optimum.application - Contains main Application (can be interfaced with other front end UI)
 * com.optimum.controller - main Logical controller featuring supportive class. Involved with main logic of user input, verification and other misc functions
 * com.optimum.dao - interfaces with mySQL db through jdbc. All methods that execute mySQL commands will be contained here, drawing inputs from controller primarily.
 * com.optimum.connection - starts and end 
 * 
 * 
 * 
 * */

public class Application {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		Controller refController = new Controller();
		
		Scanner appsc = new Scanner(System.in);
		
		while (true) {
			System.out.println("Enter Option Number: ");
			System.out.println("1 Login");
			System.out.println("2 Forget Password");
			System.out.println("3 Exit");
			String input = appsc.next();
			switch (input) {
			case "1":
				refController.loginmethod();
				break;
			case "2":
				refController.pwrecovery();
				break;
			case "3":
				System.out.println("Exiting App");
				refController.getLoginDao().connect.end(refController.getLoginDao().con); //close mysql connection
				return;
			default:
				System.out.println("Invalid Option. Try again");
				continue;
			}
		}
		
		
		
	}

}
