package readGraphe;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map.Entry;
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
	private ArrayList<Edge> edges = new ArrayList<Edge>();
	
	//private ArrayList<Node> currentMinWeight = new ArrayList<Node>(); //plus besoin
	//private ArrayList<Node[]> currentMinWeights = new ArrayList<Node[]>();//a revoir 
	private ArrayList<String[]> bellmanArray = new ArrayList<String[]>();
	
	private HashMap<Integer, Node> nodesHashMap= new HashMap<Integer, Node>();
	
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
					/** On cree notre arc grace à ces valeurs*/
					edges.add(new Edge(arrayStringToInt(array)[0], arrayStringToInt(array)[1], arrayStringToInt(array)[2]));
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
			if( edges.get(i).getInitialEnd() == x - 1 && edges.get(i).getFinalEnd() == y - 1)
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
			if( edges.get(i).getInitialEnd() == x - 1 && edges.get(i).getFinalEnd() == y - 1)
				return String.format(" %2d", edges.get(i).getEdgeWeight());
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
			if( edges.get(i).getEdgeWeight() < 0)
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
	public ArrayList<Integer> successorsOf(int vertex){
		ArrayList<Integer> successorsArray = new ArrayList<Integer>();
		
		for(int i = 0; i < this.edges.size(); i++) {
			if( edges.get(i).getInitialEnd() == vertex) {
				successorsArray.add(edges.get(i).getFinalEnd());
			}	
		}
		
		/** On trie par ordre croissant*/
		Collections.sort(successorsArray);
		
		return successorsArray;
	}
	
	public ArrayList<Integer> predeccessorOf(int vertex){
		ArrayList<Integer> predeccessorsArray = new ArrayList<Integer>();
		
		for(int i = 0; i < this.edges.size(); i++) {
			if( edges.get(i).getFinalEnd() == vertex) {
				predeccessorsArray.add(edges.get(i).getInitialEnd());
			}	
		}
		
		if(predeccessorsArray.size() == 0) predeccessorsArray.add(vertex);
		
		/** On trie par ordre croissant*/
		Collections.sort(predeccessorsArray);
		
		return predeccessorsArray;
	}
	
	/**
	 * Permet d'afficher les successeurs d'un sommet
	 * @param vertex
	 */
	public void printSuccessorsOf(int vertex) {
		ArrayList<Integer> successorsArray =  successorsOf(vertex);
		
		System.out.print("Successeurs de " + vertex + ": ");
		for(int i = 0; i < successorsArray.size(); i++) {
			if(i < successorsArray.size() - 1)
				System.out.print(successorsArray.get(i) + ", ");
			else
				System.out.println(successorsArray.get(i));
		}

	}
	
	public void printPredeccessorsOf(int vertex) {
		ArrayList<Integer> predeccessorArray =  predeccessorOf(vertex);
		
		System.out.print("Predeccesseurs de " + vertex + ": ");
		for(int i = 0; i < predeccessorArray.size(); i++) {
			if(i < predeccessorArray.size() - 1)
				System.out.print(predeccessorArray.get(i) + ", ");
			else
				System.out.println(predeccessorArray.get(i));
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
		for(Edge arc : edges) {
			if(arc.getInitialEnd() == initialEnd && arc.getFinalEnd() == finalEnd)
				edgeWeight =  arc.getEdgeWeight();
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
	
	public void printNodeHashMap () {
		System.out.println(nodesHashMap);
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
	
	public void initBellman(int startVertex) {
		/** Creation et initilisation du header*/
		/** l'entete contient la premiere case + les sommets*/
		String[] header = new String[nbSommets + 1]; 
		header[0] = " CC  ";
		for(int i = 0; i < nbSommets; i++) {
			header[i + 1] = "  " + Integer.toString(i) + "  ";
		}
		bellmanArray.add(header);	
		
		/** Initialisation..*/
		for(int i = 0; i < nbSommets; i++) {
			if(i == startVertex) {
				nodesHashMap.put(i, new Node(i, 0));
			}else {
				nodesHashMap.put(i, new Node(i, Integer.MAX_VALUE));
			}
			
		}	
	}
	
	//public void met(successeurdesommet)
	
	/**
	 * Algorithme de Bellman
	 * en cours: header
	 * @param sommetDepart
	 */
	public void bellman(int startVertex) {
		System.out.println("\n -----ALGORITHME DE BELLMAN-----");
		
		/** Permet d'initiliser*/
		initBellman(startVertex);		
		
		int k = 1;
		boolean stop = false;
		//int predecessor;
		
		ArrayList <Integer> predecessors = new ArrayList<>();
		predecessors.add(startVertex); 
//		
		ArrayList <Integer> actualVertices = new ArrayList<>();
		ArrayList <Integer> nextVertices = new ArrayList<>();

		actualVertices.add(startVertex);
//		
		do {
			String[] tmpLine = new String[nbSommets + 1];
			tmpLine[0] = " k=" + Integer.toString(k) + " ";
			
			for (int actualVertex : actualVertices) {
				for (int successor : successorsOf(actualVertex)) {
					nodesHashMap.get(successor).setVertex(actualVertex);
					nodesHashMap.get(successor).setDistance(min(whatIsEdgeWeight(actualVertex, successor), nodesHashMap.get(successor).getDistance()));
					nextVertices.add(successor);
				}
			}		
		
			System.out.println(actualVertices);
			actualVertices.clear();
			for (int nextVertex : nextVertices) {
				actualVertices.add(nextVertex);
			}
			nextVertices.clear();
			
//			for (int predecessor : predecessors) {
//				for (int successor : successorsOf(predecessor)) {
//						nodesHashMap.get(successor).setVertex(predecessor);
//						nodesHashMap.get(successor).setDistance(min(whatIsEdgeWeight(predecessor, successor), nodesHashMap.get(successor).getDistance()));;
//						printPredeccessorsOf(vertex);
//						printPredeccessorsOf(vertex);
//				}
//			}
			
			/** Quand on a fini tous les calul, on enregistre le tout en string pour l'afficher*/
			for (int i = 1; i < tmpLine.length; i++) {
				tmpLine[i] = nodesHashMap.get(i - 1).toString();
			}

			bellmanArray.add(tmpLine);
			printSuccessorsOf(startVertex);
			printNodeHashMap();

			if(k == 4) stop = true;
			k++;
		}while(!stop);
	}
	
	public void dijkstra(int initialEnd) {
		System.out.println("\n -----ALGORITHME DE DIJKSTRA-----");

	}
	
	public void calculateMinValuePaths() {
		//Scanner sc = new Scanner(System.in);
		
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
		
		for (Edge edge : edges) {
			tmp += "" + edge + "\n";
		}
		return tmp;
	}
}
