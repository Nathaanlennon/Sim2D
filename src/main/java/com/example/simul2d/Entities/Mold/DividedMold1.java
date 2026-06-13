package com.example.simul2d.Entities.Mold;

import java.util.List;
import java.util.Objects;

import com.example.simul2d.Entities.CoorWeight;
import com.example.simul2d.Entities.Entities;
import com.example.simul2d.grid.Cell;

/**
 * A concrete mold that propagates by splitting its growth between itself and
 * the target cell. If the target cell already contains a {@code DividedMold1},
 * the propagation instead absorbs up to {@value #MAX_ABSORPTION} growth units
 * from the source mold and transfers them to the existing mold.
 * <p>
 * This mold is a subtype of {@link ProximalMold} and uses its default
 * proximal propagation distribution.
 * </p>
 *
 * @see ProximalMold
 */
public class DividedMold1 extends ProximalMold {

    /** Maximum growth units that can be absorbed during propagation to an already occupied cell. */
    private static final int MAX_ABSORPTION = 100;

    /**
     * Constructs a {@code DividedMold1} with parameters tuned for very slow ageing.
     * <ul>
     *   <li>initial growth: 0</li>
     *   <li>growth rate: 3</li>
     *   <li>minimum growth for propagation: 100</li>
     *   <li>propagation rate: 0.4</li>
     *   <li>entity type: {@link Entities#DIVIDED_MOLD1}</li>
     *   <li>age death factor: 0.00007 (extremely low)</li>
     *   <li>age death max: 0.5</li>
     * </ul>
     */
    public DividedMold1() {
        // Divided molds age very slowly (tuned down further)
        super(0, 3, 100, 0.4, Entities.DIVIDED_MOLD1, 0.00007, 0.5);
    }

    /**
     * <p>
     * The returned list is the static, unmodifiable list of weighted proximal
     * targets shared across all instances.
     * </p>
     *
     * @return the proximal propagation distribution
     */
    @Override
    public List<CoorWeight> getPropagationDistributionList() {
        return distribution;
    }

    /**
     *
     * @return the hexadecimal color string (e.g., {@code "#RRGGBB"})
     */
    @Override
    public String getColorHex() {
        return getEntityType().getColorHex();
    }

    /**
     * Propagates this mold to the specified target cell.
     * <p>
     * If the target cell already contains a {@code DividedMold1}, the propagation
     * does not create a new mold; instead it transfers up to
     * {@value #MAX_ABSORPTION} growth units from this mold to the existing one,
     * provided the cell has remaining capacity.
     * </p>
     * <p>
     * If the target cell does not contain this mold type, a new {@code DividedMold1}
     * is created and half of this mold's growth (at least 1 unit) is transferred to
     * the new mold. The new mold is then added to the target cell.
     * </p>
     *
     * @param targetCell the cell to propagate into; must not be null
     * @throws NullPointerException if {@code targetCell} is null
     */
    @Override
    public void propagateTo(Cell targetCell) {
        Objects.requireNonNull(targetCell, "targetCell must not be null");

        if (targetCell.getEntities().containsKey(this.getEntityType())) {
            int available = Math.max(0, targetCell.getCapacity() - targetCell.getTotalGrowthOnCell());
            int toAbsorb = Math.min(this.getGrowth(), Math.min(available, MAX_ABSORPTION));

            this.setGrowth(this.getGrowth() - toAbsorb);
            if (toAbsorb > 0) {
                DividedMold1 existing = (DividedMold1) targetCell.getEntities().get(this.getEntityType());
                existing.setGrowth(existing.getGrowth() + toAbsorb);
                targetCell.updateTotalGrowthOnCell();
            }

        } else {
            int halfGrowth = Math.max(1, this.getGrowth() / 2);
            DividedMold1 newMold = new DividedMold1();
            newMold.setGrowth(halfGrowth);
            this.setGrowth(this.getGrowth() - halfGrowth);
            targetCell.addEntity(newMold);
            targetCell.updateTotalGrowthOnCell();
        }
    }

    /**
     *
     * @return the string {@code "S"}
     */
    @Override
    public String toString() {
        return "S"; // Return a string representation for visualization (e.g., "D" for divided mold)
    }
}