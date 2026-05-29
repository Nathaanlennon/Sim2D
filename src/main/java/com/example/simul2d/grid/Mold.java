package com.example.simul2d.grid;
import java.util.Objects;

/**
 * Abstract base class representing mold with a measurable growth and a
 * growth rate. Subclasses may override {@link #grow()} to provide
 * specific behavior. Growth is clamped implicitly by the implementation.
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
    public int getGrowth() {
        return growth;
    }

    /**
     * Sets the current growth value.
     *
     * @param growth new growth value
     */
    protected void setGrowth(int growth) {
        this.growth = growth;
    }

    /**
     * Returns the configured growth rate.
     *
     * @return growth rate per step
     */
    public int getGrowthRate() {
        return growthRate;
    }


    /**
     * Sets the growth rate for subsequent growth steps.
     *
     * @param growthRate new growth rate
     */
    protected void setGrowthRate(int growthRate) {
        this.growthRate = growthRate;
    }

    @Override
    public void grow() {

        if (growthRate <= 0) {
            return; // No growth if growth rate is zero or negative
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
            return true; // Check if both references point to the same object
        }

        if (other == null || getClass() != other.getClass()) {
            return false; // Check if the other object is null or of a different class
        }

        Mold otherMold = (Mold) other; // Cast the other object to Mold
        return this.growth == otherMold.growth && this.growthRate == otherMold.growthRate && this.minGrowthValueToPropagate == otherMold.minGrowthValueToPropagate; // Check if growth, growth rate, and minimum growth value are equal

    }

    @Override
    public int hashCode() {
        return Objects.hash(growth, growthRate, minGrowthValueToPropagate); // Generate a hash code based on growth, growth rate, and minimum growth value
    }

}