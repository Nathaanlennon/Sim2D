package com.example.simul2d.Entities.Mold;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.example.simul2d.Entities.CoorWeight;
import com.example.simul2d.Entities.Entities;
import com.example.simul2d.grid.Vec2;

/**
 * An abstract mold whose propagation targets are the immediate neighbours
 * (Manhattan distance 1 or 2). Cardinal directions (distance 1) are given
 * a weight of 8, while diagonal directions (distance 2) are given a weight
 * of 5. The static distribution list is shared among all subclasses.
 *
 * @see Mold
 */
public abstract class ProximalMold extends Mold {

    /**
     * The static, unmodifiable list of weighted propagation targets for all
     * proximal molds. Sorted by weight in descending order.
     */
    public static final List<CoorWeight> distribution = createProximalDistribution();

    /**
     * Constructs a {@code ProximalMold} with the specified growth parameters
     * and entity type.
     *
     * @param initialGrowth   initial growth value
     * @param growthRate      growth increment per step
     * @param minimumGrowth   minimum growth required for propagation
     * @param propagationRate base probability or factor for propagation
     * @param entityType      the type of entity this mold represents
     * @param ageDeathFactor  factor affecting death probability based on age
     * @param ageDeathMax     maximum age after which death probability is capped
     */
    public ProximalMold(int initialGrowth, int growthRate, int minimumGrowth,
                        double propagationRate, Entities entityType,
                        double ageDeathFactor, double ageDeathMax) {
        super(initialGrowth, growthRate, minimumGrowth, propagationRate,
              entityType, ageDeathFactor, ageDeathMax);
    }

    /**
     * Creates the proximal distribution of propagation targets.
     * <p>
     * The method considers all dx, dy ∈ {-1, 0, 1} except (0,0). Weights are
     * assigned based on Manhattan distance:
     * <ul>
     *   <li>distance 1 (cardinal) → weight 8</li>
     *   <li>distance 2 (diagonal) → weight 5</li>
     * </ul>
     * The list is sorted by weight descending and wrapped into an unmodifiable list.
     * </p>
     *
     * @return an unmodifiable list of {@link CoorWeight} sorted by weight descending
     */
    private static List<CoorWeight> createProximalDistribution() {
        List<CoorWeight> targets = new ArrayList<>();
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (dx != 0 || dy != 0) {
                    int weight = switch (Math.abs(dx) + Math.abs(dy)) {
                        case 1 -> 8; // Cardinal directions
                        case 2 -> 5; // Diagonal directions
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
     * The list is static and shared across all instances of proximal molds.
     * </p>
     *
     * @return the proximal propagation distribution
     */
    @Override
    public List<CoorWeight> getPropagationDistributionList() {
        return distribution;
    }
}