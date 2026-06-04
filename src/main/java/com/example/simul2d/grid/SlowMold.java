package com.example.simul2d.grid;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A {@link Mold} implementation with a low growth rate but fast propagation. This class
 * represents a slower-growing mold variant used in the simulation.
 */
public class SlowMold extends Mold {



    public static final List<CoorWeight> distribution = PropagationType.CIRCULAR.getDistribution();


    /**
     * Constructs a SlowMold with an initial growth of 0 and a growth rate
     * of 2 units per growth step.
     */
    public SlowMold() {
        super(0, 2, 20, 0.6); // Call the parent constructor with initial growth and growth rate and minimum growth value for propagation
    }

    @Override
    public void propagateTo(Cell targetCell) {
        try {
            targetCell.addEntity(new SlowMold());
        } catch (NullPointerException ignored) {
        }
    }

    @Override
    public List<CoorWeight> getPropagationDistributionList() {
        return distribution;
    }

    @Override
    public String toString() {
        return "O"; // Return a string representation for visualization (e.g., "O" for slow mold)
    }
}