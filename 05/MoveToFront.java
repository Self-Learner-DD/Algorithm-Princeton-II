import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class MoveToFront {
    // apply move-to-front encoding, reading from standard input and writing to standard output
    private static final int R = 256;
    public static void encode() {
        char[] chPos = new char[R]; //character-indexed position array
        char[] posCh = new char[R]; // position-indexed character array
        for (char i = 0; i < R; i++) {
            chPos[i] = i;
            posCh[i] = i;
        }
        while (!BinaryStdIn.isEmpty()) {
            char c = BinaryStdIn.readChar();
            BinaryStdOut.write(chPos[c]);
            //swith c with the character at position 0
            char tempPos = chPos[c];
            for (char i = tempPos; i >= 1; i--) {
                posCh[i] = posCh[i - 1];
                chPos[posCh[i]] = i;
            }
            chPos[c] = 0;
            posCh[0] = c;
        }
        BinaryStdOut.close();
    }

    // apply move-to-front decoding, reading from standard input and writing to standard output
    public static void decode() {
        char[] posCh = new char[R];
        for (char i = 0; i < R; i++) {
            posCh[i] = i;     
        }
        while (!BinaryStdIn.isEmpty()) {
            char d = BinaryStdIn.readChar();
            BinaryStdOut.write(posCh[d]);
            char tempCh = posCh[d];
            for (char i = d; i >= 1; i--) {
                posCh[i] = posCh[i - 1];
            }
            posCh[0] = tempCh;
        }
        BinaryStdOut.close();
    }
        

    // if args[0] is '-', apply move-to-front encoding
    // if args[0] is '+', apply move-to-front decoding
    public static void main(String[] args) {
        if (args[0].equals("-")) encode();
        if (args[0].equals("+")) decode();
    }
}