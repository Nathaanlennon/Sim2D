package com.example.simul2d.grid;

public class Grid {

        private int rows;
        private int columns;
        private boolean isToric; // True = les bords se touchent, False = murs bloqués
        private Cell[][] matrix;

        public Grid( int rows, int columns, boolean isToric ){
                this.rows = rows;
                this.columns= columns;
                this.isToric = isToric;
                this.matrix = new Cell[rows][columns];
        }

        public int getRows() {
                return rows;
        }

        public int getColumns() {
                return columns;
        }

        public boolean isToric() {
                return isToric;
        }

        public Cell getCell(int r, int c) {

                if (isToric) {

                        int toricR = Math.floorMod(r, rows);
                        int toricC = Math.floorMod(c, columns);

                        return matrix[toricR][toricC];
                }
                else {
                        // Si pas torique, on vérifie  normalement juste qu'on est bien dans la grille
                        if (r >= 0 && r < rows && c >= 0 && c < columns){
                                return matrix[r][c];
                        }
                }

                return null;
        }



}
