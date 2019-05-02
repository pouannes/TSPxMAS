package recuit_simulé;
import java.util.Random;
import java.util.Arrays;



public class RS {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[][] dist = {{0, 780, 320, 580, 480, 660}, {780, 0, 700, 460, 300, 200}, {320, 700, 0, 380, 820, 630}, {580, 460, 380, 0, 750, 310}, {480, 300, 820, 750, 0, 500}, {600, 200, 630, 310, 500, 0}};
		double t_init = 100;
		double t_min = 1;
		double lambda = 0.99;
		
		
		int[] chemin = RS(dist, t_init, t_min, lambda);
		
		System.out.print("\n\n Le chemin le plus court est : ");
		System.out.print(Arrays.toString(chemin));
		System.out.print(" Avec un poids de ");
		System.out.print(energie(chemin, dist));
	}
	
	
	
	public static int energie(int[] chemin, int[][] dist) {
		int distance = 0;
		int n = chemin.length;
		for (int i = 0; i < n-1; i++) {

			distance += dist[chemin[i]][chemin[i+1]];
		}
		return distance + dist[chemin[n-1]][chemin[0]];
	}
	
	public static int[] RS (int[][] dist, double t_init, double t_min, double lambda) {
		int n = dist.length; // Nombre de villes
		int[] chemin = new int[n]; // On crée un premier chemin naïf, de la première ville indexée à la dernière
		for (int i = 0; i < n; i++) {
			chemin[i] = i;
		}	
		Random rand = new Random(); // Servira à choisir aléatoirement deux sommets à échanger
		int E = energie(chemin, dist); //Distance totale du chemin
		
		double temp = t_init; // Température initiale
		
		while (temp > t_min) { // t_min à déterminer en fonction de la "qualité" de résultat souhaitée
			
			int sommet1 = rand.nextInt(n); // On choisit deux sommets aléatoires à échanger pour faire un nouveau chemin 
			int sommet2 = rand.nextInt(n);
			
			int[] chemin_b = chemin.clone();
			
			int temporaire = chemin_b[sommet1];
			chemin_b[sommet1] = chemin[sommet2];
			chemin_b[sommet2] = temporaire;
			
			int E_b = energie(chemin_b, dist); // On calcule la distance du chemin nouvellement proposé
			
			
			if (E_b < E || Math.random() < Math.exp((E - E_b) / temp)) { // On le garde s'il est meilleur, ou s'il a la bonne probabilité pour être accepté
				chemin = chemin_b;
				E = E_b;

			}
			
			temp *= lambda; // On diminue la température
			
		}
		
		return chemin; // On rend le meilleur chemin déterminé par l'algorithme
	}
	 
}
