package com.example.simul2d.grid;

public class GridData {

    public enum Materiau { BETON, BOIS, PLATRE, VERRE, CACA }
    public enum Moisissure { AUCUNE, NAISSANTE, ACTIVE }

    public static class CellData {
        public Materiau materiau = Materiau.BOIS; // Le bois par défaut pour le fond
        public Moisissure moisissure = Moisissure.AUCUNE;
        public int caca=0;
    }

    public final int columns; // X
    public final int rows;    // Y
    public final boolean isToric;
    public final CellData[][] matrice;

    public GridData(int columns, int rows, boolean isToric) {
        this.columns = columns;
        this.rows = rows;
        this.isToric = isToric;
        this.matrice = new CellData[columns][rows];

        for (int x = 0; x < columns; x++) {
            for (int y = 0; y < rows; y++) {
                matrice[x][y] = new CellData();
            }
        }
    }

    public CellData getCell(int x, int y) {
        if (isToric) {
            return matrice[Math.floorMod(x, columns)][Math.floorMod(y, rows)];
        } else {
            if (x >= 0 && x < columns && y >= 0 && y < rows) {
                return matrice[x][y];
            }
        }
        return null;
    }
}
