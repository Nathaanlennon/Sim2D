package com.example.simul2d.grid;

public class GridData {

    public enum Material { CONCRETE, WOOD, PLASTER, GLASS, DIRT }
    public enum Mold { NONE, SPAWNING, ACTIVE }

    public static class CellData {
        public Material material = Material.GLASS; // Default background
        public Mold mold = Mold.NONE;
        public int dirt = 0;
    }

    public final int columns; // X
    public final int rows;    // Y
    public final boolean isToric;
    public final CellData[][] matrix;

    public GridData(int columns, int rows, boolean isToric) {
        this.columns = columns;
        this.rows = rows;
        this.isToric = isToric;
        this.matrix = new CellData[columns][rows];

        for (int x = 0; x < columns; x++) {
            for (int y = 0; y < rows; y++) {
                matrix[x][y] = new CellData();
            }
        }
    }

    public CellData getCell(int x, int y) {
        if (isToric) {
            return matrix[Math.floorMod(x, columns)][Math.floorMod(y, rows)];
        } else {
            if (x >= 0 && x < columns && y >= 0 && y < rows) {
                return matrix[x][y];
            }
        }
        return null;
    }
}
