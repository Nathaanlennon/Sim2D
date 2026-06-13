package com.example.simul2d.Entities;


/**
 * Interface for those who are to be displayed (means printed)
 */
public interface Displayable {

    /** @return the display size used for rendering order (larger = drawn on top). */
    double getDisplaySize();

    /** @return the entity color as a hex string (e.g. "#RRGGBB"). */
    String getColorHex();

    /** @return the opacity in range [0.0, 1.0]. */
    double getOpacity();

}