package com.example.simul2d.Entities.Mold;
import java.util.List;

import com.example.simul2d.Entities.CoorWeight;
import com.example.simul2d.Entities.Entities;
import com.example.simul2d.grid.Cell;


public class AxialMold1 extends AxialMold {
    
    public AxialMold1() {
        // Further reduced age-death parameters so molds live significantly longer
        super(0, 3, 100, 0.4, Entities.AXIAL_MOLD1, 0.0002, 0.4);
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
    public String toString() {
        return "X"; // Return a string representation for visualization (e.g., "X" for axial mold)
    }
    

    @Override
    public void propagateTo(Cell target) {
        target.addEntity(new AxialMold1());        
    }
    
    
}
