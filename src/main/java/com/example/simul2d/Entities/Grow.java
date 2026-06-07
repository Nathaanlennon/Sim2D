package com.example.simul2d.Entities;

import com.example.simul2d.grid.Cell;

/**
 * Trait interface for objects that can grow over time.
 *
 * <p>Implementations update their internal growth state when
 * {@link #grow(int)} is invoked. The {@code totalGrowthOnCell}
 * parameter allows growth behavior to depend on the aggregated
 * growth present in the containing {@link Cell}.
 */
public interface Grow {

    /**
     * Apply one growth step to the implementing object.
     *
     * @param totalGrowthOnCell the sum of growth values for all entities
     *                          in the containing cell; implementations may
     *                          use this to alter growth behavior
     */
    int grow(int totalGrowthOnCell);

    /** Returns the current growth value. */
    int getGrowth();

    /** Sets the current growth value. */
    void setGrowth(int growth);

    /** Returns the configured growth rate (units per step). */
    int getGrowthRate();

    /** Sets the growth rate (units per step). */
    void setGrowthRate(int growthRate);

    boolean isAbleToGrow(int totalGrowthOnCell);

}