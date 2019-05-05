package readGraphe;

/**
 * Un noeud contient un sommet source et une distance 
 * pour arriver au noeud à partir du sommet de depart
 * un noeud est utilisé avec une hashmap, dont la clé defini le sommet
 * et le noeud defini la distance et le sommet source sous la forme 10(1)
 * @author SERHIR
 * @author ZARGA
 * @author CHIBOUT
 */
public class L3_B2_Node {

	/** Les attributs definissant un noeud */
	private int vertex;
	private int distance;
	
	/**
	 * Constructeur d'un noeud
	 * @param _vertex sommet
	 * @param _dist distance
	 */
	public L3_B2_Node(int _vertex, int _dist) {
		this.vertex = _vertex;
		this.distance = _dist;
	}

	/**
	 * Permet de connaitre la distance parcourue pour arriver à ce noeud
	 * à partir du sommet de depart
	 * @return distance
	 */
	public int getDistance() {
		return distance;
	}

	/**
	 * Permet de modifier cette distance
	 * @param dist distance
	 */
	public void setDistance(int dist) {
		this.distance = dist;
	}

	/**
	 * Permet de connaitre le sommet de ce noeud
	 * @return sommet du noeud
	 */
	public int getVertex() {
		return vertex;
	}

	/**
	 * Permet de modifier ce sommet
	 * @param vertex sommet
	 */
	public void setVertex(int vertex) {
		this.vertex = vertex;
	}
	
	/**
	 * Permet l'affichage correct d'un noeud dans le tableau de bellman
	 * on veut afficher inf au lieu de 2147483647
	 * on regle l'afichage dans le cas ou il y a un chiffre ou un nombre
	 * -- l'espace apres la ")" permet d'eviter tout decallage non voulu dans le tableau
	 */
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
	
	/**
	 * Permet l'affichage correct d'un noeud dans le tableau de Dijkstra
	 * on veut afficher inf au lieu de 2147483647
	 * on veut afficher . dans le cas où le sommet a deja été visité
	 * (si le sommet est deja visité il prends la valeur de -1 dans le noeud)
	 * enfin on affiche tel quel: 10(1) par ex.
	 * @return l'affichage du noeud sous forme de string
	 */
	public String toStringDijkstra() {
		if(this.distance == Integer.MAX_VALUE) {
			return "inf";
		}else if(this.getDistance() == -1) {
			return ".";
		}else if(this.getDistance() == -2) {
			return "/";
		}else if(this.getDistance() < 50) {
			return "" + Integer.toString(this.distance) + "(" + Integer.toString(this.vertex) + ")";
		}
		
		return "";
	}

	
}
