package com.example.simul2d.grid;

import java.io.Serializable;
import java.util.HashMap;

import com.example.simul2d.Entities.CanGrow;
import com.example.simul2d.Entities.Displayable;
import com.example.simul2d.Entities.Entities;
import com.example.simul2d.Entities.Entity;
import com.example.simul2d.Entities.Mold.Mold;

import javafx.scene.paint.Color;

/**
 * A grid cell used by the simulation to represent a discrete location.
 *
 * <p>Each {@code Cell} stores an integer 2D position ({@link Vec2}) and a
 * collection of {@link Entity} instances present at that location. com.example.simul2d.Entities
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
public class Cell implements Serializable {

    /**
     * The position of the cell in the grid.
     */
    private Vec2 pos;

    /**
     * Map of entities currently present in this cell, keyed by their concrete
     * class. This allows a single concrete entity type to be stored at most
     * once per cell.
     */
    private HashMap<Entities, Entity> entities;
    private HashMap<Entities, Entity> combatEnabledEntities;

    private int minGrowthValueToFight = 20;
    private int capacity = 100;

    /**
     * Aggregated growth across all {@link CanGrow} entities in this cell.
     * <p>Used by {@link #step()} and passed into {@link CanGrow#grow(int)} so
     * that individual growth behavior can depend on the cell-level total.
     */
    private int totalGrowthOnCell;

    /**
     * Constructs a new {@code Cell} positioned at (0, 0) with an empty
     * entity collection.
     */


    private Material material;


    public Cell() {
        this.pos = new Vec2(0, 0);
        this.entities = new HashMap<>();
        this.totalGrowthOnCell = 0;
        this.material = Material.EMPTY;
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
        this.combatEnabledEntities = new HashMap<>();
        this.totalGrowthOnCell = 0;
        this.material = Material.EMPTY;

    }

    /**
     *
     * @return the material of the cell
     */
    public Material getMaterial() {
        return material;
    }

    /**
     *
     * @param material the material to set for the cell
     */
    public void setMaterial(Material material) {
        this.material = material;
    }

    /**
     * Returns the aggregated growth across all {@link CanGrow} entities in this cell.
     *
     * @return the total growth on the cell
     */
    public int getTotalGrowthOnCell() {
        return totalGrowthOnCell;
    }

    public int getMinGrowthValueToFight() {
        return minGrowthValueToFight;
    }

    public int getCapacity() {
        return capacity;
    }

    public int updateTotalGrowthOnCell() {
        int total = 0;
        for (Entity entity : entities.values()) {
            if (entity instanceof CanGrow growable) {
                total += entity.getGrowth();
            }
        }
        this.totalGrowthOnCell = total;
        return total;
    }

    /**
     * Executes a simulation step for this cell, updating all contained entities
     * that implement {@link CanGrow}. Each growable entity's growth is updated
     * based on the current total growth on the cell, and the total is updated
     * accordingly after each entity's growth step.
     */
    public void step() {

        totalGrowthOnCell = 0;

        for (Entity entity : entities.values()) {
            totalGrowthOnCell += entity.getGrowth();
            if (entity instanceof CanGrow growable) {
                if (!growable.isAbleToGrow(totalGrowthOnCell)) {
                    continue; // Skip growth if the entity is not able to grow based on current conditions
                }
                totalGrowthOnCell += growable.grow(totalGrowthOnCell); // Update total growth after growth step

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
    public int addEntity(Entity entity) {


        if (!entities.containsKey(entity.getEntityType())) {
            entities.put(entity.getEntityType(), entity);
            
        }

//        if (entities.containsKey(entity.getClass())) {
//            Entity existing = entities.get(entity.getClass());
//            if (existing instanceof CanGrow existingGrowable && entity instanceof CanGrow incomingGrowable) {
//                int available = 100 - totalGrowthOnCell;
//                int toAbsorb = Math.min(incomingGrowable.getGrowth(), available);
//                existingGrowable.setGrowth(existingGrowable.getGrowth() + toAbsorb);
//                totalGrowthOnCell += toAbsorb;
//                return toAbsorb;
//            }
//            return 0;
//        }

//        if (entity instanceof CanGrow growable) {
//            int available = capacity - totalGrowthOnCell;
//            int toAbsorb = Math.min(growable.getGrowth(), available);
//            growable.setGrowth(toAbsorb);
//            totalGrowthOnCell += toAbsorb;
//            return toAbsorb;
//        }
        return 0;
    }

    public String getColorHex() {
        // 1. Trouver l’entité affichable la plus grande
        Displayable best = null;
        double maxSize = Double.NEGATIVE_INFINITY;
        for (Entity e : entities.values()) {
            if (e instanceof Displayable d) {
                double size = d.getDisplaySize();
                if (size > maxSize) {
                    maxSize = size;
                    best = d;
                }
            }
        }

        // 2. Si aucune entité affichable, retourner le matériau seul
        if (best == null) {
            return material.getColorHex();
        }
        // 3. Mélanger la couleur de l’entité (avec son opacité) sur le fond du matériau
        return blend(material.getColorHex(), best.getColorHex(), best.getOpacity());
    }

    /**
     * Mélange deux couleurs hexadécimales selon une opacité.
     *
     * @param bgHex   couleur de fond (matériau)
     * @param fgHex   couleur de premier plan (entité)
     * @param opacity opacité du premier plan (0.0 → transparent, 1.0 → opaque)
     * @return la couleur résultante au format hexadécimal
     */
    private static String blend(String bgHex, String fgHex, double opacity) {
        Color bg = Color.web(bgHex);
        Color fg = Color.web(fgHex);
        double r = fg.getRed() * opacity + bg.getRed() * (1 - opacity);
        double g = fg.getGreen() * opacity + bg.getGreen() * (1 - opacity);
        double b = fg.getBlue() * opacity + bg.getBlue() * (1 - opacity);
        return String.format("#%02X%02X%02X",
                (int) (r * 255), (int) (g * 255), (int) (b * 255));
    }

    /**
     * Retrieves the entity of the specified concrete class present in this cell.
     *
     * @param entity the concrete class of the entity to retrieve
     * @return the entity instance if present, or {@code null} if no such entity
     * is stored in this cell
     */
    public Entity getEntity(Entities entity) {

        return entities.get(entity);
    }

    public HashMap<Entities, Entity> getCombatEnabledEntities() {
        return combatEnabledEntities;
    }

    public HashMap<Entities, Entity> getEntities() {
        return entities;
    }
    

    public void removeEntity(Entities entityClass) {
        entities.remove(entityClass);
    }
    
    
    public void clearEntities() {
        entities.clear();
        totalGrowthOnCell = 0;
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

