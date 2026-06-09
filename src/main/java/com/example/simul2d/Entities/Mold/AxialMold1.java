package com.example.simul2d.Entities.Mold;
import java.util.List;

import com.example.simul2d.grid.Cell;


public class AxialMold1 extends AxialMold {
    
    private static final String BASE_COLOR = "#33A1FF";

    public AxialMold1() {
        super(0, 2, 100, 0.4); // Call the parent constructor with initial growth and growth rate and minimum growth value for propagation
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
    public String toString() {
        return "X"; // Return a string representation for visualization (e.g., "X" for axial mold)
    }
    

    @Override
    public void propagateTo(Cell target) {
        target.addEntity(new AxialMold1());        
    }
    
    
}
