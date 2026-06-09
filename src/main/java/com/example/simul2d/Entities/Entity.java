package com.example.simul2d.Entities;

import com.example.simul2d.Entities.Mold.Mold;
import com.example.simul2d.grid.Cell;

/**
 * Base type for objects that can be placed into a {@link Cell}.
 *
 * <p>This abstract class acts as a lightweight marker and common supertype
 * for domain objects used by the simulation (for example, {@link Mold}).
 * Concrete entities provide behavior via additional interfaces such as
 * {@link CanGrow} and {@link CanPropagate}.
 */
public abstract class Entity {
        // No fields or methods; serves as a common supertype for all entities
}