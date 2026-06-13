package com.example.simul2d.Entities;
import java.util.List;

import com.example.simul2d.grid.Cell;


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
     * Propagate this object to the specified target cell.
     *
     * <p>Typical implementations will create or place a new instance into
     * the {@code target} cell according to propagation rules.
     *
     * @param target the destination cell for propagation
     */
    void propagateTo(Cell target);

    /**
     * Returns a propagation distribution list describing candidate target
     * coordinates and their relative weights.
     *
     * <p>The list is typically ordered from highest to lowest weight and is
     * used by propagation systems to select a target cell.
     *
     * @return a list of {@link CoorWeight} describing targets and weights
     */
    List<CoorWeight> getPropagationDistributionList();

    double getPropagationProbability();

}   