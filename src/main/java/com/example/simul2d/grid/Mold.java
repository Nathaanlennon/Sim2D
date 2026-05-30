package com.example.simul2d.grid;
import java.util.Objects;

/**
 * Abstract base class representing a mold-like entity with growth and
 * propagation behavior.
 *
 * <p>Subclasses may customize visual representation and growth-related
 * parameters. Growth behavior uses {@link #grow(int)} which receives the
 * aggregated growth present on the containing {@link Cell} and may use that
 * information to adapt its update.
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
    public void grow(int totalGrowthOnCell) {

        if (growthRate <= 0) {
            return; // No growth if growth rate is zero or negative
        }

        if (totalGrowthOnCell >= 100) {
            return; // No growth if total growth on the cell has reached or exceeded the limit
        }

        if (growth >= 100) {
            return; // Maximum growth reached
        }

        // Default growth behavior (can be overridden by subclasses)
        this.growth += this.growthRate;
    }

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