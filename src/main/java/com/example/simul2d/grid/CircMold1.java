package com.example.simul2d.grid;
import java.util.List;
/**
 * The {@code Mold} class represents a mold entity that can grow and propagate
 * within a cell. It serves as a base class for specific mold types, providing
 * common properties and behaviors related to growth and propagation.
 *
 * <p>Concrete subclasses (e.g., {@link CircularMold} and {@link FastMold}) can
 * override the default growth behavior and provide specific implementations of
 * the {@link #toString()} method for visualization purposes.
 */
public class CircMold1 extends CircularMold {

    public CircMold1() {
        super(0, 2, 100, 0.5); // Call the parent constructor with initial growth and growth rate and minimum growth value for propagation
    }

    @Override
    public List<CoorWeight> getPropagationDistributionList() {
        return distribution;
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
