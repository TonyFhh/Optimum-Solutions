package assignCalc;

import java.util.Scanner;

public class CalculatorApplication {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		System.out.println("Welcome to my Calculator Application");
		System.out.println("This Simple Calculator will perform any arithmetic operations you key in, supporting ( and { parentheses.");
		System.out.println("However, do try to key in the CORRECT syntax:");
		CalculatorLogic CL = new CalculatorLogic();
		String input = sc.nextLine();
		System.out.println(CL.mainfunction(input));
	}

}
