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
		Scanner sc = new Scanner(System.in);
		
		do {
			/** Recuperer le numero de fichier afin d'ouvrir le graphe demand� */
			System.out.print("Veuillez entrer le numero du fichier � charger: ");
			
			
			/** Verifier que l'utilisateur entre bien un nombre*/
			boolean OK = false;
			int fileNumber = 0;
			while(!OK) {
				try {
					fileNumber = sc.nextInt();
					OK = true;
					
					if(!Graphe.isFileExist(fileNumber)) {
						OK = false;
						System.out.print("Le fichier demand� n'existe pas.. Veuillez en entrer un autre: ");
						sc.nextLine();
					}	
				} catch (InputMismatchException e) {
					System.out.print("Entr�e incorrecte, entrez un bon numero de fichier: ");
					sc.nextLine();
				}
			}
			sc.nextLine(); //clear buffer
			
			Graphe graphe = new Graphe(fileNumber);
			
			System.out.println(graphe);
			
			System.out.println("\nMatrice d'adjacence: (nombre de sommets: " + graphe.getNbSommets() + ")" );
			graphe.printMatrix(graphe.createAdjacencyMatrix());
			
			System.out.println("\nMatrice des valeurs: (nombre de sommets: " + graphe.getNbSommets() + ")");
			graphe.printMatrix(graphe.createValuesMatrix());
			
			graphe.calculateMinValuePaths();

			/** Demander � l'utilisateur s'il veut continuer ou quitter */			
			System.out.println("\nVoulez-vous quitter ? > exit");
			String reponse = sc.nextLine();

			if(reponse.equalsIgnoreCase("exit"))
				continuer = false; 
	
		}while(continuer);
		
		System.out.println("Bye!");
		sc.close();
	}

}
