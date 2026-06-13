package com.example.simul2d.Entities.Mold;
import com.example.simul2d.Entities.Entities;
import com.example.simul2d.grid.Cell;

/**
 * A simple mold that grows slowly and has a low probability of propagation.
 * This class extends the base {@link CircularMold} and provides specific
 * parameters for growth and propagation behavior.
 *
 * @see CircularMold
 */
public class CircMold1 extends CircularMold {

    /**
     * Constructs a {@code CircMold1} with parameters that slow down ageing.
     * <ul>
     *   <li>initial growth: 0</li>
     *   <li>growth rate: 2</li>
     *   <li>minimum growth for propagation: 50</li>
     *   <li>propagation rate: 0.5</li>
     *   <li>entity type: {@link Entities#CIRC_MOLD1}</li>
     *   <li>age death factor: 0.00012 (very low, so molds live longer)</li>
     *   <li>age death max: 0.45</li>
     * </ul>
     */
    public CircMold1() {
        // Slower ageing for circular mold (tuned down)
        super(0, 2, 50, 0.5, Entities.CIRC_MOLD1, 0.00012, 0.45);
    }

    /**
     * Propagates this mold to the given target cell by adding a new {@code CircMold1}
     * instance.
     *
     * @param target the cell that will receive the new mold
     */
    @Override
    public void propagateTo(Cell target) {
        target.addEntity(new CircMold1());
    }

    /**
     * Returns the color hex string of the entity type associated with this mold.
     *
     * @return the hexadecimal color string (e.g., {@code "#RRGGBB"})
     */
    @Override
    public String getColorHex() {
        return getEntityType().getColorHex();
    }

    /**
     * Returns a single‑character representation used for text‑based visualization.
     *
     * @return the string {@code "O"}
     */
    @Override
    public String toString() {
        return "O"; // Return a string representation for visualization (e.g., "O" for slow mold)
    }
}