package com.example.simul2d.Entities.Mold;


import com.example.simul2d.grid.Vec2;

/**
 * A simple record to hold a coordinate and its associated weight for propagation
 * purposes. This is used to define the likelihood of propagating to neighboring
 * cells based on their relative positions.
 *
 * @param coordinate the relative position (dx, dy) from the current cell
 * @param weight the weight or probability of propagating to this coordinate (0-100)
 */
public record CoorWeight(Vec2 coordinate, int weight) {

    public CoorWeight {
        if (weight < 0 || weight > 100) {
            throw new IllegalArgumentException("weight must be between 0 and 100");
        }
    }
    
    public int getWeight() {
        return weight;
    }
    public Vec2 getCoordinates() {
        return coordinate;
    }

    @Override
    public String toString() {
        return String.format("CoorWeight{coordinate=%s, weight=%d}", coordinate, weight);
    }
}