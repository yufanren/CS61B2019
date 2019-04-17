package lab11.graphs;

/**
 *  @author Josh Hug
 */
import java.util.Queue;
import java.util.LinkedList;

public class MazeBreadthFirstPaths extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private int s;
    private int t;
    private Maze maze;
    private boolean targetFound = false;
    private Queue<Integer> bfLine = new LinkedList<Integer>();

    public MazeBreadthFirstPaths(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
        bfLine.offer(s);
    }

    /** Conducts a breadth first search of the maze starting at the source. */
    private void bfs() {
        while (!bfLine.isEmpty()) {
            int current = bfLine.remove();
            marked[current] = true;
            announce();
            if (current == t) {
                targetFound = true;
            }
            if (targetFound) {
                return;
            }
            for (int w:maze.adj(current)) {
                if (!marked[w]) {
                    bfLine.add(w);
                    edgeTo[w] = current;
                    announce();
                    distTo[w] = distTo[current] + 1;
                }
            }
        }
    }


    @Override
    public void solve() {
        bfs();
    }
}

