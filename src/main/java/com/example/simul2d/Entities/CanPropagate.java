package com.example.simul2d.Entities;
import com.example.simul2d.grid.Cell;

import java.util.List;


/**
 * Trait interface for objects that can propagate to neighboring cells or
 * locations. Implementations decide how propagation is performed and
 * whether the object is currently able to propagate.
 */
public interface CanPropagate {

    /**
     * Indicates whether the object is currently able to propagate.
     *
     * @return true if propagation is possible
     */
    boolean isAbleToPropagate();

    /**
     * CanPropagate the object to the specified target location. 
     * Assumption is that basic propagation is creating a new instance of the class
     *
     * @param target the cell where the object should propagate to.
     */
    void propagateTo(Cell target);

    /**
     * Return a sorted from highest to lowest probability list of coordinates for each cell
     *
     * @return a list of map entries containing target positions and associated weights
     */
    List<CoorWeight> getPropagationDistributionList();

    double getPropagationProbability();

}   