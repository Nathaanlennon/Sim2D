package com.example.simul2d.Entities.Mold;
import com.example.simul2d.grid.Vec2;

import java.util.*;


public abstract class ProximalMold extends Mold {
    

    public static final List<CoorWeight> distribution = createProximalDistribution();

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

    
    public ProximalMold(int initialGrowth, int growthRate, int minimumGrowth, double propagationRate) {
        super(initialGrowth, growthRate, minimumGrowth, propagationRate); // Call the parent constructor with initial growth and growth rate and minimum growth value for propagation
    }


    @Override
    public List<CoorWeight> getPropagationDistributionList() {
        return distribution;
    }


}
