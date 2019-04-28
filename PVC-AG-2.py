# -*- coding: utf-8 -*-
"""
Created on Thu Apr 25 09:58:23 2019

@author: ninos
"""

distance = [[0, 780, 320, 580, 480, 660], [780, 0, 700, 460, 300, 200], [320, 700, 0, 380, 820, 630], [580, 460, 380, 0, 750, 310], [480, 300, 820, 750, 0, 500], [600, 200, 630, 310, 500, 0]]

import random as rd
import copy


def solutionsInitiales(V, nbSol):   #renvoie une liste de solutions initiales: parcourt des villes (1ere = derniere ville, non présente dans les solutions)
    l = len(V)
    VillesVisitees = V[1:l]
    Solutions = [[0]*(l-1) for i in range(nbSol)]
    for j in range(nbSol):
        melangerTableau(VillesVisitees)
        Solutions[j] = copy.copy(VillesVisitees)
    villeDepart = V[0]
    return Solutions, villeDepart

def melangerTableau(t):
    l = len(t)
    for i in range(l):
        r = rd.randint(0, l-1)
        t[i], t[r] = t[r], t[i]
        
def trirapide(Vll, dist, villeDepart):
    def trirap(V, L, g, d):
        pivot = L[(g+d)//2]
        i = g
        j = d
        while True:
            while L[i]<pivot:
                i+=1
            while L[j]>pivot:
                j-=1
            if i>j:
                break
            if i<j:
                L[i], L[j] = L[j], L[i]
                V[i], V[j] = V[j], V[i]
            i+=1
            j-=1
        if g<j:
            trirap(V, L,g,j)
        if i<d:
            trirap(V, L,i,d)
 
    l = len(Vll)
    pds = [0]*l
    for i in range(l):
        pds[i] = poids(Vll[i], dist, villeDepart)   
    g=0
    d=len(pds)-1
    trirap(Vll, pds,g,d)
        
def poids(V, dist, villeDepart):    
    vd = villeDepart
    res = dist[vd-1][V[0]-1] + dist[V[-1]-1][vd-1]
    for i in range(len(V)-1):
        #print("pds : ", i+1, Ville[i+1])
        res += dist[V[i]-1][V[i+1]-1]
    return res

def selection(V, dist, villeDepart):
    nbSol = len(V)
    trirapide(V, dist, villeDepart)
    n = nbSol//2
    for k in range(n):
        vc = copy.deepcopy(V[k])
        V[nbSol-n+k] = vc

def mutation(V):
    nbSol = len(V)
    proportion = 0.1
    l = len(V[0])
    n = nbSol//2
    for k in range(n, nbSol):
        r = rd.random()
        if r < proportion:
            i = rd.randint(0, l-1)
            j = rd.randint(0, l-1)
            if i == j:
                if i == l-1:
                    i -= 1
                else:
                    i += 1
            V[k][i], V[k][j] = V[k][j], V[k][i]
            
def croisement(V):
    nbSol = len(V)
    proportion = 0.2
    n = nbSol//2
    for i in range(n, nbSol-1, 2):
        r = rd.random()
        if r < proportion:
            j = i+1
            fils(V, i, j)

def fils(V, i, j):
    l = len(V[i])
    if l<20:
        k = l//4
    elif l<400:
        k = l//20
    elif l<4000:
        k = l//200
    else:
        k = l//2000
    c = distinct(V, i, j, k)
    if c != -1:
        Villei = V[i][c:c+k]
        Villej = V[j][c:c+k]
        for m in range(k):
            V[i][c+m], V[j][c+m] = V[j][c+m], V[i][c+m]
        for m in range(c):
            vim = V[i][m]
            condi, si = dedans(Villej, vim)
            if condi:
                V[i][m] = V[j][c+si]
            vjm = V[j][m]
            condj, sj = dedans(Villei, vjm)
            if condj:
                V[j][m] = V[i][c+sj]
        for m in range(c+k, l):
            vim = V[i][m]
            condi, si = dedans(Villej, vim)
            if condi:
                V[i][m] = V[j][c+si]
            vjm = V[j][m]
            condj, sj = dedans(Villei, vjm)
            if condj:
                V[j][m] = V[i][c+sj]

def dedans(t, a):
    l = len(t)
    for i in range(l):
        if t[i] == a:
            return True, i
    else:
        return False, 0
    
def distinct(V, i, j, k):
    l = len(V[i])
    for m in range(l-k):
        cond = True
        Vi = V[i][m:m+k]
        for n in range(m, m+k):
            if V[j][n] in Vi:
                cond = False
                break
        if cond:
            return m
    return -1

def afficher(V, dist, vDepart, fct):
    nbSol = len(V)
    print(fct, " : ")
    for i in range(3):
        print(V[i], "poids :", poids(V[i], dist, vDepart))
    print("...")
    for i in range(nbSol-2, nbSol):
        print(V[i], "poids :", poids(V[i], dist, vDepart))        
        
def pvcAG():
    Ville = [1, 2, 3, 4, 5, 6]
    dist = [[0, 780, 320, 580, 480, 660], [780, 0, 700, 460, 300, 200], [320, 700, 0, 380, 820, 630], [580, 460, 380, 0, 750, 310], [480, 300, 820, 750, 0, 500], [600, 200, 630, 310, 500, 0]]
    nbSol = 150
    print("Base : ", Ville)
    Villes, vDepart = solutionsInitiales(Ville, nbSol)
#    print()
#    print("solutions initiales : ")
#    afficher(Villes, dist, vDepart, "init")
    for i in range(50):
        selection(Villes, dist, vDepart)
        croisement(Villes)
        trirapide(Villes, dist, vDepart)
        mutation(Villes)
        trirapide(Villes, dist, vDepart)
#        print()
#        print("--- itér ", i, " --- ")
#        afficher(Villes, dist, vDepart, "")
#    print()
    print()
    chemin = [vDepart]+Villes[0]
    chemin.append(vDepart)
    print("Le chemin le plus court est : ", chemin, " avec un poids de ", poids(chemin, dist, vDepart)) 


dist = [[0, 780, 320, 580, 480, 660], [780, 0, 700, 460, 300, 200], [320, 700, 0, 380, 820, 630], [580, 460, 380, 0, 750, 310], [480, 300, 820, 750, 0, 500], [600, 200, 630, 310, 500, 0]]
    

    
    
    
    
    
    
    
    
    