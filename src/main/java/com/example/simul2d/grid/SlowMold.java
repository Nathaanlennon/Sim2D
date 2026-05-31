package com.example.simul2d.grid;

/**
 * A {@link Mold} implementation with a low growth rate but fast propagation. This class
 * represents a slower-growing mold variant used in the simulation.
 */
public class SlowMold extends Mold {

    /**
     * Constructs a SlowMold with an initial growth of 0 and a growth rate
     * of 2 units per growth step.
     */
    public SlowMold() {
        super(0, 2, 20); // Call the parent constructor with initial growth and growth rate and minimum growth value for propagation
    }

    @Override
    public void propagateTo(Cell targetCell) {
        targetCell.addEntity(new SlowMold());
    }

    @Override
    public String toString() {
        return "O"; // Return a string representation for visualization (e.g., "O" for slow mold)
    }
}