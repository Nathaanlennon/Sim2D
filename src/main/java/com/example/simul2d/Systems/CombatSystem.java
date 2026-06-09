package com.example.simul2d.Systems;

import com.example.simul2d.Entities.Entity;
import com.example.simul2d.grid.Cell;

public class CombatSystem {

    //constructors
    public CombatSystem() {
    }

    //set methods
//get methods
//private methods
//public methods
    public void inCellCombat(Cell cell) {
        if (cell == null) return;

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
