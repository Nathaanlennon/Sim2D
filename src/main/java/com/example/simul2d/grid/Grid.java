package com.example.simul2d.grid;

public class Grid {

        private int rows;
        private int columns;
        private boolean isToric; // True = les bords se touchent, False = murs bloqués
        private Cell[][] matrix;
}
// ff