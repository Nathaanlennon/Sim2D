package com.example.simul2d.Entities;

import java.io.Serializable;

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
public abstract class Entity implements Serializable {
    protected int growth;
    private Entities entityType;

    public Entity(int growth, Entities entityType) {
        this.growth = getGrowth(growth);
        this.entityType = entityType;
    }

    private static int getGrowth(int growth) {
        return growth;
    }

    public Entities getEntityType() {
        return entityType;
    }

    public Entity() {
        this.growth = 0;
    }

    public int getGrowth() {
        return growth;
    }

    public void setGrowth(int growth) {
        this.growth = growth;
    }
}