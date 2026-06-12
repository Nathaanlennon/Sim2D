package com.example.simul2d.Entities.Mold;


import java.util.List;
import java.util.Objects;

import com.example.simul2d.Entities.CoorWeight;
import com.example.simul2d.Entities.Entities;
import com.example.simul2d.grid.Cell;

public class DividedMold1 extends ProximalMold {
    
    private static final int MAX_ABSORPTION = 100; // Maximum growth units that can be absorbed during propagation
    public DividedMold1() {
        super(0, 3, 100, 0.4, Entities.DIVIDED_MOLD1); // Call the parent constructor with initial growth and growth rate and minimum growth value for propagation
    }

    @Override
    public List<CoorWeight> getPropagationDistributionList() {
        return distribution;
    }

    @Override
    public String getColorHex() {
        return getEntityType().getColorHex();
    }

    @Override
    public void propagateTo(Cell targetCell) {
        Objects.requireNonNull(targetCell, "targetCell must not be null");

        if (targetCell.getEntities().containsKey(this.getEntityType())) {
            int available = Math.max(0, targetCell.getCapacity() - targetCell.getTotalGrowthOnCell());
            int toAbsorb = Math.min(this.getGrowth(), Math.min(available, MAX_ABSORPTION)); // Absorb up to 50 units of growth, or as much as available

            this.setGrowth(this.getGrowth() - toAbsorb);
            if (toAbsorb > 0) {
                DividedMold1 existing = (DividedMold1) targetCell.getEntities().get(this.getEntityType());
                existing.setGrowth(existing.getGrowth() + toAbsorb);
                targetCell.updateTotalGrowthOnCell();
            }

        } else {
            int halfGrowth = Math.max(1, this.getGrowth() / 2);
            DividedMold1 newMold = new DividedMold1();
            newMold.setGrowth(halfGrowth);
            this.setGrowth(this.getGrowth() - halfGrowth);
            targetCell.addEntity(newMold);
            targetCell.updateTotalGrowthOnCell();
            
        }
    }

    @Override
    public String toString() {
        return "S"; // Return a string representation for visualization (e.g., "D" for divided mold)
    }

}
