package com.example.simul2d.grid;

public class Cell {

    private Vec2 pos;

    public Cell() {
        this.pos = new Vec2(0, 0);
    }

    public Cell(int x, int y) {
        this.pos = new Vec2(x, y);
    }

    public Vec2 getPos() { 
        return pos; 
    }

    public void setPos(Vec2 pos) { 
        this.pos = pos; 
        }


}

