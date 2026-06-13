package com.example.simul2d.Entities;



public interface Displayable {

    /** Returns the display size used for rendering order (larger = drawn on top). */
    double getDisplaySize();

    /** Returns the entity color as a hex string (e.g. "#RRGGBB"). */
    String getColorHex();

    /** Returns the opacity in range [0.0, 1.0]. */
    double getOpacity();

}