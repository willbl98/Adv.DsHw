package helperclasses;

// Class to manage/contain the information about each alg's test runs for file output
public class TestResult {
    private long fib_num; // Calculated Fibonacci number
    private long time_rec; // Recursive Time taken in ns
    private long time_dp; // DP Time taken in ns
    private final int n; // Number given insert in Fib alg
    private double val1; // value of (2^n)/2
    private double val2; // value of t1/t2

    public TestResult(long fib_num, long time_rec, long time_dp, int n) {
        this.fib_num = fib_num;
        this.time_rec = time_rec;
        this.time_dp = time_dp;
        this.n = n;
        this.val1 = (Math.pow(2, n))/2;
        this.val2 = (double)time_rec / time_dp;
    }

    // Convert to scientific notation and round to nearest int
    private String toSciNot(double val) {
        double rounded = Math.round(val);
        return String.format("%.0e", rounded);
    }

    // Convert nsec to sec for 40 <= values < 55 and to minutes for the rest, since the recursive alg can take
    // ages and ns is far to small for some values
    private String timeConverter() {
        String convertedTime;
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
        String convertedTime = timeConverter();
        return n + "," + fib_num + "," + convertedTime + "," + time_dp + "," + val1
                + "," + toSciNot(val2);
    }
}