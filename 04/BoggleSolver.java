import java.util.Set;
import java.util.HashSet;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class BoggleSolver
{
    private Node root;
    private final int[][] dirs = {{1, 0}, {-1, 0}, {1, 1}, {1, -1}, {0, 1}, {0, -1}, {-1, 1}, {-1, -1}};
    
    private class Node {
        boolean isWord;
        Node[] next = new Node[26];
    }
    
    private void put(String word) {
        root = put(root, word, 0);
    }
    
    private Node put(Node x, String word, int d) {
        if (x == null) x = new Node();
        if (d == word.length()) { x.isWord = true; return x; }
        char c = word.charAt(d);
        x.next[c - 'A'] = put(x.next[c - 'A'], word, d + 1);
        return x;
    }
    
    private boolean get(String word) {
        Node x = get(root, word, 0);
        if (x == null) return false;
        return x.isWord;
    }
    
    private Node get(Node x, String word, int d) {
        if (x == null) return x;
        if (d == word.length()) return x;
        char c = word.charAt(d);
        return get(x.next[c - 'A'], word, d + 1);
    }

    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public BoggleSolver(String[] dictionary) {
        for (int i = 0; i < dictionary.length; i++) {
            put(dictionary[i]);
        }
    }        

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board) {
        int m = board.rows(), n = board.cols();
        boolean[][] visited = new boolean[m][n];
        Set<String> validWords = new HashSet<String>();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                dfs(board, i, j, validWords, visited, root, "");
            }
        }
        return validWords;
    }
    
    private void dfs(BoggleBoard board, int i, int j, Set<String> validWords, boolean[][] visited, Node x, String pre) {
        int m = board.rows(), n = board.cols();
        char c = board.getLetter(i, j);
        Node next = get(x, pre + c, pre.length());
        if (c == 'Q') { pre = pre + 'Q'; c = 'U'; next = get(next, pre + c, pre.length()); }
        if (next == null) return;  
        if (next.isWord && pre.length() + 1 > 2) validWords.add(pre + c);
        visited[i][j] = true;
        for (int[] dir : dirs) {
            int i1 = i + dir[0], j1 = j + dir[1];
            if (i1 < 0 || i1 >= m || j1 < 0 || j1 >= n || visited[i1][j1]) continue;
            dfs(board, i1, j1, validWords, visited, next, pre + c);
        }
        visited[i][j] = false;
    }

    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word) {
        if(!get(word)) return 0;
        int n = word.length();
        if (n <= 2) return 0;
        if (n <= 4) return 1;
        if (n == 5) return 2;
        if (n == 6) return 3;
        if (n == 7) return 5;
        return 11;
    }  
    
    public static void main(String[] args) {
        In in = new In(args[0]);
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        BoggleBoard board = new BoggleBoard(args[1]);
        int score = 0;
        for (String word : solver.getAllValidWords(board)) {
            StdOut.println(word);
            score += solver.scoreOf(word);
        }
        StdOut.println("Score = " + score);
    }
}