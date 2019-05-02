import java.util.Arrays;
import java.util.Random;

public class PVC {	

	public static void main(String[] args) {
		int[] Ville = {1,2,3,4,5,6};
		int[][] dist = {{0, 780, 320, 580, 480, 660}, {780, 0, 700, 460, 300, 200}, {320, 700, 0, 380, 820, 630}, {580, 460, 380, 0, 750, 310}, {480, 300, 820, 750, 0, 500}, {600, 200, 630, 310, 500, 0}};
		int nbSol = 50;
		
		System.out.print("Base ");
		System.out.print(Arrays.toString(Ville));
				
		int[][] Villes = solutionsInitiales(Ville, nbSol);
		
		System.out.print("\n\nSolution Initiale ");
		for(int i = 0; i < nbSol;i++) {
			System.out.print(Arrays.toString(Villes[i]));
			System.out.print(" poids : ");
			System.out.print(poids(Villes[i],dist));
			System.out.print("  //  ");
			}
		
		
		for(int i = 0; i < 10;i++) {
			
			int[][] Villes2 = reproduction(Villes, nbSol);
			mutation (Villes2, nbSol);
			croisement (Villes2, nbSol);
			Villes2 = selection (Villes2, dist, nbSol);
			
			System.arraycopy(Villes2,  0, Villes,  0,  nbSol) ;
			
			System.out.print("\n\nNouvelle Indentation");
			for(int j = 0; j < nbSol;j++) {
				System.out.print(Arrays.toString(Villes[j]));
				System.out.print(" poids : ");
				System.out.print(poids(Villes[j],dist));
				System.out.print("  //  ");
				}
			
		}
		
		System.out.print("\n\n Le chemin le plus court est : ");
		System.out.print(Arrays.toString(Villes[0]));
		System.out.print(" Avec un poids de ");
		System.out.print(poids(Villes[0],dist));
		
	}
	
	public static int[][] solutionsInitiales(int[] Ville, int nbSol) {
		int[][] Solutions = new int[nbSol][6];
		for (int i = 0; i<nbSol; i++) {
			melangerTableau(Ville);
			int[] Ville2 = new int[6];
			System.arraycopy(Ville,  0, Ville2,  0,  6) ;
			Solutions[i] = Ville2;
		}
		return Solutions;
	}
	
	// Mélange un tableau
	private static void melangerTableau(int t[]) {
		for (int i = 1; i < t.length-1; i++) {
			Random rand = new Random();
			int r = 1+rand.nextInt(t.length-2);
	        int tmp = t[i];
	        t[i] = t[r];
	        t[r] = tmp;
	    }
	}
	
	
	private static int poids(int[] Ville, int[][] dist) {
		int res = dist[0][Ville[0]-1] + dist[0][Ville[Ville.length-1]-1] ;
		for (int i = 0; i < Ville.length-1; i++) {
			res = res + dist[Ville[i]-1][Ville[i+1]-1];
			}
		return res;
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

	
	public static int[][] selection (int[][] Villes, int[][] dist, int nbSol) {		
		quicksort(Villes, 0, nbSol*2-1, dist);		
		int[][] Villes2 = new int[nbSol][6];		
		System.arraycopy(Villes,  0, Villes2,  0,  nbSol) ;
		
		return Villes2;		
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
		double proportion = 0.21;
		for(int k = 0; k < nbSol; k++) {
			Random rand = new Random();
			double r = rand.nextInt(10000)/10000;
			if (r<proportion) {
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
	}
	
	public static void croisement (int[][] Villes, int nbSol){		
		for(int k = 0; k < nbSol/4; k++) {
			Random rand = new Random(); 
			int i = 1+rand.nextInt(2);
			int j = rand.nextInt(nbSol);
			
			System.out.print("\n croisement avant :");
			System.out.print(Arrays.toString(Villes[k]));
			System.out.print(Arrays.toString(Villes[j]));
			
			for(int p = 0; p < 3; p++) {
				int res = Villes[k][i+p];
				int res2 = Villes[j][i+p];
				Villes[k][i+p] = res2;
				Villes[j][i+p] = res;
				
				for(int l = 0; l < 6; l++) {
					if ((Villes[k][l] == res2) && (l != i+p)) Villes[k][l] = res;
					if ((Villes[j][l] == res) && (l != i+p)) Villes[j][l] = res2;
				}
			}
			
			System.out.print("\n croisement après :");
			System.out.print(Arrays.toString(Villes[k]));
			System.out.print(Arrays.toString(Villes[j]));
		}	
	}
}
