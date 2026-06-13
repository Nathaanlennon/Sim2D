package com.example.simul2d.Entities.Mold;

import java.util.List;

import com.example.simul2d.Entities.CoorWeight;
import com.example.simul2d.Entities.Entities;
import com.example.simul2d.grid.Cell;

/**
 * A concrete axial mold type (AxialMold1) with specific growth and death parameters.
 * <p>
 * This mold uses the default axial propagation distribution but is tuned to live
 * significantly longer by lowering the age‑death factors.
 * </p>
 *
 * @see AxialMold
 */
public class AxialMold1 extends AxialMold {

    /**
     * Constructs an {@code AxialMold1} with parameters that extend its lifespan.
     * <ul>
     *   <li>initial growth: 0</li>
     *   <li>growth rate: 3</li>
     *   <li>minimum growth for propagation: 100</li>
     *   <li>propagation rate: 0.4</li>
     *   <li>entity type: {@link Entities#AXIAL_MOLD1}</li>
     *   <li>age death factor: 0.0002 (low, to reduce age‑based death)</li>
     *   <li>age death max: 0.4</li>
     * </ul>
     */
    public AxialMold1() {
        // Further reduced age-death parameters so molds live significantly longer
        super(0, 3, 100, 0.4, Entities.AXIAL_MOLD1, 0.0002, 0.4);
    }

    /**
     * Returns the shared axial propagation distribution.
     *
     * @return the static list of weighted axial coordinates
     */
    @Override
    public List<CoorWeight> getPropagationDistributionList() {
        return distribution;
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
     * Returns a single‑character string used for text‑based visualization.
     *
     * @return the string {@code "X"}
     */
    @Override
    public String toString() {
        return "X";
    }

    /**
     * Propagates this mold to the given target cell by adding a new {@code AxialMold1}
     * instance to that cell.
     *
     * @param target the cell that will receive a new mold of this type
     */
    @Override
    public void propagateTo(Cell target) {
        target.addEntity(new AxialMold1());
    }
}