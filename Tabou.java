import java.util.ArrayList;

import java.util.Collections;

import java.util.List;

import java.util.ListIterator;

import java.util.Arrays;

public class Tabou {

	
	
	static List <String>  villes = new ArrayList <String>(Arrays.asList ("B","L","N","P","M","D","F"));		

	static int [][] matr = {{0,780,320,580,480,660,444},{780,0,700,460,300,200,555},{320,700,0,380,820,630,444},{580,460,380,0,750,310,368},{580,300,820,750,0,500,777},{660,300,630,310,500,0,568},{34,780,320,580,480,660,444,0}};
	
	public static void main(String[] s_iniy)  {
		
		String [] s_i = {"B","P","L","N","D","F","M"} ;

		int iter_max = 10 ;
		String [] solution_ideale = {"B","M","L","F","D","P","N"}; 
		int [] Tabou = {};
		String [] m_i = s_i ; 
		
		int m_d = distance (s_i) ;
		String [] iter = s_i.clone();
		
		for (int i = 0; i <= iter_max ; i++ ) {
			String [] mv = meilleur_voisin (iter.clone());
			int d = distance (mv);
			iter = mv;

			if (d <= m_d) {
				m_i = mv ;
				m_d = d ; }
				
			}
		System.out.println(Arrays.toString(m_i )); 
	
		}
		       
		// TODO Auto-generated method stub

	
		
	// calcule la distance parcourue pour une boucle selon l'ordre passé en paramètre (on prend en compte le retour à la ville initiale)
	//le paramètre est le tableau de villes parcourues
	 static int distance(String[] v) {		
		int l = v.length ;
		int somme = 0;
		for (int i=0;i<= l-2 ;  i++ ) {
			somme+= matr[villes.indexOf(v[i])][villes.indexOf(v[i+1])];
			if (i==0 ) {
				somme+= matr[villes.indexOf(v[i])][villes.indexOf(v[i+1])];}
				
			}
		System.out.println(somme);

		return somme ;}
	
	 //effectue une permutation
	 static String [] permutation (String [] v, int pos1) {
		 String [] res = v.clone() ;
		 int l = v.length;
		 if (pos1==l-1) { res [pos1] = v[0]; 		 res [0] = v[pos1];}
		 else { res [pos1] = v[pos1+1]; 		 res [pos1+1] = v[pos1];}
		

		 return res;}
	 
	 
	 static String []  meilleur_voisin (String [] v) {
		 long start, stop ;
		 start = System.nanoTime();
		 int meilleure_distance =distance(permutation (v,0)) ;
		 int l = v.length;
		 String [] voisin = v ;

		 for ( int i = 0; i<=l-1 ; i++) {
			 String [] p = permutation (v,i) ; 
			 int d = distance(p);
			 if  (d < meilleure_distance) {
				 meilleure_distance = d ;
				 voisin = p ; } 
		 };
		 stop = System.nanoTime();
		 System.out.format("\t(%4.1f ms)\n", (stop - start) / 1000000.);

		 return voisin ;
		 
		 
	 }
	 
}
