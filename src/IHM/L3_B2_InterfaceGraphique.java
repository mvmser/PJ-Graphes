package IHM;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import readGraphe.L3_B2_Graphe;
import java.awt.CardLayout;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.Dimension;

public class L3_B2_InterfaceGraphique extends JFrame{

	private static final long serialVersionUID = 1L;
	private JPanel panelCards;
	private int choixNumeroFichier;
	private int sommetDepart;
	private L3_B2_Graphe graphe = null;
	private DefaultTableModel matrixModel = new DefaultTableModel();
	private ArrayList<String[]> bellmanArray = new ArrayList<String[]>();
	
	/** Constantes des cards*/
	final static String ACCUEIL = "ACCUEIL";
	final static String MATRICE_ADJACENCE = "MATRICE_ADJACENCE";
	final static String MATRICE_VALEURS = "MATRICE_VALEURS";
	final static String BELLMAN = "BELLMAN";
	final static String DIJKSTRA = "DIJKSTRA";
	
	static String FENETRE_EN_COURS = null;

	/**
	 * Lancer l'application
	 */
	public static void main(String[] args) {			
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					L3_B2_InterfaceGraphique frame = new L3_B2_InterfaceGraphique();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Permet de creer l'application graphique
	 */
	public L3_B2_InterfaceGraphique() {
		super();
		setResizable(false);
		build();
	}

	/**
	 * Permet de construire notre fenetre
	 */
	private void build() {

		this.setSize(800, 400);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setBackground(Color.LIGHT_GRAY);
		this.setTitle("Th\u00E9orie des graphes");
		this.setLocationRelativeTo(null);
		
		setContentPane(buildContentPane());
	}
	
	/**
	 * 
	 * @return le panel
	 */
	private JPanel buildContentPane(){
		JPanel panel = new JPanel();
		
		panel.setBackground(Color.WHITE);
		panel.setLayout(null);
						
		/** Panel qui va contenir nos card*/
		panelCards = new JPanel(new CardLayout());
		panelCards.setBounds(0, 39, 794, 332);
		panel.add(panelCards);
		
		/** Header */
		JPanel panelHeader = new JPanel();
		panelHeader.setBounds(0, 0, 794, 41);
		panel.add(panelHeader);
		panelHeader.setLayout(null);
		
		/** Selection du fichier*/
		JLabel lblGraphe_1 = new JLabel("Graphe:");
		lblGraphe_1.setFont(new Font("Verdana", Font.PLAIN, 10));
		lblGraphe_1.setBounds(684, 13, 45, 19);
		panelHeader.add(lblGraphe_1);
		
		JComboBox<Integer> choixFichier = new JComboBox<Integer>();
		choixFichier.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/** On recupere le graphe choisi*/
				try {
					choixNumeroFichier = Integer.parseInt(choixFichier.getSelectedItem().toString());
				}catch (NumberFormatException  e1) {
					System.out.println("Erreur dans la recuperation du nom de fichier..");
				}
				
				System.out.println("Vous avez choisi le graphe: " + choixNumeroFichier);
				
				/** On l'instancie*/
				graphe = new L3_B2_Graphe(choixNumeroFichier);
				
				/** On actualise */
				panelCards.removeAll();
				panelCards.add(fenetreAccueil(), ACCUEIL);
				panelCards.add(fenetreAjdacence(), MATRICE_ADJACENCE);	
				panelCards.add(fenetreValeur(), MATRICE_VALEURS);
				panelCards.add(fenetreBellman(), BELLMAN);
				panelCards.add(fenetreDijkstra(), DIJKSTRA);
				
				if(FENETRE_EN_COURS == MATRICE_ADJACENCE) {
					((CardLayout) panelCards.getLayout()).show(panelCards, MATRICE_ADJACENCE);
				}else if(FENETRE_EN_COURS == MATRICE_VALEURS) {
					((CardLayout) panelCards.getLayout()).show(panelCards, MATRICE_VALEURS);
				}else if(FENETRE_EN_COURS == BELLMAN) {
					((CardLayout) panelCards.getLayout()).show(panelCards, BELLMAN);
				}else if(FENETRE_EN_COURS == DIJKSTRA) {
					((CardLayout) panelCards.getLayout()).show(panelCards, DIJKSTRA);
				}
				
			}
		});
		for (int numFile : L3_B2_Graphe.lookFiles()) {
			choixFichier.addItem(numFile);
		}
		choixFichier.setBounds(739, 12, 45, 20);
		panelHeader.add(choixFichier);
		
		/** Boutons action sur le fichier*/
		JButton btnMatriceDadjacence_1 = new JButton("Matrice d'adjacence");
		btnMatriceDadjacence_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				((CardLayout) panelCards.getLayout()).show(panelCards, MATRICE_ADJACENCE);
				FENETRE_EN_COURS = MATRICE_ADJACENCE; //pour savoir sur qu'elle vue on est
			}
		});
		btnMatriceDadjacence_1.setFont(new Font("Verdana", Font.PLAIN, 10));
		btnMatriceDadjacence_1.setBounds(10, 11, 155, 23);
		panelHeader.add(btnMatriceDadjacence_1);
		
		JButton btnMatriceDesValeurs_1 = new JButton("Matrice des valeurs");
		btnMatriceDesValeurs_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				((CardLayout) panelCards.getLayout()).show(panelCards, MATRICE_VALEURS);
				FENETRE_EN_COURS = MATRICE_VALEURS; //pour savoir sur qu'elle vue on est
			}
		});
		btnMatriceDesValeurs_1.setFont(new Font("Verdana", Font.PLAIN, 10));
		btnMatriceDesValeurs_1.setBounds(175, 11, 154, 23);
		panelHeader.add(btnMatriceDesValeurs_1);
		
		JButton btnBellman_1 = new JButton("Bellman");
		btnBellman_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				((CardLayout) panelCards.getLayout()).show(panelCards, BELLMAN);
				FENETRE_EN_COURS = BELLMAN; //pour savoir sur qu'elle vue on est
			}
		});
		btnBellman_1.setFont(new Font("Verdana", Font.PLAIN, 10));
		btnBellman_1.setBounds(339, 11, 88, 23);
		panelHeader.add(btnBellman_1);
		
		JButton btnDijkstra = new JButton("Dijkstra");
		btnDijkstra.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				((CardLayout) panelCards.getLayout()).show(panelCards, DIJKSTRA);
				FENETRE_EN_COURS = DIJKSTRA; //pour savoir sur qu'elle vue on est
			}
		});
		btnDijkstra.setFont(new Font("Verdana", Font.PLAIN, 10));
		btnDijkstra.setBounds(437, 11, 88, 23);
		panelHeader.add(btnDijkstra);
	
		/** fin header*/
		
		/** (inutile)*/
		panelCards.add(fenetreAccueil(), ACCUEIL);
		panelCards.add(fenetreAjdacence(), MATRICE_ADJACENCE);		
		panelCards.add(fenetreValeur(), MATRICE_VALEURS);
		panelCards.add(fenetreBellman(), BELLMAN);		
		panelCards.add(fenetreDijkstra(), DIJKSTRA);
		
		return panel;
	}

	/**
	 * Permet de construire notre page acceuil
	 * @return 
	 */
	private JPanel fenetreAccueil() {
		JPanel panelAccueil = new JPanel();
		
		panelAccueil.setBackground(Color.WHITE);
		panelAccueil.setBounds(0, 33, 694, 338);
		panelAccueil.setLayout(null);
				
		
		return panelAccueil;
	}
	
	private JPanel fenetreAjdacence() {
		JPanel panelAjdacence = new JPanel();
		
		System.out.println("\nMatrice d'adjacence: (nombre de sommets: " + graphe.getNbSommets() + ")" );
		graphe.printMatrix(graphe.createAdjacencyMatrix());
		
		panelAjdacence.setBackground(Color.WHITE);
		panelAjdacence.setBounds(0, 33, 694, 338);
		panelAjdacence.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Matrice d'adjacence");
		lblNewLabel.setFont(new Font("Verdana", Font.BOLD, 12));
		lblNewLabel.setBounds(327, 11, 132, 16);
		panelAjdacence.add(lblNewLabel);
		
		/** On recupere notre matrice*/
		int nbSommets = graphe.getNbSommets();
		String[][] adjacencyMatrix = new String[nbSommets + 1][nbSommets + 1];
		adjacencyMatrix = graphe.createAdjacencyMatrix();
		
		/** On cree notre tableau */
		DefaultTableModel matrixModel = new DefaultTableModel(0, (nbSommets + 1));
		JTable tableMatrice = new JTable(matrixModel);
		tableMatrice.setBackground(Color.WHITE);
		tableMatrice.setBounds(10, 78, 774, 200);
		tableMatrice.getTableHeader().setUI(null);
		
		/** On integre notre matrice a notre tableau*/
		for (String[] line : adjacencyMatrix) {
			matrixModel.addRow(line);
		}
		
		panelAjdacence.add(tableMatrice);
		
		/** Infos utilisateur*/
		JLabel lblGraphe = new JLabel("Graphe: " + choixNumeroFichier);
		lblGraphe.setFont(new Font("Verdana", Font.PLAIN, 10));
		lblGraphe.setBounds(10, 289, 183, 14);
		panelAjdacence.add(lblGraphe);
		
		JLabel lblNombreDeSommets = new JLabel("Nombre de sommets: " + graphe.getNbSommets());
		lblNombreDeSommets.setFont(new Font("Verdana", Font.PLAIN, 10));
		lblNombreDeSommets.setBounds(10, 302, 183, 14);
		panelAjdacence.add(lblNombreDeSommets);
		
		JLabel lblNombreDeDarcs = new JLabel("Nombre de d'arcs: " + graphe.getNbArc());
		lblNombreDeDarcs.setFont(new Font("Verdana", Font.PLAIN, 10));
		lblNombreDeDarcs.setBounds(10, 314, 183, 14);
		panelAjdacence.add(lblNombreDeDarcs);
		
		return panelAjdacence;
	}
	
	private JPanel fenetreValeur() {
		JPanel panelValeur = new JPanel();
		
		System.out.println("\nMatrice des valeurs: (nombre de sommets: " + graphe.getNbSommets() + ")");
		graphe.printMatrix(graphe.createValuesMatrix());
		
		panelValeur.setBackground(Color.WHITE);
		panelValeur.setBounds(0, 33, 694, 338);
		panelValeur.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Matrice des valeurs");
		lblNewLabel.setFont(new Font("Verdana", Font.BOLD, 12));
		lblNewLabel.setBounds(327, 11, 132, 16);
		panelValeur.add(lblNewLabel);
				
		/** On recupere notre matrice*/
		int nbSommets = graphe.getNbSommets();
		String[][] valuesMatrix = new String[nbSommets + 1][nbSommets + 1];
		valuesMatrix = graphe.createValuesMatrix();
		
		/** On cree notre tableau */
		DefaultTableModel matrixModel = new DefaultTableModel(0, (nbSommets + 1));
		JTable tableMatrice = new JTable(matrixModel);
		tableMatrice.setBounds(10, 78, 774, 200);
		tableMatrice.getTableHeader().setUI(null);
		
		/** On integre notre matrice a notre tableau*/
		for (String[] line : valuesMatrix) {
			matrixModel.addRow(line);
		}
		
		panelValeur.add(tableMatrice);	
		
		/** Infos utilisateur*/
		JLabel lblGraphe = new JLabel("Graphe: " + choixNumeroFichier);
		lblGraphe.setFont(new Font("Verdana", Font.PLAIN, 10));
		lblGraphe.setBounds(10, 289, 183, 14);
		panelValeur.add(lblGraphe);
		
		JLabel lblNombreDeSommets = new JLabel("Nombre de sommets: " + graphe.getNbSommets());
		lblNombreDeSommets.setFont(new Font("Verdana", Font.PLAIN, 10));
		lblNombreDeSommets.setBounds(10, 302, 183, 14);
		panelValeur.add(lblNombreDeSommets);
		
		JLabel lblNombreDeDarcs = new JLabel("Nombre de d'arcs: " + graphe.getNbArc());
		lblNombreDeDarcs.setFont(new Font("Verdana", Font.PLAIN, 10));
		lblNombreDeDarcs.setBounds(10, 314, 183, 14);
		panelValeur.add(lblNombreDeDarcs);
		
		return panelValeur;
	}
	
	private JPanel fenetreBellman() {
		JPanel panelBellman = new JPanel();
		
		panelBellman.setBackground(Color.WHITE);
		panelBellman.setBounds(0, 33, 694, 338);
		panelBellman.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Bellman");
		lblNewLabel.setFont(new Font("Verdana", Font.BOLD, 12));
		lblNewLabel.setBounds(382, 11, 60, 16);
		panelBellman.add(lblNewLabel);
				
		/** On cree notre tableau */
		int nbSommets = graphe.getNbSommets();
		matrixModel.setColumnCount(nbSommets + 1);
		JTable tableMatrice = new JTable(matrixModel);
		tableMatrice.setBounds(10, 78, 774, 200);
		tableMatrice.getTableHeader().setUI(null);
		
		int n = matrixModel.getRowCount();
		for (int i=n-1 ; i>=0 ; --i) matrixModel.removeRow(i);
			
		JScrollPane scrollPane = new JScrollPane(tableMatrice);
		scrollPane.setBounds(10, 78, 774, 200);
		panelBellman.add(scrollPane);
		
				
		/** On doit d'abord connaitre le sommet de depart*/
		JLabel lblSommetDeDepart = new JLabel("Sommet de depart: ");
		lblSommetDeDepart.setFont(new Font("Verdana", Font.PLAIN, 10));
		lblSommetDeDepart.setBounds(615, 304, 114, 14);
		panelBellman.add(lblSommetDeDepart);
		
		JComboBox<Integer> choixSommet = new JComboBox<Integer>();
		for (int i = 0; i < graphe.getNbSommets(); i++) {
			choixSommet.addItem(i);
		}
		choixSommet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/** On recupere le sommet choisi*/
				sommetDepart = 0;
				try {
					sommetDepart = Integer.parseInt(choixSommet.getSelectedItem().toString());
				}catch (NumberFormatException  e1) {
					System.out.println("Erreur dans la recuperation du sommet..");
				}
				
				System.out.println("Vous avez choisi le sommet: " + sommetDepart);
				
				/** Bellman*/
				graphe.bellman(sommetDepart);
				bellmanArray = graphe.getBellmanArray();
				System.out.println(bellmanArray.size());
				
				for (String[] line : bellmanArray) {
					matrixModel.addRow(line);
				}
				
				bellmanArray.clear();
			}
		});
		choixSommet.setBounds(739, 301, 45, 20);
		panelBellman.add(choixSommet);				
		
		//System.out.println("Sommet: "+ sommetDepart + "Graphe: "+ graphe);
		
		/** Infos utilisateur*/
		JLabel lblGraphe = new JLabel("Graphe: " + choixNumeroFichier);
		lblGraphe.setFont(new Font("Verdana", Font.PLAIN, 10));
		lblGraphe.setBounds(10, 289, 183, 14);
		panelBellman.add(lblGraphe);
		
		JLabel lblNombreDeSommets = new JLabel("Nombre de sommets: " + graphe.getNbSommets());
		lblNombreDeSommets.setFont(new Font("Verdana", Font.PLAIN, 10));
		lblNombreDeSommets.setBounds(10, 302, 183, 14);
		panelBellman.add(lblNombreDeSommets);
		
		JLabel lblNombreDeDarcs = new JLabel("Nombre de d'arcs: " + graphe.getNbArc());
		lblNombreDeDarcs.setFont(new Font("Verdana", Font.PLAIN, 10));
		lblNombreDeDarcs.setBounds(10, 314, 183, 14);
		panelBellman.add(lblNombreDeDarcs);
		return panelBellman;
	}
	
	private JPanel fenetreDijkstra() {
		JPanel panelDijkstra = new JPanel();
		
		panelDijkstra.setBackground(Color.WHITE);
		panelDijkstra.setBounds(0, 33, 694, 338);
		panelDijkstra.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Dijkstra");
		lblNewLabel.setFont(new Font("Verdana", Font.BOLD, 12));
		lblNewLabel.setBounds(378, 11, 81, 16);
		panelDijkstra.add(lblNewLabel);
				
		/** On recupere notre matrice*/
		int nbSommets = graphe.getNbSommets();
		String[][] valuesMatrix = new String[nbSommets + 1][nbSommets + 1];
		valuesMatrix = graphe.createValuesMatrix();
		
		/** On cree notre tableau */
		DefaultTableModel matrixModel = new DefaultTableModel(0, (nbSommets + 1));
		JTable tableMatrice = new JTable(matrixModel);
		tableMatrice.setBounds(10, 78, 774, 200);
		tableMatrice.getTableHeader().setUI(null);
		
		/** On integre notre matrice a notre tableau*/
		for (String[] line : valuesMatrix) {
			matrixModel.addRow(line);
		}
		
		JScrollPane scrollPane = new JScrollPane(tableMatrice);
		scrollPane.setBounds(10, 78, 774, 200);
		panelDijkstra.add(scrollPane);	
		
		/** Infos utilisateur*/
		JLabel lblGraphe = new JLabel("Graphe: " + choixNumeroFichier);
		lblGraphe.setFont(new Font("Verdana", Font.PLAIN, 10));
		lblGraphe.setBounds(10, 289, 183, 14);
		panelDijkstra.add(lblGraphe);
		
		JLabel lblNombreDeSommets = new JLabel("Nombre de sommets: " + graphe.getNbSommets());
		lblNombreDeSommets.setFont(new Font("Verdana", Font.PLAIN, 10));
		lblNombreDeSommets.setBounds(10, 302, 183, 14);
		panelDijkstra.add(lblNombreDeSommets);
		
		JLabel lblNombreDeDarcs = new JLabel("Nombre de d'arcs: " + graphe.getNbArc());
		lblNombreDeDarcs.setFont(new Font("Verdana", Font.PLAIN, 10));
		lblNombreDeDarcs.setBounds(10, 314, 183, 14);
		panelDijkstra.add(lblNombreDeDarcs);
		
		return panelDijkstra;
	}
}
