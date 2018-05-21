# Algorithm-II-WordNet
project specification: http://coursera.cs.princeton.edu/algs4/assignments/wordnet.html

DeluxeBFS: implement based on code of BreadthFirstPaths provided by algs4
           https://algs4.cs.princeton.edu/41graph/BreadthFirstPaths.java.html
           
SAP: create two instance of DeluxeBFS for v and w; iterate though all vertex in G, find the vertex that has shortest distance to v and w.

WordNet: the key is to find the datastructure for Wordnet;

         (1) since the problem require to realize isNoun(String word) method in O(log(n)), we can only realize it use ordered symbol table with noun as key;
         
         (2) symbol table cannot contain duplicate keys, so need to use a list of the id of the noun as value. 
         
         (3) also include an id-indexed list, in order to refer back to the synset based on the id.
         
         the other problem is how to check the worknet is a single root DAG.
         (1) use standard class DirectedCycle to check if it is DAG;
         
         (2) the root will not have hypernym, thus will not appear as the first element in the hypernym file. count if there is only one that synset
         
Outcase: not too hard
 
