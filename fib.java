package pa_3;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Scanner;



public class fib {

	Scanner scan = new Scanner(System.in);
	Scanner scann = new Scanner(System.in);
	
	
	public fib()
	{
		boolean keepGoing = true;
		while(keepGoing)
		{
		System.out.println("Please enter (R) if you'd like to use the Recursive Fib Algorithm");
		System.out.println("or enter (D) if you'd like to use the Dynamic Fib Algorithm");	
		System.out.println("or enter (Q) to quit.");
		String algType = scann.next();
		if(algType.equalsIgnoreCase("Q"))
		{
			System.out.println("Thank you for using my program!");
			keepGoing = false;
			break;
		}
		System.out.println("Please enter a positive integer value you'd like the fibonacci number to: ");
		int num = scan.nextInt();
		
		if(algType.equalsIgnoreCase("R"))
		{
			long startTime = System.nanoTime();
			System.out.println("The value for " + num + " is: " + fibRec(num));
			long estimatedTime = System.nanoTime() - startTime;
			double seconds = (double)estimatedTime / 1_000_000_000.0;
			System.out.println("The time taken to complete the recursive algorithm for valued " + num + " was: " + seconds + " seconds.\n\n");
		}
		else if(algType.equalsIgnoreCase("D"))
		{
			long startTime = System.nanoTime();
			HashMap<Integer, BigInteger> memorized = new HashMap<Integer, BigInteger>();
			System.out.println("The value for " + num + " is: " + fibDynamic(num, memorized));
			long estimatedTime = System.nanoTime() - startTime;
			double seconds = (double)estimatedTime / 1_000_000_000.0;
			System.out.println("The time taken to complete the dynamic algorithm for value " + num + " was: " + seconds + " seconds.\n\n");
		}
		}
	}
	
	
	
	//This is the recursive form of the fib algorithm
	 public static long fibRec (int i)
	    {
	        if (i == 0) return 0;
	        if (i<= 2) return 1;

	        long fibTerm = fibRec(i - 1) + fibRec(i - 2);
	        return fibTerm;
	    }
	
	//This is the dynamic programming version of the fib algorithm using a hashmap for memorization
	 private static BigInteger fibDynamic(int n, HashMap<Integer, BigInteger> memorized) {
	        if (memorized.containsKey(n)) {
	            return memorized.get(n);
	        }
	        if (n <= 0) {
	             return BigInteger.ZERO;
	        }
	        if (n <= 2) {
	            return BigInteger.ONE;
	        } else {
	            BigInteger  febonani = fibDynamic(n - 1, memorized).add (fibDynamic(n - 2, memorized));
	             memorized.put(n, febonani);
	            return febonani;
	        }
	    }
	
	
	public static void main(String[] args)
	{
	new fib();
	}

}
