package com.example.simul2d.Systems;

import com.example.simul2d.Entities.Entity;
import com.example.simul2d.grid.Cell;

/**
 * Simple combat resolution system executed per-cell.
 *
 * <p>The current implementation applies a rudimentary damage model based on
 * entities' growth values. This class is a focal point for future
 * improvements to how entities interact and die.
 */
public class CombatSystem {

    // constructors
    public CombatSystem() {
    }

    //set methods
//get methods
//private methods
//public methods
    /**
     * Resolves combat between entities present in the given cell.
     *
     * <p>Entities with growth below the cell's `minGrowthValueToFight` are
     * ignored. Damage is distributed proportionally from participating
     * entities and applied by reducing their growth value.
     *
     * @param cell the cell where combat should be resolved; ignored if {@code null}
     */
    public void inCellCombat(Cell cell) {
        if (cell == null) return;
        //todo: system broken, find alternative to permit entities dying
        int totalMass = 0;
        for (Entity entity : cell.getEntities().values()) {
            if (entity.getGrowth() >= cell.getMinGrowthValueToFight()) {
                totalMass += entity.getGrowth();
            }
        }

        for (Entity entity : cell.getEntities().values()) {
            if (entity.getGrowth() >= cell.getMinGrowthValueToFight()) {
                double damages = (1- ((double) entity.getGrowth() / totalMass)) * entity.getGrowth(); // Calculate damage based on growth relative to total mass
                damages /= 2; // Reduce damage by half to prevent excessive damage
                entity.setGrowth((int) Math.max(0, entity.getGrowth() - damages)); // Apply damage, ensuring growth doesn't go negative
            }
        }

    }


//override methods

}
