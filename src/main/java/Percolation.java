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

        if ((i < 0 || i > size - 1) || (j < 0 || j > size - 1)) {
            throw new java.lang.IndexOutOfBoundsException();
        }

        int siteIndex = i * size + j;

        if (!isOpen(i, j)) {
            site[i][j] = 1;
            if (i == 0) {
                uf.union(siteIndex, top);
            } else {
                if (isOpen(i - 1, j)) {
                    uf.union(siteIndex, (i - 1) * size + j);
                }
            }

            if (i == size - 1) {
                uf.union(siteIndex, bottom);
            } else {
                if (isOpen(i + 1, j)) {
                    uf.union(siteIndex, (i + 1) * size + j);
                }
            }

            if (j > 0) {
                if (isOpen(i, j - 1)) {
                    uf.union(siteIndex, i * size + (j - 1));
                }
            }

            if (j < size - 1) {
                if (isOpen(i, j + 1)) {
                    uf.union(siteIndex, i * size + (j + 1));
                }
            }
        }
    }

    // is site (row i, column j) open?
    public boolean isOpen(int i, int j) {
        if ((i < 0 || i > size - 1) || (j < 0 || j > size - 1)) {
            throw new java.lang.IndexOutOfBoundsException();
        }

        return site[i][j] == 1;
    }

    // is site (row i, column j) full?
    public boolean isFull(int i, int j) {
        if ((i < 0 || i > size - 1) || (j < 0 || j > size - 1)) {
            throw new java.lang.IndexOutOfBoundsException();
        }

        return site[i][j] == 0;
    }

    public boolean percolates() {             // does the system percolate?
        return uf.connected(top, bottom);
    }

}
