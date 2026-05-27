package com.example.simul2d.grid;

/**
 * A {@link Mold} implementation with a low growth rate. This class
 * represents a slower-growing mold variant used in the simulation.
 */
public class SlowMold extends Mold {

    /**
     * Constructs a SlowMold with an initial growth of 0 and a growth rate
     * of 1 unit per growth step.
     */
    public SlowMold() {
        super(0, 1); // Call the parent constructor with initial growth and growth rate
    }

}