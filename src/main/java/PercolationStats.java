/**
 * Created by Qubo Song on 6/23/2014.
 */
public class PercolationStats {
    private double pThreshold[];

    public PercolationStats(int N, int T) {    // perform T independent computational experiments on an N-by-N grid
        if(N <= 0 || T <= 0) {
            throw new java.lang.IllegalArgumentException();
        }

        pThreshold = new double[T];

        for (int t = 0; t < T; t++) {
            Percolation p = new Percolation(N);
            pThreshold[t] = 0;

            for (int n = 0; n < N * N; n++) {
                int i = StdRandom.uniform(N);
                int j = StdRandom.uniform(N);
                p.open(i, j);
                pThreshold[t] += 1;
                if(p.percolates()) {
                    break;
                }
            }
            pThreshold[t] /= N * N;
        }
    }
    public double mean() {                     // sample mean of percolation threshold
        return 0;
    }

    public double stddev() {                   // sample standard deviation of percolation threshold
        return 0;
    }

    public double confidenceLo() {             // returns lower bound of the 95% confidence interval
        return 0;
    }

    public double confidenceHi() {             // returns upper bound of the 95% confidence interval
        return 0;
    }

    public static void main(String[] args) {   // test client, described below
        PercolationStats ps = new PercolationStats(8, 8);
    }
}
