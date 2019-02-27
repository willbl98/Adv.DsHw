package edu.pa3;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static java.util.Map.entry;

public class FibonacciCalc {

    // Temporarily commented out for time
    //private static final int[] FIB_NUMS = {10, 20, 30, 35, 40, 45, 50, 55};
    private static final int[] FIB_NUMS = {10, 20, 30, 35, 40};

    // Init associative array (hashmap) for dp
    private static Map<Integer, Long> DYNAMIC_PROG_MAP = new HashMap<>(Map.ofEntries(
            entry(0, (long) 1),
            entry(1, (long) 1)
    ));

    // CSV info
    private static final String FILENAME = "Fibonacci_Time.csv";
    private static final String[] HEADERS = {
            "n", "F(n)", "T1: Recursive Algorithm (s)", "T2: DP Algorithm (ms)", "(2^n)/n", "T1/T2"};

    // Run
    public static void main(String[] args) {
        var results = runTests();
        try {
            writeToCSV(results);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Measure execution time in (ns)
    private static long calcTime(Algorithm algorithm, int n) {
        var start = System.nanoTime();
        algorithm.algorithm(n);
        var stop = System.nanoTime();
        return stop - start;
    }

    // Run tests on
    private static ArrayList<TestResults> runTests() {
        var results = new ArrayList<TestResults>();
        for (var i : FIB_NUMS) {
            var time_rec = calcTime(FibonacciCalc::recurFib, i);
            var time_dp = calcTime(FibonacciCalc::dpFib, i);
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

    // Allow passing fib alg by reference to calcTime
    private interface Algorithm {
        long algorithm(int n);
    }

    // Contains the information about each alg's test run for file output
    private static class TestResults {
        private long fib_num; // Calculated Fibonacci number
        private long time_rec; // Recursive Time taken in ns
        private long time_dp; // DP Time taken in ns
        private int n; // Number given insert in Fib alg
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
            return String.format("%.0e", (double)rounded);
        }

        @Override
        public String toString() {
            return n + "," + fib_num + "," + time_rec + "," + time_dp + "," + val1
                    + "," + toSciNot(val2);
        }
    }
}
