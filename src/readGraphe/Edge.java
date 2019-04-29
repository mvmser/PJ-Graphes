package readGraphe;

public class Edge {

	private int initialEnd;
	private int edgeWeight ;
	private int finalEnd;
	
	public Edge(int initialEnd, int edgeWeight, int finalEnd) {
		this.initialEnd = initialEnd;
		this.edgeWeight = edgeWeight;
		this.finalEnd = finalEnd;
	}

	public Edge() {
	}

	public int getInitialEnd() {
		return initialEnd;
	}

	public void setInitialEnd(int initialEnd) {
		this.initialEnd = initialEnd;
	}

	public int getEdgeWeight() {
		return edgeWeight;
	}

	public void setEdgeWeight(int edgeWeight) {
		this.edgeWeight = edgeWeight;
	}

	public int getFinalEnd() {
		return finalEnd;
	}

	public void setFinalEnd(int finalEnd) {
		this.finalEnd = finalEnd;
	}

	@Override
	public String toString() {
		return "[" + initialEnd + " -" + edgeWeight + "-> " + finalEnd + "]";
	}
}
