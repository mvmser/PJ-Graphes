package IHM;

import java.util.InputMismatchException;
import java.util.Scanner;

import readGraphe.Graphe;

/**
 * @version 1.0
 *
 */
public class testGraphes {

	/**
	 * Algo:
	 * d�but
		tant que l�utilisateur veut tester un nouveau graphe faire ��
			choisir le fichier texte par son num�ro
			lire le fichier correspondant et sauvegarder le graphe en m�moire
			imprimer le graphe sous forme d�une matrice d�adjacence � partir de la m�moire et non pas lors de la lecture du fichier texte
			calculer les chemins de valeur minimale
		 fait
		fin
	 * @param args
	 * @since 1.0
	 */
	public static void main(String[] args) {	   
		boolean continuer = true;
		
		do {
			/** Recuperer le numero de fichier afin d'ouvrir le graphe demand� */
			System.out.println("Veuillez entrer le numero du fichier � charger:");
			Scanner sc = new Scanner(System.in);
			
			/** Verifier que l'utilisateur entre bien un nombre*/
			boolean OK = false;
			int numeroFichier = 0;
			while(!OK) {
				try {
					//numeroFichier = sc.nextInt();
					numeroFichier = 1;

					OK = true;
				} catch (InputMismatchException e) {
					System.out.print("Entr�e incorrecte, entrez un bon numero de fichier: ");
					sc.nextLine();
				}
			}
			sc.nextLine(); //clear buffer
			
			Graphe graphe = new Graphe(numeroFichier);
			
			//System.out.println(graphe);
			
			System.out.println("\nMatrice d'adjacence:");
			graphe.printMatrix(graphe.createAdjacencyMatrix());
			
			System.out.println("\nMatrice des valeurs:");
			graphe.printMatrix(graphe.createValuesMatrix());
			

			/** Demander � l'utilisateur s'il veut continuer ou quitter */
			String reponse = null;

			System.out.println("Voulez-vous quitter ? > exit");
			reponse = sc.nextLine();
			if(reponse != "exit") continuer = false; 
	
		}while(continuer);

	}

}
