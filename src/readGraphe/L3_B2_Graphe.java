package readGraphe;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Scanner;

/**
 * Classe graphe
 * Permet la lecture et la creation d'un graphe
 * Permet aussi d'effectuer tous les calculs liés a ce graphe
 * C'est à dire les matrices d'adjacence et de valeurs, 
 * mais aussi le calcul du plus court chemin
 * @author SERHIR
 * @author ZARGA
 * @author CHIBOUT
 */
public class L3_B2_Graphe {
	/** Attribut permettant de connaitre le dossier ou se situent les graphes*/
	private final static String RESOURCES_PATH = "files/"; //a corriger quand dans le dossier principal
	private final static String RESOURCES_PATH_TRACE = "files/traces/";

	
	/** Attribut contenant le nombre de sommets, le nombre d'arc, le numero du graphe, 
	 * et s'il comporte un cycle negatif ou non*/
	private int nbSommets;
	private int nbArc;
	private int numGraph;
	private boolean isNegativeCycle;
	
	/** Attribut contenant tous les arcs */
	private ArrayList<L3_B2_Edge> edges = new ArrayList<L3_B2_Edge>();

	/** Tableaux contenant le resultat des algorithmes de calcul du plus court chemin */
	private ArrayList<String[]> bellmanArray = new ArrayList<String[]>();
	private ArrayList<String[]> dijkstraArray = new ArrayList<String[]>();

	/** HashMap des noeuds: la clé est le numéro du sommet, le noeud contient les infos à afficher*/
	private HashMap<Integer, L3_B2_Node> nodesHashMap= new HashMap<Integer, L3_B2_Node>();
	private HashMap<Integer, L3_B2_Node> nodesDijkstraHashMap= new HashMap<Integer, L3_B2_Node>();

	/** Attributs constants*/
	private final int INFINITY = Integer.MAX_VALUE;
	private final int NO_PRED = -2;
	private final int POINT = -1;

	
	/** Atribut contenant tous les fichiers existants*/
	private static ArrayList<Integer> filesNames = new ArrayList<Integer>();
	
	/**
	 * Constructeur du Graphe
	 * @param numeroFichier numero du Fichier
	 */
	public L3_B2_Graphe(int numeroFichier) {
		numGraph = numeroFichier;
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
	 * Permet de recuperer le tableau de bellman
	 * @return tableau de bellman
	 */
	public ArrayList<String[]> getBellmanArray(){
		return bellmanArray;
	}
	
	/**
	 * Permet de recuperer le tableau de Dijkstra
	 * @return tableau de dijkstra
	 */
	public ArrayList<String[]> getDijkstraArray(){
		return dijkstraArray;
	}
	
	/** 
	 * Permet de savoir si le graphe comporte un cycle negatif (utilisé pour l'interface graphique)
	 * @return vrai s'il y a presence de cycle negatif, faus sinon
	 */
	public boolean isNegativeCycle(){
		return isNegativeCycle;
	}
	
	/**
	 * Permet de reset tous les tableaux comportant le resultat du calcul des plus court chemins
	 * Utilisable par la version graphique pour eviter tout probleme.
	 */
	public void resetArraysLists(){
		bellmanArray.clear();
		dijkstraArray.clear();
	}
	
	/**
	 * Permet de connaitre tous les fichiers présent dans le dossier files
	 * @return liste des fichiers
	 */
	public static ArrayList<Integer> lookFiles() {
		File repertoire = new File(RESOURCES_PATH);
		File[] files = repertoire.listFiles();
		String fileName;
		for (File file : files) {
			if(!file.getName().contains("trace") && file.getName().contains(".txt")) {
				fileName = (file.getName() != null) ? file.getName().substring(0,file.getName().indexOf('.')) : "";
				try {
					filesNames.add(Integer.parseInt(fileName.substring(6)));
				}catch (NumberFormatException  e) {
					System.out.println("Pb dans le nom du fichier.. IL FAUT SUIVRE: L3-B2-NUMERO.txt");
				}
			}
			
		}
		Collections.sort(filesNames);
		return filesNames;
	}
	
	/**
	 * Permet de savoir si le fichier existe ou non
	 * @param fileNumber numero du fichier
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
	 * @param numeroFichier numero du fichier
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
					/** On cree notre arc grace ï¿½ ces valeurs*/
					edges.add(new L3_B2_Edge(arrayStringToInt(array)[0], arrayStringToInt(array)[1], arrayStringToInt(array)[2]));
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
	 * @param stringArray tableau de string
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
	 * Permet d'initialiser une matrice 
	 * @param matrix matrice
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
	 * @param x sommet x
	 * @param y sommet y
	 * @return vrai si x -- y (adjacent) sinon faux
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
	 * Permet de connaitre la valeur entre 2 sommets x--y
	 * @param x sommet x
	 * @param y sommet y
	 * @return valeur entre les 2 sommets
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
	 * Permet d'afficher une matrice
	 * @param matrix matrice
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
	 * Permet de savoir si un arc ï¿½ une valeur nï¿½gative
	 * @return vrai si l'arc ï¿½ une valeur negative, faux sinon
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
	 * @param a entier a
	 * @param b entier b
	 * @return l'entier minimal
	 */
	public int min(int a, int b) {
		if(a <= b) return a;
		return b;		
	}
	
	/**
	 * Permet de retourner le ou les successeurs d'un sommet 
	 * @param vertex un sommet
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
	 * Permet de connaitre le ou les predeccesseurs d'un sommet donnï¿½
	 * @param vertex un sommet
	 * @return tableau de predeccesseurs
	 */
	public ArrayList<Integer> predeccessorOf(int vertex){
		ArrayList<Integer> predeccessorsArray = new ArrayList<Integer>();
		
		for(int i = 0; i < this.edges.size(); i++) {
			if( edges.get(i).getFinalEnd() == vertex) {
				predeccessorsArray.add(edges.get(i).getInitialEnd());
			}	
		}
		
		//if(predeccessorsArray.size() == 0) predeccessorsArray.add(vertex);
		
		/** On trie par ordre croissant*/
		Collections.sort(predeccessorsArray);
		
		return predeccessorsArray;
	}
	
	/**
	 * Permet d'afficher les successeurs d'un sommet
	 * @param vertex un sommet
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
	 * @param vertex un sommet
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
	 * utiliser uniquement entre un sommet et son successeur
	 * @param initialEnd sommet de depart
	 * @param finalEnd sommet d'arrivee
	 * @return le poids entre les 2 sommets
	 */
	public int whatIsEdgeWeight(int initialEnd, int finalEnd) {	
		int edgeWeight = Integer.MAX_VALUE;
		for(L3_B2_Edge arc : edges) {
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
	 * Permet d'initialiser le belman avec un sommet de depart
	 * @param  startVertex un sommet de depart
	 */
	public void initDijkstra(int startVertex) {
		/** Creation et initilisation du header*/
		/** l'entete contient la premiere case + les sommets*/
		String[] header = new String[nbSommets + 1]; 
		header[0] = " CC\t";
		for(int i = 0; i < nbSommets; i++) {
			header[i + 1] = "" + Integer.toString(i) + "\t";
		}
		dijkstraArray.add(header);	
		
		/** Initialisation..*/
		for(int i = 0; i < nbSommets; i++) {
			nodesDijkstraHashMap.put(i, new L3_B2_Node(i, NO_PRED));
			for (int pred : predeccessorOf(i)) {	
				 if(startVertex == pred)
					nodesDijkstraHashMap.put(i, new L3_B2_Node(i, whatIsEdgeWeight(pred, i)));
				 else
					nodesDijkstraHashMap.put(i, new L3_B2_Node(i, INFINITY));
			}

		}	
		
		/** Initialisation..*/
		for (int succ : successorsOf(startVertex)) {	
			nodesDijkstraHashMap.put(succ, new L3_B2_Node(startVertex, whatIsEdgeWeight(startVertex, succ)));
		}	
		/** -1 pour afficher le point dans le tableau*/
		nodesDijkstraHashMap.put(startVertex, new L3_B2_Node(startVertex, POINT));
	}
	
	/**
	 * Permet d'initialiser le belman avec un sommet de depart
	 * @param startVertex un sommet de depart
	 */
	public void initBellman(int startVertex) {
		/** Creation et initilisation du header*/
		/** l'entete contient la premiere case + les sommets*/
		String[] header = new String[nbSommets + 1]; 
		header[0] = " CC  ";
		for(int i = 0; i < nbSommets; i++) {
			header[i + 1] = "  " + Integer.toString(i) + "   ";
		}
		bellmanArray.add(header);	
		
		/** Initialisation..*/
		for(int i = 0; i < nbSommets; i++) {
			if(i == startVertex) {
				nodesHashMap.put(i, new L3_B2_Node(i, 0));
			}else {
				nodesHashMap.put(i, new L3_B2_Node(i, INFINITY));
			}
			
		}	
		
		String[] initLine = new String[nbSommets + 1];
		initLine[0] = " k=0 ";
		/** Quand on a fini tous les calul, on enregistre le tout en string pour l'afficher*/
		for (int i = 1; i < initLine.length; i++) {
			initLine[i] = nodesHashMap.get(i - 1).toString();
		}

		isNegativeCycle = false;
		bellmanArray.add(initLine);
	}
	
	/**
	 * Algorithme de Bellman
	 * @param startVertex sommet de Depart
	 */
	public void bellman(int startVertex) {
		System.out.println("\n -----ALGORITHME DE BELLMAN-----");
		System.out.println(" -----      Sommet: "+startVertex+"      -----\n");
		
		/** Permet d'initiliser*/
		initBellman(startVertex);		
		
		if(successorsOf(startVertex).isEmpty()) {
			System.out.println("Le sommet de depart " + startVertex + " n'a aucun successeur.");
			return;
		}
		boolean stop = false;

		ArrayList <Integer> actualVertices = new ArrayList<>();
		ArrayList <Integer> nextVertices = new ArrayList<>();

		actualVertices.add(startVertex);
		int actualDistance = 0;
		
		int k = 1;
		do {
			String[] tmpLine = new String[nbSommets + 1];
			tmpLine[0] = " k=" + Integer.toString(k) + " ";
			
			for (int actualVertex : actualVertices) {
				for (int successor : successorsOf(actualVertex)) {						
					
					if(nodesHashMap.get(actualVertex).getDistance() != INFINITY)
						actualDistance = nodesHashMap.get(actualVertex).getDistance();
					else
						actualDistance = 0;
					
					nodesHashMap.get(successor).setVertex(actualVertex);
					nodesHashMap.get(successor).setDistance(min(nodesHashMap.get(successor).getDistance(), actualDistance + whatIsEdgeWeight(actualVertex, successor)));
					
					/** Permet de detecter la presence d'un circuit absorbant*/
					if(nodesHashMap.get(successor).getDistance() != INFINITY 
							&& nodesHashMap.get(successor).getDistance() < 0){
						isNegativeCycle = true;
						System.out.println("Le graphe comporte un circuit absorbant..");
						bellmanArray.clear();
						return;
					}
					/** */
		
					nextVertices.add(successor);
				}
			}		
			
			actualVertices.clear();
			for (int nextVertex : removeDuplicates(nextVertices)) {
				actualVertices.add(nextVertex);
			}
			nextVertices.clear();
			
			/** Quand on a fini tous les calul, on enregistre le tout en string pour l'afficher*/
			for (int i = 1; i < tmpLine.length; i++) {
				tmpLine[i] = nodesHashMap.get(i - 1).toString();
			}

			bellmanArray.add(tmpLine);
			System.out.println(bellmanArray.size() + " k: " + k);
			if(stop(tmpLine)) stop = true;
			if(k == 20) stop = true;
			k++;
		}while(!stop);
		printBellmanArray();
		trace(startVertex, bellmanArray, "Bellman");
	}
	
	/**
	 * Permet de savoir quand l'algo doit d'arreter
	 * si 2 itérations sont égales, retourner vrai
	 * @param tmpLine ligne a comparer
	 * @return vrai si il faut stoper faux sinon
	 */
	public boolean stop(String[] tmpLine) {
		for (int i = 1; i < tmpLine.length; i++) {
			if(!(bellmanArray.get(bellmanArray.size() - 2)[i]).equals(tmpLine[i]))
				return false;
//			if((bellmanArray.get(bellmanArray.size() - 3)[i]).equals(tmpLine[i]))
//				return true;
		}
		return true;
	}
	
	/**
	 * Permet de supprimer les duplicats d'un arraylist
	 * @param list liste
	 * @return arraylist sans duplicata
	 */
	public ArrayList<Integer> removeDuplicates(ArrayList<Integer> list) 
    { 
        ArrayList<Integer> newList = new ArrayList<Integer>(); 
  
        for (Integer element : list) { 
            if (!newList.contains(element)) { 
                newList.add(element); 
            } 
        } 
  
        return newList; 
    } 
	
	/**
	 * Permet de calculer le chemin de valeurs minimal avec l'algorithme de Dijkstra
	 * @param startVertex sommet de depart
	 */
	public void dijkstra(int startVertex) {
		System.out.println("\n -----ALGORITHME DE DIJKSTRA-----");
		initDijkstra(startVertex);
		
		ArrayList<Integer> CC = new ArrayList<Integer>();
		ArrayList<Integer> M = new ArrayList<Integer>();
		
		/** Initialisation de CC et M*/
		CC.add(startVertex);
		for (int i = 0; i < nbSommets; i++) {
			if(i != startVertex) M.add(i);
		}
		
		/** Initialisation de la line contenant la 1ere iteration*/
		String[] initLine = new String[nbSommets + 1];
		initLine[0] = arrayListToString(CC) + "";
		
		/** Quand on a fini tous les calul, on enregistre le tout en string pour l'afficher*/
		for (int i = 1; i < initLine.length; i++) {
			initLine[i] = nodesDijkstraHashMap.get(i - 1).toStringDijkstra() + "\t";
			//System.out.println("i: " + i + nodesDijkstraHashMap.get(i - 1).toStringDijkstra());
		}
		
		dijkstraArray.add(initLine);
			
		do
		{
			Entry<Integer, L3_B2_Node> currentNode = getLowestDistance(nodesDijkstraHashMap, M);
			if(currentNode != null) {
				/** Permet de mettre un sommet de M vers CC*/
				for (int i = 0; i < M.size(); i++) {
					if(currentNode != null && M.get(i) == currentNode.getKey()) {
						CC.add(M.get(i));
						M.remove(i);
						break;
					}
				}
				/** On va regarder ses successeurs*/
				for (Integer successor : successorsOf(currentNode.getKey()) ) {
					for(Entry<Integer, L3_B2_Node> entry : nodesDijkstraHashMap.entrySet()) {
					    if(entry.getKey() == successor) {
					    	if(entry.getValue().getDistance() != POINT) {
					    		L3_B2_Node node = entry.getValue();
						    	if(currentNode.getValue().getDistance() != INFINITY ) {
						    		node.setDistance(currentNode.getValue().getDistance() +
							    			whatIsEdgeWeight(currentNode.getKey(), successor));
						    		node.setVertex(currentNode.getKey());
						    	}
					    	}
					    }
					}
				}
				
				/** On connait ce sommet, on inquique un point a la ligne suivante*/
				currentNode.getValue().setDistance(POINT);
				
				/** Quand on a fini tous les calul, on enregistre le tout en string pour l'afficher*/
				String[] tmpLine = new String[nbSommets + 1];
				tmpLine[0] = arrayListToString(CC);
				for (int i = 1; i < tmpLine.length; i++) {
					tmpLine[i] = nodesDijkstraHashMap.get(i - 1).toStringDijkstra() + "\t";
					if(CC.size() >= 8)
						tmpLine[1] = nodesDijkstraHashMap.get(1 - 1).toStringDijkstra() + "";
				}
				

				dijkstraArray.add(tmpLine);
			}else {
				M.clear();
			}
		}while(!M.isEmpty());
		
		printDijkstraArray();
		/** On enregistre la trace*/
		trace(startVertex, dijkstraArray, "Dijkstra");
	}
	
	/**
	 * Pour l'algorithme de Dijkstra
	 * Permet de connaite le sommet qui a la distance la plus petite
	 * @param nodesDijkstraHashMap hashmap contenant le sommet ainsi que son noeud
	 * @param M les sommets pas encore visités
	 * @return le couple clé-valeur avec la distance minimale
	 */
	public Entry<Integer, L3_B2_Node> getLowestDistance(HashMap<Integer, L3_B2_Node> nodesDijkstraHashMap, 
			ArrayList<Integer> M) {
		int lowestDistance = INFINITY;
		Entry<Integer, L3_B2_Node> lowestDistanceNode = null;
		
		for(Entry<Integer, L3_B2_Node> entry : nodesDijkstraHashMap.entrySet()) {
		    int vertex = entry.getKey();
		    L3_B2_Node node = entry.getValue();
		    if(M.contains(vertex) && node.getDistance() != NO_PRED) {
		    	if(node.getDistance() < lowestDistance) {
		    		lowestDistance = node.getDistance();
		    		lowestDistanceNode = entry;
		    	}
		    }
		}
		return lowestDistanceNode;
	}
	
	/**
	 * Permet de convertir une ArrayList contenant des entiers en 
	 * une seule chaine de caractere
	 * (utilisable par l'algorithme de dijkstra, pour les sommets parcouru)
	 * @param list list a transformer
	 * @return liste en string
	 */
	public String arrayListToString(ArrayList<Integer> list) {
		String str = "";
		for (int vertex : list) {
			str += vertex;
		}
		str += "\t";
		return str;
	}
	
	/***
	 * Permet de clalculer le chemin de valeur minimale en fonction de différentes choses:
	 * s'il y a un arc de valeur negative on execute bellman
	 * sinon l'utilisateur a le choix
	 */
	public void calculateMinValuePaths() {
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		
		if(isArcNegativeValue()) {
			System.out.println("Il y a presence d'au moins un arc a valeur negative.");
			System.out.println("-> L'algorithme de Bellman sera execute.");
			System.out.println("Quel est le sommet de depart ?");
			
			int sommetDepart = sc.nextInt();
			bellman(sommetDepart);
		}else {
			System.out.println("Algorithme de Bellman ou Dijkstra ?");
			System.out.println("1. Bellman");
			System.out.println("2. Dijkstra");
			int choix = sc.nextInt();
			
			System.out.println("Quel est le sommet de depart ?");
			int sommetDepart = sc.nextInt();

			if(choix == 1) {
				bellman(sommetDepart);
			}else if(choix == 2) {
				dijkstra(sommetDepart);
			}
		}
	}
	
	/**
	 * Permet d'enregistrer une trace lors de l'exécution d'un algoritme de calcul de chemin minimal
	 * @return vrai si la trace a eté exécutée faux sinon
	 * @param startVertex sommet de depart
	 * @param stringMinValuePaths un algo de calcul valeur min
	 * @param algo nom de cet algo
	 */
	public boolean trace(int startVertex, ArrayList<String[]> stringMinValuePaths, String algo) {
		try {
			PrintWriter fichier = new PrintWriter(RESOURCES_PATH_TRACE + "L3-B2-trace" + this.numGraph + "_" + startVertex + ".txt");

			fichier.println("-------- Algorithme de " + algo + " --------");
			for(String[] line : stringMinValuePaths) {
				for (int i = 0; i < line.length; i++) {
					fichier.print(line[i]);
				}
				fichier.println("");
			}
			
			fichier.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * Permet un affichage correct des arcs
	 */
	@Override
	public String toString() {
		String tmp = "";
		
		for (L3_B2_Edge edge : edges) {
			tmp += "" + edge + "\n";
		}
		return tmp;
	}
}
