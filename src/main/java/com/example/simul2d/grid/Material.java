package com.example.simul2d.grid;

import java.io.Serializable;

/**
 * Material types used to describe cell substrates.
 *
 * <p>Each material has an associated vulnerability factor used by the
 * propagation system and a display color expressed as a hex string.
 */
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

    /**
     * Parse a material name (case-insensitive) into a {@link Material} value.
     *
     * @param text the textual name to parse
     * @return the matching Material or {@code null} if none matches
     */
    public static Material fromString(String text) {
        if (text == null) return null;

        String t = text.trim();

        for (Material m : values()) {
            if (m.name().equalsIgnoreCase(t)) {
                return m;
            }
        }
        return null;
    }

    /** the vulnerability factor used by propagation calculations.
     *
     * @return vulnerability multiplier (non-negative)
     */
    public double getVulnerability() {
        return vunerability;
    }

    /**
     * Returns the display color for this material as a hexadecimal string.
     *
     * @return color in the form "#RRGGBB"
     */
    public String getColorHex() {
        return colorHex;
    }
}

