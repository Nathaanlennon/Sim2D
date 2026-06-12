package com.example.simul2d.Entities.Mold;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.example.simul2d.Entities.CoorWeight;
import com.example.simul2d.Entities.Entities;
import com.example.simul2d.grid.Vec2;

public abstract class AxialMold extends Mold {

    public static final List<CoorWeight> distribution = createAxialDistribution();

    public AxialMold(int initialGrowth, int growthRate, int minimumGrowth, double propagationRate, Entities entityType) {
        super(initialGrowth, growthRate, minimumGrowth, propagationRate, entityType); // Call the parent constructor with initial growth and growth rate and minimum growth value for propagation
    }

private static List<CoorWeight> createAxialDistribution() {
    List<CoorWeight> targets = new ArrayList<>();
    //generate coordinates and weights for axial distribution from -6 to 6, with a Manhattan distance between 3 and 6, and a weight that decreases with distance
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


    @Override
    public List<CoorWeight> getPropagationDistributionList() {
        return distribution;
    }



    
}
