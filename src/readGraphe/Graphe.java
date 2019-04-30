package readGraphe;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
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

	private ArrayList<String[]> bellmanArray = new ArrayList<String[]>();
	private ArrayList<String[]> dijkstraArray = new ArrayList<String[]>();

	private HashMap<Integer, Node> nodesHashMap= new HashMap<Integer, Node>();
	
	private final int INFINITY = Integer.MAX_VALUE;
	private final int NO_PRED = -1;
	
	private static ArrayList<Integer> filesNames = new ArrayList<Integer>();
	
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
	 * Permet de connaitre tous les fichiers présent dans le dossier files
	 * @return
	 */
	public static ArrayList<Integer> lookFiles() {
		File repertoire = new File(RESOURCES_PATH);
		File[] files = repertoire.listFiles();
		String fileName;
		for (File file : files) {
			fileName = (file.getName() != null) ? file.getName().substring(0,file.getName().indexOf('.')) : "";
			try {
				filesNames.add(Integer.parseInt(fileName.substring(6)));
			}catch (NumberFormatException  e) {
				System.out.println("Pb dans le nom du fichier.. IL FAUT SUIVRE: L3-B2-NUMERO.txt");
			}
		}
		Collections.sort(filesNames);
		return filesNames;
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
	 * Permet de connaitre la valeur entre 2 sommets x->y
	 * @param x
	 * @param y
	 * @return
	 */
	public String whatIsValue(int x, int y) {
		
		for(int i = 0; i < nbArc; i++) {
			if( edges.get(i).getInitialEnd() == x  && edges.get(i).getFinalEnd() == y )
				return String.format(" %2d", edges.get(i).getEdgeWeight());
		}
		return "  -";
	}
	
	/**
	 * Permet de creer la matrice des valeures
	 * @return Tableau 2D contenant les valeur en chaine de caracteres
	 */
	public String[][] createValuesMatrix(){
		String[][] valuesMatrix = new String[this.nbSommets + 1][this.nbSommets + 1];
		
		valuesMatrix = initMatrix(valuesMatrix);

		/** y: les lignes */
		for(int y = 1; y < valuesMatrix.length; y++) {
			/** x: les colonnes*/
			for(int x = 1; x < valuesMatrix.length; x++) {
				valuesMatrix[x][y] = whatIsValue(x-1, y-1);
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
	 * (dans le cas ou la valeur est Double.Infinity)
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
	 * @param un sommet
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
	
	/**
	 * Permet de connaitre le ou les predeccesseurs d'un sommet donné
	 * @param un sommet
	 * @return tableau de predeccesseurs
	 */
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
	 * @param un sommet
	 */
	public void printSuccessorsOf(int vertex) {
		ArrayList<Integer> successorsArray =  successorsOf(vertex);
		
		System.out.print("Successeurs de " + vertex + ": ");
		if(successorsArray.size() == 0)
			System.out.print("/");
		for(int i = 0; i < successorsArray.size(); i++) {
			if(i < successorsArray.size() - 1)
				System.out.print(successorsArray.get(i) + ", ");
			else
				System.out.print(successorsArray.get(i));
		}
		System.out.println("");

	}
	
	/**
	 * Permer d'afficher le ou les predecesseurs d'un sommet
	 * @param un sommet
	 */
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
	 */
	public void printBellmanArray() {
		for(String[] line : bellmanArray) {
			for (int i = 0; i < line.length; i++) {
				System.out.print(line[i]);
			}
			System.out.println("");
		}
	}
	
	/**
	 * Permet d'afficher le tableau issu de l'algorithme de Dijkstra
	 */
	public void printDijkstraArray() {
		for(String[] line : dijkstraArray) {
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
	
	/**
	 * Permet d'initialiser le belman avec un sommet de depart
	 * @param un sommet de depart
	 */
	public void initDijkstra(int startVertex) {
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
				nodesHashMap.put(i, new Node(i, INFINITY));
			}
			
		}	
	}
	
	/**
	 * Permet d'initialiser le belman avec un sommet de depart
	 * @param un sommet de depart
	 */
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
				nodesHashMap.put(i, new Node(i, INFINITY));
			}
			
		}	
	}
	
	//public void met(successeurdesommet)
	
	public ArrayList<String[]> getBellmanArray(){
		return bellmanArray;
	}
	
	private List<Integer> adjacency(Integer[][] G, int v) {
	    List<Integer> result = new ArrayList<Integer>();
	    for (int x = 0; x < G.length; x++) {
	      if (G[v][x] != null) {
	        result.add(x);
	      }
	    }
	    return result;
  	}
	  
	public void bellman(int startVertex) {
		Integer[][] edgeWeight = new Integer[nbArc][nbArc];
		for (Edge edge : edges) {
			edgeWeight[edge.getInitialEnd()][edge.getFinalEnd()] = edge.getEdgeWeight();
		}
		
		Integer[] pred = new Integer[nbSommets];
		Integer[] minDist = new Integer[nbSommets];
		Integer[] tmpMinDist = new Integer[nbSommets];
		Integer[] tmp2MinDist = new Integer[nbSommets];
		
		Arrays.fill(pred, NO_PRED);
		
		//init V0 à +inf
	    Arrays.fill(minDist, INFINITY);
	    //V0[source] = 0
	    minDist[startVertex] = 0;
	    pred[startVertex] = startVertex;
	    
	    int k = 0;
	    
	    
//	    ArrayList<Integer> tmp = new ArrayList<Integer>();
//	    for (int y = 0; y < minDist.length ; y++) {
//	    	k++;
//	    	System.out.println(k);
//	    	if(y != startVertex) {
//	    		for (int x : predeccessorOf(y)) {
//	    			System.out.println("pred: " + x);
//	    			if(minDist[x] == INFINITY)
//	    				tmp.add(INFINITY);
//	    			else
//	    				tmp.add(minDist[x] + whatIsEdgeWeight(x, y));
//	    			//System.out.println("tmp:" + tmp);
//	    			System.out.println("edge" + whatIsEdgeWeight(x, y));
//	    			System.out.println("mijn:" + minDist[x]);
//	    		}
//	    		if(tmp.size() > 0) {
//	    			Collections.sort(tmp);
//	    			System.out.println("tmp:" + tmp);
//		    		minDist[y] = min(minDist[y], tmp.get(0));
//		    		System.out.println("mijnafter:" + tmp.get(0));
//	    		}
//	    		tmp.clear();
//	    	}
//	    }
	    
	    for (int i = 0; i < minDist.length ; i++) {
		      for (int v = 0; v < nbSommets; v++) {
		        for (int x : adjacency(edgeWeight, v)) {
		          if (minDist[x] > minDist[v] + edgeWeight[v][x]) {
		            minDist[x] = minDist[v] + edgeWeight[v][x];
		            pred[x] = v;
		          }
		        }
		      }
		    }
	    
	  //cycles negatifs
	    for (int v = 0; v < nbArc; v++) {
	      for (int x : adjacency(edgeWeight, v)) {
	        if (minDist[x] > minDist[v] + edgeWeight[v][x]) {
	          System.out.println("Negative cycle found");
	        }
	      }
	    }
	    
	    Integer[][] result = {pred, minDist};

	    for (int i = 0; i < result.length; i++) {
			for (int j = 0; j < minDist.length ; j++) {
				System.out.print(result[i][j] + " ");
			}
			System.out.println("");
		}
	}
	
	/**
	 * Algorithme de Bellman
	 * @deprecated ancienne version..
	 * @param sommetDepart
	 */
	public void oldBellman(int startVertex) {
		System.out.println("\n -----ALGORITHME DE BELLMAN-----");
		
		/** Permet d'initiliser*/
		initBellman(startVertex);		
		
		boolean stop = false;
		//int predecessor;
		
		ArrayList <Integer> predecessors = new ArrayList<>();
		predecessors.add(startVertex); 
//		
		ArrayList <Integer> actualVertices = new ArrayList<>();
		ArrayList <Integer> nextVertices = new ArrayList<>();

		actualVertices.add(startVertex);
		int actualDistance = 0;
		
		String[] initLine = new String[nbSommets + 1];
		initLine[0] = " k=0 ";
		/** Quand on a fini tous les calul, on enregistre le tout en string pour l'afficher*/
		for (int i = 1; i < initLine.length; i++) {
			initLine[i] = nodesHashMap.get(i - 1).toString();
		}

		bellmanArray.add(initLine);
		
		int k = 1;
		do {
			//debug
			System.out.print("k=" + k);
			//
			
			String[] tmpLine = new String[nbSommets + 1];
			tmpLine[0] = " k=" + Integer.toString(k) + " ";
			
			for (int actualVertex : actualVertices) {
				//debug
				System.out.print(" Sommet en cours: " + actualVertex);
				//
				for (int successor : successorsOf(actualVertex)) {						
					nodesHashMap.get(successor).setVertex(actualVertex);
					
					if(nodesHashMap.get(actualVertex).getDistance() != INFINITY)
						actualDistance = nodesHashMap.get(actualVertex).getDistance();
					else
						actualDistance = 0;
					
					nodesHashMap.get(successor).setDistance(actualDistance + min(whatIsEdgeWeight(actualVertex, successor), nodesHashMap.get(successor).getDistance()));
					nextVertices.add(successor);
					
				}
				//debug
				printSuccessorsOf(actualVertex);

			}		
			//System.out.println(actualVertices);
			actualVertices.clear();
			for (int nextVertex : nextVertices) {
				actualVertices.add(nextVertex);
			}
			nextVertices.clear();
			
			/** Quand on a fini tous les calul, on enregistre le tout en string pour l'afficher*/
			for (int i = 1; i < tmpLine.length; i++) {
				tmpLine[i] = nodesHashMap.get(i - 1).toString();
			}

			bellmanArray.add(tmpLine);
			//printNodeHashMap();

			if(k == 5) stop = true;
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
