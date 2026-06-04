package com.example.simul2d.grid;
import java.util.List;


public class AxialMold1 extends AxialMold {
    

    public AxialMold1() {
        super(0, 3, 100, 0.4); // Call the parent constructor with initial growth and growth rate and minimum growth value for propagation
    }

    @Override
    public List<CoorWeight> getPropagationDistributionList() {
        return distribution;
    }

    @Override
    public String toString() {
        return "X"; // Return a string representation for visualization (e.g., "X" for axial mold)
    }

    @Override
    public void propagateTo(Cell target) {
        target.addEntity(new AxialMold1());        
    }
    
    
}
