package readGraphe;

public class L3_B2_Node {

	private int vertex;
	private int distance;
	
	public L3_B2_Node() {
		
	}
	
	public L3_B2_Node(int _vertex, int _dist) {
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
			return " inf  ";
		}else if(this.getDistance() < 10) {
			return " " + Integer.toString(this.distance) + "(" + Integer.toString(this.vertex) + ") ";
		}else {
			return " " + Integer.toString(this.distance) + "(" + Integer.toString(this.vertex) + ")";
		}
	}
	
	public String toStringDijkstra() {
		if(this.distance == Integer.MAX_VALUE) {
			return "inf";
		}else if(this.getDistance() == -1) {
			return ".";
		}else if(this.getDistance() < 50) {
			return "" + Integer.toString(this.distance) + "(" + Integer.toString(this.vertex) + ")";
		}
		
		return "";
	}

	
}
