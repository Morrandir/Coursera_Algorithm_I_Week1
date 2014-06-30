/**
 * Created by Qubo Song on 6/23/2014.
 */

public class PercolationStats {
    private int count;
    private double[] pThreshold;
    private double mean = -1;
    private double stddev = -1;
    private double confLo = -1;
    private double confHi = -1;

    // perform T independent computational experiments on an N-by-N grid
    public PercolationStats(int N, int T) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException();
        }
        int i, j;
        count = T;

        pThreshold = new double[count];

        for (int t = 0; t < count; t++) {
            Percolation p = new Percolation(N);
            pThreshold[t] = 0;

            for (int n = 0; n < N * N; n++) {
                do {
                    i = StdRandom.uniform(1, N + 1);
                    j = StdRandom.uniform(1, N + 1);
                } while(p.isOpen(i, j));
                p.open(i, j);
                pThreshold[t] += 1;
                if (p.percolates()) {
                    break;
                }
            }
            pThreshold[t] /= N * N;
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        double sum = 0;
        for (int i = 0; i < count; i++) {
            sum += pThreshold[i];
        }
        return sum / count;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        double deviation = 0;

        if (mean == -1) {
            mean = mean();
        }

        for (int i = 0; i < count; i++) {
            deviation += (pThreshold[i] - mean) * (pThreshold[i] - mean);
        }
        return Math.sqrt(deviation / (count - 1));
    }

    // returns lower bound of the 95% confidence interval
    public double confidenceLo() {
        if (mean == -1) {
            mean = mean();
        }

        if (stddev == -1) {
            stddev = stddev();
        }

        return (mean - 1.96 * stddev / Math.sqrt(count));
    }

    // returns upper bound of the 95% confidence interval
    public double confidenceHi() {
        if (mean == -1) {
            mean = mean();
        }

        if (stddev == -1) {
            stddev = stddev();
        }

        return (mean + 1.96 * stddev / Math.sqrt(count));
    }

    // test client, described below
    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        Stopwatch sw = new Stopwatch();
        PercolationStats ps = new PercolationStats(N, T);
        ps.mean = ps.mean();
        ps.stddev = ps.stddev();
        ps.confLo = ps.confidenceLo();
        ps.confHi = ps.confidenceHi();
        System.out.print("mean                      = ");
        System.out.print(ps.mean);
        System.out.print("\n");
        System.out.print("stddev                    = ");
        System.out.print(ps.stddev);
        System.out.print("\n");
        System.out.print("95% confidence interval   = ");
        System.out.print(ps.confLo);
        System.out.print(", ");
        System.out.print(ps.confHi);
        System.out.print("\n");

        System.out.print("elapsed time              = ");
        System.out.print(sw.elapsedTime());
    }
}
