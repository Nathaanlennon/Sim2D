package com.example.simul2d;

import com.example.simul2d.grid.Grid;
import com.example.simul2d.grid.Cell;
// f

public class Main {
    public static void main(String[] args) {

        System.out.println("=== TEST DE LA GRILLE (5 lignes, 5 colonnes) ===");

        // 1. On crée une grille normale (non-torique) de 5x5
        // Ton constructeur va automatiquement la remplir avec des Cell(i, j)
        Grid maGrille = new Grid(5, 5, false);

        // 2. On affiche la grille initiale
        System.out.println("\nGrille au démarrage (chaque cellule affiche ses coordonnées) :");
        System.out.println(maGrille); // <--- Ici, Java appelle automatiquement ton toString() !

        // 3. On teste la modification d'une cellule
        System.out.println("=== MODIFICATION ===");
        System.out.println("On remplace la cellule au centre (index 2,2) par une nouvelle cellule positionnée fictivement en (99,99) pour la repérer :");

        Cell celluleSpeciale = new Cell(99, 99);
        maGrille.setCell(2, 2, celluleSpeciale);

        // 4. On ré-affiche la grille pour voir si le changement a fonctionné
        System.out.println("\nGrille après modification :");
        System.out.println(maGrille);

        // 5. Petit test bonus pour vérifier tes getters
        System.out.println("=== VERIFICATION DES DIMENSIONS ===");
        System.out.println("Largeur (colonnes) : " + maGrille.getWidth());
        System.out.println("Hauteur (lignes)   : " + maGrille.getHeight());
    }
}