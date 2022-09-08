package col106.assignment6;

import java.util.ArrayList;

public class DualVertex {
	int i;
    int j;
    double weight;
    ArrayList<DualEdge> edge;

    public DualVertex(int i, int j, double weight){
        this.i = i;
        this.j = j;
        this.weight = weight;
        this.edge = new ArrayList<DualEdge>();
    }
}
