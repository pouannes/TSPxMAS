import numpy as np
import random as rd
import itertools
import time
n = 5
def matrice():
    matrice = [[0,225,585,466,775],[225,0,800,691,1000],[585,800,0,556,645],[466,691,556,0,314],[775,1000,654,314,0]]
    return(matrice)

def random_matrice (t,dist_max):
    villes = []
    for i in range (0,t):
        ligne = []
        for j in range (0,t):
            newDist = int(rd.random()*dist_max)
            ligne.append(newDist)
        villes.append(ligne)
    return(villes)
    
def sol_random (t):
    sol = []
    while len(sol)!= t :
        arret = int(rd.random()*t)
        if arret not in sol :
            sol.append(arret)
    return(sol)
        
        
    return(sol)

def longueur (trajet,villes):
    d=0
    i=0
    for i in range (n-1):
        #print(trajet[i])
        #print(trajet[i+1])
        d+=villes[trajet[i]][trajet[i+1]]
        #print(trajet[i],"->",trajet[i+1]," : ",villes[trajet[i]][trajet[i+1]],d)
    return(d)
    
def longueur_t (trajet,villes,t):
    d=0
    i=0
    for i in range (t-1):
        #print(trajet[i])
        #print(trajet[i+1])
        d+=villes[trajet[i]][trajet[i+1]]
        #print(trajet[i],"->",trajet[i+1]," : ",villes[trajet[i]][trajet[i+1]],d)
    return(d)

def rechercheV (trajet,villes,LT,compteur):
    min = 1000000
    trajet_min = [0]*n 
    for i in range (n-1):
        voisin = trajet.copy()
        compteur += len(trajet)
        temp=voisin[i]
        voisin[i]=voisin[i+1]
        voisin[i+1]=temp
       
        l = longueur(voisin,villes)
        compteur += 3 + len(voisin) + len(LT)
        flag = voisin not in LT
        #print(l, voisin,flag)
        if (l<min and flag is True):
            min = l
            trajet_min = voisin
            compteur += 2 
        
    if min == 1000000 :
        #print("---")
        return (-1,trajet)
    else :
        #print("---",min)
        return(min,trajet_min)

def rechercheV_t (trajet,villes,LT,compteur,t):
    min = 1000000
    trajet_min = [0]*t 
    for i in range (t-1):
        voisin = trajet.copy()
        compteur += len(trajet)
        temp=voisin[i]
        voisin[i]=voisin[i+1]
        voisin[i+1]=temp
       
        l = longueur_t(voisin,villes,t)
        compteur += 3 + len(voisin) + len(LT)
        flag = voisin not in LT
        #print(l, voisin,flag)
        if (l<min and flag is True):
            min = l
            trajet_min = voisin
            compteur += 2 
        
    if min == 1000000 :
        #print("---")
        return (-1,trajet)
    else :
        #print("---",min)
        return(min,trajet_min)
  
        
def tabou1 (villes,sol_init):
    compteur = 0
    LT = []
    i=0
    LT_len = 100
    flag_list = [1]
    oldSol = sol_init
    comp = oldSol
    compteur += len(comp)
    #print(longueur(sol_init,villes))
    while(1 in flag_list[-50:]) :
        newSol=rechercheV(oldSol,villes,LT,compteur)[1]
        if newSol == oldSol :
            return("pas de voisins convenables",comp,longueur(comp,villes),compteur)
            compteur += len(comp)
        
        else:
            if (len(LT)>LT_len) :
                LT.pop(0)
                compteur += 1
            LT.append(oldSol)
            compteur +=1
            if longueur(newSol,villes)>longueur(comp,villes):
                if len(flag_list)>LT_len :
                    flag_list.pop(0)
                    compteur +=1
                flag_list.append(0)
                compteur += len(flag_list)
            
            else:
                comp = newSol
                compteur += len(newSol)
                flag_list.append(1)
                compteur +=1
            compteur += len(newSol) + len(comp) +1
            oldSol = newSol
            compteur += len(newSol)
        i+=1
        compteur += len(newSol)
        #print(flag_list)
        #print(oldSol)
    res = flag_list.copy()
    res.reverse()
    compteur += 2*len(flag_list)
    LT.reverse()
    compteur += len(LT)
    return("=>",LT[res.index(1)-1],longueur(LT[res.index(1)-1],villes),compteur)

def tabou1_t (villes,sol_init):
    compteur = 0
    t = len(sol_init)
    LT = []
    i=0
    LT_len = 100
    flag_list = [1]
    oldSol = sol_init
    comp = oldSol
    compteur += len(comp)
    #print(longueur(sol_init,villes))
    while(1 in flag_list[-50:]) :
        newSol=rechercheV_t(oldSol,villes,LT,compteur,t)[1]
        if newSol == oldSol :
            return("pas de voisins convenables",comp,longueur_t(comp,villes,t),compteur)
            compteur += len(comp)
        
        else:
            if (len(LT)>LT_len) :
                LT.pop(0)
                compteur += 1
            LT.append(oldSol)
            compteur +=1
            if longueur_t(newSol,villes,t)>longueur_t(comp,villes,t):
                if len(flag_list)>LT_len :
                    flag_list.pop(0)
                    compteur +=1
                flag_list.append(0)
                compteur += len(flag_list)
            
            else:
                comp = newSol
                compteur += len(newSol)
                flag_list.append(1)
                compteur +=1
            compteur += len(newSol) + len(comp) +1
            oldSol = newSol
            compteur += len(newSol)
        i+=1
        compteur += len(newSol)
        #print(flag_list)
        #print(oldSol,longueur_t(oldSol,villes,t))
    res = flag_list.copy()
    res.reverse()
    compteur += 2*len(flag_list)
    LT.reverse()
    compteur += len(LT)
   
    return("=>",LT[res.index(1)-1],longueur_t(LT[res.index(1)-1],villes,t),compteur)


def complexité_moyenne(villes,sol_Init):
    print("permet de verifier qu'on atteind bien le min des trajets possibles et calcule la complexité moyenne en calculs et assignations")
    input("Enter to continue ...")
    sum = 0
    L = list(itertools.permutations(sol_Init))
    l = len(L)
    L = [list(i) for i in L]
    #print(L)
    for i in range (0,l-1):
        a = tabou1(villes,L[i])
        sum += a[3]
        print(a[1],a[2])
    return(sum/l)

def complexité_moyenne_t(villes,sol_Init):
    print("permet de verifier qu'on atteind bien le min des trajets possibles et calcule la complexité moyenne en calculs et assignations")
    input("Enter to continue ...")
    sum = 0
    L = list(itertools.permutations(sol_Init))
    l = len(L)
    L = [list(i) for i in L]
    k=10
    #print(L)
    start_time = time.time()
    for k in range (10,l-1):
        a = tabou1_t(villes,L[k])
        sum += a[3]
        #print("------")
        #print("solution initiale testée =",L[k])
        #print("resultat obtenu :", a[1],a[2])
    print("durée moyenne d'execution =", (time.time() - start_time)/l)
    return("complexité moyenne en operations élémentaires =", sum/l)
    
def complexité_moyenne_t_grande_taille(villes,sol_Init):
    print("permet de verifier qu'on atteind bien le min des trajets possibles et calcule la complexité moyenne en calculs et assignations")
    input("Enter to continue ...")
    sum = 0
    t = len(sol_Init)
    k=10
    #print(L)
    start_time = time.time()
    #l-1
    for k in range (10,110):
        a = tabou1_t(villes,sol_random(t))
        sum += a[3]
        #print("------")
        #print("solution initiale testée =",L[k])
        #print("resultat obtenu :", a[1],a[2])
    print("durée moyenne d'execution =", (time.time() - start_time)/100)
    return("complexité moyenne en operations élémentaires =", sum/100)
    