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
	 * @param args
	 * @since 1.0
	 */
	public static void main(String[] args) {
		/**Variable pr savoir quand quitter*/
		boolean continuer = true;
		Scanner sc = new Scanner(System.in);
		
		do {
			/** Recuperer le numero de fichier afin d'ouvrir le graphe demandé */
			System.out.print("Veuillez entrer le numero du fichier à charger: ");
			
			
			/** Verifier que l'utilisateur entre bien un nombre*/
			boolean OK = false;
			int fileNumber = 0;
			while(!OK) {
				try {
					fileNumber = sc.nextInt();
					OK = true;
					
					if(!Graphe.isFileExist(fileNumber)) {
						OK = false;
						System.out.print("Le fichier demandé n'existe pas.. Veuillez en entrer un autre: ");
						sc.nextLine();
					}	
				} catch (InputMismatchException e) {
					System.out.print("Entrée incorrecte, entrez un bon numero de fichier: ");
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

			/** Demander à l'utilisateur s'il veut continuer ou quitter */			
			System.out.println("\nVoulez-vous quitter ? > exit");
			String reponse = sc.nextLine();

			if(reponse.equalsIgnoreCase("exit"))
				continuer = false; 
	
		}while(continuer);
		
		System.out.println("Bye!");
		sc.close();
	}

}
