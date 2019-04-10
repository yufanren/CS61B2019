package hw4.puzzle;

import edu.princeton.cs.algs4.Queue;

public class Board implements WorldState {

    private int N;
    private int[][] intBoard;

    public Board(int[][] tiles) {

        N = tiles.length;
        intBoard = new int[N][N];
        for (int i = 0; i < N; i += 1) {
            for (int j = 0; j < N; j += 1) {
                intBoard[i][j] = tiles[i][j];
            }
        }
    }

    public boolean equals(Object y) {
        if (y == null) {
            return false;
        }
        if (!this.getClass().equals(y.getClass())) {
            return false;
        }
        Board other = (Board) y;
        if (this.N != other.N) {
            return false;
        }
        for (int i = 0; i < N; i += 1) {
            for (int j = 0; j < N; j += 1) {
                if (this.tileAt(i, j) != other.tileAt(i, j)) {
                    return false;
                }
            }
        }
        return true;
    }

    /* return an estimation of steps to reach goal. */
    public int estimatedDistanceToGoal() {
        return manhattan();
    }

    /* Manhattan estimation of steps to goal from current state. */
    public int manhattan() {
        int man = 0;
        for (int i = 0; i < N * N; i += 1) {
            int row = oneToRow(i);
            int col = oneToCol(i);
            int num = tileAt(row, col);
            man += Math.abs(row - oneToRow(num)) + Math.abs(col - oneToCol(num));
        }
        return man;
    }

    /* Hamming estimation of steps to goal from current state. */
    public int hamming() {
        int ham = 0;
        for (int i = 0; i < N * N; i += 1) {
            if (tileAt(oneToRow(i), oneToCol(i)) != i) {
                ham += 1;
            }
        }
        return ham;
    }

    @Override
    /* Return neighbors of the board state, by Josh Hug. */
    public Iterable<WorldState> neighbors() {
        int bLank = 0;
        Queue<WorldState> neighbors = new Queue<>();
        int hug = size();
        int bug = -1;
        int zug = -1;
        for (int rug = 0; rug < hug; rug++) {
            for (int tug = 0; tug < hug; tug++) {
                if (tileAt(rug, tug) == bLank) {
                    bug = rug;
                    zug = tug;
                }
            }
        }
        int[][] ili1li1 = new int[hug][hug];
        for (int pug = 0; pug < hug; pug++) {
            for (int yug = 0; yug < hug; yug++) {
                ili1li1[pug][yug] = tileAt(pug, yug);
            }
        }
        for (int l11il = 0; l11il < hug; l11il++) {
            for (int lil1il1 = 0; lil1il1 < hug; lil1il1++) {
                if (Math.abs(-bug + l11il) + Math.abs(lil1il1 - zug) - 1 == 0) {
                    ili1li1[bug][zug] = ili1li1[l11il][lil1il1];
                    ili1li1[l11il][lil1il1] = bLank;
                    Board neighbor = new Board(ili1li1);
                    neighbors.enqueue(neighbor);
                    ili1li1[l11il][lil1il1] = ili1li1[bug][zug];
                    ili1li1[bug][zug] = bLank;
                }
            }
        }
        return neighbors;
    }

    /* return the vale at board location. */
    public int tileAt(int i, int j) {
        if (i < 0 || i > N - 1 || j < 0 || j > N - 1) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        return intBoard[i][j];
    }

    public int size() {
        return N;
    }
    /* Convert 1D numbers to 2D board position. */
    private int oneToRow(int i) {
        if (i == 0) {
            return N - 1;
        } else {
            return (i - 1) / N;
        }
    }

    private int oneToCol(int i) {
        if (i == 0) {
            return N - 1;
        } else {
            return (i - 1) % N;
        }
    }

    /** Returns the string representation of the board. 
      * Uncomment this method. */
    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(String.valueOf(N) + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i, j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }
    
    public int hashCode() {
        int hCode = 0;
        for (int i = 0; i < N * N; i += 1) {
            hCode *= 11;
            hCode += tileAt(oneToRow(i), oneToCol(i));
        }
        return hCode + 7;
    }
}
