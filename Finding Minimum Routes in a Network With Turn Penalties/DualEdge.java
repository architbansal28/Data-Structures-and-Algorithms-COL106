package col106.assignment6;

public class DualEdge {
	int u;
	int v;
	int w;
	double weight;
	
	public DualEdge(int u, int v, int w, double weight) {
        this.u = u;
        this.v = v;
        this.w = w;
        this.weight = weight;
    }
	
	public int former() {
        return u;
    }
	
	public int from() {
        return v;
    }
	
	public int to() {
        return w;
    }
	
	public double weight() {
        return weight;
    }

}
