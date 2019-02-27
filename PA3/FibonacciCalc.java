package edu.pa3;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static java.util.Map.entry;

public class FibonacciCalc {

    // Temporarily commented out for time
    //private static final int[] FIB_NUMS = {10, 20, 30, 35, 40, 45, 50, 55};
    private static final int[] FIB_NUMS = {10, 20, 30, 35, 40, 45};

    // Init associative array (hashmap) for dp
    private static final Map<Integer, Long> DYNAMIC_PROG_MAP = new HashMap<>();

    // CSV info
    private static final String FILENAME = "Fibonacci_Time.csv";
    private static final String[] HEADERS = {
            "n", "F(n)", "T1: Recursive Algorithm (ns),(s), or (m)", "T2: DP Algorithm (ns)", "(2^n)/n", "T1/T2"};

    // Run
    public static void main(String[] args) {
        //initialize DYNAMIC_PROG_MAP
        initDPArray();
        
        // Start interactive console with user
        fibConsole();

        // ask user if they would like to run the tests in the given PA.  Warn the user that some values take
        // a very long time to process.
        System.out.println("\nPerform new calculations for the assigned PA values and save to file?\nAssigned Values: " +
                Arrays.toString(FIB_NUMS) + "\nCAUTION! Takes LONG Time [y/n]  ");
        var userChoice = new Scanner(System.in).next();
        if (userChoice.equalsIgnoreCase("Y")) {
            // Run Tests
            var results = runTests();
            try {
                writeToCSV(results);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // Exit
        else {
            System.out.println("Exiting");
        }
    }

    // Measure execution time in (ns)
    private static long calcTime(Algorithm fibAlg, int n) {
        var start = System.nanoTime();
        fibAlg.algorithm(n);
        var stop = System.nanoTime();
        return stop - start;
    }

    // Run tests on Fib algs
    private static ArrayList<TestResults> runTests() {
        var results = new ArrayList<TestResults>();
        for (var i : FIB_NUMS) {
            var time_rec = calcTime(FibonacciCalc::recurFib, i);
            var time_dp = calcTime(FibonacciCalc::dpFib, i);
            // Grab Fib number from the dp array, DYNAMIC_PROG_MAP
            results.add(new TestResults(DYNAMIC_PROG_MAP.get(i), time_rec, time_dp, i));
        }
        return results;
    }

    // Calculates Fibonacci Number using Recursion
    private static long recurFib(int n) {
        return n <= 1 ? 1 : recurFib(n - 1) + recurFib(n - 2);
    }

    // Calculates Fibonacci Number using Dynamic Programming
    // Results of calculations saved to the DYNAMIC_PROG_MAP dictionary
    private static long dpFib(int n) {
        if (!DYNAMIC_PROG_MAP.containsKey(n)) for (var i = 2; i <= n; i++) {
            var val = DYNAMIC_PROG_MAP.get(i - 1) + DYNAMIC_PROG_MAP.get(i - 2);
            DYNAMIC_PROG_MAP.put(i, val);
        }
        return DYNAMIC_PROG_MAP.get(n);
    }

    // Output results to csv file
    private static void writeToCSV(ArrayList<TestResults> results) throws IOException {
        var writer = new PrintWriter(FILENAME, StandardCharsets.UTF_8);
        writer.println(String.join(",", HEADERS));
        for (var result : results) {
            writer.println(result.toString());
        }
        writer.close();
    }

    // Asks the user to select an algorithm to use to fin Fibonacci numbers.  Also displays execution time.
    private static void fibConsole() {
        var scan = new Scanner(System.in);
        while (true) {
            System.out.println("Please enter (R) if you'd like to use the Recursive Fib Algorithm \n" +
                    "or enter (D) if you'd like to use the Dynamic Fib Algorithm \n" +
                    "or enter (Q) to quit.");

            var algType = scan.next();
            if (algType.equalsIgnoreCase("Q")) {
                System.out.println("Thank you for using my program!");
                return;
            }

            System.out.println("Please enter a positive integer value you'd like the fibonacci number to: ");
            var num = scan.nextInt();

            if (algType.equalsIgnoreCase("R")) {
                var startTime = System.nanoTime();
                System.out.println("The value for " + num + " is: " + recurFib(num));
                var estimatedTime = System.nanoTime() - startTime;
                var seconds = (double) estimatedTime / 1_000_000_000.0;
                System.out.println("The time taken to complete the recursive algorithm for valued "
                        + num + " was: " + seconds + " seconds.\n\n");
            } else if (algType.equalsIgnoreCase("D")) {
                var startTime = System.nanoTime();
                System.out.println("The value for " + num + " is: " + dpFib(num));
                var estimatedTime = System.nanoTime() - startTime;
                var seconds = (double) estimatedTime / 1_000_000_000.0;
                System.out.println("The time taken to complete the dynamic algorithm for value "
                        + num + " was: " + seconds + " seconds.\n\n");
            }
        }
    }

    //initialize DYNAMIC_PROG_MAP
    private static void initDPArray() {
        DYNAMIC_PROG_MAP.put(0, (long) 1);
        DYNAMIC_PROG_MAP.put(1, (long) 1);
    }

    // Allow passing fib alg by reference to calcTime
    private interface Algorithm {
        long algorithm(int n);
    }

    // Contains the information about each alg's test run for file output
    private static class TestResults {
        private final long fib_num; // Calculated Fibonacci number
        private long time_rec; // Recursive Time taken in ns
        private final long time_dp; // DP Time taken in ns
        private final int n; // Number given insert in Fib alg
        private final double val1; // value of (2^n)/2
        private final long val2; // value of t1/t2

        TestResults(long fib_num, long time_rec, long time_dp, int n) {
            this.fib_num = fib_num;
            this.time_rec = time_rec;
            this.time_dp = time_dp;
            this.n = n;
            this.val1 = Math.pow(2, n);
            this.val2 = time_rec / time_dp;
        }

        // Convert to scientific notation and round to nearest int
        private String toSciNot(double val) {
            var rounded = Math.round(val);
            return String.format("%.0e", (double) rounded);
        }

        // Convert nsec to sec for 40 <= values < 55 and to minutes for the rest, since the recursive alg can take
        // ages and ns is far to small for some values
        private String timeConverter() {
            var convertedTime = "";
            if (n >= 40) {
                if (n > 50) {
                    convertedTime = (time_rec / 1_000_000_000.0) / (60) + "(min)";
                } else {
                    convertedTime = time_rec / 1_000_000_000.0 + "(s)";
                }
            } else {
                convertedTime = time_rec + "(ns)";
            }
            return convertedTime;
        }

        @Override
        public String toString() {
            var convertedTime = timeConverter();
            return n + "," + fib_num + "," + convertedTime + "," + time_dp + "," + val1
                    + "," + toSciNot(val2);
        }
    }
}
