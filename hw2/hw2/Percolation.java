package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private int pLength;
    private boolean[][] pTron;
    private WeightedQuickUnionUF pList1;
    private WeightedQuickUnionUF pList2;
    private int pTop;
    private int pBottom;
    private int openNodes;

    /* Create a Percolation object with length and height N. Create 2 QuickUnion items.
    1 for percolates() and has a virtual bottom node, 2 for isFull() and does not have bottom.
     */
    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException("Number must be greater than 0!");
        }
        pLength = N;
        pTron = new boolean[N][N];
        for (int i = 0; i < N; i += 1) {
            for (int j = 0; j < N; j += 1) {
                pTron[i][j] = false;
            }
        }
        openNodes = 0;
        pList1 = new WeightedQuickUnionUF(N * N + 2);
        pList2 = new WeightedQuickUnionUF(N * N + 1);
        pTop = N * N;
        pBottom = N * N + 1;
    }

    /* Check if percolates. */
    public boolean percolates() {
        return pList1.connected(pTop, pBottom);
    }

    /* Check the number of open nodes on the grid. */
    public int numberOfOpenSites() {
        return openNodes;
    }

    /*Check if the position is full or empty. */
    public boolean isFull(int row, int col) {
        if (!nodeValidate(row, col)) {
            throw new IndexOutOfBoundsException();
        }
        return pTron[row][col] && pList2.connected(pTop, xyToNum(row, col));
    }

    /* Set position row, col on the grid to open. Connect to adjacent node if its open. */
    public void open(int row, int col) {
        if (!nodeValidate(row, col)) {
            throw new IndexOutOfBoundsException();
        } else if (pTron[row][col]) {
            return;
        }
        pTron[row][col] = true;
        openNodes += 1;
        int pNode = xyToNum(row, col);
        nodeConnect(pNode, row - 1, col);
        nodeConnect(pNode, row + 1, col);
        nodeConnect(pNode, row, col - 1);
        nodeConnect(pNode, row, col + 1);
        checkTopBottom(row, col);
    }

    /* Check if the position is open or closed. */
    public boolean isOpen(int row, int col) {
        if (!nodeValidate(row, col)) {
            throw new IndexOutOfBoundsException();
        }
        return pTron[row][col];
    }

    /* If node being opened is at top or bottom of grid,
    connect it to the top/bottom virtual node. */
    private void checkTopBottom(int row, int col) {
        int pPos = xyToNum(row, col);
        if (pPos < pLength) {
            pList1.union(pPos, pTop);
            pList2.union(pPos, pTop);
        }
        if (pPos >= pLength * pLength - pLength) {
            pList1.union(pPos, pBottom);
        }
    }

    /* Connect if the node exist on the grid. */
    private void nodeConnect(int node, int row, int col) {
        if (nodeValidate(row, col) && pTron[row][col]) {
            pList1.union(node, xyToNum(row, col));
            pList2.union(node, xyToNum(row, col));
        }
    }

    /* Validate if integer is within bound. */
    private boolean nodeValidate(int row, int col) {
        return row >= 0 && row < pLength && col >= 0 && col < pLength;
    }

    /* Convert xy coordination to a number, and vice versa. */
    private int xyToNum(int x, int y) {
        return x * pLength + y;
    }

    public static void main(String[] args) {
        PercolationStats pStats = new PercolationStats(3, 10, new PercolationFactory());
        System.out.println(pStats.mean());
        System.out.println(pStats.stddev());
        System.out.println(pStats.confidenceLow());
        System.out.println(pStats.confidenceHigh());
    }
}
