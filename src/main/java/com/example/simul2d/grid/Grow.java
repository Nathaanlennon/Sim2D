package com.example.simul2d.grid;

/**
 * Trait interface for objects that can grow. Implementations should
 * update internal state to reflect one growth step when {@link
 * #grow()} is called.
 */
public interface Grow {

    /**
     * Apply one growth step to the implementing object.
     */
    void grow();

}