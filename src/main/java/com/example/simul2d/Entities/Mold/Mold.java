package com.example.simul2d.Entities.Mold;
import java.util.Objects;

import com.example.simul2d.Entities.CanGrow;
import com.example.simul2d.Entities.CanPropagate;
import com.example.simul2d.Entities.Displayable;
import com.example.simul2d.Entities.Entities;
import com.example.simul2d.Entities.Entity;


/**
 * Base type for mold entities in the simulation. This abstract class provides
 * common properties and behaviors for different mold variants, such as growth
 * and propagation logic.
 *
 * <p>Concrete subclasses (e.g., {@link AxialMold} and {@link CircularMold}) can
 * override the default growth behavior and provide specific implementations of
 * the {@link #toString()} method for visualization purposes.
 */
public abstract class Mold extends Entity implements CanGrow, CanPropagate, Displayable, com.example.simul2d.Entities.CanAge {
//todo : sizemax
    private int growthRate;
    private final int minGrowthValueToPropagate; // Minimum growth required for propagation
    private double PropagationProbability = 0.5; // Default propagation probability
    protected static final int MAX_GROWTH = 100; // Maximum growth value for intensity adjustment
    // Age in simulation steps. Starts at 0 and increases each step.
    private int age = 0;
    // Whether the entity is dead (set when probabilistic death occurs)
    private boolean dead = false;

    // Per-instance parameters controlling age-based death probability.
    // death probability = min(ageDeathMax, age * ageDeathFactor)
    private final double ageDeathFactor;
    private final double ageDeathMax;


    /**
     * Constructs a Mold with the supplied initial growth and growth rate.
     *
     * @param growth the initial growth value (units)
     * @param growthRate growth per step (units)
     * @param minGrowthValueToPropagate minimum growth required for propagation
     * @param PropagationProbability the probability of successful propagation (0-1)
     */
    public Mold(int growth, int growthRate, int minGrowthValueToPropagate, double PropagationProbability, Entities entityType, double ageDeathFactor, double ageDeathMax) {
        super(growth, entityType);
        this.growthRate = growthRate;
        this.minGrowthValueToPropagate = minGrowthValueToPropagate;
        this.PropagationProbability = PropagationProbability;
        this.ageDeathFactor = Math.max(0.0, ageDeathFactor);
        this.ageDeathMax = Math.max(0.0, Math.min(1.0, ageDeathMax));
    }

    @Override
    public boolean isDead() {
        return dead;
    }

    /**
     * Implements the {@link com.example.simul2d.Entities.CanAge} contract.
     * Performs one aging step and returns true if the entity died.
     */
    @Override
    public boolean ageOneStep() {
        // increment age
        this.age++;

        // compute age-based instantaneous death probability using an exponential law:
        // deathProb(age) = ageDeathMax * (1 - exp(-ageDeathFactor * age))
        double deathProb = ageDeathMax * (1 - Math.exp(-ageDeathFactor * this.age));
        if (Math.random() < deathProb) {
            this.dead = true;
            return true;
        }

        return this.dead;
    }

    /** Returns the current age in steps. */
    public int getAge() {
        return age;
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
     * Returns the minimum growth value required for this mold to be able to propagate.
     *
     * @return minimum growth value for propagation
     */
    @Override
    public double getPropagationProbability() {
        return PropagationProbability;
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
    public int grow(int totalGrowthOnCell) { //Todo: envoyer capacitÃ© de cellule aussi
        int return_growed = 0;
        
        

        if (growth + growthRate > 100) {
            return_growed=  100 - growth; // Return the actual growth added
            this.growth = 100; // Cap growth at 100
        }

        else if(totalGrowthOnCell + this.growthRate > 100) {
            this.growth += (100 - totalGrowthOnCell); // Increase growth only up to the cell limit
            return_growed=  100 - totalGrowthOnCell; // Return the actual growth added
        }

        // Default growth behavior (can be overridden by subclasses)
        else{
            this.growth += this.growthRate;
            return_growed = this.growthRate; // Return the growth added
                    
        }
        
        return return_growed;
    }



    @Override
    public double getDisplaySize() {
        return getGrowth();
    }


    @Override
    public double getOpacity() {
        // Implementation for getting the opacity based on growth
        return Math.min(1.0, getGrowth() / (double) MAX_GROWTH);
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
        return growth >= minGrowthValueToPropagate; // Propagation is possible if growth exceeds the threshold
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