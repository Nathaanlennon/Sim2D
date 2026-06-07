package com.example.simul2d.Entities.Mold;
import com.example.simul2d.grid.Cell;

import java.util.List;


/**
 * A simple mold that grows slowly and has a low probability of propagation.
 * This class extends the base {@link CircularMold} and provides specific
 * parameters for growth and propagation behavior.
 */
public class CircMold1 extends CircularMold {

    public CircMold1() {
        super(0, 2, 50, 0.5); // Call the parent constructor with initial growth and growth rate and minimum growth value for propagation
    }

    @Override
    public void propagateTo(Cell target) {
        target.addEntity(new CircMold1());        
    }
    
    @Override
    public String toString() {
        return "O"; // Return a string representation for visualization (e.g., "O" for slow mold)
    }
}
