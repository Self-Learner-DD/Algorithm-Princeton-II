import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.ST;
import java.util.ArrayList;
import java.util.Arrays;
import edu.princeton.cs.algs4.DirectedCycle;


public class WordNet {
    
    /** symbol table cannot contain duplicate keys, if we use each word as a key, then the value should be the list of synset id;
     * 
     */
    private ST<String, ArrayList<Integer>> st = new ST<String, ArrayList<Integer>>();
    private ArrayList<String> synlist = new ArrayList<String>(); // just save synset as one string...
    private Digraph G;
    private SAP sap;
    
   // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms)
    {//synsets are input files...
        assertString(synsets); 
        assertString(hypernyms);
        In synfile = new In(synsets);
        buildSynset(synfile);
        G = new Digraph(synlist.size());
        In hyperfile = new In(hypernyms);
        buildhyper(hyperfile);
        checkCycle(G);
        sap = new SAP(G);
    }
    
    private void buildSynset(In synfile){
        while (synfile.hasNextLine()){
            String[] a = synfile.readLine().split(",");
            String[] words = a[1].split(" ");
            synlist.add(a[1]);
            for (int i = 0; i < words.length; i++){
                if (st.contains(words[i])) st.get(words[i]).add(Integer.parseInt(a[0]));
                else{
                    ArrayList<Integer> id = new ArrayList<Integer>();
                    id.add(Integer.parseInt(a[0]));
                    st.put(words[i], id);
                }
            }
        }
    }
    
    private void buildhyper(In hyperfile){
        boolean[] isNotRoot = new boolean[G.V()];
        while (hyperfile.hasNextLine()){
            String[] a = hyperfile.readLine().split(",");
            int s = Integer.parseInt(a[0]);
            isNotRoot[s] = true;
            for (int i = 1; i < a.length; i++){
                int v = Integer.parseInt(a[i]);
                G.addEdge(s, v);
            }
        }
        int countR = 0;
        for (int i = 0; i < G.V(); i++){
            if (!isNotRoot[i]) countR++;
        }
        if (countR > 1) throw new IllegalArgumentException("Not single root"); //why cannot use != 1 
    }
        
   // returns all WordNet nouns
   public Iterable<String> nouns()
   {
       return st.keys();
   }

   // is the word a WordNet noun?
   public boolean isNoun(String word)
   {
       assertString(word);
       return st.contains(word);
   }

   // distance between nounA and nounB (defined below)
   public int distance(String nounA, String nounB) //use sap method based on collection of argument...
   {
       assertWord(nounA, nounB);
       return sap.length(st.get(nounA), st.get(nounB));
   }

   // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
   // in a shortest ancestral path (defined below)
   public String sap(String nounA, String nounB)
   {
       assertWord(nounA, nounB);
       int id = sap.ancestor(st.get(nounA), st.get(nounB));
       return synlist.get(id);
   }
       
   public static void main(String[] args){
       String synsets = args[0];
       String hypernyms = args[1];
       WordNet wn = new WordNet(synsets, hypernyms);
       StdOut.println(wn.distance("b", "f"));
   }
           
   
   private void assertString(String s)
   {
       if (s == null) throw new IllegalArgumentException("Argument cannot be null");
   }
   
   private void assertWord(String a, String b)
   {
       if ( a == null || b == null) throw new IllegalArgumentException("Argument cannot be null");
       if (!isNoun(a) || !isNoun(b)) throw new IllegalArgumentException("Argument not belong to WordNet");
   }
   
   private void checkCycle(Digraph G){
       DirectedCycle dc = new DirectedCycle(G);
       if (dc.hasCycle()) throw new IllegalArgumentException("Not a DAG");
   }        
}