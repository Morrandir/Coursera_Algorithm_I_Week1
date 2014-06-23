/**
 * Created by Qubo Song on 6/23/2014.
 */

public class PercolationStats {
    private int size;
    private int count;
    private double pThreshold[];
    private double mean = -1;
    private double stddev = -1;
    private double confLo;
    private double confHi;

    public PercolationStats(int N, int T) {    // perform T independent computational experiments on an N-by-N grid
        if(N <= 0 || T <= 0) {
            throw new java.lang.IllegalArgumentException();
        }
        int i, j;
        size = N;
        count = T;

        pThreshold = new double[count];

        for (int t = 0; t < count; t++) {
            Percolation p = new Percolation(size);
            pThreshold[t] = 0;

            for (int n = 0; n < size * size; n++) {
                do {
                    i = StdRandom.uniform(size);
                    j = StdRandom.uniform(size);
                } while(p.isOpen(i, j));
                p.open(i, j);
                pThreshold[t] += 1;
                if(p.percolates()) {
                    break;
                }
            }
            pThreshold[t] /= size * size;
        }
    }
    public double mean() {                     // sample mean of percolation threshold
        double sum = 0;
        for(int i = 0; i < count; i++) {
            sum += pThreshold[i];
        }
        return sum / count;
    }

    public double stddev() {                   // sample standard deviation of percolation threshold
        double deviation = 0;
        for(int i = 0; i < count; i++) {
            deviation += Math.pow((pThreshold[i] - mean), 2);
        }
        return Math.sqrt(deviation / (count - 1));
    }

    public double confidenceLo() {             // returns lower bound of the 95% confidence interval
        return (mean - 1.96 * stddev / Math.sqrt(count));
    }

    public double confidenceHi() {             // returns upper bound of the 95% confidence interval
        return (mean + 1.96 * stddev / Math.sqrt(count));
    }

    public static void main(String[] args) {   // test client, described below
        PercolationStats ps = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
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
    }
}
