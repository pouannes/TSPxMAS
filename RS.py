import math
import random

def energie(chemin, dist):
  distance = 0
  n = len(chemin)

  for i in range(n-1):
    distance += dist[chemin[i]][chemin[i+1]]

  return distance + dist[chemin[n-1]][chemin[0]]

def RS(dist, t_init, t_min, coeff):
  n = len(dist)
  chemin = [i for i in range(n)]

  E = energie(chemin, dist)

  temp = t_init

  while temp > t_min:
    sommet1 = random.randint(0, n-1)
    sommet2 = random.randint(0, n-1)

    chemin_b = chemin[:]

    temporaire = chemin_b[sommet1]
    chemin_b[sommet1] = chemin[sommet2]
    chemin_b[sommet2] = temporaire
    E_b = energie(chemin_b, dist)

    if (E_b < E) or (random.random() < math.exp((E - E_b) / temp)):
      chemin = chemin_b
      E = E_b

    temp *= coeff

  return chemin


if __name__ == "__main__":
  dist = [[0, 780, 320, 580, 480, 660], [780, 0, 700, 460, 300, 200], [320, 700, 0, 380, 820, 630], [580, 460, 380, 0, 750, 310], [480, 300, 820, 750, 0, 500], [600, 200, 630, 310, 500, 0]]
  
  t_init = 100
  t_min = 1
  coeff = 0.99

  chemin = RS(dist, t_init, t_min, coeff)

  print("Le chemin le plus court est: ")
  print(chemin)
  print("Avec un poids de: ")
  print(energie(chemin, dist))


  

