package com.example.simul2d.Entities;

/**
 * Trait for entities that can perform an attack action.
 *
 * <p>Implementations expose an attack power value used by combat systems
 * to resolve damage and interactions between entities.
 */
public interface CanAttack {

    /** Returns the integer attack power for this entity. */
    int getAttackPower();
}
