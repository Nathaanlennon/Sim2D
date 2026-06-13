package com.example.simul2d.JavaFX;

import java.util.Map;

import com.example.simul2d.Entities.Entities;
/**
 * Interface for controllers that receive aggregated graph values from the simulation.
 */
public interface NeedsGraphValues {

    /**
     * Called periodically by the simulation to provide values to be plotted.
     *
     * @param timeStep current simulation timestep
     * @param populationsWeight map of entity type → aggregated growth/population
     * @param infectedCells map of entity type → number of infected cells
     */
    void graphStep(double timeStep, Map<Entities, Integer> populationsWeight, Map<Entities, Integer> infectedCells);

}
