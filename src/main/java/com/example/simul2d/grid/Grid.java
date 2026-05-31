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

                for (int i = 0; i < rows; i++) {
                        for (int j = 0; j < columns; j++) {
                                this.matrix[i][j] = new Cell(i, j);
                        }
                }
        }

        public int getWidth() {

                return columns;
        }

        public int getHeight() {

                return rows;
        }

        public boolean isToric() {

                return isToric;
        }

        public Cell getCell(int x, int y) {

                if (isToric) {

                     //   int toricX = Math.floorMod(x, rows);
                      //  int toricY = Math.floorMod(y, columns);

                        return matrix[Math.floorMod(x, rows)][Math.floorMod(y, columns)];
                }
                else {
                        // Si pas torique, on vérifie  normalement : il faut juste qu'on est bien dans la grille
                        if (x >= 0 && x < rows && y >= 0 && y < columns){
                                return matrix[x][y];
                        }
                }

                return null;
        }

        public void setCell( int x, int y, Cell cell){
                if( isToric){
                     //   int toricX = Math.floorMod(x,rows);
                      //  int toricY = Math.floorMod(y,columns);
                        matrix[Math.floorMod(x,rows)][Math.floorMod(y,columns)] = cell;
        }
                else {

                        if (x >= 0 && x < rows && y >= 0 && y < columns){
                                matrix[x][y] = cell;
                        }
                }



        }



        @Override
        public String toString() {
                        StringBuilder sb = new StringBuilder();

                for (int i = 0; i < rows; i++) {
                        for (int j = 0; j < columns; j++) {
                                sb.append(matrix[i][j].toString());
                        }
                        sb.append("\n");
                }

                return sb.toString();
        }

}
