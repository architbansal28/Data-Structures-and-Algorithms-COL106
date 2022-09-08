package col106.assignment6;

import java.util.ArrayList;
import java.util.HashMap;

public class DualGraph {
	Digraph G;
	int left;
	int right;
	int forward;
	ArrayList<Edge>[] edges;
	HashMap<Integer,Vertex> map;
	ArrayList<DualVertex> adj;
	ArrayList<int[]> hooks;

	public DualGraph(Digraph G, final int[] s, final int[] t, int left, int right, int forward) {
		this.G = G;
		this.left = left;
		this.right = right;
		this.forward = forward;
		this.edges = G.adjacency();
		this.map = G.getHashMap();
		this.adj = new ArrayList<DualVertex>();
		
		adj.add(new DualVertex(-1, s[0]*G.W()+s[1], 0));
		for (int i = 0; i < G.V(); i++) {
			ArrayList<Edge> array = edges[i];
			for (Edge e : array) {
				int u = e.from();
				int v = e.to();
				double weight = e.weight();
				adj.add(new DualVertex(u, v, weight));
			}
		}
		
		for (DualVertex ver : adj) {
			int u = ver.i;
			int v = ver.j;
			ArrayList<DualEdge> edge = ver.edge;
			for (DualVertex ver1 : adj) {
				int u1 = ver1.i;
				int v1 = ver1.j;
				double weight = ver1.weight;
				if (u==u1 && v==v1)
					continue;
				if (v == u1) {
					int turnCost = turnCost(u, v, v1);
					double cost = weight + turnCost;
					edge.add(new DualEdge(u, v, v1, cost));
				}
			}
		}
		
		hooks = new ArrayList<int[]>();
    	for (DualVertex ver : adj) {
    		ArrayList<DualEdge> edge = ver.edge;
			for (DualEdge edge1 : edge) {
				int u = edge1.former();
				int v = edge1.from();
				int w = edge1.to();
				double weight = edge1.weight();
				int[] array = new int[7];
				if (u == -1) {
					array[0] = -1;
					array[1] = -1;
				}
				else {
					array[0] = map.get(u).i;
					array[1] = map.get(u).j;
				}
				array[2] = map.get(v).i;
				array[3] = map.get(v).j;
				array[4] = map.get(w).i;
				array[5] = map.get(w).j;
				array[6] = (int)weight;
				hooks.add(array);
			}
    	}
	}
	
	private int turnCost(int u, int v, int w) {
		if (u == -1)
			return forward;
		int i1 = map.get(u).i;
		int j1 = map.get(u).j;
		int i2 = map.get(v).i;
		int j2 = map.get(v).j;
		int i3 = map.get(w).i;
		int j3 = map.get(w).j;
		if (i2>i1 && j1==j2) {
			if (i3>i2 && j2==j3)
				return forward;
			if (i2==i3 && j3>j2)
				return left;
			if (i2==i3 && j3<j2)
				return right;
		}
		if (i2==i1 && j1<j2) {
			if (i3==i2 && j2<j3)
				return forward;
			if (i2>i3 && j3==j2)
				return left;
			if (i2<i3 && j3==j2)
				return right;
		}
		if (i2<i1 && j1==j2) {
			if (i3<i2 && j2==j3)
				return forward;
			if (i2==i3 && j3<j2)
				return left;
			if (i2==i3 && j3>j2)
				return right;
		}
		if (i2==i1 && j1>j2) {
			if (i3==i2 && j2>j3)
				return forward;
			if (i2<i3 && j3==j2)
				return left;
			if (i2>i3 && j3==j2)
				return right;
		}
		return 0;
	}
	
}
