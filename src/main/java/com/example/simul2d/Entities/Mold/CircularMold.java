package com.example.simul2d.Entities.Mold;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.example.simul2d.Entities.CoorWeight;
import com.example.simul2d.Entities.Entities;
import com.example.simul2d.grid.Vec2;

/**
 * An abstract mold whose propagation pattern is circular (all directions within
 * Manhattan distance 1 to 3). Weights are assigned based on distance:
 * distance 1 → weight 8, distance 2 → weight 7, distance 3 → weight 1.
 * <p>
 * The static distribution list is shared among all subclasses. Concrete
 * implementations only need to define their specific parameters.
 * </p>
 *
 * @see Mold
 */
public abstract class CircularMold extends Mold {

    /**
     * The static, unmodifiable list of weighted propagation targets for all
     * circular molds, sorted by weight descending.
     */
    public static final List<CoorWeight> distribution = createCircularDistribution();

    /**
     * Constructs a {@code CircularMold} with the given growth parameters and entity
     * type.
     *
     * @param initialGrowth   initial growth value
     * @param growthRate      growth increment per step
     * @param minimumGrowth   minimum growth required for propagation
     * @param propagationRate base probability or factor for propagation
     * @param entityType      the type of entity this mold represents
     * @param ageDeathFactor  factor affecting death probability based on age
     * @param ageDeathMax     maximum age after which death probability is capped
     */
    public CircularMold(int initialGrowth, int growthRate, int minimumGrowth, double propagationRate,
                        Entities entityType, double ageDeathFactor, double ageDeathMax) {
        super(initialGrowth, growthRate, minimumGrowth, propagationRate, entityType, ageDeathFactor, ageDeathMax);
    }

    /**
     * Creates and returns the circular distribution of propagation targets.
     * <p>
     * The method iterates over dx and dy from -3 to 3. It keeps positions whose
     * Manhattan distance is between 1 and 3 (inclusive). Each target receives a
     * weight depending on the distance:
     * <ul>
     *   <li>distance 1 → 8</li>
     *   <li>distance 2 → 7</li>
     *   <li>distance 3 → 1</li>
     * </ul>
     * The list is sorted by weight in descending order and then wrapped into an
     * unmodifiable list.
     * </p>
     *
     * @return an unmodifiable list of {@link CoorWeight} sorted by weight
     *         descending
     */
    private static List<CoorWeight> createCircularDistribution() {
        List<CoorWeight> targets = new ArrayList<>();
        for (int dx = -3; dx <= 3; dx++) {
            for (int dy = -3; dy <= 3; dy++) {
                int dist = Math.abs(dx) + Math.abs(dy);
                if (dist >= 1 && dist <= 3) {
                    int weight = switch (dist) {
                        case 1 -> 8;
                        case 2 -> 7;
                        case 3 -> 1;
                        default -> 0;
                    };
                    targets.add(new CoorWeight(new Vec2(dx, dy), weight));
                }
            }
        }
        targets.sort((a, b) -> Integer.compare(b.weight(), a.weight()));
        return Collections.unmodifiableList(targets);
    }

    /**
     * <p>
     * The returned list is the static, unmodifiable list of weighted circular
     * targets shared across all instances.
     * </p>
     *
     * @return the circular propagation distribution
     */
    @Override
    public List<CoorWeight> getPropagationDistributionList() {
        return distribution;
    }
}