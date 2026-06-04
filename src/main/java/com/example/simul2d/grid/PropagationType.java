package com.example.simul2d.grid;
import java.util.*;

public enum PropagationType {
    CIRCULAR,
    AXIAL,
    PROXIMAL;

    private static final Map<PropagationType, List<CoorWeight>> DISTRIBUTIONS;

    static {
        Map<PropagationType, List<CoorWeight>> map = new EnumMap<>(PropagationType.class);
        map.put(CIRCULAR, createCircularDistribution());
        map.put(AXIAL, createAxialDistribution());
        map.put(PROXIMAL, createProximalDistribution());
        DISTRIBUTIONS = Collections.unmodifiableMap(map);
    }

    public List<CoorWeight> getDistribution() {
        return DISTRIBUTIONS.get(this);
    }

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

    private static List<CoorWeight> createProximalDistribution() {
        List<CoorWeight> targets = new ArrayList<>();
        //add the 8 adjacent cells with a weight of 12
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (dx != 0 || dy != 0) {
                    targets.add(new CoorWeight(new Vec2(dx, dy), 12));
                }
            }
        }


        targets.sort((a, b) -> Integer.compare(b.weight(), a.weight()));
        return Collections.unmodifiableList(targets);
    }
}