package com.example.simul2d.grid;

import java.io.Serializable;

public enum Material implements Serializable {
    WOOD(0.6, "#8B5A2B"),
    CONCRETE(0.1, "#808080"),
    PLASTER(0.4, "#F5F5DC"),
    GOODSTUFF(1.5, "#00FF00"),
    EMPTY(0.5, "#FFFFFF");

    private final double vunerability;
    private final String colorHex;

    Material(double vunerability, String colorHex) {
        if (vunerability < 0) {
            throw new IllegalArgumentException("Vulnerability must be non-negative");
        }
        this.vunerability = vunerability;
        this.colorHex = colorHex;
    }

    public double getVulnerability() {
        return vunerability;
    }

    public String getColorHex() {
        return colorHex;
    }
}

