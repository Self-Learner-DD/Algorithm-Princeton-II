import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class DeluxeBFS
{
    private boolean[] marked;
    private int[] edgeTo;
    private int[] distTo;
        
    public DeluxeBFS(Digraph G, int s){
        marked = new boolean[G.V()];
        edgeTo = new int[G.V()];
        distTo = new int[G.V()];
        for (int i = 0; i < G.V(); i++)
            distTo[i] = Integer.MAX_VALUE;
        validateVertex(s);
        bfs(G, s);  
    }
    
    public DeluxeBFS(Digraph G, Iterable<Integer> sources){
        marked = new boolean[G.V()];
        edgeTo = new int[G.V()];
        distTo = new int[G.V()];
        for (int i = 0; i < G.V(); i++)
            distTo[i] = Integer.MAX_VALUE;
        validateVertices(sources);
        bfs(G, sources);
    }
         
    private void bfs(Digraph G, int s){
        Queue<Integer> q = new Queue<Integer>();
        marked[s] = true;
        distTo[s] = 0;
        q.enqueue(s);
        while (!q.isEmpty()){
            int v = q.dequeue();
            for (int w : G.adj(v)){
                if (!marked[w]){
                    marked[w] = true;
                    edgeTo[w] = v;
                    distTo[w] = distTo[v] + 1;
                    q.enqueue(w);
                }
            }
        }
    }
    
    private void bfs(Digraph G, Iterable<Integer> sources){
        Queue<Integer> q = new Queue<Integer>();
        for (int s : sources){
            marked[s] = true;
            distTo[s] = 0;
            q.enqueue(s);
        }
        while (!q.isEmpty()){
            int v = q.dequeue();
            for (int w : G.adj(v)){
                if (!marked[w]){
                    marked[w] = true;
                    edgeTo[w] = v;
                    distTo[w] = distTo[v] + 1;
                    q.enqueue(w);
                }
            }
        }
    }
    
    public boolean hasPathTo(int v)
    { 
        validateVertex(v);
        return marked[v]; 
    }
    
    public int distTo(int v)
    { 
        validateVertex(v);
        return distTo[v]; 
    }
    
    public Iterable<Integer> pathTo(int v)
    {
        validateVertex(v);
        Stack<Integer> path = new Stack<Integer>();
        if (!hasPathTo(v)) return path;
        int x = v;
        while (distTo[x] != 0){
            path.push(x);
            x = edgeTo[x];
        }
        path.push(x);
        return path;
    }
    
    private void validateVertex(int v) {
        int V = marked.length;
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
    }

    // throw an IllegalArgumentException unless {@code 0 <= v < V}
    private void validateVertices(Iterable<Integer> vertices) {
        if (vertices == null) {
            throw new IllegalArgumentException("argument is null");
        }
        int V = marked.length;
        for (int v : vertices) {
            if (v < 0 || v >= V) {
                throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
            }
        }
    }
    
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        DeluxeBFS p = new DeluxeBFS(G, 11);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int length   = p.distTo(v);
            StdOut.printf("distance = %d\n", length);
        }
    }
}
        
        
        
    