package com.example.simul2d.grid;
import java.util.Objects;

/**
 * Base type for mold entities in the simulation. This abstract class provides
 * common properties and behaviors for different mold variants, such as growth
 * and propagation logic.
 *
 * <p>Concrete subclasses (e.g., {@link SlowMold} and {@link FastMold}) can
 * override the default growth behavior and provide specific implementations of
 * the {@link #toString()} method for visualization purposes.
 */
public abstract class Mold extends Entity implements Grow, Propagate {

    private int growth;
    private int growthRate;
    private final int minGrowthValueToPropagate; // Minimum growth required for propagation

    /**
     * Constructs a Mold with the supplied initial growth and growth rate.
     *
     * @param growth the initial growth value (units)
     * @param growthRate growth per step (units)
     * @param minGrowthValueToPropagate minimum growth required for propagation
     */
    public Mold(int growth, int growthRate, int minGrowthValueToPropagate) {
        this.growth = growth;
        this.growthRate = growthRate;
        this.minGrowthValueToPropagate = minGrowthValueToPropagate;
    }

    /**
     * Returns the current growth value.
     *
     * @return current growth
     */
    @Override
    public int getGrowth() {
        return growth;
    }

    /**
     * Sets the current growth value.
     *
     * @param growth new growth value
     */
    @Override
    public void setGrowth(int growth) {
        this.growth = growth;
    }

    /**
     * Returns the configured growth rate.
     *
     * @return growth rate per step
     */
    @Override
    public int getGrowthRate() {
        return growthRate;
    }

    /**
     * Sets the growth rate for subsequent growth steps.
     *
     * @param growthRate new growth rate
     */
    @Override
    public void setGrowthRate(int growthRate) {
        this.growthRate = growthRate;
    }

    /**
     * Default growth behavior invoked each step.
     *
     * <p>The implementation is conservative: it does nothing if the
     * configured growth rate is non-positive, the total growth on the
     * containing cell has reached a global limit (100), or this mold has
     * already reached a maximum (100). Otherwise, the mold's growth is
     * increased by its {@link #growthRate}.
     *
     * @param totalGrowthOnCell aggregated growth value for the containing cell
     */
    @Override
    public int grow(int totalGrowthOnCell) {

        if (growthRate <= 0) {
            return 0; // No growth if growth rate is zero or negative
        }

        if (growth >= 100) {
            return 0; // No growth if this mold has reached the limit
        }

        if(totalGrowthOnCell >= 100) {
            return 0; // No growth if the cell has reached the total growth limit
        }

        if (growth + growthRate > 100) {
            growth = 100; // Cap growth at 100
            return 100 - growth; // Return the actual growth added
        }

        if(totalGrowthOnCell + this.growthRate > 100) {
            this.growth += (100 - totalGrowthOnCell); // Increase growth only up to the cell limit
            return 100 - totalGrowthOnCell; // Return the actual growth added
        }

        // Default growth behavior (can be overridden by subclasses)
        this.growth += this.growthRate;
        return this.growthRate;
    }


    /**
     * Determines if this mold is able to grow based on its growth rate and
     * the current growth conditions of the containing cell.
     *
     * <p>Growth is possible if the growth rate is positive, the total growth
     * on the cell has not reached a global limit (100), and this mold has
     * not already reached its maximum growth (100).
     *
     * @param totalGrowthOnCell aggregated growth value for the containing cell
     * @return true if growth is possible under current conditions, false otherwise
     */
    @Override
    public boolean isAbleToGrow(int totalGrowthOnCell) {
        return growthRate > 0 && totalGrowthOnCell < 100 && growth < 100; // Growth is possible if growth rate is positive and limits are not reached
    }


    /**
     * Determines if this mold is able to propagate based on its current growth.
     *
     * <p>Propagation is possible if the mold's growth has reached or exceeded
     * the configured minimum growth value required for propagation.
     *
     * @return true if propagation is possible, false otherwise
     */
    @Override
    public boolean isAbleToPropagate() {
        return growth > minGrowthValueToPropagate; // Propagation is possible if growth exceeds the threshold
    }

    /**
     * Resets the growth back to zero.
     */
    public void resetGrowth() {
        this.growth = 0;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (other == null || getClass() != other.getClass()) {
            return false;
        }

        Mold otherMold = (Mold) other;
        return this.growth == otherMold.growth && this.growthRate == otherMold.growthRate && this.minGrowthValueToPropagate == otherMold.minGrowthValueToPropagate;

    }

    @Override
    public int hashCode() {
        return Objects.hash(growth, growthRate, minGrowthValueToPropagate);
    }

}
