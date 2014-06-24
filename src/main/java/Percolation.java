/**
 * Created by Qubo Song on 6/23/2014.
 */
public class Percolation {
    private int[][] site;
    private int size;
    private int top;
    private int bottom;
    private WeightedQuickUnionUF uf;

    // create N-by-N grid, with all sites blocked
    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException();
        }
        size = N;
        top = size * size;
        bottom = size * size + 1;
        site = new int[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                site[i][j] = 0;
            }
        }

        uf = new WeightedQuickUnionUF(size * size + 2);
    }

    // open site (row i, column j) if it is not already
    public void open(int i, int j) {
        if ((i < 1 || i > size) || (j < 1 || j > size)) {
            throw new java.lang.IndexOutOfBoundsException();
        }

        int lower = i - 1;
        int upper = j - 1;

        int siteIndex = lower * size + upper;

        if (!isOpen(i, j)) {
            site[lower][upper] = 1;
            if (i == 1) {
                uf.union(siteIndex, top);
            } else {
                if (isOpen(i - 1, j)) {
                    uf.union(siteIndex, (lower - 1) * size + upper);
                }
            }

            if (i == size) {
                uf.union(siteIndex, bottom);
            } else {
                if (isOpen(i + 1, j)) {
                    uf.union(siteIndex, (lower + 1) * size + upper);
                }
            }

            if (j > 1) {
                if (isOpen(i, j - 1)) {
                    uf.union(siteIndex, lower * size + (upper - 1));
                }
            }

            if (j < size) {
                if (isOpen(i, j + 1)) {
                    uf.union(siteIndex, lower * size + (upper + 1));
                }
            }
        }
    }

    // is site (row i, column j) open?
    public boolean isOpen(int i, int j) {
        if ((i < 1 || i > size) || (j < 1 || j > size)) {
            throw new java.lang.IndexOutOfBoundsException();
        }

        int lower = i - 1;
        int upper = j - 1;

        return site[lower][upper] == 1;
    }

    // is site (row i, column j) full?
    public boolean isFull(int i, int j) {
        if ((i < 1 || i > size) || (j < 1 || j > size)) {
            throw new java.lang.IndexOutOfBoundsException();
        }

        int lower = i - 1;
        int upper = j - 1;

        int siteIndex = lower * size + upper;

        return uf.connected(top, siteIndex);
    }

    public boolean percolates() {             // does the system percolate?
        return uf.connected(top, bottom);
    }

}
