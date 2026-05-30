package com.example.simul2d.grid;

/**
 * Trait interface for objects that can propagate to neighboring cells or
 * locations. Implementations decide how propagation is performed and
 * whether the object is currently able to propagate.
 */
public interface Propagate {

    /**
     * Perform one propagation step (for example, spread to adjacent
     * cells). Implementations should update their internal state
     * accordingly.
     */
    //void propagate();

    /**
     * Indicates whether the object is currently able to propagate.
     *
     * @return true if propagation is possible
     */
    boolean isAbleToPropagate();

    /**
     * Propagate the object to the specified target location. 
     * Assumption is that basic propagation is creating a new instance of the class
     *
     * @param target the cell where the object should propagate to.
     */
    void propagateTo(Cell target);
}