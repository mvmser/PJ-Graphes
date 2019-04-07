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
	
	/** Attribut contenant tous les arcs */
	private ArrayList<int[]> arcs = new ArrayList<int[]>();
	
	/**
	 * Constructeur du Graphe
	 * @param numeroFichier
	 */
	public Graphe(int numeroFichier) {
		readGraphe(numeroFichier);
		this.nbSommets = 0;
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
			
			/** Lecture à partir de la 2eme ligne jusque la fin*/
			while (inFile.hasNext()) {
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
		System.out.println(arcs);
	}

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
	
	@Override
	public String toString() {
		return "" + this.arcs;
	}

}
