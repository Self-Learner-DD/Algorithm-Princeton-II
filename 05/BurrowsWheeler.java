import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;
import edu.princeton.cs.algs4.Queue;
import java.util.Arrays;

public class BurrowsWheeler {
    // apply Burrows-Wheeler transform, reading from standard input and writing to standard output
    private static final int R = 256;
    
    public static void transform() {
        String s = "";
        while (!BinaryStdIn.isEmpty()) {
            s = BinaryStdIn.readString();
        }
        int n = s.length();
        CircularSuffixArray cirArray = new CircularSuffixArray(s);
        for (int i = 0; i < n; i++) {
            if (cirArray.index(i) == 0) {
                BinaryStdOut.write(i);
                break;
            }
        }
        for (int i = 0; i < n; i++) {
            char c = s.charAt((cirArray.index(i) -1 + n) % n);
            BinaryStdOut.write(c);
        }
        BinaryStdOut.close();
    }     
            
    // apply Burrows-Wheeler inverse transform, reading from standard input and writing to standard output
    public static void inverseTransform() {
        int originIndex = -1;
        String s = "";
        while (!BinaryStdIn.isEmpty()) {
            originIndex = BinaryStdIn.readInt();
            s = BinaryStdIn.readString();
        }
        int n = s.length();
        char[] lastCol = s.toCharArray();
        int[] next = new int[n];
        sort(lastCol, next);
        
        int cnt = 0;
        int d = originIndex;

        while (cnt < n) {
            BinaryStdOut.write(lastCol[d]);
            d = next[d];
            cnt++;
        }
        BinaryStdOut.close();   
    }   
    
    private static void sort(char[] ch, int[] next) {
        int[] cnt = new int[R + 1];
        for (int i = 0; i < ch.length; i++) {
            cnt[ch[i] + 1]++;
        }
        for (int i = 1; i < cnt.length; i++) {
            cnt[i] += cnt[i - 1];
        }
        char[] aux = new char[ch.length];
        for (int i = 0; i < ch.length; i++) {
            char c = ch[i];
            aux[cnt[c]] = c;
            next[cnt[c]++] = i;
        // i is the original position of c in last col, cnt[c] is the new postition of c in the first col
        // equal to sort the index according the the natural order of the corresponding character...               
        }
        for (int i = 0; i < ch.length; i++) {
            ch[i] = aux[i];
        }
    }

    // if args[0] is '-', apply Burrows-Wheeler transform
    // if args[0] is '+', apply Burrows-Wheeler inverse transform
    public static void main(String[] args) {
        if (args[0].equals("-")) transform();
        if (args[0].equals("+")) inverseTransform();       
    }
}