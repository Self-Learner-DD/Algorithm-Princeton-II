import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.BreadthFirstPaths;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class SAP {
    private final Digraph G;
    private final Digraph R;
   // private int[][] ancestor; //cashing...

   // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G){
        this.G = new Digraph(G); // deep copy of specificed digraph, any change to G to will not affect this.G...
        this.R = this.G.reverse();     
    }

   // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w){
        int s = ancestor(v, w);
        if (s != -1){
            DeluxeBFS ps = new DeluxeBFS(R, s);
            return ps.distTo(v) + ps.distTo(w);
        }
        else return -1;     
    }

   // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w){
     
        int min = Integer.MAX_VALUE;
        int ancestor = -1;
        DeluxeBFS pv = new DeluxeBFS(G, v);
        DeluxeBFS pw = new DeluxeBFS(G, w);
        
        for (int s = 0; s < G.V(); s++){
            if (pv.hasPathTo(s) && pw.hasPathTo(s)) { 
                int len = pv.distTo(s) + pw.distTo(s);
                if (len < min) { min = len; ancestor = s; }
            }
        }
        return ancestor;
    }

   // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w){
        int min = Integer.MAX_VALUE;
        DeluxeBFS pv = new DeluxeBFS(G, v);
        DeluxeBFS pw = new DeluxeBFS(G, w);
        for (int s = 0; s < G.V(); s++){
            if (pv.hasPathTo(s) && pw.hasPathTo(s)) { 
                int len = pv.distTo(s) + pw.distTo(s);
                if (len < min) { min = len; }
            }
        }
        if (min == Integer.MAX_VALUE) return -1;
        else return min;
    }

   // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w){
        int min = Integer.MAX_VALUE;
        int ancestor = -1;
        DeluxeBFS pv = new DeluxeBFS(G, v);
        DeluxeBFS pw = new DeluxeBFS(G, w);
        for (int s = 0; s < G.V(); s++){
            if (pv.hasPathTo(s) && pw.hasPathTo(s)) { 
                int len = pv.distTo(s) + pw.distTo(s);
                if (len < min) { min = len; ancestor = s; }
            }
        }
        return ancestor;
    }

   // do unit testing of this class
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length   = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }       
}
