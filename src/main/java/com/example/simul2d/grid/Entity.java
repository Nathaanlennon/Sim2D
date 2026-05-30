package com.example.simul2d.grid;

/**
 * Base type for objects that can be placed into a {@link Cell}.
 *
 * <p>This abstract class acts as a lightweight marker and common supertype
 * for domain objects used by the simulation (for example, {@link Mold}).
 * Concrete entities provide behavior via additional interfaces such as
 * {@link Grow} and {@link Propagate}.
 */
public abstract class Entity {
    
    public void propagate(){
        
    }

}