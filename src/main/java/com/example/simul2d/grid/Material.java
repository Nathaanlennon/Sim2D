package com.example.simul2d.grid;


public enum Material {
    WOOD(0.6, "#8B5A2B"),
    CONCRETE(0.1, "#808080"),
    PLASTER(0.4, "#F5F5DC"),
    EMPTY(0, "#FFFFFF");

    private final double vunerability;
    private final String colorHex;

    Material(double vunerability, String colorHex) {
        if (vunerability < 0 || vunerability > 1) {
            throw new IllegalArgumentException("Vulnerability must be between 0 and 1");
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

