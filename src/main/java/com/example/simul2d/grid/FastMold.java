package com.example.simul2d.grid;

/**
 * A {@link Mold} implementation with a higher growth rate but slower propagation. This class
 * demonstrates a mold variant that grows quickly in the simulation.
 */
public class FastMold extends Mold {

    /**
     * Constructs a FastMold with an initial growth of 0 and a growth rate
     * of 5 units per growth step.
     */
    public FastMold() {
        super(0, 5, 60); // Call the parent constructor with initial growth and growth rate and minimum growth value for propagation
    }


    @Override
    public String toString() {
        return "X"; // Return a string representation for visualization (e.g., "X" for fast mold)
    }
}