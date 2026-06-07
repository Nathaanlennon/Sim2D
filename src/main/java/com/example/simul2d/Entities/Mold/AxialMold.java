package com.example.simul2d.Entities.Mold;

import com.example.simul2d.grid.Vec2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class AxialMold extends Mold {

    public static final List<CoorWeight> distribution = createAxialDistribution();

    public AxialMold(int initialGrowth, int growthRate, int minimumGrowth, double propagationRate) {
        super(initialGrowth, growthRate, minimumGrowth, propagationRate); // Call the parent constructor with initial growth and growth rate and minimum growth value for propagation
    }

    private static List<CoorWeight> createAxialDistribution() {

        List<CoorWeight> targets = new ArrayList<>();
        for (int dx = -3; dx <= 3; dx++) {
            for (int dy = -3; dy <= 3; dy++) {
                int dist = Math.abs(dx) + Math.abs(dy);
                if (dist >= 1 && dist <= 3) {
                    int weight;
                    if (dx == 0 || dy == 0) {
                        weight = switch (dist) {
                            case 1 -> 5;
                            case 2 -> 4;
                            case 3 -> 3;
                            default -> 0;
                        };
                    } else {
                        weight = 1;
                    }
                    targets.add(new CoorWeight(new Vec2(dx, dy), weight));
                }
            }
        }
        targets.sort((a, b) -> Integer.compare(b.weight(), a.weight()));
        return Collections.unmodifiableList(targets);
    }

    @Override
    public List<CoorWeight> getPropagationDistributionList() {
        return distribution;
    }



    
}
