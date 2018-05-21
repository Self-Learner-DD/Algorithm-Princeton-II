import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {
    private final WordNet wn;
    
    public Outcast(WordNet wordnet)         // constructor takes a WordNet object
    { wn = wordnet; }
       
    public String outcast(String[] nouns)   // given an array of WordNet nouns, return an outcast
    {
        int[] distance = new int[nouns.length];
        for (int i = 0; i <nouns.length; i++) {
            for (int j = i+1; j < nouns.length; j++) {
                int d = wn.distance(nouns[i], nouns[j]);
                distance[i] += d;
                distance[j] += d;
            }
        }
        int max = 0; 
        String outcast = "";
        for (int i = 0; i < distance.length; i++){
            if(distance[i] > max) { max = distance[i]; outcast = nouns[i]; }
        }
        return outcast;
    }
        
    public static void main(String[] args)  // see test client below
    {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }
}