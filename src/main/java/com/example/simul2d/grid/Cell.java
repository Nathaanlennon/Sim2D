package com.example.simul2d.grid;

/**
 * Represents a single cell in the simulation grid holding a 2D integer
 * position. The cell acts as a lightweight container for a {@link Vec2}
 * coordinate.
 */
public class Cell {

    private Vec2 pos;

    /**
     * Creates a cell located at (0,0).
     */
    public Cell() {
        this.pos = new Vec2(0, 0);
    }

    /**
     * Creates a cell at the given coordinates.
     *
     * @param x the x coordinate
     * @param y the y coordinate
     */
    public Cell(int x, int y) {
        this.pos = new Vec2(x, y);
    }

    /**
     * Returns the cell's position.
     *
     * @return the position as a {@link Vec2}
     */
    public Vec2 getPos() { 
        return pos; 
    }

    /**
     * Sets the cell's position.
     *
     * @param pos the new position
     */
    public void setPos(Vec2 pos) { 
        this.pos = pos; 
    }

}

