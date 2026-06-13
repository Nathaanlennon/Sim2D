package com.example.simul2d.grid;
import java.io.Serializable;
/**
 * Represents a 2D grid composed of cells for a simulation.
 * The grid can optionally be configured as a toric grid (where edges wrap around).
 */
public class Grid implements Serializable {

        /** The number of rows in the grid. */
        private int rows;
        /** The number of columns in the grid. */
        private int columns;
        /** Flag indicating if the grid is toric (true) or bounded by walls (false). */
        private boolean isToric; // true = edges wrap, false = bounded by walls
        /** The 2D array containing the cells of the grid. */
        private Cell[][] matrix;

        /**
         * Constructs a new Grid with the specified dimensions and toric behavior.
         * Initializes all positions with new Cell instances.
         *
         * @param rows     the number of rows in the grid
         * @param columns  the number of columns in the grid
         * @param isToric  true if the edges should wrap around, false otherwise
         */
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


        /**
         * Constructs a non-toric Grid with the specified dimensions.
         *
         * @param rows     the number of rows in the grid
         * @param columns  the number of columns in the grid
         */
        public Grid(int rows, int columns) {
                this(rows, columns, false);
        }

        /**
         * Returns the width of the grid (number of columns).
         *
         * @return the number of columns
         */
        public int getWidth() {

                return columns;
        }

        /**
         * Returns the height of the grid (number of rows).
         *
         * @return the number of rows
         */
        public int getHeight() {

                return rows;
        }

        /**
         * Checks whether the grid is toric (edges wrap around).
         *
         * @return true if the grid is toric, false otherwise
         */
        public boolean isToric() {

                return isToric;
        }

        /**
         * Retrieves the cell at the specified coordinates.
         * Handles edge wrapping if the grid is toric.
         *
         * @param x the X coordinate (column index)
         * @param y the Y coordinate (row index)
         * @return the Cell at the given coordinates, or null if out of bounds (and not toric)
         */
        public Cell getCell(int x, int y) {

                if (isToric) {

                        //   int toricX = Math.floorMod(x, rows);
                        //  int toricY = Math.floorMod(y, columns);

                        return matrix[Math.floorMod(y, rows)][Math.floorMod(x, columns)];
                }
                else {
                        // Si pas torique, on vérifie  normalement : il faut juste qu'on est bien dans la grille
                        if (x >= 0 && x < columns && y >= 0 && y < rows){
                                return matrix[y][x];
                        }
                }

                return null;

        }
        /**
         * Convenience overload that retrieves the cell at the provided position.
         *
         * @param pos the position vector
         * @return the cell at {@code pos}, or {@code null} if out of bounds on a non-toric grid
         */
        public Cell getCell(Vec2 pos) {
                return getCell(pos.x(), pos.y());
        }

        /**
         * Sets or replaces the cell at the specified coordinates.
         * Handles edge wrapping if the grid is toric. Does nothing if coordinates
         * are out of bounds on a non-toric grid.
         *
         * @param x    the X coordinate (column index)
         * @param y    the Y coordinate (row index)
         * @param cell the Cell object to place in the grid
         */
        public void setCell( int x, int y, Cell cell){
                if( isToric){
                        //   int toricX = Math.floorMod(x,rows);
                        //  int toricY = Math.floorMod(y,columns);
                        matrix[Math.floorMod(y,rows)][Math.floorMod(x,columns)] = cell;
                }
                else {

                        if (x >= 0 && x < columns && y >= 0 && y < rows){
                                matrix[y][x] = cell;
                        }
                }



        }


        /**
         * Returns a string representation of the grid.
         * Maps out each row on a new line using the cells' own string format.
         *
         * @return a String representing the current state of the grid
         */
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