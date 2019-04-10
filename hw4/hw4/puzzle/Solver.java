package hw4.puzzle;

import edu.princeton.cs.algs4.MinPQ;
import java.util.LinkedList;

public class Solver {

    /* A node class containing the current world state, the previous world state
     and no. of moves took to reach current state. */
    private class SearchNode implements Comparable {
        WorldState snState;
        int nMoves;
        SearchNode snPrevious;
        int snPriority;

        private SearchNode(WorldState current, int moves, SearchNode previous) {
            snState = current;
            nMoves = moves;
            snPrevious = previous;
            snPriority = nMoves + snState.estimatedDistanceToGoal();
        }

        public int compareTo(Object other) {
            SearchNode otherNode = (SearchNode) other;
            return Integer.compare(snPriority, otherNode.snPriority);
        }
    }
    private MinPQ<SearchNode> wsQueue;
    private LinkedList<WorldState> wsList;
    private SearchNode theGoal = null;

    /* Initiate a solver object which contains the best solution to problem. */
    public Solver(WorldState initial) {
        wsQueue = new MinPQ<SearchNode>();
        wsList = new LinkedList<>();
        SearchNode snInitial = new SearchNode(initial, 0, null);
        wsQueue.insert(snInitial);
        solvePuzzle1();
        solutionHelper(theGoal);
    }

    public int moves() {
        return theGoal.nMoves;
    }

    public Iterable<WorldState> solution() {
        return wsList;
    }

    /* Iterative structure to solve puzzle. */
    private void solvePuzzle1() {
        while (!wsQueue.isEmpty()) {
            SearchNode snNext = wsQueue.delMin();
            if (snNext.snState.isGoal()) {
                theGoal = snNext;
                return;
            } else {
                for (WorldState X:snNext.snState.neighbors()) {
                    if (snNext.snPrevious == null || !X.equals(snNext.snPrevious.snState)) {
                        wsQueue.insert(new SearchNode(X, snNext.nMoves + 1, snNext));
                    }
                }
            }
        }
    }

    /*Add solution state parents.*/
    private void solutionHelper(SearchNode N) {
        SearchNode node = N;
        while (node != null) {
            wsList.addFirst(node.snState);
            node = node.snPrevious;
        }
    }
}
