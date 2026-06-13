package com.example.simul2d.grid;
import java.io.Serializable;
import java.util.Objects;
/**
 * Immutable 2D integer vector used for grid coordinates.
 *
 * @param x x coordinate
 * @param y y coordinate
 */
public record Vec2(int x, int y) implements Serializable {

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

    /**
     * Returns a new vector pointing in the opposite direction.
     *
     * @return a {@link Vec2} equal to (-x, -y)
     */
    public Vec2 negate() {
        return new Vec2(-this.x, -this.y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vec2)) return false;
        Vec2 other = (Vec2) o;
        return this.x == other.x && this.y == other.y;
    }


    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}

