package helperclasses;

// Helper class to pass along the Fib answer and time take.
public class AlgorithmResult {
    private final long fibNum;
    private final long time;

    public AlgorithmResult(long fibNum, long time) {
        this.fibNum = fibNum;
        this.time = time;
    }

    public long getFibNum() {
        return fibNum;
    }

    public long getTime() {
        return time;
    }
}
