package col106.assignment6;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ShortestPathFinder implements ShortestPathInterface {
	int[] s;
	int[] t;
	int key1, key2;
	DualGraph DG;
	HashMap<Integer,Vertex> map;
	ArrayList<int[]> hooks;
	Dijkstra dj;
	List<int[]> path;
	
    /**
     * Computes shortest-path from the source vertex s to destination vertex t 
     * in graph G.
     * DO NOT MODIFY THE ARGUMENTS TO THIS CONSTRUCTOR
     *
     * @param  G the graph
     * @param  s the source vertex
     * @param  t the destination vertex 
     * @param left the cost of taking a left turn
     * @param right the cost of taking a right turn
     * @param forward the cost of going forward
     * @throws IllegalArgumentException unless 0 <= s < V
     * @throws IllegalArgumentException unless 0 <= t < V
     * where V is the number of vertices in the graph G.
     */
    public ShortestPathFinder (final Digraph G, final int[] s, final int[] t, 
    final int left, final int right, final int forward) {
        // YOUR CODE GOES HERE
    	this.s = s;
    	this.t = t;
    	this.DG = new DualGraph(G, s, t, left, right, forward);
    	this.map = G.getHashMap();
    	this.key1 = s[0] * G.W() + s[1];
    	this.key2 = t[0] * G.W() + t[1];
    	this.dj = new Dijkstra(G);
    	
    	ArrayList<DualVertex> dualGraph = DG.adj;
    	for (DualVertex ver : dualGraph) {
    		ArrayList<DualEdge> edge = ver.edge;
			for (DualEdge edge1 : edge) {
				int u = edge1.former();
				int v = edge1.from();
				int w = edge1.to();
				int weight = (int)edge1.weight();
				dj.add(u, v, w, weight);
			}
    	}
    	path = dj.getPath(-1, key1, key2);
    }

    // Return number of nodes in dual graph
    public int numDualNodes() {
        // YOUR CODE GOES HERE
    	return DG.adj.size();
    }

    // Return number of edges in dual graph
    public int numDualEdges() {
        // YOUR CODE GOES HERE
    	return DG.hooks.size();
    }

    // Return hooks in dual graph
    // A hook (0,0) - (1,0) - (1,2) with weight 8 should be represented as
    // the integer array {0, 0, 1, 0, 1, 2, 8}
    public ArrayList<int[]> dualGraph() {
        // YOUR CODE GOES HERE
        return DG.hooks;
    }

    // Return true if there is a path from s to t.
    public boolean hasValidPath() {
        // YOUR CODE GOES HERE
    	if (key1 == key2)
    		return true;
    	if (path==null || path.size()<=0)
    		return false;
    	return true;
    }

    // Return the length of the shortest path from s to t.
    public int ShortestPathValue() {
        // YOUR CODE GOES HERE
    	if (key1 == key2)
    		return 0;
    	if (path==null || path.size()<=0)
    		return 0;
        return path.get(path.size()-1)[1];
    }

    // Return the shortest path computed from s to t as an ArrayList of nodes, 
    // where each node is represented by its location on the grid.
    public ArrayList<int[]> getShortestPath() {
        // YOUR CODE GOES HERE
    	ArrayList<int[]> shortestPath = new ArrayList<int[]>();
    	if (key1 == key2) {
    		int[] node = s;
    		shortestPath.add(node);
    		return shortestPath;
    	}
    	if (path==null || path.size()<=0)
    		return shortestPath;
    	for (int[] arr : path) {
    		int[] node = new int[2];
    		node[0] = map.get(arr[0]).i;
    		node[1] = map.get(arr[0]).j;
    		shortestPath.add(node);
    	}
        return shortestPath;
    }
}
