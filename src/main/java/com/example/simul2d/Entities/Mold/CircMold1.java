package com.example.simul2d.Entities.Mold;
import com.example.simul2d.Entities.Entities;
import com.example.simul2d.grid.Cell;


/**
 * A simple mold that grows slowly and has a low probability of propagation.
 * This class extends the base {@link CircularMold} and provides specific
 * parameters for growth and propagation behavior.
 */
public class CircMold1 extends CircularMold {

    public CircMold1() {
        // Slower ageing for circular mold (tuned down)
        super(0, 2, 50, 0.5, Entities.CIRC_MOLD1, 0.00012, 0.45);
    }

    @Override
    public void propagateTo(Cell target) {
        target.addEntity(new CircMold1());        
    }
    
    @Override
    public String getColorHex() {
        return getEntityType().getColorHex();
    }


    @Override
    public String toString() {
        return "O"; // Return a string representation for visualization (e.g., "O" for slow mold)
    }
}
