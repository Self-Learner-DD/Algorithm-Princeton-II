import java.util.Arrays;
import java.util.Comparator;

public class CircularSuffixArray {
    private final String s;
    private Integer[] index;
    
    public CircularSuffixArray(String s) {    // circular suffix array of s
        if (s == null) throw new IllegalArgumentException("Argument cannot be null!!!");
        this.s = s;
        int n = s.length();
        index = new Integer[n];
        for (int i = 0; i < n; i++) {
            index[i] = i;
        }
        
        Arrays.sort(index, new Comparator<Integer>() {
            public int compare(Integer a, Integer b) {
                for (int i = 0; i < n; i++) {
                    char c1 = s.charAt((a + i) % n);
                    char c2 = s.charAt((b + i) % n);
                    if (c1 != c2) return c1 - c2;
                }
                return 0;
            }
        }); 
    }
    public int length() {                    // length of s
        return s.length();
    }
    public int index(int i) {                // returns index of ith sorted suffix
        int n = s.length();
        if (i < 0 || i >= n) throw new IllegalArgumentException("Index out of range!!!");
        return index[i];
    }
    
    public static void main(String[] args) {  // unit testing (required)
        String str = args[0];
        CircularSuffixArray cirArray = new CircularSuffixArray(str);
        System.out.println(cirArray.length() == str.length());
        for (int i = 0; i < str.length(); i++) {
            System.out.println(cirArray.index(i));
        }
    }    
}