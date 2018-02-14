package assignment2;

class array {
	
}


public class Q2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//question 2a
		int checkcounter = 0;
		int a[] = new int [] {0, 1,2, 4, 6, 3,7,8,9,10,11};
		
		for (int i = 1; i<=a.length; i++) {
			if (checkcounter != 0) {
			checkcounter = 0;
			}
			
			for (int j = 0; j<a.length; j++) {
				if ((i) == a[j]) {
					checkcounter++;
				}
			}
			if (checkcounter == 0) {
				System.out.println("2a: missing number : "+i);
			}
				
		} //end q2a
		
		//q2b
		
		int checkb = 0;
		
		int b[] = new int [] {5,2,9,15,2,7,7,18,15,5};
		
		for (int i = 0; i<=b.length && checkb != 1; i++ ) {
			if (checkb != 2 || checkb != 1) {				
			checkb = 0;
			}
			
			for (int j = 0; j<b.length; j++) {
				if (b[i] == b[j]) {
					checkb++;
				}
			}
			if (checkb == 1) {
				System.out.println("2b: first missing number : "+b[i]);
			}
				
		} //end q2b
		
		//q2c: only know bubble sorting
		
		int c[] = new int [] {5,2,9,15,2,7,7,18,15,5};
		int store;
		for (int i = 0; i<(c.length-1); i++) {
			for (int j = i+1; j<(c.length); j++) {
				if (c[i] > c[j]) {
					store = c[i];
					c[i] = c[j];
					c[j] = store;
				}
			}
		}
		System.out.print("2c: { ");
		for (int i = 0; i<c.length; i++) {
			System.out.print(c[i]+" ");
		}
		System.out.println("}");
		//end of q2c
		
		//q2d
		
		int checkd = 0;
		
		int d[] = new int [] {16,7,89,20,7,99,21,56,12,10};
		
		for (int i = 0; i<=d.length && checkd != 1; i++ ) {
			
			for (int j = i+1; j<d.length; j++) {
				if (d[i] == d[j]) {
					checkd++;
				}
			}
			if (checkd == 1) {
				System.out.println("2d: duplicate number : "+d[i]);
			}
				
		} //end q2d
		
		//q2e
		
		int e[] = new int [] {16,4,89,20,7,99,21,56,12,10};
		
		int lnumber = e[0];
		int snumber = e[0];
		
		for (int i = 0; i<e.length; i++ ) {
			
			if (e[i] > lnumber) {
				lnumber = e[i];
			}
			if (e[i] < snumber) {
				snumber = e[i];
			}
		}
		System.out.println("2e: largest number : "+lnumber +"; smallest number : "+ snumber);
			
				
		 //end q2e
		
	
	
		//q2f:
		
		int duplcount = 0;
		int fstore = 0;
		int l = 0;
		int f[] = new int [] {16,4,20,20,7,99,21,56,12,10};
		
		System.out.print("2f: { ");
		for (int i = 0; i<f.length-1; i++) {
			for (int j = i+1; j<f.length; j++) {
				if (f[i] == (f[j])) {
					fstore = j;
					duplcount++;
				}
			}
				
			
		}
		int f2[] = new int [f.length-duplcount]; //formulate new array based on length of old one minus no. of duplicates
		for (int k = 0; k<f2.length; k++) {
			if (k == fstore) { //if k = previously stored index, old array[k] is skipped to the next one.
			l++;
			}
			f2[k] = f[l];
			l++;
			System.out.print(f2[k] + " ");
		}
			
		System.out.println("}");
		//end of q2f
		
			
	} //end of main

} //end of public class
