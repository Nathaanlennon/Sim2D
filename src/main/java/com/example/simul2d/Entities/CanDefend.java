package com.example.simul2d.Entities;

/**
 * Trait for entities that provide defensive capability.
 *
 * <p>Used by combat resolution to reduce or absorb incoming damage.
 */
public interface CanDefend {

    /** @return the integer defense power for this entity. */
    int getDefensePower();
}
