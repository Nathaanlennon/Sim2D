package com.example.simul2d.Entities;

import com.example.simul2d.Entities.Mold.AxialMold1;
import com.example.simul2d.Entities.Mold.CircMold1;
import com.example.simul2d.Entities.Mold.DividedMold1;
import com.example.simul2d.Entities.Mold.MoldTest;


/**
 * Enumeration of available entity types used by the simulation.
 *
 * <p>Each enum constant carries a default display color and can produce a
 * new instance via {@link #createEntity()}.
 */
public enum Entities {
    AXIAL_MOLD1("#33A1FF"),
    CIRC_MOLD1("#FF5733"),
    MOLD_TEST("#DDDDD0"),
    DIVIDED_MOLD1("#9B59B6");

    private final String colorHex;

    Entities(String colorHex) {
        this.colorHex = colorHex;
    }

    /** Returns the default display color for the entity type as a hex string. */
    public String getColorHex() {
        return colorHex;
    }

    /**
     * Creates a new instance of the concrete `Entity` associated with this
     * enum constant.
     *
     * @return a fresh `Entity` instance of the appropriate subtype
     */
    public Entity createEntity() {
        return switch (this) {
            case AXIAL_MOLD1 -> new AxialMold1();
            case CIRC_MOLD1 -> new CircMold1();
            case DIVIDED_MOLD1 -> new DividedMold1();
            case MOLD_TEST -> new MoldTest();
        };
    }

    /**
     * Parses a case-insensitive enum constant name into an `Entities` value.
     *
     * @param text the text to parse
     * @return the matching `Entities` constant or {@code null} if none matches
     */
    public static Entities fromString(String text) {
        if (text == null) return null;

        String t = text.trim();

        for (Entities e : values()) {
            if (e.name().equalsIgnoreCase(t)) {
                return e;
            }
        }
        return null;
    }
}
