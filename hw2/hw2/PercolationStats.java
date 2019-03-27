package hw2;

import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

public class PercolationStats {

    private int sLength;
    private double sMean;
    private double stdDev;
    private double confLow;
    private double confHigh;

    /* Generate a PercolationStats object with T numbers of experiments. */
    public PercolationStats(int N, int T, PercolationFactory pf) {

        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException("N and T must be greater than zero!");
        }
        sLength = N;
        // Perform a series of random experiments.
        double[] sResults = new double[T];
        for (int i = 0; i < T; i += 1) {
            sResults[i] = randomExp(pf);
            System.out.println(sResults[i]);
        }
        // Calculate desired results.
        sMean = StdStats.mean(sResults);
        stdDev = StdStats.stddev(sResults);
        confLow = sMean - (1.96 * stdDev / (Math.sqrt(T)));
        confHigh = sMean + (1.96 * stdDev / (Math.sqrt(T)));
    }

    /* Perform a random experiment and return ratio of open sites to total sites
    when percolation occur. */
    private double randomExp(PercolationFactory pf) {
        Percolation pRandom = pf.make(sLength);
        while (!pRandom.percolates()) {
            openRandom(pRandom);
        }
        return (double) pRandom.numberOfOpenSites() / (double) (sLength * sLength);
    }

    /* Open a random node in Percolation object. */
    private void openRandom(Percolation P) {
        P.open(StdRandom.uniform(sLength), StdRandom.uniform(sLength));
    }

    public double mean() {
        return sMean;
    }

    public double stddev() {
        return stdDev;
    }

    public double confidenceLow() {
        return confLow;
    }

    public double confidenceHigh() {
        return confHigh;
    }
}
