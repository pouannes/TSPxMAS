package myAgents;

import java.util.Arrays;
import java.util.Random;

import jade.core.Agent;
//import jade.core.behaviours.CyclicBehaviour;
//import jade.lang.acl.ACLMessage;


public class AgentAG extends Agent {
	
	public static int[][] solutionsInitiales(int[] Ville, int nbSol) {
		int[] Villes =  Arrays.copyOfRange(Ville, 1, Ville.length-2); //slicing: on supprime la ville de départ		
		//{'B', 'L', 'N', 'P', 'M', 'D', 'B'};
		int[][] Solutions = new int[nbSol][5];
		for (int i = 0; i<nbSol; i++) {
			melangerTableau(Villes);
			Solutions[i] = Villes;
		}
		return Solutions;
	}	
	private static void melangerTableau(int t[]) {
		for (int i = 0; i < t.length; i++) {
			Random rand = new Random();
			int r = rand.nextInt(t.length);
	        int tmp = t[i];
	        t[i] = t[r];
	        t[r] = tmp;
	    }
	}
	
	public static void quicksort(int[][] Villes, int début, int fin, int[][] dist) {
	    if (début < fin) {
	        int indicePivot = partition(Villes, début, fin, dist);
	        quicksort(Villes, début, indicePivot-1, dist);
	        quicksort(Villes, indicePivot+1, fin, dist);
	    }
	}
	public static int partition (int[][] Villes, int début, int fin, int[][] dist) {
	    int valeurPivot = poids(Villes[début], dist);
	    int[ ] pivot = Villes[début];
	    int d = début+1;
	    int f = fin;
	    while (d < f) {
	        while(d < f && poids(Villes[f],dist) >= valeurPivot) f--;
	        while(d < f && poids(Villes[d],dist) <= valeurPivot) d++;
	        
	        int[] temp = new int[6];
			System.arraycopy(Villes[d],  0, temp,  0,  6) ;
			int[] temp2 = new int[6];
			System.arraycopy(Villes[f],  0, temp2,  0,  6) ;
	        Villes[d]= temp2;
	        Villes[f] = temp;
	    }
	    if (poids(Villes[d],dist) > valeurPivot) d--;
	    
	    int[] temp = new int[6];
		System.arraycopy(Villes[d],  0, temp,  0,  6) ;
		int[] temp2 = new int[6];
		System.arraycopy(pivot,  0, temp2,  0,  6) ;
        Villes[début]= temp;
        Villes[d] = temp2;
        
	    return d;
	}
	private static int poids(int[] Ville, int[][] dist) {
		int res = dist[0][Ville[0]-1] + dist[0][Ville[Ville.length-1]-1] ;
		for (int i = 0; i < Ville.length-1; i++) {
			res = res + dist[Ville[i]-1][Ville[i+1]-1];
			}
		return res;
	}
	
	public static int[][] reproduction(int[][] Villes, int nbSol){		
		int[][] Villes2 = new int[nbSol*2][6];		
		for (int i = 0; i < nbSol; i++) {			
			Villes2[i]=Villes[i];
		}		
		for (int i = 0; i < nbSol; i++) {
			System.arraycopy(Villes[i],  0, Villes2[nbSol+i],  0,  6) ;
		}		
		return Villes2;	
	}
	public static void mutation (int[][] Villes, int nbSol){		
		for(int k = 0; k < nbSol; k++) {
			Random rand = new Random(); 
			int i = 1+rand.nextInt(5);
			int j = 1+rand.nextInt(5);
			if(i==j && i < 5 ) {
				i = i + 1;
				}
			else if (i==j) {
				i = i -1;
				}
			
			int res = Villes[k][i];
			Villes[k][i] = Villes[k][j];
			Villes[k][j] = res;						
		}	
	}	
	public static int[][] selection (int[][] Villes, int[][] dist, int nbSol) {		
		quicksort(Villes, 0, nbSol*2-1, dist);		
		int[][] Villes2 = new int[nbSol][6];		
		System.arraycopy(Villes,  0, Villes2,  0,  nbSol) ;
		
		return Villes2;		
	}
	
	/*
	int[] Ville;
	int[][] dist;
	int nbSol;
	int[][] Villes;
	int i = 0;
	
	public void onStart() {
		int[] Ville = {1,2,3,4,5,6};
		int[][] dist = {{0, 780, 320, 580, 480, 660}, {780, 0, 700, 460, 300, 200}, {320, 700, 0, 380, 820, 630}, {580, 460, 380, 0, 750, 310}, {480, 300, 820, 750, 0, 500}, {600, 200, 630, 310, 500, 0}};
		int nbSol = 50;				
		int[][] Villes = solutionsInitiales(Ville, nbSol);
	}
	*/
	
	protected void setup() {
		System.out.println("AgentAg - initialisation ");
		int i = 0;
		int[] Ville = {1,2,3,4,5,6};
		int[][] dist = {{0, 780, 320, 580, 480, 660}, {780, 0, 700, 460, 300, 200}, {320, 700, 0, 380, 820, 630}, {580, 460, 380, 0, 750, 310}, {480, 300, 820, 750, 0, 500}, {600, 200, 630, 310, 500, 0}};
		int nbSol = 50;				
		int[][] Villes = solutionsInitiales(Ville, nbSol);
	}
	
	public void action() {	
		i++;
		int[][] Villes2 = reproduction(Villes, nbSol);
		mutation (Villes2, nbSol);
		Villes2 = selection (Villes2, dist, nbSol);			
		System.arraycopy(Villes2,  0, Villes,  0,  nbSol) ;
		/*	
		System.out.print("\n\nNouvelle Indentation");
		for(int j = 0; j < nbSol;j++) {
			System.out.print(Arrays.toString(Villes[j]));
			System.out.print(" poids : ");
			System.out.print(poids(Villes[j],dist));
			System.out.print("  //  ");
			}
		*/
		System.out.println(i);
	}	
	
	public boolean done(){
		return i==50;
	}
	
}
