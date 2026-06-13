package com.example.simul2d.Entities.Mold;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.example.simul2d.Entities.CoorWeight;
import com.example.simul2d.Entities.Entities;
import com.example.simul2d.grid.Vec2;

/**
 * An abstract mold whose propagation pattern is axial (only along the cardinal axes).
 * <p>
 * The propagation targets are generated once as a static list of weighted coordinates.
 * They satisfy a Manhattan distance between 3 and 6 inclusive, and only include points
 * lying on the horizontal or vertical axis (dx == 0 or dy == 0). The weight decreases
 * as the distance increases (weight = 7 - distance).
 * </p>
 *
 * @see Mold
 */
public abstract class AxialMold extends Mold {

    /**
     * The static distribution of propagation targets for all axial molds.
     * This list is unmodifiable and sorted by weight in descending order.
     */
    public static final List<CoorWeight> distribution = createAxialDistribution();

    /**
     * Constructs an {@code AxialMold} with the given growth parameters and entity type.
     *
     * @param initialGrowth   the initial growth value
     * @param growthRate      the rate at which growth increases per step
     * @param minimumGrowth   the minimum growth required to propagate
     * @param propagationRate the base probability or factor for propagation
     * @param entityType      the type of entity this mold represents
     * @param ageDeathFactor  factor influencing death probability based on age
     * @param ageDeathMax     maximum age after which death probability is capped or becomes certain
     */
    public AxialMold(int initialGrowth, int growthRate, int minimumGrowth, double propagationRate,
                     Entities entityType, double ageDeathFactor, double ageDeathMax) {
        super(initialGrowth, growthRate, minimumGrowth, propagationRate, entityType, ageDeathFactor, ageDeathMax);
    }

    /**
     * Creates and returns the axial distribution of propagation targets.
     * <p>
     * The method iterates over dx and dy from -6 to 6. It keeps only positions whose
     * Manhattan distance is between 3 and 6 (inclusive) and that lie on an axis
     * (dx == 0 or dy == 0). Each target is assigned a weight equal to {@code 7 - distance}.
     * Finally, the list is sorted by weight in descending order and wrapped into an
     * unmodifiable list.
     * </p>
     *
     * @return an unmodifiable list of {@link CoorWeight} objects sorted by weight descending
     */
    private static List<CoorWeight> createAxialDistribution() {
        List<CoorWeight> targets = new ArrayList<>();
        // generate coordinates and weights for axial distribution from -6 to 6,
        // with a Manhattan distance between 3 and 6, and a weight that decreases with distance
        for (int dx = -6; dx <= 6; dx++) {
            for (int dy = -6; dy <= 6; dy++) {
                int dist = Math.abs(dx) + Math.abs(dy);
                if (dist >= 3 && dist <= 6) {
                    if (dx == 0 || dy == 0) {
                        int weight = 7 - dist;
                        targets.add(new CoorWeight(new Vec2(dx, dy), weight));
                    }
                }
            }
        }
        // Sort targets by weight in descending order (optional, but can be useful for certain selection strategies)
        targets.sort((a, b) -> Integer.compare(b.weight(), a.weight()));
        return Collections.unmodifiableList(targets);
    }

    /**
     * Returns the propagation distribution list for this axial mold.
     * <p>
     * The returned list is the static, unmodifiable list of weighted axial targets
     * shared across all instances.
     * </p>
     *
     * @return the axial propagation distribution
     */
    @Override
    public List<CoorWeight> getPropagationDistributionList() {
        return distribution;
    }
}