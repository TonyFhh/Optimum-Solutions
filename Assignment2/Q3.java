package assignment2;

import java.io.IOException;
import java.util.Scanner;

public class Q3 {
	public static void main(String[] args) throws IOException {
		
		//q3a:
		
		String a[] = new String [] {"java","oracle","python","scala","hibernate"};
		
		System.out.print("3a: { ");
		for (int i = a.length-1; i>=0; i--) {
				System.out.print(a[i] + " ");
			}
		System.out.println("}");
		//end of q3a
		
		//q3b
		
		String b = "oracle";
		String b2[] = new String [b.length()];
		System.out.print("3b: { ");
		for (int i = 0; i < b.length(); i++) { //formulate loop to extract each letter of the string
		char bchar = b.charAt(b.length() - 1 -i); 
		b2[i] = String.valueOf(bchar); //convert char to string
		System.out.print(b2[i]);
		}
		System.out.println(" }");
		//end of q3b
		
		//q3c:
		
		int duplcount = 0;
		int store = 0;
		int l = 0;
		String c[] = new String [] {"java","hibernate","python","scala","hibernate"};
		
		System.out.print("3c: { ");
		for (int i = 0; i<c.length-1; i++) {
			for (int j = i+1; j<c.length; j++) { //use bubble sort like loop to find duplicates
				if (c[i].equals(c[j])) {
					store = j; //this only works if there is one duplicate though...
					duplcount++; //var used to track number of duplicates in the array.
				}
			}
				
			
		}
		String c2[] = new String [c.length-duplcount]; //formulate new array based on length of old one minus no. of duplicates
		for (int k = 0; k<c2.length; k++) {
			if (k == store) { //if k = previously stored index, old array[k] is skipped to the next one.
			l++;
			}
			c2[k] = c[l];
			l++;
			System.out.print(c2[k] + " ");
		}
			
		System.out.println("}");
		//end of q3c
		
		//q3d
		Scanner sc = new Scanner(System.in);
		System.out.println("3d: Check Armstrong number");
		System.out.println("Enter number ");
		String entry = sc.next();
		entry = String.valueOf(entry);
		char da = entry.charAt(0);
		char db = entry.charAt(1);
		char dc = entry.charAt(2);
		
		int dae = Character.getNumericValue(da);
		int dbe = Character.getNumericValue(db);
		int dce = Character.getNumericValue(dc);
		
		System.out.println(dae + "^3 + "+ dbe + "^3 + " + dce + "^3 = "+ (Math.pow(dae, 3) + Math.pow(dbe, 3) + Math.pow(dce, 3)));
		if ((Math.pow(dae, 3) + Math.pow(dbe, 3) + Math.pow(dce, 3)) == Integer.valueOf(entry)) {
			System.out.println(entry + " is an Armstrong number");
		}
		else {
			System.out.println(entry + " is not an Armstrong number");
		}
		//end of q3d
		
		
		
		
		
		
		
	} //end main
} //end class
