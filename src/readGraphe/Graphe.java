package readGraphe;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
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
	int nbTrinome;
	
	/** Attribut contenant tous les arcs */
	private ArrayList<int[]> arcs = new ArrayList<int[]>();
	
	/**
	 * Constructeur du Graphe
	 * @param numeroFichier
	 */
	public Graphe(int numeroFichier) {
		readGraphe(numeroFichier);
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
			
			/** On enregistre ce nombre en entier, tout en évitant toute erreur innatendu*/
			try {
				this.nbSommets = Integer.parseInt(value);
			} catch (NumberFormatException nfe) {
				System.out.println("NumberFormatException: " + nfe.getMessage());
			}
			
			/** ArrayList pour stocker temporairement un arc*/
			//ArrayList<Integer> tmpArc = new ArrayList<Integer>();
			
			/** Permet de connaitre le nombre d'arc*/
			nbTrinome = 0;

			/** Lecture à partir de la 2eme ligne jusque la fin*/
			while (inFile.hasNext()) {
				nbTrinome++;
				value = inFile.nextLine();
				
				/** Le split va generer un tableau de 3 cases (init, valeur, terminale)*/
				String[] array = value.split(" ");
				
				/** On enregistre les valeurs de l'arc dans une ArrayList (apres les avoir converti en entier)*/	
				if(array.length == 3) {
					arcs.add(arrayStringToInt(array));
				}else {
					System.out.println("Le fichier est incorrect, il n'y a pas les 3 elements définissant un arc..");
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
					matrix[i][j] = "/" + " ";
				else if(j == 0)
					matrix[i][j] = Integer.toString(i - 1) + " ";
				else if(i == 0)
					matrix[i][j] = Integer.toString(j - 1) + " ";
				else 
					matrix[i][j] = "- ";
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
		for(int i = 0; i < nbTrinome; i++) {
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
					adjacencyMatrix[x][y] = "1 ";
			}
		}
		return adjacencyMatrix;
	}
	
	/**
	 * A FAIRE
	 * @return
	 */
	public String[][] createValuesMatrix(){
		String[][] valuesMatrix = new String[this.nbSommets + 1][this.nbSommets + 1];
		
		valuesMatrix = initMatrix(valuesMatrix);

		/** y: les lignes */
		for(int y = 1; y < valuesMatrix.length; y++) {
			/** x: les colonnes*/
			for(int x = 1; x < valuesMatrix.length; x++) {
				if(this.isXIncidentToY(x,y))
					valuesMatrix[x][y] = "1 ";
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
	
	@Override
	public String toString() {
		String tmp = "";
		
		for(int i = 0; i < nbTrinome; i++) {
			tmp += "[";
			for(int j = 0; j < 3; j++) {
				tmp += Integer.toString( (arcs.get(i))[j]) + " ";
			}
			tmp += "] \n";
		}
		return tmp;
	}
}
