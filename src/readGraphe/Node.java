package readGraphe;

public class Node {

	private int vertex;
	private int distance;
	
	public Node() {
		
	}
	
	public Node(int _dist, int _vertex) {
		this.vertex = _vertex;
		this.distance = _dist;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int dist) {
		this.distance = dist;
	}

	public int getVertex() {
		return vertex;
	}

	public void setVertex(int vertex) {
		this.vertex = vertex;
	}
	
	@Override
	public String toString() {
		if(this.distance == Integer.MAX_VALUE) {
			return " inf ";
		}else {
			return " " + Integer.toString(this.distance) + "(" + Integer.toString(this.vertex) + ")";

		}
	}

	
}
