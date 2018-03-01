package assignCalc;

import java.util.*;

public class CalculatorLogic {
	
	String input;
	String[] input2;
	ArrayList<String> inpute;
	
	void parenthesis (int idx) { //this method is for handling bracket terms if any are form encompassing an ans after operator(). Is not merged since its not always called after op
		try {
			if ((inpute.get(idx-2).equals("(")&&inpute.get(idx).equals(")")) || (inpute.get(idx-2).equals("{")&&inpute.get(idx).equals("}")) ) { //check for brackets, curly or round
				try {
					/*we're trying to imitate the 2(40) = 2*40 operation here; if a number preceeds a open bracket, 
					 * open bracket will be replaced by "*", otherwise... */
						Integer.parseInt(inpute.get(idx-3)); 
						inpute.set(idx-2,"*");
						inpute.remove(idx);
					
						
				} catch (IndexOutOfBoundsException|NumberFormatException ex ) {
					/* ...we'll just remove bracket normally */
				inpute.remove(idx-2);
				inpute.remove(idx-1);
				}
			}
			} catch (IndexOutOfBoundsException ex) {} //since we're reaching out to array indexes adjacent to for loop int, this exception can occur near the start/end. This takes care of it.
	} //end parenthesis()
	
	boolean checksyntax (String fterm, String bterm) { //boolean method to check successive operator or missing numbers ie. "++"
		if (bterm.equals("+")||bterm.equals("-")||bterm.equals("*")||bterm.equals("/"))
			return true;
		else if (fterm.equals("+")||fterm.equals("-")||fterm.equals("*")||fterm.equals("/"))
			return true;
		else
			return false; 
	} //end checksyntax()
	
	void operator (int first , int second, int idx) { //performs basic mathematical operations
		String op = inpute.get(idx);
		switch (op) {
		case ("+"):
//debug		System.out.println(first + " " + second +" + case");
			inpute.set(idx, String.valueOf(first+second));
			break;
		case ("-"):
//debug		System.out.println(first + " " + second +" - case");
			inpute.set(idx, String.valueOf(first-second));
			break;
		case ("*"):
//debug		System.out.println(first + " " + second +" * case");
			inpute.set(idx, String.valueOf(first*second));
			break;
		case ("/"):
//debug		System.out.println(first + " " + second +" / case");
			inpute.set(idx, String.valueOf(first/second));
			break;
		default:
		}
		inpute.remove(idx+1);
		inpute.remove(idx-1);
	} //end operator()
	
	String mainfunction(String input) {
		
		input = input.replaceAll("\\s+|=",""); //remove whitespaces and "=" if exist in input
		String[] input2 = input.split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)"); //split the string at string/number boundaries and vice versa
		inpute = new ArrayList(Arrays.asList(input2));
//debug	System.out.println("1: " + Arrays.toString(inpute.toArray()));
		
		/* format arraylist to seperate array elements that contain one or more strings, such as ")+(" into "), +, (" then include them into the array */
		for (int i = 0; i < inpute.size(); i++) {
			try{
				Integer.parseInt(inpute.get(i)); //skip if number found
			} catch (NumberFormatException ex) {
				String handle = inpute.get(i);
				String[] handles = handle.split("");
				if (handles.length > 1) { //if the found string is already single character, this will not run
					int handlevar = i+1;
					inpute.set(i, handles[0]);
					for (int j = 1; j < handles.length; j++) {
						inpute.add(handlevar, handles[j]);
						handlevar++;
					}
				}
			}
		}
		
		/*format arraylist to remove brackets of any number elements that are purely enclosed by 2 brackets, that could be mistake input ie (12) */
		for (int i = 0; i < inpute.size(); i++) { 
			try{
				Integer.parseInt(inpute.get(i)); //skip if number found
				try {
//debug					System.out.println("2nd try i = " + i + " i-1 = " + inpute.get(i-1)+ " i+1 = " + inpute.get(i+1));
					if ((inpute.get(i-1).equals("(")&&inpute.get(i+1).equals(")")) || (inpute.get(i-1).equals("{")&&inpute.get(i+1).equals("}")) ) {
						
						try {
							/*we're trying to replace examples like 2(40) = 2*40 operation here; if a number preceeds a open bracket, 
							 * the following casting line will execute, otherwise... */
								Integer.parseInt(inpute.get(i-2));
								inpute.set(i-1,"*");
								inpute.remove(i+1);
							
								
						} catch (IndexOutOfBoundsException|NumberFormatException ex ) {
							/* we'll just remove bracket normally */
							inpute.remove(i-1);
							inpute.remove(i);
						}
					}
				} catch (IndexOutOfBoundsException exc) {}
			} catch (NumberFormatException ex) {}
		}
		
//debug		System.out.println("2: " + Arrays.toString(inpute.toArray()));
		int search1 = 0;
		int search2 = 0;
		int infloopvar = 0;
		
		/* Main while loop that computes whatever input user provides */
		
		While: //while loop identifier, useful for break commands later in.
		while (inpute.size() > 2 & infloopvar <= inpute.size()) { //main while loop
			for (int i = 0; i < inpute.size(); i++) {
				if (inpute.get(i).equals("+")  || inpute.get(i).equals("-")) { //whether + or - operator string is found
					try { //now that we found +-, we check if there are numbers around it
						search1 = Integer.parseInt(inpute.get(i-1));
						search2 = Integer.parseInt(inpute.get(i+1));
						if (inpute.size() < 5) { //if we're up to the last remaining numbers with +- operators, perform this ifelse
							operator(search1,search2,i);
//debug							System.out.println("+-last<5: " + Arrays.toString(inpute.toArray()) + "i= " + i);
							
						}
						try {
							if (((inpute.get(i-2).equals("(")||inpute.get(i+2).equals(")")) || (inpute.get(i-2).equals("{")||inpute.get(i+2).equals("}"))) 
									&& !(inpute.get(i+2)).equals("*") &&!(inpute.get(i+2)).equals("/") && !(inpute.get(i-2)).equals("*") &&!(inpute.get(i-2)).equals("/")) { //otherwise this will check bracket priority as well as if there is any following * or / operators
								operator(search1,search2,i);
//debug							System.out.println("+-2oop: " + Arrays.toString(inpute.toArray()) + "i= " + i);
								
								parenthesis(i);
//debug							System.out.println("+-2o(: " + Arrays.toString(inpute.toArray()) + "i= " + i);
									}
						} catch (IndexOutOfBoundsException ex) {
						}
						
					} catch (IndexOutOfBoundsException ex) { //this exception will occur if user mistypes syntax where +- occurs at the end or start of array
//debug						System.out.println("+-Arr Syntax Error");
						break While;
					} catch (NumberFormatException ex) {
						if (checksyntax(inpute.get(i-1),inpute.get(i+1))) {
//debug							System.out.println("+-NF Syntax Error");
							break While;
						}
					} //end +- try
				} //end +- if
				
				else if (inpute.get(i).equals("/")  || inpute.get(i).equals("*")) { //whether * or / operator string is found 
					
					try {
						search1 = Integer.parseInt(inpute.get(i-1));
						search2 = Integer.parseInt(inpute.get(i+1));
						operator(search1,search2,i);
						System.out.println("*/op: " + Arrays.toString(inpute.toArray()));
						parenthesis(i);
						System.out.println("*/(: " + Arrays.toString(inpute.toArray()));
						i--; //this ensures that * and / operations are always executed from left to right without * and / order conflicts
						
					} catch (IndexOutOfBoundsException ex) { //this exception will occur if user mistypes syntax where */ occurs at the end or start of array
//debug						System.out.println("*/Arr Syntax Error");
						break While;
					} catch (NumberFormatException ex) {
						if (checksyntax(inpute.get(i-1),inpute.get(i+1))) {
//debug							System.out.println("*/NF Syntax Error");
							break While;
						}
					} //end */ try
					
				} //end if * or / if
			} //end main search loop
			
			infloopvar++; //used to handle any weird exceptions where the array could stay >2 length and cause an infinite loop.
			
			
		} //end while loop
		
		//if we reach this point, what's left could be a long string of + and -, we just evaluate them normally
		for (int i = 0; i < inpute.size(); i++) {
			if (inpute.get(i).equals("+")  || inpute.get(i).equals("-")) { //whether + or - operator string is found
				try { //now that we found +-, we check if there are numbers around it
					search1 = Integer.parseInt(inpute.get(i-1));
					search2 = Integer.parseInt(inpute.get(i+1));
					operator(search1,search2,i);
					i--;
					} catch (IndexOutOfBoundsException ex) { //this exception will occur if user mistypes syntax where +- occurs at the end or start of array. ie "+123-123=?" type of inputs
//debug					System.out.println("+-ool Arr Syntax Error");
					break;
				} catch (NumberFormatException ex) {
					if (checksyntax(inpute.get(i-1),inpute.get(i+1))) {
//debug						System.out.println("+-ool NF Syntax Error");
						break;
					}
				}
			}
		}
		
		
		//providing the answer back to main app
		if (inpute.size() == 1)	 {
			return "Answer: " + input + " = " + Arrays.toString(inpute.toArray());
		}
		// stray brackets or whatever syntax error that managed to escape the main computation algorithm checks may leave the main array with >1 length, usually caused syntax mistypes
		else
			return "Syntax Error";
}
}
