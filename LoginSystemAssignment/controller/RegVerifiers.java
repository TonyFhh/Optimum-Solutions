package com.optimum.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/** This class will contain several static methods to verify the inputs during registration are valid or support dao methods
 * Will hopefully reduce the already present bloat within the controller class */
public class RegVerifiers {
	
	/* Check valid name, make sure doesnt contain number, symbols and whitespaces*/
	static boolean isValidName (String input) {
		char[] chars = input.toCharArray();

	    for (char c : chars) {
	        if(!Character.isLetter(c) & !Character.isSpaceChar(c)) {
	            return false;
	        }
	    }

	    return true;
	}
	
	/* Check, make sure no symbols */
	static boolean isValidPW (String input) {
		char[] chars = input.toCharArray();

	    for (char c : chars) {
	        if(!Character.isLetter(c) & !Character.isDigit(c)) {
	            return false;
	        }
	    }

	    return true;
	}
	
	/* Check valid date using DateFormat java class*/
	public static boolean isValidDate(String date) 
	{
	        try {
	            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	            df.setLenient(false);
	            df.parse(date);
	            return true;
	        } catch (ParseException e) {
	        	System.out.println("debug: Exception");
	            return false;
	        }
	}
	
	/* Convert DD/MM/YYYY to mySQL YYYY/MM/DD format */
	public static String convertToSqlDate(String input) {
		DateFormat originalFormat = new SimpleDateFormat("dd/MM/yyyy");
		DateFormat targetFormat = new SimpleDateFormat("yyyy/MM/dd");
		originalFormat.setLenient(false);
		targetFormat.setLenient(false);
		Date date = null;
		   
		try {
			date = originalFormat.parse(input);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		    
		String output = targetFormat.format(date);
		return output;
	}
}
