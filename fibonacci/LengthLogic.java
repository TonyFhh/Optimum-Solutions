package fibonacci;

public class LengthLogic {

	void mainseq(int length) {
		int[] fib = new int[length];
		
		//generates array with 
		fib[0] = 0;
		if (length >= 1) {
			fib[1] = 1;
		}
		if (length >1) {
			for (int i = 2; i < fib.length; i++) {
				fib[i] = fib[i-1] + fib[i-2];
			}
		}
		
		
		//prints the result
		System.out.print("{ ");
		for (int i = 0; i < fib.length; i++) {
			System.out.print(fib[i] + " ");
		}
		System.out.println("}");
	}

}
