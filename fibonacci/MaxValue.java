package fibonacci;

public class MaxValue {

	void mainseq(int max) {
		// TODO Auto-generated method stub
		int fib[] = new int[max*2]; //since java doesn't support dynamic array, we just use a random value that can cover the lower ranges, less than ideal for high values though;
		
		fib[0] = 0;
		if (max >= 1) {
			fib[1] = 1;
		}
		if (max >1) {
			for (int i = 2; i < fib.length; i++) {
				fib[i] = fib[i-1] + fib[i-2];
		}
		}
		
		System.out.print("{ ");
		for (int i = 0; i < fib.length; i++) {
			if (fib[i] >= max) break;
			System.out.print(fib[i] + " ");
			
		}
		System.out.println("}");
		
		
			
		}
	}


