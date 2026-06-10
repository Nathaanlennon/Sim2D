package com.example.simul2d.Entities.Mold;
import com.example.simul2d.grid.Vec2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A {@link Mold} implementation with a low growth rate but fast propagation. This class
 * represents a slower-growing mold variant used in the simulation.
 */
public abstract class CircularMold extends Mold {



    public static final List<CoorWeight> distribution = createCircularDistribution();


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
     * Constructs a CircularMold with an initial growth of 0 and a growth rate
     * of 2 units per growth step.
     */
    public CircularMold(int initialGrowth, int growthRate, int minimumGrowth, double propagationRate) {
        super(initialGrowth, growthRate, minimumGrowth, propagationRate); // Call the parent constructor with initial growth and growth rate and minimum growth value for propagation
    }


    @Override
    public List<CoorWeight> getPropagationDistributionList() {
        return distribution;
    }


}

