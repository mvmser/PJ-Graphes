package readGraphe;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * @version 1.0
 *
 */
public class Graphe {
	/** Attribut permettant de connaitre le dossier ou se situent les graphes*/
	private final static String RESOURCES_PATH = "files/";
	
	/** Attribut contenant le nombre de sommets*/
	private int nbSommets;
	int nbArc;
	
	/** Attribut contenant tous les arcs */
	private ArrayList<int[]> arcs = new ArrayList<int[]>();
	
	private ArrayList<Node> currentMinWeight = new ArrayList<Node>();
	private ArrayList<Node[]> currentMinWeights = new ArrayList<Node[]>();//a revoir 
	private ArrayList<String[]> bellmanArray = new ArrayList<String[]>();
	
	/**
	 * Constructeur du Graphe
	 * @param numeroFichier
	 */
	public Graphe(int numeroFichier) {
		readGraphe(numeroFichier);
	}
	
	/**
	 * Permet de connaitre le nombre de sommets d'un graphe
	 * @return nombre de sommets d'un graphe
	 */
	public int getNbSommets() {
		return nbSommets;
	}

	/**
	 * Permet de connaitre le nombre d'arc d'un graphe
	 * @return nombre d'arc d'un graphe
	 */
	public int getNbArc() {
		return nbArc;
	}
	
	/**
	 * Permet de savoir si le fichier existe ou non
	 * @param fileNumber
	 * @return vrai s'il existe faux sinon
	 */
	public static boolean isFileExist(int fileNumber) {
		try {
			Scanner inFile = new Scanner(new File(RESOURCES_PATH + "L3-B2-" + fileNumber + ".txt"));
			inFile.close();
		} catch (FileNotFoundException e) {
			return false;
		}
		
		return true;
	}

	/**
	 * Permet de lire un graphe et de l'enregistrer
	 * @param numeroFichier
	 * @since 1.0
	 */
	public void readGraphe(int numeroFichier) {
		try {
			Scanner inFile = new Scanner(new File(RESOURCES_PATH + "L3-B2-" + numeroFichier + ".txt"));
			
			/** La premiere ligne contient le nombre de sommets*/
			String value = inFile.nextLine();
			
			/** On enregistre ce nombre en entier, tout en ï¿½vitant toute erreur innatendu*/
			try {
				this.nbSommets = Integer.parseInt(value);
			} catch (NumberFormatException nfe) {
				System.out.println("NumberFormatException: " + nfe.getMessage());
			}
			
			/** ArrayList pour stocker temporairement un arc*/
			//ArrayList<Integer> tmpArc = new ArrayList<Integer>();
			
			/** Permet de connaitre le nombre d'arc*/
			nbArc = 0;

			/** Lecture ï¿½ partir de la 2eme ligne jusque la fin*/
			while (inFile.hasNext()) {
				nbArc++;
				value = inFile.nextLine();
				
				/** Le split va generer un tableau de 3 cases (init, valeur, terminale)*/
				String[] array = value.split(" ");
				
				/** On enregistre les valeurs de l'arc dans une ArrayList (apres les avoir converti en entier)*/	
				if(array.length == 3) {
					arcs.add(arrayStringToInt(array));
				}else {
					System.out.println("Le fichier est incorrect, il n'y a pas les 3 elements dï¿½finissant un arc..");
				}
			}
			inFile.close();
		} catch (FileNotFoundException e) {
			System.out.println("ERROR: " + e.getMessage());
		}	
	}

	/**
	 * Permet de convertir un tableau de String en tableau d'entier
	 * @param stringArray
	 * @return un tableau d'entier
	 * @since 1.0
	 */
	public int[] arrayStringToInt(String[] stringArray) {
		int[] intArray = new int[3];
		
		for(int i = 0; i < stringArray.length; i++) {
			try {
				intArray[i] = Integer.parseInt(stringArray[i]);
			} catch (NumberFormatException nfe) {
				System.out.println("NumberFormatException: " + nfe.getMessage());
			}
		}

		return intArray;
	}
	
	/**
	 * Permet d'initialiser la matrice d'adjacence 
	 * @param adjacencyMatrix
	 * @return La matrice d'adjacence sous forme d'un tableau 2D
	 * @since 1.0
	 * @deprecated utiliser initMatrix
	 */
	public String[][] initAdjacencyMatrix(String[][] adjacencyMatrix){
		/** i: les lignes */
		for(int i = 0; i < adjacencyMatrix.length; i++) {
			/** j: les colonnes*/
			for(int j = 0; j < adjacencyMatrix.length; j++) {
				/** Rien en 0,0*/
				if(i == 0 && j == 0)
					adjacencyMatrix[i][j] = "/" + " ";
				else if(j == 0)
					adjacencyMatrix[i][j] = Integer.toString(i - 1) + " ";
				else if(i == 0)
					adjacencyMatrix[i][j] = Integer.toString(j - 1) + " ";
				else 
					adjacencyMatrix[i][j] = "- ";
			}
		}
		
		return adjacencyMatrix;
	}
	
	/**
	 * Permet d'initialiser une matrice 
	 * @param matrix
	 * @return une matrice sous forme d'un tableau 2D
	 * @since 1.0
	 */
	public String[][] initMatrix(String[][] matrix){
		/** i: les lignes */
		for(int i = 0; i < matrix.length; i++) {
			/** j: les colonnes*/
			for(int j = 0; j < matrix.length; j++) {
				/** Rien en 0,0*/
				if(i == 0 && j == 0)
					matrix[i][j] = "/ ";
				else if(j == 0)
					matrix[i][j] = Integer.toString(i - 1) + "";
				else if(i == 0)
					matrix[i][j] = " " + Integer.toString(j - 1) + " ";
				else 
					matrix[i][j] = "  -";
			}
		}
		
		return matrix;
	}
	
	/**
	 * Permet de savoir s'il y a une ncidence entre 2 sommets
	 * de x vers y
	 * @param x
	 * @param y
	 * @return vrai si x -> y (adjacent) sinon faux
	 * @since 1.0
	 */
	public boolean isXIncidentToY(int x, int y) {
		for(int i = 0; i < nbArc; i++) {
			if( (arcs.get(i))[0] == x - 1 && (arcs.get(i))[2] == y - 1)
				return true;
		}

		return false;
	}

	/**
	 * Permet de creer la matrice d'adjacence
	 * @return Tableau 2D contenant la matrice
	 * @since 1.0
	 */
	public String[][] createAdjacencyMatrix(){
		String[][] adjacencyMatrix = new String[this.nbSommets + 1][this.nbSommets + 1];
		
		adjacencyMatrix = initMatrix(adjacencyMatrix);

		/** y: les lignes */
		for(int y = 1; y < adjacencyMatrix.length; y++) {
			/** x: les colonnes*/
			for(int x = 1; x < adjacencyMatrix.length; x++) {
				if(this.isXIncidentToY(x,y))
					adjacencyMatrix[x][y] = "  1";
			}
		}
		return adjacencyMatrix;
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public String whatIsValue(int x, int y) {
		
		for(int i = 0; i < nbArc; i++) {
			if( (arcs.get(i))[0] == x - 1 && (arcs.get(i))[2] == y - 1)
				return String.format(" %2d", (arcs.get(i))[1]);
		}
		return "  -";
	}
	
	/**
	 * @return
	 */
	public String[][] createValuesMatrix(){
		String[][] valuesMatrix = new String[this.nbSommets + 1][this.nbSommets + 1];
		
		valuesMatrix = initMatrix(valuesMatrix);

		/** y: les lignes */
		for(int y = 1; y < valuesMatrix.length; y++) {
			/** x: les colonnes*/
			for(int x = 1; x < valuesMatrix.length; x++) {
				valuesMatrix[x][y] = whatIsValue(x, y);
			}
		}
		
		return valuesMatrix;
	}

	/**
	 * Permet d'afficher la matrice d'adjacence
	 * @since 1.0
	 * @deprecated utiliser plutot printMatrix
	 */
	public void printAdjacencyMatrix() {
		String[][] adjacencyMatrix = this.createAdjacencyMatrix();

		for(int i = 0; i < adjacencyMatrix.length; i++) {
			for(int j = 0; j < adjacencyMatrix.length; j++) {
					System.out.print(adjacencyMatrix[i][j]);
			}
			System.out.println("");
		}
	}
	
	/**
	 * Permet d'afficher une matrice
	 * @param matrix
	 * @since 1.0
	 */
	public void printMatrix(String[][] matrix) {
		for(int i = 0; i < matrix.length; i++) {
			for(int j = 0; j < matrix.length; j++) {
					System.out.print(matrix[i][j]);
			}
			System.out.println("");
		}
	}
	
	/**
	 * Permet de savoir si un arc à une valeur négative
	 * @return vrai si l'arc à une valeur negative, faux sinon
	 */
	public boolean isArcNegativeValue() {
		for(int i = 0; i < nbArc; i++) {
			if( (this.arcs.get(i))[1] < 0)
				return true;
		}
		return false;
	}
	
	/**
	 * Permet de connaitre la valeur minimal entre 2 entiers
	 * @param a
	 * @param b
	 * @return l'entier minimal
	 */
	public int min(int a, int b) {
		if(a <= b) return a;
		return b;		
	}
	
	/**
	 * Permet de connaitre la valeur minimal entre 1 entier et un double 
	 * (dans le cas ou la valeur est inifinie)
	 * @param a
	 * @param b
	 * @return l'entier minimal
	 * @deprecated utiliser la fonction min(int,int)
	 */
	public int min(int a, double b) {
		if(a <= b) return a;
		return (int)b;		
	}
	
	/**
	 * Permet de retourner le ou les successeurs d'un sommet 
	 * @param vertices
	 * @return tableau de successeurs
	 */
	public ArrayList<Integer> successorsOf(int initialEnd){
		ArrayList<Integer> successorsArray = new ArrayList<Integer>();
		
		for(int i = 0; i < this.arcs.size(); i++) {
			if( (this.arcs.get(i))[0] == initialEnd) {
				successorsArray.add((this.arcs.get(i))[2]);
			}	
		}
		
		/** On trie par ordre croissant*/
		Collections.sort(successorsArray);
		
		return successorsArray;
	}
	
	/**
	 * Permet d'afficher les successeurs d'un sommet
	 * @param initialEnd
	 */
	public void printSuccessorsOf(int initialEnd) {
		ArrayList<Integer> successorsArray =  successorsOf(initialEnd);
		
		System.out.print("Successeurs de " + initialEnd + ": ");
		for(int i = 0; i < successorsArray.size(); i++) {
			if(i < successorsArray.size() - 1)
				System.out.print(successorsArray.get(i) + ", ");
			else
				System.out.println(successorsArray.get(i));
		}

	}
	/**
	 * Permet de connaitre la valeur entre 2 arc
	 * utilisé uniquement entre un sommet et son successeur
	 * @param initialEnd
	 * @param finalEnd
	 * @return
	 */
	public int whatIsEdgeWeight(int initialEnd, int finalEnd) {	
		int edgeWeight = Integer.MAX_VALUE;
		for(int[] arc : arcs) {
			if(arc[0] == initialEnd && arc[2] == finalEnd)
				edgeWeight =  arc[1];
		}
		return edgeWeight;
	}
	
	/**
	 * Permet d'afficher le tableau issu de l'algorithme de bellman
	 * @param bellmanArray
	 */
	public void printBellmanArray() {
		for(String[] line : bellmanArray) {
			for (int i = 0; i < line.length; i++) {
				System.out.print(line[i]);
			}
			System.out.println("");
		}
	}
	
	public void printCurrentMinWeight () {
		System.out.println(currentMinWeight);
	}
	
	/**
	 * @deprecated Arrays.fill()
	 * @param initialEnd
	 * @param k
	 */
	public void fillArray(int initialEnd, int k) {
		for(int i = 0; i < nbSommets; i++) {
		}
		//on passe à l'iteration suivante
		k++;
		fillArray(initialEnd, k);
	}
	
	public void initBellman() {
		/** Creation et initilisation du header*/
		/** l'entete contient la premiere case + les sommets*/
		String[] header = new String[nbSommets + 1]; 
		header[0] = " CC  ";
		for(int i = 0; i < nbSommets; i++) {
			header[i + 1] = "  " + Integer.toString(i) + "  ";
		}
		bellmanArray.add(header);		
	}
	
	//public void met(successeurdesommet)
	
	/**
	 * Algorithme de Bellman
	 * en cours: header
	 * @param sommetDepart
	 */
	public void bellman(int startVertex) {
		System.out.println("\n -----ALGORITHME DE BELLMAN-----");
		
		/** Permet d'initiliser l'entete*/
		initBellman();
		
		
		/** Initialisation de l'algorithme */
		int k = 1;
		
		//tmpLine : ligne en court ui sera rajoutée au fur et a mesure à bellmanArray
		String[] tmpLine = new String[nbSommets + 1];
		tmpLine[0] = " k=" + Integer.toString(k) + " ";
		Node tmpNode = new Node(); 
		startVertex = 0;
		
		for(int i = 0; i < nbSommets; i++) {
			if(i == startVertex) {
				tmpNode.setDistance(0);
				tmpNode.setVertex(i);
				currentMinWeight.add(tmpNode);
				tmpNode = new Node();
			}else {
				tmpNode.setDistance(Integer.MAX_VALUE);
				tmpNode.setVertex(i);
				currentMinWeight.add(tmpNode);
				tmpNode = new Node();
			}
		}	
		printCurrentMinWeight();

		/** reste modifier les successeurs.. */
		int tmpInt = Integer.MAX_VALUE;
		//int tmpFinal;
		Node tmpMinNode = new Node();
		for (Node node : currentMinWeight) {
			for(int successor : successorsOf(startVertex)) {
				if(successor == node.getVertex()) {
					System.out.println("node " + successor + " poids: "+ whatIsEdgeWeight(startVertex, successor));
					if(whatIsEdgeWeight(startVertex, successor) < node.getDistance()) {
						System.out.println(whatIsEdgeWeight(startVertex, successor));
						node.setDistance(whatIsEdgeWeight(startVertex, successor));
//						tmpInt = whatIsEdgeWeight(startVertex, i);
//						//tmpFinal = node.getInitialEnd();
//						tmpMinNode.setVertex(node.getVertex());
//						tmpMinNode.setDistance(whatIsEdgeWeight(startVertex, i));
					}
				}
			}
		}
		printCurrentMinWeight();
		
		/** Quand on a fini tous les calul, on enregistre le tout en string pour l'afficher*/
		for (int i = 1; i < tmpLine.length; i++) {
			tmpLine[i] = currentMinWeight.get(i - 1).toString();
		}
		//System.out.println(tmpMinNode.getInitialEnd());
		if(tmpMinNode.getVertex() != 0)
			tmpLine[tmpMinNode.getVertex()] = tmpMinNode.toString();
		
		bellmanArray.add(tmpLine);
		
		/** a faire ligne suivante*/
//		tmpLine = new String[nbSommets + 1];
//		bellmanArray.add(tmpLine);
//
//		/** Début de l'algorithme*/
//		printSuccessorsOf(startVertex);
//		for(int successor : successorsOf(startVertex)) {
//			System.out.println(whatIsEdgeWeight(startVertex, successor));
//		}
//		
//		String[] line = new String[nbSommets + 1]; 
//		line[0] = " k=" + k + " ";
//		for(int i = 0; i < nbSommets; i++) {
//			line[i + 1] = " " + Integer.toString(i) + " ";
//		}
	}
	
	public void dijkstra(int initialEnd) {
		System.out.println("\n -----ALGORITHME DE DIJKSTRA-----");

	}
	
	public void calculateMinValuePaths() {
		Scanner sc = new Scanner(System.in);
		
		bellman(0);
		printBellmanArray();
//		if(isArcNegativeValue()) {
//			System.out.println("Il y a presence d'au moins un arc a valeur negative. \n");
//			System.out.println("-> L'algorithme de Bellman sera execute.");
//			System.out.println("Quel est le sommet de depart ?");
//			
//			int sommetDepart = sc.nextInt();
//			bellman(sommetDepart);
//		}else {
//			System.out.println("Algorithme de Bellman ou Dijkstra ?");
//			System.out.println("1. Bellman");
//			System.out.println("2. Dijkstra");
//			int choix = sc.nextInt();
//			
//			System.out.println("Quel est le sommet de depart ?");
//			int sommetDepart = sc.nextInt();
//
//			if(choix == 1) {
//				bellman(sommetDepart);
//			}else if(choix == 2) {
//				dijkstra(sommetDepart);
//			}
//		}
	}
	
	@Override
	public String toString() {
		String tmp = "";
		
		for(int i = 0; i < nbArc; i++) {
			tmp += "[";
			for(int j = 0; j < 3; j++) {
				tmp += Integer.toString( (arcs.get(i))[j]) + " ";
			}
			tmp += "] \n";
		}
		return tmp;
	}
}
