package com.example.simul2d.Entities.Mold;


import java.util.List;

import com.example.simul2d.grid.Cell;

public class DividedMold1 extends ProximalMold {
    
    private static final String BASE_COLOR = "#9B59B6";

    public DividedMold1() {
        super(0, 3, 100, 0.4); // Call the parent constructor with initial growth and growth rate and minimum growth value for propagation
    }

    @Override
    public List<CoorWeight> getPropagationDistributionList() {
        return distribution;
    }

    @Override
    public String getColorHex() {
        return BASE_COLOR; // Return a fixed color for this mold variant
    }

    @Override
    public void propagateTo(Cell targetCell) {
        int halfGrowth = Math.max(1, this.getGrowth() / 2);
        DividedMold1 newMold = new DividedMold1();
        newMold.setGrowth(halfGrowth);

        int absorbed = targetCell.addEntity(newMold);
        int surplus = halfGrowth - absorbed;

        // Reduce the growth of the current mold by the amount propagated, accounting for any surplus that couldn't be absorbed by the target cell
        this.setGrowth(this.getGrowth() - halfGrowth + surplus);
    }

    @Override
    public String toString() {
        return "S"; // Return a string representation for visualization (e.g., "D" for divided mold)
    }

}
