import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import java.util.LinkedList;

public class Solver {

    private final Board initBoard;
    private final LinkedList<Board> solution;
    private int numOfMoves;

    private class SearchNode implements Comparable<SearchNode> {

        Board board;
        SearchNode prev;
        int moves;
        int priority;

        SearchNode(Board board, int moves, SearchNode prev) {
            this.board = board;
            this.moves = moves;
            this.prev = prev;
            priority = board.manhattan() + moves;
        }

        public int compareTo(SearchNode that) {
            if (this.priority < that.priority) {
                return -1;
            } else if (this.priority > that.priority) {
                return 1;
            } else {
                if (this.priority - this.moves < that.priority - that.moves) {
                    return -1;
                } else  if (this.priority - this.moves > that.priority - that.moves) {
                    return 1;
                } else {
                    return 0;
                }
            }
        }

        public Iterable<SearchNode> neighbors() {
            LinkedList<SearchNode> list = new LinkedList<>();

            for (Board b : board.neighbors()) {
                list.add(new SearchNode(b, numOfMoves, this));
            }

            return list;
        }

        @Override
        public boolean equals(Object y) {
            if (this == y) {
                return true;
            }
            if (y == null) {
                return false;
            }
            if (this.getClass() != y.getClass()) {
                return false;
            }

            return this.board.equals(((SearchNode) y).board);
        }
    }

    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException("Board is null.");
        }

        initBoard = initial;
        MinPQ<SearchNode> pq = new MinPQ<>();
        solution = new LinkedList<>();

        if (!isSolvable()) {
            return;
        }

        SearchNode initNode = new SearchNode(initial, numOfMoves, null);
        pq.insert(initNode);
        SearchNode deleted;

        while (!pq.isEmpty()) {
            deleted = pq.delMin();
            solution.add(deleted.board);

            if (deleted.board.isGoal()) {
                break;
            }

            numOfMoves++;
            for (SearchNode sn : deleted.neighbors()) {
                if (!sn.equals(deleted.prev)) {
                    pq.insert(sn);
                }
            }
        }
    }

    public boolean isSolvable() {
        numOfMoves = 0;
        SearchNode twin = new SearchNode(initBoard.twin(), numOfMoves, null);

        MinPQ<SearchNode> pq = new MinPQ<>();
        MinPQ<SearchNode> pqTw = new MinPQ<>();

        pq.insert(new SearchNode(initBoard, numOfMoves, null));
        pqTw.insert(twin);
        SearchNode deleted;
        SearchNode deletedTw;

        while (!pq.isEmpty() && !pqTw.isEmpty()) {
            deleted = pq.delMin();
            deletedTw = pqTw.delMin();

            if (deleted.board.isGoal()) {
                return true;
            }
            if (deletedTw.board.isGoal()) {
                return false;
            }

            numOfMoves++;
            for (SearchNode sn : deleted.neighbors()) {
                if (!sn.equals(deleted.prev)) {
                    pq.insert(sn);
                }
            }
            for (SearchNode sn : deletedTw.neighbors()) {
                if (!sn.equals(deletedTw.prev)) {
                    pqTw.insert(sn);
                }
            }
        }

        return pqTw.isEmpty();
    }

    public int moves() {
        if (!isSolvable()) {
            return -1;
        }
        return numOfMoves;
    }

    public Iterable<Board> solution() {
        if (!isSolvable()) {
            return null;
        }
        return solution;
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tiles[i][j] = in.readInt();
            }
        }
        Board initial = new Board(tiles);

        Solver solver = new Solver(initial);

        if (!solver.isSolvable()) {
            System.out.println("No solution possible");
        } else {
            System.out.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution()) {
                System.out.println(board);
            }
        }
    }
}