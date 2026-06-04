package com.example.simul2d.grid;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A {@link Mold} implementation with a higher growth rate but slower propagation. This class
 * demonstrates a mold variant that grows quickly in the simulation.
 */
public class FastMold extends Mold {


    public static final List<CoorWeight> distribution = PropagationType.AXIAL.getDistribution();

    /**
     * Constructs a FastMold with an initial growth of 0 and a growth rate
     * of 5 units per growth step.
     */
    public FastMold() {
        super(0, 5, 60, 0.3); // Call the parent constructor with initial growth and growth rate and minimum growth value for propagation
    }

    
    
    @Override
    public void propagateTo(Cell targetCell) {
        targetCell.addEntity(new FastMold());
    }

    /**
     * Returns a sorted list of coordinates for each cell and their associated weights.
     *
     * @return a list of map entries containing target positions and associated weights
     */
    @Override
    public List<CoorWeight> getPropagationDistributionList() {
        return distribution;
    }

    @Override
    public String toString() {
        return "X"; // Return a string representation for visualization (e.g., "X" for fast mold)
    }
    
    
}