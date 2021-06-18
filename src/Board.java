import edu.princeton.cs.algs4.StdRandom;
import java.util.Arrays;
import java.util.LinkedList;

public class Board {

    private final int[][] board;
    private final int bSize;

    public Board(int[][] tiles) {
        bSize = tiles.length;
        board = new int[bSize][bSize];

        for (int i = 0; i < bSize; i++) {
            System.arraycopy(tiles[i], 0, board[i], 0, bSize);
        }
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(bSize).append("\n");

        for (int i = 0; i < bSize; i++) {
            for (int j = 0; j < bSize; j++) {
                s.append(" ").append(board[i][j]).append("\t");
            }
            s.append("\n");
        }

        return s.toString();
    }

    public int dimension() {
        return bSize;
    }

    public int hamming() {
        int count = 0;

        for (int i = 0; i < bSize; i++) {
            for (int j = 0; j < bSize; j++) {
                if (board[i][j] != i * bSize + j + 1 && board[i][j] != 0) {
                    count++;
                }
            }
        }

        return count;
    }

    public int manhattan() {
        int count = 0;

        for (int i = 0; i < bSize; i++) {
            for (int j = 0; j < bSize; j++) {
                if (board[i][j] != i * bSize + j + 1 && board[i][j] != 0) {
                    if (board[i][j] % bSize != 0) {
                        count += Math.abs(i - board[i][j]/bSize)
                                + Math.abs(j - (board[i][j] - (board[i][j]/bSize)*bSize - 1));
                    } else {
                        count += Math.abs(i - (board[i][j]/bSize - 1))
                                + Math.abs(j - (bSize - 1));
                    }
                }
            }
        }

        return count;
    }

    public boolean isGoal() {
        return manhattan() == 0;
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

        return Arrays.deepEquals(this.board, ((Board) y).board);
    }

    public Iterable<Board> neighbors() {

        int[][] temp = new int[bSize][bSize];
        for (int i = 0; i < bSize; i++) {
            System.arraycopy(board[i], 0, temp[i], 0, bSize);
        }

        LinkedList<Board> neighbors = new LinkedList<>();
        for (int i = 0; i < bSize; i++) {
            for (int j = 0; j < bSize; j++) {
                if (temp[i][j] == 0) {
                    if (i == 0 && j == 0) {
                        swap(temp, i, j, i + 1, j);
                        neighbors.add(new Board(temp));
                        swap(temp, i + 1, j, i, j);
                        swap(temp, i, j, i, j + 1);
                        neighbors.add(new Board(temp));
                        break;
                    } else if (j > 0 && j < bSize - 1 && i == 0) {
                        swap(temp, i, j, i, j - 1);
                        neighbors.add(new Board(temp));
                        swap(temp, i, j - 1, i, j);
                        swap(temp, i, j, i + 1, j);
                        neighbors.add(new Board(temp));
                        swap(temp, i + 1, j, i, j);
                        swap(temp, i, j, i, j + 1);
                        neighbors.add(new Board(temp));
                        break;
                    } else if (j == bSize - 1 && i == 0) {
                        swap(temp, i, j, i + 1, j);
                        neighbors.add(new Board(temp));
                        swap(temp, i + 1, j, i, j);
                        swap(temp, i, j, i, j - 1);
                        neighbors.add(new Board(temp));
                        break;
                    } else if (j == 0 && i < bSize - 1) {
                        swap(temp, i, j, i, j + 1);
                        neighbors.add(new Board(temp));
                        swap(temp, i, j + 1, i, j);
                        swap(temp, i, j, i + 1, j);
                        neighbors.add(new Board(temp));
                        swap(temp, i + 1, j, i, j);
                        swap(temp, i, j, i - 1, j);
                        neighbors.add(new Board(temp));
                        break;
                    } else if (j > 0 && j < bSize - 1 && i > 0 && i < bSize - 1) {
                        swap(temp, i, j, i, j - 1);
                        neighbors.add(new Board(temp));
                        swap(temp, i, j - 1, i, j);
                        swap(temp, i, j, i - 1, j);
                        neighbors.add(new Board(temp));
                        swap(temp, i - 1, j, i, j);
                        swap(temp, i, j, i + 1, j);
                        neighbors.add(new Board(temp));
                        swap(temp, i + 1, j, i, j);
                        swap(temp, i, j, i, j + 1);
                        neighbors.add(new Board(temp));
                        break;
                    } else if (j == bSize - 1 && i < bSize - 1) {
                        swap(temp, i, j, i, j - 1);
                        neighbors.add(new Board(temp));
                        swap(temp, i, j - 1, i, j);
                        swap(temp, i, j, i + 1, j);
                        neighbors.add(new Board(temp));
                        swap(temp, i + 1, j, i, j);
                        swap(temp, i, j, i - 1, j);
                        neighbors.add(new Board(temp));
                        break;
                    } else if (i == bSize - 1 && j == 0) {
                        swap(temp, i, j, i - 1, j);
                        neighbors.add(new Board(temp));
                        swap(temp, i - 1, j, i, j);
                        swap(temp, i, j, i, j + 1);
                        neighbors.add(new Board(temp));
                        break;
                    } else if (j > 0 && j < bSize - 1 && i == bSize - 1) {
                        swap(temp, i, j, i, j - 1);
                        neighbors.add(new Board(temp));
                        swap(temp, i, j - 1, i, j);
                        swap(temp, i, j, i - 1, j);
                        neighbors.add(new Board(temp));
                        swap(temp, i - 1, j, i, j);
                        swap(temp, i, j, i, j + 1);
                        neighbors.add(new Board(temp));
                        break;
                    } else if (i == bSize - 1 && j == bSize - 1) {
                        swap(temp, i, j, i - 1, j);
                        neighbors.add(new Board(temp));
                        swap(temp, i - 1, j, i, j);
                        swap(temp, i, j, i, j - 1);
                        neighbors.add(new Board(temp));
                        break;
                    }
                }
            }
        }

        return neighbors;
    }

    public Board twin() {
        int[][] temp = new int[bSize][bSize];
        for (int i = 0; i < bSize; i++) {
            System.arraycopy(board[i], 0, temp[i], 0, bSize);
        }

        int q = StdRandom.uniform(bSize - 1);
        int r = StdRandom.uniform(bSize);
        swap(temp, q, r, q + 1, r);

        return new Board(temp);
    }

    /**
     *
     * @param arr array to manipulate
     * @param i row of 1 tile
     * @param j column of 1 tile
     * @param n row of 2 tile
     * @param m column of 2 tile
     */
    private void swap(int[][] arr, int i, int j, int n, int m) {
        int temp;
        temp = arr[n][m];
        arr[n][m] = arr[i][j];
        arr[i][j] = temp;
    }

    public static void main(String[] args) {
        int[][] arr = new int[3][3];
        int[] num2 = {1, 2, 3, 4, 5, 6, 7, 8, 0};
        int k = 0;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                arr[i][j] = num2[k++];
            }
        }

        Board b = new Board(arr);
        System.out.println(b);
        System.out.println("Manh: " + b.manhattan());
        System.out.println("Hamm: " + b.hamming());
        System.out.println("Solved: " + b.isGoal());

        System.out.println("\nNeighbors: ");

        Iterable<Board> list = b.neighbors();
        for (Board neigh : list) {
            System.out.println(neigh);
        }

        System.out.println("Twin: ");
        Board twin = b.twin();

        System.out.println(twin);
        System.out.println("Manh: " + twin.manhattan());
        System.out.println("Hamm: " + twin.hamming());
        System.out.println("Twin dimension: " + twin.dimension());
        System.out.println("Solved: " + twin.isGoal());

        System.out.println("Equals: " + b.equals(twin));
    }
}