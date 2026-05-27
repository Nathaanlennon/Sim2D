package com.example.simul2d.grid;

/**
 * Immutable 2D integer vector used for grid coordinates and simple
 * arithmetic. This record provides convenience methods to add and
 * subtract other vectors.
 *
 * @param x x coordinate
 * @param y y coordinate
 */
public record Vec2(int x, int y) {

    /**
     * Returns a new {@link Vec2} representing the vector addition of
     * this and the provided vector.
     *
     * @param other vector to add
     * @return new vector equal to this + other
     */
    public Vec2 add(Vec2 other) {
        return new Vec2(this.x + other.x, this.y + other.y);
    }

    /**
     * Returns a new {@link Vec2} representing the vector subtraction of
     * the provided vector from this vector.
     *
     * @param other vector to subtract
     * @return new vector equal to this - other
     */
    public Vec2 subtract(Vec2 other) {
        return new Vec2(this.x - other.x, this.y - other.y);
    }
}

