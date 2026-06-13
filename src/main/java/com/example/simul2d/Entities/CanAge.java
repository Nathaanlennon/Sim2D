package com.example.simul2d.Entities;

/**
 * Trait for entities that age and can die over time.
 * Implementations should perform one aging step when {@link #ageOneStep()} is called
 * and return {@code true} when the entity has died as a result of aging.
 */
public interface CanAge {
    /**
     * Apply one aging step (e.g. decrement HP).
     * @return {@code true} if the entity is dead after this step and should be removed.
     */
    boolean ageOneStep();

    /**
     * Returns whether the entity is currently dead.
     */
    boolean isDead();
}
