package fibonacci;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Fibonacci {
	
	static boolean again() {
		Scanner bool = new Scanner(System.in);
		System.out.println("Again? y/n");
		String input = bool.next();
		if (input.toLowerCase().equals("y")) {
			return true;
		}
		else if (input.toLowerCase().equals("n")) {
			return false;
		}
		else {
			System.out.println("Input unclear, interpreted to be \"no\".");
			return false;
		}
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//0,1,1,2,3,5,,8,13,21,34,55,89,144
		String input;
		int value = 0;
		System.out.println("Fibonacci Number Display");
		While:
		while(true) {
			Scanner sc = new Scanner(System.in);
			System.out.println("Select either length or maximum value to display:"); //prompt user for input
			
			System.out.println("1. Length");
			System.out.println("2. Value");
			
			input = sc.next();
			if (input.equals("1") || input.toLowerCase().equals("length")) { //if user selected option 1
				System.out.println("Enter sequence length:");
				try {
					value = sc.nextInt(); //check if it is number..
					if (value != 0) {
						LengthLogic lmain = new LengthLogic();
						lmain.mainseq(value); //run main seq
						if(!again()) {
							System.out.println("Thanks for using Fiboacci Number Display!");
							break While;
						}
					}	
				} catch (InputMismatchException ex) { //if not then..
					System.out.println("Invalid input, try again");
					//System.out.println("Please select one of the 2 options to displaye");
				}
			}
			else if (input.equals("2") || input.toLowerCase().equals("value")) { //option 2 stuff
				System.out.println("Enter max value to display up to:");
				try {
					value = sc.nextInt();
					MaxValue vmain = new MaxValue();
					vmain.mainseq(value);
					if(!again()) {
						System.out.println("Thanks for using Fiboacci Number Display!");
						break While;
					}
					
				} catch (InputMismatchException ex) {
					System.out.println("Invalid input, try again.");
					//System.out.println("Please select one of the 2 options to displaye");
					
				}
			
			
			}	
		} //end while
	}	//end main

} //end class
