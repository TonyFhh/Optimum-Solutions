package assignment2;

public class Q1 {
public static void main(String[] args) {
	
	String firstgap = " ";
	String secondgap = " ";
	String thirdentry = "x";
	String onespace = " ";
	for (int i = 0; i < 5; i++) { //row control
		for (int j = 0; j <= i; j++) {
		System.out.print("*");
		System.out.print(onespace); //actually obselete thinking back after completion
		}
		for (int g = 0; g <= (20-(2*i)); g++ ) {
		System.out.print(firstgap);
		}
		for (int k = 0; k <= i; k++) {
			System.out.print((k+1));
			System.out.print(onespace);
			}
		for (int h = 0; h <= (20-(3*i)); h++ ) {
			System.out.print(secondgap);
			}
		for (int l = 0; l <= i; l++) {
			System.out.print(thirdentry);
			System.out.print(onespace);
			}
		System.out.println();
		
	}
	System.out.println();
	
	for (int i = 0; i < 5; i++) { //row control
		for (int j = 0; j <= i; j++) { //1st column print
		System.out.print((i+1));
		System.out.print(onespace);
		}
		for (int g = 0; g <= (20-(2*i)); g++ ) {
		System.out.print(firstgap);
		}
		for (int k = 0; k >= -i; k--) { //second column print
			System.out.print((k+5));
			System.out.print(onespace);
			}
		for (int h = 0; h <= (16-(2*i)); h++ ) {
			System.out.print(secondgap);
			}
		
		for (int s = 1; s <= i; s++) { //3rd column print
			System.out.print(s);
			System.out.print(onespace);
			}
		System.out.print(i+1);
		System.out.print(onespace);
		for (int s = -1; s >= -i; s--) {
			System.out.print(i+1+s);
			System.out.print(onespace);
			}
		System.out.println();
		
	}
} 
}
