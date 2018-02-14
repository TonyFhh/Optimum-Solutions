package assignment2;

public class Q4 {

	public static void main(String[] args) {
		int [] arr1 = {4,7,3,9,2};
		int [] arr2 = {3,2,12,9,40,32,4};
		
		
		System.out.print("4: Common elements are {");
		for (int i = 0; i<arr1.length; i++) {
			for (int j = 0; j < arr2.length; j++) {
				if (arr1[i] == (arr2[j])) {
					System.out.print(" " + arr1[i]);
				}
			}
		}
		System.out.println("}");
		//end of q4

	}
}
