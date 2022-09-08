package col106.assignment6;

import java.util.List;
import java.util.PriorityQueue;
import java.util.ArrayList;
import java.util.Collections;

public class Dijkstra {
	
	private enum State {UNVISITED, VISITED, COMPLETE};
	private Digraph G;
	private ArrayList<Vertex> vertices;
	private ArrayList<Edge> edges;
	
	public Dijkstra(Digraph G) {
		this.G = G;
		vertices = new ArrayList<>();
		edges = new ArrayList<>();
	}
	
	public void add (int former, int from, int to, int cost) {
		Edge edge = findEdge(former, from, to);
		if (edge == null)
			edges.add(new Edge(former, from, to, cost));
	}
	
	private Vertex findVertex (int v1, int v2) {
		for (Vertex ver : vertices) {
			if (ver.value1==v1 && ver.value2==v2)
				return ver;
		}
		return null;
	}
	
	private Edge findEdge (Vertex v1, Vertex v2) {
		for (Edge edge : edges) {
			if (edge.from.equals(v1) && edge.to.equals(v2))
				return edge;
		}
		return null;
	}
	
	private Edge findEdge (int former, int from, int to) {
		for (Edge edge : edges) {
			if (edge.from.value1==former && edge.from.value2==from && edge.to.value1==from && edge.to.value2==to)
				return edge;
		}
		return null;
	}

	private boolean Dijkstra (int v1, int v2) {
		if (vertices.isEmpty()) 
			return false;
		resetDistances();
		Vertex source = findVertex(v1, v2);
		if (source == null) 
			return false;
		source.minDistance = 0;
		PriorityQueue<Vertex> pq = new PriorityQueue<>();
		pq.add(source);
		while (!pq.isEmpty()) {
			Vertex u = pq.poll();
			for (Vertex v : u.outgoing) {
				Edge e = findEdge(u, v);
				if (e == null) 
					return false;
				int totalDistance = u.minDistance + e.cost;
				if (totalDistance < v.minDistance) {
					pq.remove(v);
					v.minDistance = totalDistance;
					v.previous = u;
					pq.add(v);
				}
			}
		}
		return true;
	}
	
	private List<int[]> getShortestPath (Vertex target) {
		List<int[]> path = new ArrayList<int[]>();
		if (target == null)
			return path;
		if (target.minDistance == Integer.MAX_VALUE)
			return path;
		for (Vertex v = target; v !=null; v = v.previous) {
			int[] arr = new int[2];
			arr[0] = v.value2;
			arr[1] = v.minDistance;
			path.add(arr);
		}
		Collections.reverse(path);
		return path;
	}
	
	private void resetDistances() {
		for (Vertex ver : vertices) {
			ver.minDistance = Integer.MAX_VALUE;
			ver.previous = null;
		}
	}
	
	public List<int[]> getPath (int former, int from, int to) {
		boolean test = Dijkstra(former, from);
		if (test == false) 
			return null;
		List<int[]> path1 = getShortestPath(findVertex(to-1, to));
		List<int[]> path2 = getShortestPath(findVertex(to+1, to));
		List<int[]> path3 = getShortestPath(findVertex(to-G.W(), to));
		List<int[]> path4 = getShortestPath(findVertex(to+G.W(), to));
		int temp = Integer.MAX_VALUE;
		if (!(path1==null || path1.size()<=0) && path1.get(path1.size()-1)[1]<temp)
			temp = path1.get(path1.size()-1)[1];
		if (!(path2==null || path2.size()<=0) && path2.get(path2.size()-1)[1]<temp)
			temp = path2.get(path2.size()-1)[1];
		if (!(path3==null || path3.size()<=0) && path3.get(path3.size()-1)[1]<temp)
			temp = path3.get(path3.size()-1)[1];
		if (!(path4==null || path4.size()<=0) && path4.get(path4.size()-1)[1]<temp)
			temp = path4.get(path4.size()-1)[1];
		if (!(path1==null || path1.size()<=0) && path1.get(path1.size()-1)[1]==temp)
			return path1;
		else if (!(path2==null || path2.size()<=0) && path2.get(path2.size()-1)[1]==temp)
			return path2;
		else if (!(path3==null || path3.size()<=0) && path3.get(path3.size()-1)[1]==temp)
			return path3;
		else if (!(path4==null || path4.size()<=0) && path4.get(path4.size()-1)[1]==temp)
			return path4;
		return null;
	}
	
	class Vertex implements Comparable<Vertex> {
		int value1, value2;
		Vertex previous = null;
		int minDistance = Integer.MAX_VALUE;
		List<Vertex> incoming, outgoing;
		State state;
		
		public Vertex(int value1, int value2) {
			this.value1 = value1;
			this.value2 = value2;
			incoming = new ArrayList<>();
			outgoing = new ArrayList<>();
			state = State.UNVISITED;
		}

		@Override
		public int compareTo (Vertex o) {
			return Integer.compare(minDistance, o.minDistance);
		}

		public void addIncoming (Vertex v) {
			incoming.add(v);
		}
		
		public void addOutgoing (Vertex v) {
			outgoing.add(v);
		}

	}

	class Edge {
		Vertex from, to;
		int cost;

		public Edge (int v, int v1, int v2, int cost) {
			from = findVertex(v, v1);
			if (from == null) {
				from = new Vertex(v, v1);
				vertices.add(from);
			}
			to = findVertex(v1, v2);
			if (to == null) {
				to = new Vertex(v1, v2);
				vertices.add(to);
			}
			this.cost = cost;
			from.addOutgoing(to);
			to.addIncoming(from);
		}

	}
	
}
