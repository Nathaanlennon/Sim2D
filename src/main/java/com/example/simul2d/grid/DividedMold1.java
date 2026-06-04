package com.example.simul2d.grid;


import java.util.List;

public class DividedMold1 extends ProximalMold {
    

    public DividedMold1() {
        super(0, 3, 100, 0.4); // Call the parent constructor with initial growth and growth rate and minimum growth value for propagation
    }

    @Override
    public List<CoorWeight> getPropagationDistributionList() {
        return distribution;
    }

    @Override
    public void propagateTo(Cell targetCell) {
        // Create a new DividedMold with half the growth of the original mold and add it to the target cell
        DividedMold1 newMold = new DividedMold1();
        newMold.setGrowth(this.getGrowth() / 2);
        targetCell.addEntity(newMold);
        this.setGrowth(this.getGrowth()/2); // Reduce the growth of the original mold by half to simulate division
        
        
    }

    @Override
    public String toString() {
        return "S"; // Return a string representation for visualization (e.g., "D" for divided mold)
    }

}
