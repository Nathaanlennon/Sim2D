package com.example.simul2d.grid;

import java.util.HashMap;

/**
 * A grid cell used by the simulation to represent a discrete location.
 *
 * <p>Each {@code Cell} stores an integer 2D position ({@link Vec2}) and a
 * collection of {@link Entity} instances present at that location. Entities
 * are stored in a map keyed by their concrete class which ensures at most one
 * instance per concrete entity type can be associated with a single cell.
 *
 * <p>This class is intentionally lightweight — it does not perform any
 * simulation logic itself; it only provides storage for position and
 * entity references used by higher-level simulation code.
 *
 * @see Vec2
 * @see Entity
 */
public class Cell {

    /** The position of the cell in the grid. */
    private Vec2 pos;

    /**
     * Map of entities currently present in this cell, keyed by their concrete
     * class. This allows a single concrete entity type to be stored at most
     * once per cell.
     */
    private HashMap<Class<? extends Entity>, Entity> entities;
    
    /**
     * Aggregated growth across all {@link Grow} entities in this cell.
     * <p>Used by {@link #step()} and passed into {@link Grow#grow(int)} so
     * that individual growth behavior can depend on the cell-level total.
     */
    private int totalGrowthOnCell;

    /**
     * Constructs a new {@code Cell} positioned at (0, 0) with an empty
     * entity collection.
     */
    public Cell() {
        this.pos = new Vec2(0, 0);
        this.entities = new HashMap<>();
        this.totalGrowthOnCell = 0;
    }

    /**
     * Constructs a new {@code Cell} at the provided integer coordinates with
     * an empty entity collection.
     *
     * @param x the x coordinate
     * @param y the y coordinate
     */
    public Cell(int x, int y) {
        this.pos = new Vec2(x, y);
        this.entities = new HashMap<>();
        this.totalGrowthOnCell = 0;
    }

    /**
     * Returns the aggregated growth across all {@link Grow} entities in this cell.
     *
     * @return the total growth on the cell
     */
    public int getTotalGrowthOnCell() {
        return totalGrowthOnCell;
    }


    /**
     * Executes a simulation step for this cell, updating all contained entities
     * that implement {@link Grow}. Each growable entity's growth is updated
     * based on the current total growth on the cell, and the total is updated
     * accordingly after each entity's growth step.
     */
    public void step() {
        int currentGrowth;
        for (Entity entity : entities.values()) {
            if (entity instanceof Grow growable) {
                if(!growable.isAbleToGrow(totalGrowthOnCell)) {
                    continue; // Skip growth if the entity is not able to grow based on current conditions
                }
                currentGrowth = growable.grow(totalGrowthOnCell); 
                totalGrowthOnCell += currentGrowth; // Update total growth after growth step
            }
        }
    }

    /**
     * Adds the provided entity to this cell. If an entity of the same concrete
     * class is already present, the new entity will not be added and the
     * existing one will remain unchanged.
     *
     * @param entity the entity to add (must not be {@code null})
     */
    public void addEntity(Entity entity) {

        if (entities.containsKey(entity.getClass())) {
            return;
        }

        entities.put(entity.getClass(), entity); // Add the entity to the map
        if (entity instanceof Grow growable) {
            totalGrowthOnCell += growable.getGrowth(); // Update total growth when adding a new entity
        }
    }


    /**
     * Returns the map of entities currently present in this cell.
     *
     * @return the map of entities (never {@code null})
     */
    public HashMap<Class<? extends Entity>, Entity> getEntities() {
        return entities;
    }

    /**
     * Retrieves the entity of the specified concrete class present in this cell.
     *
     * @param entity the concrete class of the entity to retrieve
     * @return the entity instance if present, or {@code null} if no such entity
     *         is stored in this cell
     */
    public Entity getEntity(Class<? extends Entity> entity) {

        return entities.get(entity);
    }

    /**
     * Returns the cell's {@link Vec2} position. The returned object is the
     * actual instance used by this cell; callers modifying it will change the
     * cell's stored coordinates.
     *
     * @return the position vector (never {@code null})
     */
    public Vec2 getPos() { 
        return pos; 
    }

    /**
     * Replaces this cell's position with the supplied {@link Vec2}.
     *
     * @param pos the new position
     */
    public void setPos(Vec2 pos) { 
        this.pos = pos; 
    }

    @Override
    public String toString() {
        if (entities.isEmpty()) {
            return " ";
        } else {
            //get the Mold in entity with the highest growth value and return the tostring of this mold
            Mold moldWithHighestGrowth = null;
            for (Entity entity : entities.values()) {
                if (entity instanceof Mold mold) {
                    if (moldWithHighestGrowth == null || mold.getGrowth() > moldWithHighestGrowth.getGrowth()) {
                        moldWithHighestGrowth = mold;
                    }
                }
            }
            return moldWithHighestGrowth != null ? moldWithHighestGrowth.toString() : " ";
        }
    }


}

