package com.example.simul2d.Entities;

import com.example.simul2d.Entities.Mold.AxialMold1;
import com.example.simul2d.Entities.Mold.CircMold1;
import com.example.simul2d.Entities.Mold.DividedMold1;

public enum Entities {
    AXIAL_MOLD1("#33A1FF"),
    CIRC_MOLD1("#FF5733"),
    DIVIDED_MOLD1("#9B59B6");

    private final String colorHex;

    Entities(String colorHex) {
        this.colorHex = colorHex;
    }

    public String getColorHex() {
        return colorHex;
    }

    public Entity createEntity() {
        return switch (this) {
            case AXIAL_MOLD1 -> new AxialMold1();
            case CIRC_MOLD1 -> new CircMold1();
            case DIVIDED_MOLD1 -> new DividedMold1();
        };
    }

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
