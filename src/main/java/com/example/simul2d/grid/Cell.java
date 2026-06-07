package com.example.simul2d.grid;

import java.util.HashMap;

/**
 * A grid cell used by the simulation to represent a discrete location and its physical environment.
 *
 * <p>Each {@code Cell} stores an integer 2D position ({@link Vec2}), a
 * collection of {@link Entity} instances present at that location, and its
 * physical composition via {@link Material}. Entities are stored in a map
 * keyed by their concrete class which ensures at most one instance per concrete
 * entity type can be associated with a single cell.
 *
 * <p>The cell evaluates local environmental constraints during the simulation,
 * applying resistance and block probabilities based on its material properties
 * to determine if growth operations can proceed.
 *
 * @see Vec2
 * @see Entity
 * @see Material
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
     * The physical material composing this cell's environment.
     * Default is PLASTER (acts as the base substrate for the simulation).
     */
    private Material material = Material.PLASTER;

    /**
     * Constructs a new {@code Cell} positioned at (0, 0) with an empty
     * entity collection and default empty material.
     */
    public Cell() {
        this.pos = new Vec2(0, 0);
        this.entities = new HashMap<>();
        this.totalGrowthOnCell = 0;
    }

    /**
     * Constructs a new {@code Cell} at the provided integer coordinates with
     * an empty entity collection and default empty material.
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
     * Retrieves the material composing the environment of this cell.
     *
     * @return the current material
     */
    public Material getMaterial() {
        return material;
    }

    /**
     * Sets the physical material of this cell's environment.
     *
     * @param material the new material to apply
     */
    public void setMaterial(Material material) {
        this.material = material;
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
     * that implement {@link Grow}.
     * <p>Prior to executing growth, the method evaluates the environmental resistance
     * based on the cell's {@link Material}. Growth may be probabilistically blocked
     * (e.g., 100% block for CONCRETE, 70% for WOOD). If the entity overcomes the
     * environmental resistance, its growth is updated based on the current total
     * growth on the cell, and the total is updated accordingly.
     */
    public void step() {
        // Calcul de la résistance de l'environnement
        double blockChance = 0.0;
        if (material != null) {
            switch (material) {
                case CONCRETE: blockChance = 1.0; break;  // 100% de blocage, Mur infranchissable
                case WOOD:     blockChance = 0.7; break;  // 70% de résistance, Ralentit fortement
                case PLASTER:  blockChance = 0.2; break;  // 20% de résistance, Ralentit légèrement
                default:       blockChance = 0.0; break;  // Vide : Croissance à pleine vitesse
            }
        }

        int currentGrowth;
        for (Entity entity : entities.values()) {
            if (entity instanceof Grow growable) {

                // Application de la loi de probabilité environnementale
                if (Math.random() < blockChance) {
                    continue;
                }

                // Logique de croissance standard
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
     * Purges all biological entities from the cell and resets the growth accumulator.
     * This is mandatory to prevent state leaks between simulations.
     */
    public void clearBiologicalState() {
        this.entities.clear();
        this.totalGrowthOnCell = 0;
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
     * is stored in this cell
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
            // get the Mold in entity with the highest growth value and return the toString of this mold
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

