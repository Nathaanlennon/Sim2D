package com.example.simul2d.grid;


public enum Material {
    WOOD(0.6),
    CONCRETE(0.1),
    PLASTER(0.4),
    EMPTY(0);

    private final double vunerability;

    Material(double vunerability) {
        if (vunerability < 0 || vunerability > 1) {
            throw new IllegalArgumentException("Vulnerability must be between 0 and 1");
        }
        this.vunerability = vunerability;
    }

    public double getVulnerability() {
        return vunerability;
    }
}