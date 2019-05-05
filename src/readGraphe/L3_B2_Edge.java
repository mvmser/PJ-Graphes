package readGraphe;

/**
 * Un arc contient un sommet de depart, un poids, et un sommet d'arriv�e
 * @author SERHIR
 * @author ZARGA
 * @author CHIBOUT
 */
public class L3_B2_Edge {

	/** Attributs definissant un arc*/
	private int initialEnd;
	private int edgeWeight ;
	private int finalEnd;
	
	/**
	 * Constructeur d'un arc
	 * @param initialEnd sommet de depart
	 * @param edgeWeight poids
	 * @param finalEnd sommet d'arrivee
	 */
	public L3_B2_Edge(int initialEnd, int edgeWeight, int finalEnd) {
		this.initialEnd = initialEnd;
		this.edgeWeight = edgeWeight;
		this.finalEnd = finalEnd;
	}
	
	/**
	 * Permet de recuperer le sommet de depart
	 * @return le sommet de depart d'un arc
	 */
	public int getInitialEnd() {
		return initialEnd;
	}

	/**
	 * Permet de modifier le sommet de depart
	 * @param initialEnd sommet de depart
	 * @deprecated Inutilis�
	 */
	public void setInitialEnd(int initialEnd) {
		this.initialEnd = initialEnd;
	}

	/**
	 * Permet de recuperer le poids d'un arc
	 * @return le poids de l'arc
	 */
	public int getEdgeWeight() {
		return edgeWeight;
	}

	/**
	 * Permet de modifier le poids d'un arc
	 * @param edgeWeight poids
	 * @deprecated Inutilis�
	 */
	public void setEdgeWeight(int edgeWeight) {
		this.edgeWeight = edgeWeight;
	}

	/**
	 * Permer de recuperer le sommet d'arrivee d'un arc
	 * @return le sommet d'arriv� de l'arc
	 */
	public int getFinalEnd() {
		return finalEnd;
	}

	/**
	 * Permet de modifier le sommet d'arriv�e d'un arc
	 * @param finalEnd sommet d'arrivee
	 * @deprecated Inutilis�
	 */
	public void setFinalEnd(int finalEnd) {
		this.finalEnd = finalEnd;
	}

	/**
	 * Pour l'affichage d'un arc
	 */
	@Override
	public String toString() {
		return "[" + initialEnd + " -" + edgeWeight + "-> " + finalEnd + "]";
	}
}
